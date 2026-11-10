// Which blog posts to suggest on which docs page ("Keep learning" at the end
// of a docs page, built by plugins/explore.js).
//
// A post is suggested on a page when the two are about the same thing. That is
// measured from their text, not from where a post happens to link:
//
//   1. Every docs page and every post becomes a weighted bag of words. Words
//      from the title count most, then tags, the description and headings,
//      then the body. Words that appear everywhere ("java", "component") weigh
//      almost nothing; words only a few texts share ("repository", "toast")
//      weigh a lot (TF-IDF).
//   2. The similarity of a page and a post is the cosine of their two bags.
//   3. A post whose title or tags name the page's own topic (the words of the
//      page title, such as "Table" or "Route security") scores higher.
//   4. A post that links the page gets a bonus, larger when the post links few
//      docs pages (it is focused on this one).
//   5. A post is kept only above a minimum score and within reach of the best
//      post for that page. A page with no post about its topic shows none.
//   6. The posts kept for a page are shown newest first.
//
// Release notes are never suggested: they touch many pages in passing and go
// out of date.
//
// The video of a page is chosen the same way from the title and description
// of each video. At most one, and none when no video is about the page.

const MAX_RELATED = 6;
// lowest similarity for a post that does not link the page
const MIN_SIMILARITY = 0.16;
// lowest similarity for a post that links the page
const MIN_SIMILARITY_LINKED = 0.1;
// how much a post gains when its title and tags name the whole page topic
const TOPIC_BONUS = 0.3;
// a post must reach this share of the page's best score
const MIN_SHARE_OF_BEST = 0.4;
// a word found in more than this share of all texts says nothing about a topic
const MAX_DOCUMENT_SHARE = 0.35;

// lowest score for a video to be shown on a page
const MIN_VIDEO_SCORE = 0.3;

const WEIGHTS = { title: 6, tags: 4, description: 3, headings: 3, body: 1 };

const STOP_WORDS = new Set(`a an and are as at be been but by can do does for from had has have how if in into is it its
  more most no not of on or our so such than that the their them then there these they this to up use used using was we
  were what when where which who why will with you your about after all also any before between both each every here
  just like may need only other out over same see should some still through under very want well while would get make
  new one two way work works app apps web webforj java example code page pages guide step let can't don't
  tutorial overview introduction`.split(/\s+/));

