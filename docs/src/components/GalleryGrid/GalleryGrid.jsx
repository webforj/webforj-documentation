import React from "react";
import styles from "./GalleryGrid.module.css";

export default function GalleryGrid({ children }) {
  return <div className={styles.grid}>{children}</div>;
}
