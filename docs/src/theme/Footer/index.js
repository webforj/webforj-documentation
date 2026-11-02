import React, { useEffect, useState } from 'react';
import clsx from 'clsx';
import Translate, { translate } from '@docusaurus/Translate';
import { FaChevronDown } from 'react-icons/fa6';
import Link from '@docusaurus/Link';
import useBaseUrl from '@docusaurus/useBaseUrl';
import ConnectIcons from '@site/src/components/connect/ConnectIcons';
import { FOLLOW, RSS } from '@site/src/components/connect/links';
import { getFooter } from './content';
import styles from './styles.module.css';

// site pages through the router, other sites (and mail) in a new tab
function FooterLink({ item, className, children }) {
  if (item.to) {
    return (
      <Link className={className} to={item.to}>
        {children}
      </Link>
    );
  }
  const web = /^https?:/.test(item.href);
  return (
    <a
      className={className}
      href={item.href}
      {...(web ? { target: '_blank', rel: 'noopener noreferrer' } : {})}>
      {children}
    </a>
  );
}

// The site footer, dark like the navbar in both themes: the brand with its
// social icons and calls to action, the link columns (content.js), then
// the copyright. Nothing sits in the bottom-right corner, where the
// "Questions?" launcher floats
// A link group: a title and its links. On phones the title becomes a button
// that opens and closes the group, so the footer stays short.
function FooterColumn({ column, index }) {
  const [open, setOpen] = useState(false);
  // by position, since titles are translated into any script
  const id = `footer-links-${index}`;
  return (
    <div className={clsx(styles.column, open && styles.open)}>
      <p className={styles.title}>{column.title}</p>
      <button
        type="button"
        className={styles.toggle}
        aria-expanded={open}
        aria-controls={id}
        onClick={() => setOpen(!open)}>
        {column.title}
        <FaChevronDown className={styles.chevron} aria-hidden="true" />
      </button>
      <ul id={id} className={styles.links}>
        {column.items.map((item) => (
          <li key={item.label}>
            <FooterLink item={item} className={styles.link}>
              {item.label}
            </FooterLink>
          </li>
        ))}
      </ul>
    </div>
  );
}

// The current year. The page is built ahead of time, so the built year comes
// first and the visitor's year replaces it once the page runs in the browser.
function useCurrentYear() {
  const [year, setYear] = useState(() => new Date().getFullYear());
  useEffect(() => setYear(new Date().getFullYear()), []);
  return year;
}

export default function Footer() {
  const logo = useBaseUrl('/img/webforj.svg');
  const year = useCurrentYear();
  const footer = getFooter();
  return (
    <footer className={clsx('theme-layout-footer', styles.footer)}>
      <div className={styles.inner}>
        <div className={styles.top}>
          <div className={styles.brand}>
            <Link
              to="/"
              aria-label={translate({
                id: 'component.footer.homeLabel',
                message: 'webforJ documentation home',
              })}>
              <img className={styles.logo} src={logo} alt="webforJ" width="140" height="32" />
            </Link>
            <p className={styles.pitch}>{footer.pitch}</p>
            <ConnectIcons links={[...FOLLOW, RSS]} className={styles.socials} compact />
          </div>
          <div className={styles.actions}>
            {footer.actions.map((action) => (
              <FooterLink
                key={action.label}
                item={action}
                className={clsx(styles.button, action.primary && styles.primary)}>
                {action.label}
              </FooterLink>
            ))}
          </div>
        </div>

        <nav
          className={styles.columns}
          aria-label={translate({ id: 'component.footer.navLabel', message: 'Footer' })}>
          {footer.columns.map((column, index) => (
            <FooterColumn key={column.title} column={column} index={index} />
          ))}
        </nav>

        <div className={styles.bottom}>
          <p className={styles.copyright}>
            <Translate
              id="component.footer.copyright"
              values={{
                year,
                company: (
                  <a href="https://basis.cloud/contact/" target="_blank" rel="noopener noreferrer">
                    BASIS International Ltd.
                  </a>
                ),
              }}>
              {'Copyright © {year} {company} All rights reserved.'}
            </Translate>
          </p>
        </div>
      </div>
    </footer>
  );
}
