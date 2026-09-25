---
title: 样式
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Style webforJ apps with the DWC design system using CSS custom properties,
  palettes, shadow parts, and the Figma kit.
_i18n_hash: bacf450dadef59e4496e78d465a1e44d
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

webforJ 配备了一个全面的设计系统，名为 **DWC**。它不仅仅是一个主题，而是一个结构化、可扩展的系统，管理您应用程序的视觉语言。DWC 的构建旨在帮助开发人员和设计师快速而自信地创建一致的、品牌对齐的界面。

DWC 的核心提供了一组经过精心设计的 CSS 变量（设计令牌），涵盖了关键的视觉元素，如颜色、排版、边框和间距。这些令牌作为所有组件样式的基础构建块，并允许以最小的努力进行全局自定义。

为了支持更高级的样式，webforJ 使用 CSS Shadow Parts，允许在不破坏封装的情况下选择性地为组件内部样式。这使团队能够对组件的外观进行精细控制，即使是在更大的应用程序中也是如此。

DWC 还包含一个可自定义的颜色调色板，默认采用干净、明亮的视觉主题，但每个方面都可以根据您的品牌或产品风格进行调整。

<AISkillTip skill="webforj-styling-apps" />

## Figma 设计工具包 {#figma-design-kit}

[DWC 设计工具包](https://www.figma.com/community/file/1682060886525639971/dwc-design-kit) 是为设计 webforJ 应用程序提供的官方 Figma 资源。它涵盖了每个 DWC 组件的主题、扩展和状态，以及设计系统的颜色调色板、排版、间距和阴影令牌，以 Figma 变量和样式呈现，适用于明亮和黑暗模式。设计师和开发人员可以使用此工具包构建视觉一致、用户友好的界面，确保组件行为的可预测性、精确的间距和可访问的颜色对比度。

<iframe
  title="DWC 设计工具包"
  src="https://embed.figma.com/design/xZVIDRnF7FJ3Dibb5At2lU/DWC-Design-Kit?node-id=6707-254&embed-host=webforj-docs"
  style={{width: '100%', aspectRatio: '16 / 10', borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}}
  loading="lazy"
  allowFullScreen
/>

## 主题 {#topics}

<DocCardList className="topics-section" />
