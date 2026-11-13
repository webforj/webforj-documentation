import React from 'react';
import useBaseUrl from '@docusaurus/useBaseUrl';
import {
  FaArrowUpRightDots,
  FaArrowsRotate,
  FaBookOpen,
  FaBug,
  FaBuilding,
  FaCalendarDays,
  FaChalkboardUser,
  FaCode,
  FaComments,
  FaEnvelope,
  FaFigma,
  FaFileCode,
  FaFileLines,
  FaFlaskVial,
  FaGithub,
  FaGraduationCap,
  FaHandshake,
  FaHeadset,
  FaLayerGroup,
  FaLeaf,
  FaLifeRing,
  FaLinkedin,
  FaNewspaper,
  FaPlay,
  FaRobot,
  FaRocket,
  FaShapes,
  FaShip,
  FaTerminal,
  FaTrophy,
  FaWandMagicSparkles,
  FaWindowRestore,
  FaYoutube,
} from 'react-icons/fa6';
import { SiKotlin } from 'react-icons/si';

// the webforJ mark, in the brand's own colors
function WebforjMark({ className }) {
  return (
    <img
      className={className}
      src={useBaseUrl('/img/webforj_icon.svg')}
      alt="webforJ"
      aria-hidden="true"
      width="20"
      height="20"
    />
  );
}

// The icons a menu entry can name in config/navbar-menus.js
export const ICONS = {
  ai: FaRobot,
  apiDocs: FaFileCode,
  basics: FaLayerGroup,
  blog: FaNewspaper,
  bug: FaBug,
  company: FaBuilding,
  components: FaShapes,
  cookbook: FaBookOpen,
  craftforj: FaWandMagicSparkles,
  deploy: FaShip,
  design: FaFigma,
  events: FaCalendarDays,
  github: FaGithub,
  kotlin: SiKotlin,
  linkedin: FaLinkedin,
  mail: FaEnvelope,
  mcpApps: FaComments,
  partners: FaHandshake,
  plans: FaLifeRing,
  reload: FaArrowsRotate,
  rocket: FaRocket,
  script: FaTerminal,
  spring: FaLeaf,
  start: FaPlay,
  stories: FaTrophy,
  support: FaHeadset,
  testing: FaFlaskVial,
  tutorial: FaGraduationCap,
  upgrade: FaArrowUpRightDots,
  web: FaCode,
  webforj: WebforjMark,
  webinars: FaChalkboardUser,
  webswing: FaWindowRestore,
  whitepapers: FaFileLines,
  youtube: FaYoutube,
};
