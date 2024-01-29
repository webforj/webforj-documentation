/** @jsxImportSource @emotion/react */
import { useState, useEffect, useRef } from 'react';
import clsx from 'clsx';
import Link from '@docusaurus/Link';
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';
import Layout from '@theme/Layout';
import HomepageFeatures from '@site/src/components/HomepageFeatures';
import HomepageSection from '@site/src/components/HomepageFeatures/HomepageSection'

import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import CircularProgress from '@mui/material/CircularProgress';

import logo from "../../static/img/logo.png"
import javaland from "../../static/img/JavaLand_2024.jpg"
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

  
  const [open, setOpen] = useState(false);
  const [loading, setLoading] = useState(true);

  const handleOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  useEffect(() => {
    const script = document.createElement('script');
    script.charset = 'utf-8';
    script.type = 'text/javascript';
    script.src = '//js.hsforms.net/forms/embed/v2.js';

    script.onload = () => {
      hbspt.forms.create({
        region: 'na1',
        portalId: '14494994',
        formId: '3ba0ef89-b572-4fa5-833c-4f63f25586d0',
        target: `#script-container`,
      });
      setLoading(false)
    };
    document.body.appendChild(script);
  }, [open]);

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

  const dialogStyles = css`
    /* display: flex; */
    width: 500px;
    @media screen and (max-width: 992px) {
      display: flex;
      width: unset;
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
        <Link 
          onClick={handleOpen}
          className="button button--secondary button--lg"
          to='#script-container'>
          Contact Us
        </Link>
      </div>
      <Dialog open={open} onClose={handleClose}>
        {/* <DialogTitle>Dialog Title</DialogTitle> */}
        <DialogContent css={dialogStyles}>
          <DialogContentText>
            {loading ? <CircularProgress /> : <div id='script-container'>
          </div>}
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          {/* <Link onClick={handleClose} className="button button--secondary button--lg">
            Close
          </Link> */}
        </DialogActions>
      </Dialog>
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
        // src='https://www.javaland.eu/_assets/21d419d44498ff574c15c53ba467c474/Images/logo_javaland.png' 
        src={javaland}
        css={logoStyles}
      />
      <div css={infoStyles}>
        <h2 css={titleStyles}>Catch us at Javaland 2024!</h2>
        <p css={descriptionStyles}>Come say hello and tell us what you think about the new Dynamic Web Client for Java at the Javaland conference this April at Nürburgring.</p>
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
  

  const mainStyles = css`
    display: flex;
    flex-direction: column;
    width: 100%;
  `

  const {siteConfig} = useDocusaurusContext();
  return (
    <Layout
      title={`${siteConfig.title}`}
      description="Build your modern web applications with the DWCJ.">
        <div css={mainStyles}>
        <HomepageTitle />
        <HomepageAnnoucement />
        <HomepageSection />
        </div>
    </Layout>
  );
}
