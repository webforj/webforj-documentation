import React, { useEffect, useState } from "react";
import { createPortal } from "react-dom";
import clsx from "clsx";
import useBaseUrl from "@docusaurus/useBaseUrl";
import styles from "./ThemeGallery.module.css";

export default function ThemeGallery({ items, alt = "Screenshot" }) {
  const [mode, setMode] = useState("light");
  const [index, setIndex] = useState(0);
  const [zoomed, setZoomed] = useState(false);
  const baseUrl = useBaseUrl("/");

  const current = items[index];
  const src = `${baseUrl}${current[mode].replace(/^\//, "")}`;
  const fullSrc = src.replace(/\.webp$/, "-full.webp");
  const label = `${alt}, ${mode === "light" ? "light" : "dark"} palette, ${current.label} primary color`;

  useEffect(() => {
    if (!zoomed) return undefined;
    const onKey = (event) => {
      if (event.key === "Escape") setZoomed(false);
    };
    const overflow = document.body.style.overflow;
    document.body.style.overflow = "hidden";
    document.addEventListener("keydown", onKey);
    return () => {
      document.body.style.overflow = overflow;
      document.removeEventListener("keydown", onKey);
    };
  }, [zoomed]);

  return (
    <figure className={styles.gallery}>
      <button
        type="button"
        className={styles.frame}
        onClick={() => setZoomed(true)}
        aria-label={`Enlarge: ${label}`}
      >
        <img src={src} alt={label} className={styles.image} width="1600" height="1151" />
      </button>

      <figcaption className={styles.controls}>
        <div className={styles.modes} role="group" aria-label="Palette">
          {["light", "dark"].map((value) => (
            <button
              key={value}
              type="button"
              className={clsx(styles.mode, mode === value && styles.modeActive)}
              aria-pressed={mode === value}
              onClick={() => setMode(value)}
            >
              {value === "light" ? "Light" : "Dark"}
            </button>
          ))}
        </div>

        <div className={styles.swatches} role="group" aria-label="Primary color">
          {items.map((item, i) => (
            <button
              key={item.label}
              type="button"
              className={clsx(styles.swatch, i === index && styles.swatchActive)}
              style={{ "--swatch": item.color }}
              aria-pressed={i === index}
              aria-label={item.label}
              title={item.label}
              onClick={() => setIndex(i)}
            />
          ))}
          <span className={styles.swatchLabel}>{current.label}</span>
        </div>
      </figcaption>

      {zoomed && createPortal(
        <div
          className={styles.overlay}
          role="dialog"
          aria-modal="true"
          aria-label={label}
          onClick={() => setZoomed(false)}
        >
          <img src={fullSrc} alt={label} className={styles.overlayImage} />
        </div>,
        document.body
      )}
    </figure>
  );
}
