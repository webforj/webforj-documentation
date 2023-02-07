import React from 'react';
import clsx from 'clsx';
import styles from './styles.module.css';
import Link from '@docusaurus/Link';

const FeatureList = [
  {
    title: 'Documentation',
    path: 'docs/intro',
    Svg: require('@site/static/img/documentation.svg').default,
    description: (
      <>
        View extensive documentation on setting up and using the DWCJ, as well 
        as its various components, controls, and features. 
      </>
    ),
  },
  {
    title: 'Live Demo',
    path: 'live_demo',
    Svg: require('@site/static/img/demo.svg').default,
    description: (
      <>
        Try out the DWCJ right here using our embedded editor without setting
        up the tool on your local machine.
      </>
    ),
  },
  {
    title: 'Tutorials',
    path: 'tutorials',
    Svg: require('@site/static/img/tutorial.svg').default,
    description: (
      <>
        View tutorials created to walk you through various aspects of the DWCJ,
        including installation, control demonstrations, and appliication/web development .
      </>
    ),
  },
];

function Feature({Svg, title, description, path}) {
  return (
    <div className={clsx('col col--4')}>
      <div className="text--center">
        <Svg className={styles.featureSvg} role="img" />
      </div>
      <div className="text--center padding-horiz--md">
        <h3>{title}</h3>
        <p>{description}</p>
          <div className={styles.buttons}>
            <Link 
              className="button button--primary button--lg"
              to={path}>
              View {title}
            </Link>
          </div>
      </div>
    </div>
  );
}

export default function HomepageFeatures() {
  return (
    <section className={styles.features}>
      <div className="container">
        <div className="row">
          {FeatureList.map((props, idx) => (
            <Feature key={idx} {...props} />
          ))}
        </div>
      </div>
    </section>
  );
}
