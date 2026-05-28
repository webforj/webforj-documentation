import React from 'react';
import Layout from '@theme/Layout';
import Head from '@docusaurus/Head';
import Link from '@docusaurus/Link';
import ProjectCard from '@site/src/components/HomePage/ProjectCard';
import { builtWithProjects } from './homePageData';
import styles from '@site/src/components/HomePage/HomePageLayout.module.css';

export default function BuiltWithWebforJ() {
  const winners = builtWithProjects.filter((p) => p.winner);
  const community = builtWithProjects.filter((p) => !p.winner);

  return (
    <Layout
      title="Built with webforJ"
      description="Community projects and apps built with the webforJ framework — browse the source code and get inspired for your next project."
    >
      <Head>
        <style>{`
          .container { max-width: 65em !important; }
        `}</style>
      </Head>

      <main className="container" style={{ padding: '3rem 1rem' }}>
        <div style={{ marginBottom: '3rem' }}>
          <h1 style={{ marginBottom: '0.5rem' }}>Built with webforJ</h1>
          <p style={{ color: 'var(--ifm-color-content-secondary)', maxWidth: '600px', marginBottom: '1.25rem' }}>
            Real-world apps, demos, and prototypes from the webforJ community. Browse the source
            code and get inspired for your next project.
          </p>
          <a
            href="https://github.com/webforj/built-with-webforj"
            target="_blank"
            rel="noopener noreferrer"
            className="button button--secondary button--sm"
          >
            Submit your project on GitHub
          </a>
        </div>

        {winners.length > 0 && (
          <section style={{ marginBottom: '3rem' }}>
            <h2 style={{ marginBottom: '1rem' }}>🏆 Featured</h2>
            <div className={styles.projectGrid}>
              {winners.map((project) => (
                <ProjectCard key={project.name} {...project} />
              ))}
            </div>
          </section>
        )}

        <section>
          <h2 style={{ marginBottom: '1rem' }}>Community Projects</h2>
          <div className={styles.projectGrid}>
            {community.map((project) => (
              <ProjectCard key={project.name} {...project} />
            ))}
          </div>
        </section>

        <div
          style={{
            marginTop: '3rem',
            paddingTop: '1.5rem',
            borderTop: '1px solid var(--dwc-border-color)',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'space-between',
            flexWrap: 'wrap',
            gap: '1rem',
          }}
        >
          <p style={{ margin: 0, color: 'var(--ifm-color-content-secondary)', fontSize: '0.9rem' }}>
            All projects are community-contributed.{' '}
            <a
              href="https://github.com/webforj/built-with-webforj"
              target="_blank"
              rel="noopener noreferrer"
            >
              See the full list on GitHub.
            </a>
          </p>
          <Link className="button button--primary button--sm" to="/docs/introduction/getting-started">
            Start building your own →
          </Link>
        </div>
      </main>
    </Layout>
  );
}
