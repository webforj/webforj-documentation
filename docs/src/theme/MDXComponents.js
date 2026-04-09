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
import AccordionGroup from '@site/src/components/DocsTools/AccordionGroup';
import DocCardList from '@theme/DocCardList';
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';
import Accordion from '@mui/material/Accordion';
import AccordionSummary from '@mui/material/AccordionSummary';
import AccordionDetails from '@mui/material/AccordionDetails';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import GLOBALS from '../../siteConfig';
import GalleryCard from '@site/src/components/GalleryCard/GalleryCard';
import GalleryGrid from '@site/src/components/GalleryGrid/GalleryGrid';


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
  AccordionGroup,
  Accordion,
  AccordionSummary,
  AccordionDetails,
  ExpandMoreIcon,
  GalleryCard,
  GalleryGrid,
  table: TableWrapper
};

