import React, { useEffect, useState } from 'react';
import GitHubIcon from '@mui/icons-material/GitHub';
import BugReportOutlinedIcon from '@mui/icons-material/BugReportOutlined';
import HistoryOutlinedIcon from '@mui/icons-material/HistoryOutlined';
import ArrowForwardIcon from '@mui/icons-material/ArrowForward';
import StarOutlineIcon from '@mui/icons-material/StarOutline';
import styles from './GettingStarted.module.css';

const REPO = 'webforj/webforj';
const CACHE_KEY = 'gs-github-stats';
const CACHE_TTL_MS = 60 * 60 * 1000; // 1 hour

function formatCount(n) {
  if (n == null) return null;
  if (n < 1000) return `${n}`;
  return `${(n / 1000).toFixed(n >= 10000 ? 0 : 1)}k`;
}

/**
 * Pulls live GitHub stats (stars + latest release tag) once on mount and
 * caches them in localStorage for an hour to avoid hitting the 60-req/hr
 * anonymous rate limit. Falls back silently to default labels on failure.
 */
function useGithubStats(repo) {
  const [stats, setStats] = useState(() => {
    if (typeof window === 'undefined') return null;
    try {
      const raw = window.localStorage.getItem(CACHE_KEY);
      if (!raw) return null;
      const parsed = JSON.parse(raw);
      if (Date.now() - parsed.ts > CACHE_TTL_MS) return null;
      return parsed.data;
    } catch {
      return null;
    }
  });

  useEffect(() => {
    if (stats) return;
    let cancelled = false;

    async function load() {
      try {
        const [repoRes, releaseRes] = await Promise.all([
          fetch(`https://api.github.com/repos/${repo}`),
          fetch(`https://api.github.com/repos/${repo}/releases/latest`),
        ]);
        if (!repoRes.ok) return;

        const repoData = await repoRes.json();
        const releaseData = releaseRes.ok ? await releaseRes.json() : null;
        const next = {
          stars: repoData.stargazers_count,
          version: releaseData?.tag_name || null,
        };
        if (cancelled) return;
        setStats(next);
        try {
          window.localStorage.setItem(CACHE_KEY, JSON.stringify({ ts: Date.now(), data: next }));
        } catch {}
      } catch {
        // Rate-limited or offline — silently fall back to default labels.
      }
    }
    load();
    return () => { cancelled = true; };
  }, [repo, stats]);

  return stats;
}

export default function CommunityBand() {
  const stats = useGithubStats(REPO);
  const starCount = formatCount(stats?.stars);

  return (
    <section className={styles.community}>
      <div className={styles.communityLinks}>
        <a
          href={`https://github.com/${REPO}`}
          target="_blank"
          rel="noopener noreferrer"
          className={styles.communityLink}
        >
          <GitHubIcon />
          <span>Star on GitHub</span>
          {starCount && (
            <span className={styles.communityBadge}>
              <StarOutlineIcon style={{ fontSize: 14 }} />
              {starCount}
            </span>
          )}
        </a>
        <a
          href={`https://github.com/${REPO}/issues/new/choose`}
          target="_blank"
          rel="noopener noreferrer"
          className={styles.communityLink}
        >
          <BugReportOutlinedIcon />
          <span>Report an issue</span>
        </a>
        <a
          href={`https://github.com/${REPO}/releases/latest`}
          target="_blank"
          rel="noopener noreferrer"
          className={styles.communityLink}
        >
          <HistoryOutlinedIcon />
          <span>{stats?.version ? `Changelog · ${stats.version}` : 'Read the changelog'}</span>
        </a>
      </div>
      <a
        href="https://github.com/webforj/built-with-webforj"
        target="_blank"
        rel="noopener noreferrer"
        className={styles.communityCta}
      >
        Submit your project
        <ArrowForwardIcon style={{ fontSize: 16 }} />
      </a>
    </section>
  );
}
