import React from 'react';
import GitHubIcon from '@mui/icons-material/GitHub';
import BugReportOutlinedIcon from '@mui/icons-material/BugReportOutlined';
import HistoryOutlinedIcon from '@mui/icons-material/HistoryOutlined';
import ArrowForwardIcon from '@mui/icons-material/ArrowForward';
import styles from './GettingStarted.module.css';

const links = [
  {
    icon: <GitHubIcon />,
    label: 'Star on GitHub',
    href: 'https://github.com/webforj/webforj',
  },
  {
    icon: <BugReportOutlinedIcon />,
    label: 'Report an issue',
    href: 'https://github.com/webforj/webforj/issues/new/choose',
  },
  {
    icon: <HistoryOutlinedIcon />,
    label: 'Read the changelog',
    href: 'https://github.com/webforj/webforj/releases/latest',
  },
];

export default function CommunityBand() {
  return (
    <section className={styles.community}>
      <div className={styles.communityLinks}>
        {links.map((link) => (
          <a
            key={link.href}
            href={link.href}
            target="_blank"
            rel="noopener noreferrer"
            className={styles.communityLink}
          >
            {link.icon}
            <span>{link.label}</span>
          </a>
        ))}
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
