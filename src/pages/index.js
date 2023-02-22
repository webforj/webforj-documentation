/** @jsx jsx */

import React from 'react';
import clsx from 'clsx';
import Link from '@docusaurus/Link';
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';
import Layout from '@theme/Layout';
import HomepageFeatures from '@site/src/components/HomepageFeatures';

import logo from "../../static/img/logo.png"
import { css, jsx } from '@emotion/react';

import styles from './index.module.css';


function HomepageHeader() {
  const {siteConfig} = useDocusaurusContext();
  return (
    <header className={clsx('hero hero--primary', styles.heroBanner)}>
      <div className="container">
        <div className={clsx(styles.test)}>
        <img className="hero_image" src={logo} style={{"grid-column": "1"}}></img>
        <div>
        <h1 className="hero__title">{siteConfig.title}</h1>
        <p className="hero__subtitle">{siteConfig.tagline}</p>
        </div>
        </div>
        {/* <div className={styles.buttons}>
          <Link 
            className="button button--secondary button--lg"
            to="/docs/intro">
            View Documentation
          </Link>
        </div> */}
      </div>
    </header>
  );
}

function HomepageTitle(){
  
  const headerStyles = css`
    display: flex;
    flex-direction: column;
    align-items: center;
    /* justify-content: center; */
    margin: 5em 3em 3em 3em;
    gap: 30px;
    
  `

  const versionStyles = css`
    align-self: flex-end;
    justify-self: flex-start;
    margin-top: -2em;
  `

  const titleStyles = css`
    max-width: 50%;
    font-size: 5em;
    color: black;
    font-weight: bold;
    text-align: center;

    @media(max-width: 768px){
      max-width: 100%;
      font-size: 3.75em;
    }
  `

  const descriptionStyles = css`
    font-size: 1.6em;
    color: slategrey;
    max-width: 60%;
    text-align: center;

    @media(max-width: 768px){
      max-width: 100%;
      font-size: 1.5em;
    }

  `

  const getStartedStyles = css`
    /* background-color: purple; */
    background-color: #ae5bc7;
    border: none;
  `
  const buttonStyles = css`
    display: flex;
    gap: 20px;
  `


  return(
    <div css={headerStyles}>
      <b css={versionStyles}>BBj Version: 22.XX.? </b>
      <h1 css={titleStyles}>
        The Dynamic Web Client for Java
      </h1>
      <p css={descriptionStyles}>
        The DWCJ provides a robust and flexible framework that can help you deliver a modern and engaging web user interface with ease.
      </p>
      <div css={buttonStyles}>
        <Link 
        css={getStartedStyles}
          className="button button--primary button--lg"
          to='docs/intro'>
          Get Started
        </Link>
        <Link 
          className="button button--secondary button--lg"
          to='docs/intro'>
          View Documentation
        </Link>
      </div>
    </div>
  );
}

function HomepageAnnoucement(){

  const announcementStyles = css`
    display: flex;
    flex-direction: row;
    width: 60%;
    height: 200px;
    /* border: 2px gray solid;*/
    border-radius: 20px;
    background: rgb(2,0,36);
    background: linear-gradient(90deg, rgba(2,0,36,1) 0%, rgba(69,0,111,1) 100%);
    align-self: center;
    align-items: center;
    justify-content: space-around;
    padding: 0 20px 0 20px;
    margin-bottom: 50px;
  `

  const infoStyles = css`
    display: flex;
    flex-direction: column;
    max-width: 60%;
  `

  const logoStyles = css`
    height: 150px;
  `

  const titleStyles = css`
    color: white;

  `

  const descriptionStyles = css`
    color: white;
  `


  return(
    <div css={announcementStyles}>
      <img 
        src='https://www.javaland.eu/typo3conf/ext/doag/Resources/Public/Images/logo_javaland.png' 
        css={logoStyles}
      />
      <div css={infoStyles}>
        <h2 css={titleStyles}>Catch us at Javaland 2023!</h2>
        <p css={descriptionStyles}>Come say hello and tell us what you think about the new Dynamic Web Client for Java at the Javaland conference this March at Phantasialand Brühl</p>
      </div>
    </div>
  );
}




export default function Home() {

  const mainStyles = css`
    display: flex;
    flex-direction: column;
    width: 100%;
  `

  const descriptionStyles = css
  const {siteConfig} = useDocusaurusContext();
  return (
    <Layout
      title={`${siteConfig.title}`}
      description="Description will go into a meta tag in <head />">
        <div css={mainStyles}>
        <HomepageTitle />
        <HomepageAnnoucement />
        </div>
    </Layout>
  );
}
