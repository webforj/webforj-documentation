---
title: 样式设置
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Style webforJ apps with the DWC design system using CSS custom properties,
  palettes, shadow parts, and the Figma kit.
_i18n_hash: 18caef78e838347a9b6376fb635573d9
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

webforJ 随附一套名为 **DWC** 的完整设计系统。它不仅仅是一个主题，更是一套结构化、可扩展的系统，用于规范应用的视觉语言。DWC 旨在帮助开发者和设计师快速、自信地创建一致且符合品牌形象的界面。

DWC 的核心是一组经过精心设计的 CSS 变量（设计令牌），涵盖颜色、排版、边框和间距等关键视觉元素。这些令牌是所有组件样式的基础构建块，让你只需少量操作即可进行全局自定义。

为了支持更高级的样式定制，webforJ 使用 CSS Shadow Parts，让你可以选择性地设置组件内部元素的样式，同时不破坏封装性。这样一来，即使在大型应用中，团队也能精细控制组件的外观。

DWC 还包含可自定义的调色板，默认采用简洁的浅色视觉主题，但你可以根据品牌或产品风格调整各个方面。

<AISkillTip skill="webforj-styling-apps" />

## Figma 设计套件 {#figma-design-kit}

[DWC 设计套件](https://www.figma.com/community/file/1682060886525639971/dwc-design-kit)是用于设计 webforJ 应用的官方 Figma 资源。它涵盖了每个 DWC 组件及其主题、尺寸规格和状态，还包含设计系统的色板、排版、间距和阴影令牌，并以 Figma 变量和样式的形式提供浅色和深色模式。设计师和开发者可以使用该套件构建视觉一致、易于使用的界面，并确保组件行为可预测、间距精准、颜色对比度符合无障碍要求。

<a href="https://www.figma.com/community/file/1682060886525639971/dwc-design-kit" target="_blank" rel="noopener noreferrer">
  <img
    src="https://cdn.webforj.com/webforj-documentation/figma/dwc-design-kit-cover.jpg"
    alt="Figma Community 上的 DWC 设计套件"
    style={{width: '100%', borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}}
    loading="lazy"
  />
</a>

## 主题 {#topics}

<DocCardList className="topics-section" />