function stripMarkup(text) {
  return text
    .replace(/^---\n[\s\S]*?\n---\n/, '')
    .replace(/^(import|export) .*$/gm, ' ')
    .replace(/<!--[\s\S]*?-->/g, ' ')
    .replace(/!\[[^\]]*\]\([^)]*\)/g, ' ')
    .replace(/\]\([^)]*\)/g, '] ')
    .replace(/https?:\/\/\S+/g, ' ')
    .replace(/\{#[^}]*\}/g, ' ')
    .replace(/<[^>]+>/g, ' ');
}

function tokens(text) {
  return (text.toLowerCase().match(/[a-z][a-z0-9]{2,}/g) ?? [])
    .map((word) => (word.length > 4 ? word.replace(/(ies)$/, 'y').replace(/([^s])s$/, '$1') : word))
    .filter((word) => !STOP_WORDS.has(word));
}

function headingsOf(markdown) {
  return [...markdown.matchAll(/^#{1,4}\s+(.+)$/gm)].map((match) => match[1]).join(' ');
}

// { title, description, tags?, markdown } -> Map(word -> weighted count)
function bagOf(text) {
  const markdown = stripMarkup(text.markdown ?? '');
  const fields = {
    title: text.title ?? '',
    tags: (text.tags ?? []).join(' '),
    description: text.description ?? '',
    headings: headingsOf(markdown),
    body: markdown,
  };
  const bag = new Map();
  for (const [field, value] of Object.entries(fields)) {
    for (const word of tokens(value)) {
      bag.set(word, (bag.get(word) ?? 0) + WEIGHTS[field]);
    }
  }
  return bag;
}

function vectors(bags) {
  const seenIn = new Map();
  for (const bag of bags) {
    for (const word of bag.keys()) seenIn.set(word, (seenIn.get(word) ?? 0) + 1);
  }
  const weightOf = (word) => {
    const share = (seenIn.get(word) ?? 0) / bags.length;
    return share === 0 || share > MAX_DOCUMENT_SHARE ? 0 : Math.log(1 / share);
  };
  const list = bags.map((bag) => {
    const vector = new Map();
    let length = 0;
    for (const [word, count] of bag) {
      const weight = weightOf(word);
      if (!weight) continue;
      const value = Math.sqrt(count) * weight;
      vector.set(word, value);
      length += value * value;
    }
    length = Math.sqrt(length) || 1;
    for (const [word, value] of vector) vector.set(word, value / length);
    return vector;
  });
  // a page's own topic word always counts, however common it is ("Button")
  const topicWeightOf = (word) => {
    const share = (seenIn.get(word) ?? 0) / bags.length;
    return share === 0 ? 0 : Math.max(Math.log(1 / share), 0.5);
  };
  return { list, weightOf: topicWeightOf, isSpecific: (word) => weightOf(word) > 0 };
}

// The share of the page's topic that the post names in its title and tags:
// 1 when it names all of it, 0 when none. The topic is the page title, or the
// title plus the docs folder the page sits in, whichever the post names more
// of: "Inspector" under /docs/craftforj/ is also about craftforJ, while
// "Button" under /docs/components/ is about buttons alone.
function topicShare(doc, post, { weightOf, isSpecific }) {
  const named = new Set(tokens(`${post.title ?? ''} ${(post.tags ?? []).join(' ')}`));
  const share = (text) => {
    let total = 0;
    let found = 0;
    for (const word of new Set(tokens(text))) {
      total += weightOf(word);
      if (named.has(word)) found += weightOf(word);
    }
    return total ? found / total : 0;
  };
  // a folder name found all over the site ("components") is not a topic
  const folder = tokens((doc.page ?? '').split('/').slice(-2, -1)[0] ?? '').filter(isSpecific).join(' ');
  const title = doc.title ?? '';
  return Math.max(share(title), folder ? share(`${title} ${folder}`) : 0);
}

function cosine(a, b) {
  const [small, large] = a.size < b.size ? [a, b] : [b, a];
  let sum = 0;
  for (const [word, value] of small) sum += value * (large.get(word) ?? 0);
  return sum;
}

/** The /docs/... pages a post links, without anchors or trailing slashes. */
function docsLinks(markdown) {
  return new Set([...markdown.matchAll(/\]\((\/docs\/[^)\s#?]+)/g)].map((match) => match[1].replace(/\/$/, '')));
}

/**
 * @param docs  [{ page: '/docs/...', title, description, markdown }]
 * @param posts [{ title, description, tags: string[], markdown, date: Date, release: boolean }]
 * @returns {{ [page: string]: { index: number, score: number, similarity: number, linked: boolean }[] }}
 *          the posts to suggest per docs page, best first
 */
function rankRelatedPosts(docs, posts) {
  const { list, ...weights } = vectors([...docs.map(bagOf), ...posts.map(bagOf)]);
  const docVectors = list.slice(0, docs.length);
  const postVectors = list.slice(docs.length);
  const links = posts.map((post) => docsLinks(post.markdown ?? ''));
  const ranked = {};
  docs.forEach((doc, d) => {
    const scored = [];
    posts.forEach((post, p) => {
      if (post.release) return;
      const similarity =
        cosine(docVectors[d], postVectors[p]) + TOPIC_BONUS * topicShare(doc, post, weights);
      const linked = links[p].has(doc.page);
      if (similarity < (linked ? MIN_SIMILARITY_LINKED : MIN_SIMILARITY)) return;
      // a post about this one page links little else: up to +60%
      const bonus = linked ? 0.2 + 0.4 * Math.min(1, 3 / links[p].size) : 0;
      scored.push({ index: p, score: similarity * (1 + bonus), similarity, linked });
    });
    scored.sort((a, b) => b.score - a.score || posts[b.index].date - posts[a.index].date);
    const best = scored[0]?.score ?? 0;
    const kept = scored.filter((entry) => entry.score >= best * MIN_SHARE_OF_BEST).slice(0, MAX_RELATED);
    // the best matches are chosen by score, then shown newest first
    kept.sort((a, b) => posts[b.index].date - posts[a.index].date || b.score - a.score);
    if (kept.length) ranked[doc.page] = kept;
  });
  return ranked;
}

/**
 * @param docs   [{ page, title, description, markdown }]
 * @param posts  the posts of the site: they only steady the word weights
 * @param videos [{ title, description }]
 * @returns {{ [page: string]: number }} the index of the video to show per
 *          docs page; a page without a matching video is left out
 */
function rankRelatedVideos(docs, posts, videos) {
  // YouTube titles end in hashtags ("... | craftforJ #java #webforj")
  const clips = videos.map((video) => ({
    title: video.title.replace(/#\S+/g, ' '),
    markdown: video.description ?? '',
  }));
  const { list, ...weights } = vectors([...docs.map(bagOf), ...posts.map(bagOf), ...clips.map(bagOf)]);
  const clipVectors = list.slice(docs.length + posts.length);
  const ranked = {};
  docs.forEach((doc, d) => {
    let best = -1;
    let bestScore = MIN_VIDEO_SCORE;
    clips.forEach((clip, c) => {
      const score = cosine(list[d], clipVectors[c]) + TOPIC_BONUS * topicShare(doc, clip, weights);
      if (score > bestScore) {
        best = c;
        bestScore = score;
      }
    });
    if (best >= 0) ranked[doc.page] = best;
  });
  return ranked;
}

module.exports = {
  rankRelatedVideos,
  rankRelatedPosts, docsLinks, bagOf, tokens, MAX_RELATED };
