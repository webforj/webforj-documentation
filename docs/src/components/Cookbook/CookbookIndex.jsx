import React, { useState, useEffect, useMemo } from 'react';
import useBaseUrl from '@docusaurus/useBaseUrl';
import clsx from 'clsx';
import CookbookCard from './CookbookCard';
import styles from './CookbookIndex.module.css';

/** Sentinel value used for "no filter applied" selects and buttons. */
const ALL = 'all';

const DIFFICULTY_OPTIONS = [
  { value: ALL, label: 'All levels' },
  { value: 'beginner', label: 'Beginner' },
  { value: 'intermediate', label: 'Intermediate' },
  { value: 'advanced', label: 'Advanced' },
];

/**
 * Fetches the static cookbook index and renders a searchable, filterable grid
 * of recipe cards.  All filtering happens client-side so no server round-trips
 * are required after the initial JSON load.
 *
 * Data source: `/static/cookbook-index.json` — generated at build time by
 * `docs/tools/generate-cookbook-index.mjs`.
 */
export default function CookbookIndex() {
  const indexUrl = useBaseUrl('/cookbook-index.json');

  const [recipes, setRecipes] = useState([]);
  const [allTags, setAllTags] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const [search, setSearch] = useState('');
  const [activeTag, setActiveTag] = useState(ALL);
  const [difficulty, setDifficulty] = useState(ALL);

  useEffect(() => {
    fetch(indexUrl)
      .then((res) => {
        if (!res.ok) throw new Error(`Failed to load cookbook index (${res.status})`);
        return res.json();
      })
      .then((data) => {
        const list = data.recipes ?? [];
        setRecipes(list);
        setAllTags([...new Set(list.flatMap((r) => r.tags))].sort());
        setLoading(false);
      })
      .catch((err) => {
        setError(err.message);
        setLoading(false);
      });
  }, [indexUrl]);

  /** Memoised filter: recomputes only when recipes or any filter value changes. */
  const filtered = useMemo(() => {
    const q = search.toLowerCase();
    return recipes.filter((r) => {
      const matchesSearch =
        !q ||
        r.title.toLowerCase().includes(q) ||
        r.description.toLowerCase().includes(q) ||
        r.tags.some((t) => t.toLowerCase().includes(q));
      const matchesTag = activeTag === ALL || r.tags.includes(activeTag);
      const matchesDifficulty = difficulty === ALL || r.difficulty === difficulty;
      return matchesSearch && matchesTag && matchesDifficulty;
    });
  }, [recipes, search, activeTag, difficulty]);

  if (loading) return <p className={styles.status}>Loading recipes…</p>;
  if (error) return <p className={styles.status}>⚠️ {error}</p>;

  return (
    <div className={styles.wrapper}>
      {/* ── Search + difficulty ──────────────────────────────── */}
      <div className={styles.searchRow}>
        <input
          type="search"
          className={styles.searchInput}
          placeholder="Search recipes…"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          aria-label="Search cookbook recipes"
        />
        <select
          className={styles.select}
          value={difficulty}
          onChange={(e) => setDifficulty(e.target.value)}
          aria-label="Filter by difficulty"
        >
          {DIFFICULTY_OPTIONS.map((opt) => (
            <option key={opt.value} value={opt.value}>
              {opt.label}
            </option>
          ))}
        </select>
      </div>

      {/* ── Tag filter bar ───────────────────────────────────── */}
      <div className={styles.tagFilter} role="group" aria-label="Filter by tag">
        <button
          className={clsx(styles.tagBtn, { [styles.tagBtnActive]: activeTag === ALL })}
          aria-pressed={activeTag === ALL}
          onClick={() => setActiveTag(ALL)}
        >
          All
        </button>
        {allTags.map((tag) => (
          <button
            key={tag}
            className={clsx(styles.tagBtn, { [styles.tagBtnActive]: activeTag === tag })}
            aria-pressed={activeTag === tag}
            onClick={() => setActiveTag(tag)}
          >
            {tag}
          </button>
        ))}
      </div>

      {/* ── Recipe grid ─────────────────────────────────────── */}
      {filtered.length === 0 ? (
        <p className={styles.status}>No recipes match your filters.</p>
      ) : (
        <div className={styles.grid}>
          {filtered.map((recipe) => (
            <CookbookCard key={recipe.slug} recipe={recipe} />
          ))}
        </div>
      )}

      <p className={styles.resultCount}>
        {filtered.length} of {recipes.length} recipe{recipes.length !== 1 ? 's' : ''}
      </p>
    </div>
  );
}
