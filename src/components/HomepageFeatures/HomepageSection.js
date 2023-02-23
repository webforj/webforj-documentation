/** @jsx jsx*/

import React from 'react';
import Link from '@docusaurus/Link';
import styles from '../../css/custom.css'

import { jsx, css } from '@emotion/react';

const Sections = [
    {   
        placement: 'center',
        direction: 'right',
        header: "MODERN JAVA WEB FRAMEWORK",
        title: 'Build your modern web applications with the DWCJ.',
        description: (
        <>
            The DWCJ turns creating your enterprise application into a streamlined and efficient task with its comprehensive set of controls, components, features and functionalities.
            The DWCJ follows an "all-in-one" process, with UI and backend development all taking place within Java. With the DWCJ, you have access to a comprehensive set of components, 
            features, and tools necessary for building a robust, secure, and user-friendly application.   
        </>),
        image: require('@site/static/img/development.png').default
    },

    {   
        placement: 'center',
        direction: 'left',
        header: "COMPETITIVE PERFORMANCE",
        title: 'A truly dynamic tool for optimized development and performance.',
        description: (
        <>
            The DWCJ framework provides a simple pipeline which makes your application easy to build and easy to deploy. Additionally, it dynamically downloads individual messages, or chunks of
            Javascript on-demand as the application, as is necessary for use in the application. This results in measurable improvements to the initial load time, resulting in an optimal user experience.
        </>),
        image: require('@site/static/img/dynamic.png').default
    },
    
    {   
        placement: 'center',
        direction: 'right',
        header: "COMPREHENSIVE DESIGN KIT",
        title: 'Figma design kit for streamlined design and implementation. ',
        description: (
        <>
            Before implementation of your application even begins, the DWCJ's extensive array of controls can be easily used within Figma to help designers make sure the perfect tools is created.
        </>),
        image: require('@site/static/img/design_kit.png').default,
        link: {
            class: "button button--primary button--lg",
            to: 'https://www.figma.com/community/file/1144573845612007198',
            title: 'See the Design Kit'
        }
    },
    {   
        placement: 'center',
        direction: 'left',
        header: "PROVEN TRACK RECORD",
        title: 'A history of performance and reliability.',
        description: (
        <>
            Our team in the United States and Europe have been building tools and technologies to help our clients fulfil their needs for over 35 years. We have consistently produced reliable and
            innovative technologies to ensure our clients have every tool in their kit needed to tackle modern, ever-changing needs. 
            <br /><br />
            The DWCJ helps existing customers ensure they have the most 
            cutting edge features available, and provides a competitive solution for new customers.
        </>),
        image: require('@site/static/img/performance.png').default
    },
    
];

function Section({ placement, direction, header, title, description, image, link }){

    const sectionStyles = css`
        display: flex;
        flex-direction: ${direction == 'left' ? 'row' : 'row-reverse'};
        align-self: ${placement == 'left' ? 'flex-start' : placement == 'right' ? 'flex-end' : 'center'};
        max-width: 90%;
        margin: 7% 0 0 0;
        padding: 25px 50px 25px 50px;
        gap: 50px;

        @media screen and (max-width: 992px) {
            flex-direction: column;
            padding: 0;
        }    
    
    `

    const headerStyles = css`
        font-size: 1em;
        /* color: var(--ifm-color-primary); */
        background: -webkit-linear-gradient(90deg, var(--ifm-color-primary-lightest), var(--ifm-color-primary-darkest));
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        font-weight: bold;
    `
    
    const titleStyles = css`
        font-size: 2.15em;
        font-weight: bold;
        @media screen and (max-width: 992px) {
            font-size: 1.65em;
        }
    `
    
    const descStyles = css`
        font-size: 1.15em;
    
        `
    
    const imageStyles = css`
        /* max-width: 35%;
        object-fit: contain;
        border-radius: 50px; */
        width: auto;
        height: auto;
        max-width: 35%;
        border-radius: 3%;
        margin: auto;

        @media screen and (max-width: 992px) {
            align-self: center;
            justify-self: center;
            display: none;
        }
    `

    const textStyles = css`

        padding: 30px;
    `

    return(
        <div css={sectionStyles}>
            <div css={textStyles}>
                <p css={headerStyles}>
                    {header}
                </p>
                <p css={titleStyles}>
                    {title}
                </p>
                <p css={descStyles}>
                    {description}
                </p>
                {link ? 
                <Link 
                className={link.class}
                to={link.to}>
                {link.title}
                </Link> : <></>}
            </div>
            <img 
            css={imageStyles}
            src={image}
            >
            </img>
        </div>
    );
}


export default function HomepageSection(){
    
    return(
        <>
            {Sections.map((props, idx) => (
                <Section key={idx} {...props} />
            ))}
        </>
    );
}