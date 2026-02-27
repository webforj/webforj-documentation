import React from 'react';
// Import Components here and add to the export list to include in global scope

import MDXComponents from '@theme-original/MDXComponents';
import AppLayoutViewer from '@site/src/components/DocsTools/AppLayoutViewer';
import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import DocChip from '@site/src/components/DocsTools/DocChip';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';
import ParentLink from '@site/src/components/DocsTools/ParentLink';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import ComponentArchetype from '@site/src/components/DocsTools/ComponentArchetype';
import ExpandableCode from '@site/src/components/DocsTools/ExpandableCode';
import GiscusComments from '@site/src/components/DocsTools/GiscusComments';
import AskMenu from '@site/src/components/DocsTools/AskMenu';
import TableWrapper from '@site/src/components/DocsTools/TableWrapper';
import DocCardList from '@theme/DocCardList';
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';
import Accordion from '@mui/material/Accordion';
import AccordionSummary from '@mui/material/AccordionSummary';
import AccordionDetails from '@mui/material/AccordionDetails';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import GLOBALS from '../../siteConfig';
import RadiusBox from '@site/src/components/DWCTheme/RadiusBox/RadiusBox';
import ShadowBox from '@site/src/components/DWCTheme/ShadowBox/ShadowBox';
import SizingBox from '@site/src/components/DWCTheme/SizingBox/SizingBox';
import SpacingBox from '@site/src/components/DWCTheme/SpacingBox/SpacingBox';
import SurfaceBox from '@site/src/components/DWCTheme/SurfaceBox/SurfaceBox';
import TransitionBox from '@site/src/components/DWCTheme/TransitionBox/TransitionBox';
import GalleryCard from '@site/src/components/GalleryCard/GalleryCard';
import GalleryGrid from '@site/src/components/GalleryGrid/GalleryGrid';
import ColorPalette from '@site/src/components/DWCTheme/ColorPalette/ColorPalette';


export default {
  ...MDXComponents,
  AppLayoutViewer,
  ComponentDemo,
  DocCardList,
  DocChip,
  JavadocLink,
  ParentLink,
  TableBuilder,
  ComponentArchetype,
  Tabs,
  TabItem,
  ExpandableCode,
  GiscusComments,
  AskMenu,
  RadiusBox,
  ShadowBox,
  SizingBox,
  SpacingBox,
  SurfaceBox,
  TransitionBox,
  Accordion,
  AccordionSummary,
  AccordionDetails,
  ExpandMoreIcon,
  GalleryCard,
  GalleryGrid,
  ColorPalette,
  table: TableWrapper
};

