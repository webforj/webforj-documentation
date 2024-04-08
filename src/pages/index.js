/** @jsxImportSource @emotion/react */
import { useState, useEffect, useRef } from "react";
import clsx from "clsx";
import Link from "@docusaurus/Link";
import useDocusaurusContext from "@docusaurus/useDocusaurusContext";
import Layout from "@theme/Layout";
import HomepageSection from "@site/src/components/HomepageFeatures/HomepageSection";

import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import CircularProgress from "@mui/material/CircularProgress";

import logo from "../../static/img/logo.png";
import javaland from "../../static/img/JavaLand_2024.jpg";
import devoxx from "../../static/img/devoxx.png";
import { css, jsx } from "@emotion/react";

import styles from "./index.module.css";

function HomepageHeader() {
  const { siteConfig } = useDocusaurusContext();
  return (
    <header className={clsx("hero hero--primary", styles.heroBanner)}>
      <div className="container">
        <div className={clsx(styles.test)}>
          <img
            className="hero_image"
            src={logo}
            style={{ "grid-column": "1" }}
          ></img>
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

function HomepageTitle() {
  const [open, setOpen] = useState(false);
  const [loading, setLoading] = useState(true);

  const handleOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  useEffect(() => {
    const script = document.createElement("script");
    script.charset = "utf-8";
    script.type = "text/javascript";
    script.src = "//js.hsforms.net/forms/embed/v2.js";

    script.onload = () => {
      hbspt.forms.create({
        region: "na1",
        portalId: "14494994",
        formId: "3ba0ef89-b572-4fa5-833c-4f63f25586d0",
        target: `#script-container`,
      });
      setLoading(false);
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
  `;

  const versionStyles = css`
    align-self: flex-end;
    justify-self: flex-start;
    margin-top: -2em;
  `;

  const titleStyles = css`
    max-width: 50%;
    font-size: 4em;
    /* color: black; */
    font-weight: bold;
    text-align: center;

    @media (max-width: 768px) {
      max-width: 100%;
      font-size: 3.75em;
    }
  `;

  const descriptionStyles = css`
    font-size: 1.6em;
    /* color: slategrey; */
    max-width: 60%;
    text-align: center;

    @media (max-width: 768px) {
      max-width: 100%;
      font-size: 1.5em;
    }
  `;

  const getStartedStyles = css`
    /* background-color: purple; */
    /* background-color: #ae5bc7; */
    border: none;
  `;
  const buttonStyles = css`
    display: flex;
    gap: 20px;
    padding-top: 50px;
    @media screen and (max-width: 992px) {
      flex-direction: column;
    }
  `;

  const dialogStyles = css`
    /* display: flex; */
    width: 500px;
    @media screen and (max-width: 992px) {
      display: flex;
      width: unset;
    }
  `;

  return (
    <div css={headerStyles}>
      {/* <b css={versionStyles}>BBj Version: 22.XX.? </b> */}
      <h2 css={titleStyles}>webforJ</h2>
      <p css={descriptionStyles}>
        A robust and flexible framework that can help you deliver a modern and
        engaging web user interface with ease. In Java.
      </p>
      <div css={buttonStyles}>
        <Link
          css={getStartedStyles}
          className="button button--primary button--lg"
          to="docs/category/getting-started"
        >
          Get Started
        </Link>
        <Link
          onClick={handleOpen}
          className="button button--secondary button--lg"
          to="#script-container"
        >
          Contact Us
        </Link>
        <Link
          className="button button--secondary button--lg"
          href="https://calendly.com/webforj"
        >
          Schedule Demo
        </Link>
      </div>
      <Dialog open={open} onClose={handleClose}>
        {/* <DialogTitle>Dialog Title</DialogTitle> */}
        <DialogContent css={dialogStyles}>
          <DialogContentText>
            {loading ? <CircularProgress /> : <div id="script-container"></div>}
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

function HomepageAnnoucement() {
  const announcementStyles = css`
    display: flex;
    flex-direction: row;
    width: 60%;
    height: fit-content;
    background: #1b1b1d;
    align-self: center;
    align-items: center;
    justify-content: space-around;
    padding: 30px;
    gap: 20px;
    border-radius: 20px;
    @media screen and (max-width: 1432px) {
      flex-direction: column;
      width: 100%;
    }
    overflow: hidden;
  `;

  const infoStyles = css`
    display: flex;
    flex-direction: column;
    max-width: 60%;
    @media screen and (max-width: 1432px) {
      max-width: 90%;
    }
  `;

  const logoStyles = css`
    height: 150px;
    width: 325px;
    @media screen and (max-width: 600px) {
      display: none;
    }
  `;

  const titleStyles = css`
    color: white;
  `;

  const descriptionStyles = css`
    color: white;
  `;

  const bannerStyles = css`
    display: flex;
    border-radius: 10px;
    gap: 30px;
    margin: 0 5em;
    background: #1b1b1d;
    @media screen and (max-width: 992px) {
      flex-direction: column;
    }
  `;

  return (
    <div css={bannerStyles}>
      <div css={announcementStyles}>
        <img
          src={javaland}
          css={logoStyles}
        />
        <div css={infoStyles}>
          <h2 css={titleStyles}>Catch us at Javaland 2024!</h2>
          <p css={descriptionStyles}>
            Come say hello and tell us what you think about the new webforJ
            Framework at the Javaland conference this April at Nürburgring.
          </p>
          <Link
            className="button button--primary button--lg"
            to="https://www.javaland.eu/de/home/"
          >
            More Information
          </Link>
        </div>
      </div>

      <div css={announcementStyles}>
        <img
          // src='https://www.javaland.eu/_assets/21d419d44498ff574c15c53ba467c474/Images/logo_javaland.png'
          src={devoxx}
          css={logoStyles}
        />
        <div css={infoStyles}>
          <h2 css={titleStyles}>Say hello at Devoxx 2024!</h2>
          <p css={descriptionStyles}>
            We'll be in London talking all about webforJ this year at a global
            gathering of the developer community at .
          </p>
          <Link
            className="button button--primary button--lg"
            to="https://www.devoxx.co.uk/"
          >
            More Information
          </Link>
        </div>
      </div>
    </div>
  );
}

function LITracking() {
  useEffect(() => {
    const script = document.createElement("script");
    script.type = "text/javascript";
    script.async = true;
    script.src = "https://snap.licdn.com/li.lms-analytics/insight.min.js";

    script.onload = () => {
      initializeLinkedInTracking();
    };

    document.body.appendChild(script);

    return () => {
      document.body.removeChild(script);
    };
  }, []);

  function initializeLinkedInTracking() {
    window._linkedin_partner_id = "6908425";
    window._linkedin_data_partner_ids = window._linkedin_data_partner_ids || [];
    window._linkedin_data_partner_ids.push(window._linkedin_partner_id);

    (function (l) {
      if (!l) {
        window.lintrk = function (a, b) {
          window.lintrk.q.push([a, b]);
        };
        window.lintrk.q = [];
      }
    })(window.lintrk);
  }

  return null;
}

export default function Home() {
  const mainStyles = css`
    display: flex;
    flex-direction: column;
    width: 100%;
  `;

  const { siteConfig } = useDocusaurusContext();
  return (
    <Layout
      title={`${siteConfig.title}`}
      description="Build your modern web applications with webforJ."
    >
      <div css={mainStyles}>
        <LITracking />
        <HomepageTitle />
        <HomepageAnnoucement />
        <HomepageSection />
      </div>
    </Layout>
  );
}
