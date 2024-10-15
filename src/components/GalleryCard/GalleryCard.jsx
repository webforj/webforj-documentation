import React from "react";
import clsx from "clsx";
import Link from "@docusaurus/Link";
import useBaseUrl from "@docusaurus/useBaseUrl";
import styles from "./GalleryCard.module.css";

export default function GalleryCard({ href, header, image, effect, children }) {
  const isExternal = href ? /^https?:\/\//.test(href) : false;

  const content = (
    <div className={styles.cardWrapper}>
      {image && (
        <div className={styles.imageWrapper}>
          <img
            src={useBaseUrl(image)}
            alt={header}
            className={styles.cardImage}
          />
        </div>
      )}
      <div className={styles.cardContent}>
        {header && (
          <h3
            className={styles.cardHeader}
            dangerouslySetInnerHTML={{ __html: header }}
          ></h3>
        )}
        {children && <div className={styles.cardDescription}>{children}</div>}
      </div>
    </div>
  );

  return (
    <div
      className={clsx(styles.card, { [styles.cardWithImage]: image })}
      effect={effect || "scale"}
    >
      {href ? (
        isExternal ? (
          <a
            href={href}
            target="_blank"
            rel="noopener noreferrer"
            className={styles.cardLink}
          >
            {content}
          </a>
        ) : (
          <Link to={href} className={styles.cardLink}>
            {content}
          </Link>
        )
      ) : (
        content
      )}
    </div>
  );
}
