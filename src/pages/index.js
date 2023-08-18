/** @jsx jsx */

import React, { useEffect } from 'react';
import clsx from 'clsx';
import Link from '@docusaurus/Link';
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';
import Layout from '@theme/Layout';
import HomepageFeatures from '@site/src/components/HomepageFeatures';
import HomepageSection from '@site/src/components/HomepageFeatures/HomepageSection'

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
    /* color: black; */
    font-weight: bold;
    text-align: center;

    @media(max-width: 768px){
      max-width: 100%;
      font-size: 3.75em;
    }
  `

  const descriptionStyles = css`
    font-size: 1.6em;
    /* color: slategrey; */
    max-width: 60%;
    text-align: center;

    @media(max-width: 768px){
      max-width: 100%;
      font-size: 1.5em;
    }

  `

  const getStartedStyles = css`
    /* background-color: purple; */
    /* background-color: #ae5bc7; */
    border: none;
  `
  const buttonStyles = css`
    display: flex;
    gap: 20px;
    padding-top: 50px;
    @media screen and (max-width: 992px) {
      flex-direction: column;
    }
  `


  return(
    <div css={headerStyles}>
      {/* <b css={versionStyles}>BBj Version: 22.XX.? </b> */}
      <h1 css={titleStyles}>
        The Dynamic Web Client for Java
      </h1>
      <p css={descriptionStyles}>
        The DWCJ provides a robust and flexible framework that can help you deliver a modern and engaging web user interface with ease. In Java.
      </p>
      <div css={buttonStyles}>
        <Link 
        css={getStartedStyles}
          className="button button--primary button--lg"
          to='docs/category/getting-started'>
          Get Started
        </Link>
        {/* <Link 
          className="button button--secondary button--lg"
          to='docs/intro'>
          View Documentation
        </Link> */}
      </div>
    </div>
  );
}

function HomepageAnnoucement(){

  const announcementStyles = css`
    display: flex;
    flex-direction: row;
    width: 60%;
    height: fit-content;
    border-radius: 20px;
    background: #1B1B1D;
    align-self: center;
    align-items: center;
    justify-content: space-around;
    padding: 30px;
    margin-bottom: 50px;
    @media screen and (max-width: 992px) {
      width: 100%;
      border-radius: 0px;
    }
  `

  const infoStyles = css`
    display: flex;
    flex-direction: column;
    max-width: 60%;
  `

  const logoStyles = css`
    height: 150px;
    @media screen and (max-width: 992px) {
      display: none;
    }
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
        <p css={descriptionStyles}>Come say hello and tell us what you think about the new Dynamic Web Client for Java at the Javaland conference this March at Phantasialand Brühl.</p>
        <Link 
          className="button button--primary button--lg"
          to='https://www.javaland.eu/de/home/'>
          More Information
        </Link>
      </div>
    </div>
  );
}




export default function Home() {

  useEffect(() => {
    // Create a script element
    const targetElement = document.getElementById('script-container');
    
    
    const script = document.createElement('script');
    script.charset = 'utf-8';
    script.type = 'text/javascript';
    script.src = '//js.hsforms.net/forms/embed/v2.js';

    // Specify the target element where you want to insert the script
    
    if (targetElement) {
      // Append the script to the target element
      targetElement.appendChild(script);
      targetElement.appendChild(document.createElement("div"))
      console.log("loaded")
      
      // Create the form once the script is loaded
      script.onload = () => {
        console.log("in onload")
        hbspt.forms.create({
          region: 'na1',
          portalId: '14494994',
          formId: '3ba0ef89-b572-4fa5-833c-4f63f25586d0',
        });
      };
    }
  }, []);

  // useEffect(() => {
  //   const script = document.createElement('script');
  //   script.charset = 'utf-8';
  //   script.type = 'text/javascript';
  //   script.src = '//js.hsforms.net/forms/embed/v2.js';

  //   const targetElement = document.getElementById('script-container');

  //   if (targetElement) {
  //     targetElement.appendChild(script);

  //     script.onload = () => {

  //       hbspt.forms.create({
  //                 region: 'na1',
  //                 portalId: '14494994',
  //                 formId: '3ba0ef89-b572-4fa5-833c-4f63f25586d0',
  //               });


  //       const iframe = document.querySelector('#script-container iframe');
  //       if (iframe) {
  //         const iframeDocument = iframe.contentDocument || iframe.contentWindow.document;
  //         console.log("IFRAME FOUND")
  //         const styleElement = iframeDocument.createElement('style');
  //         styleElement.type = 'text/css';

  //         const customCss = `
  //         .hs-button{
  //           background-color: var(--ifm-color-primary) !important;
  //           border-color: var(--ifm-color-primary) !important;
  //         }
  //         `;

  //         styleElement.appendChild(iframeDocument.createTextNode(customCss));

  //         iframeDocument.head.appendChild(styleElement);
  //       }
  //     };
  //   }
  // }, []);


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
      description="Build your modern web applications with the DWCJ.">
        <div css={mainStyles}>
        <HomepageTitle />
        {/* <HomepageAnnoucement /> */}
        <HomepageSection />
        <div id='script-container'>
        </div>
        </div>
    </Layout>
  );
}
