import React from 'react';
// Import Components here and add to the export list to include in global scope

import MDXComponents from '@theme-original/MDXComponents';
import AppLayoutViewer from '@site/src/components/DocsTools/AppLayoutViewer';
import ComponentCard from '@site/src/components/DocsTools/ComponentCard';
import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import DocChip from '@site/src/components/DocsTools/DocChip';
import EventTable from '@site/src/components/DocsTools/EventTable';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';
import ParentLink from '@site/src/components/DocsTools/ParentLink';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import TabSwitcher from '@site/src/components/DocsTools/TabSwitcher';
import ComponentArchetype from '@site/src/components/DocsTools/ComponentArchetype';
import ExpandableCode from '@site/src/components/DocsTools/ExpandableCode';
import GiscusComments from '@site/src/components/DocsTools/GiscusComments';
import AskMenu from '@site/src/components/DocsTools/AskMenu';
import DocCardList from '@theme/DocCardList';
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';
import GLOBALS from '../../siteConfig';


export default {
  ...MDXComponents,
  AppLayoutViewer,
  ComponentCard,
  ComponentDemo,
  DocCardList,
  DocChip,
  EventTable,
  JavadocLink,
  ParentLink,
  TableBuilder,
  TabSwitcher,
  ComponentArchetype,
  Tabs,
  TabItem,
  ExpandableCode,
  GiscusComments,
  AskMenu
};

