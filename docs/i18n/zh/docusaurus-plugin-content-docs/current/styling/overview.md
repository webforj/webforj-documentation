---
title: Styling
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Style webforJ apps with the DWC design system using CSS custom properties,
  palettes, shadow parts, and the Figma kit.
_i18n_hash: 40e7755b35318ea88eb990c6b6dbd240
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

webforJ 自带一个名为 **DWC** 的全面设计系统。它不仅仅是一个主题，它是一个结构化、可扩展的系统，管理着您应用程序的视觉语言。DWC 旨在帮助开发人员和设计师快速而自信地创建一致的、与品牌对齐的界面。

在其核心，DWC 提供了一组经过精心设计的 CSS 变量（设计令牌），覆盖关键视觉元素如颜色、排版、边框和间距。这些令牌作为所有组件样式的基础构建块，使全局自定义变得轻而易举。

为了支持更高级的样式，webforJ 利用 CSS Shadow Parts，允许选择性地为组件内部样式，以保持封装性。这使得团队可以对组件的外观进行细致的控制，即使在较大的应用程序中。

DWC 还包括一个可自定义的调色板，默认使用干净、明亮的视觉主题，但每个方面都可以根据您的品牌或产品风格进行调整。

<AISkillTip skill="webforj-styling-apps" />

## Figma 设计工具包 {#figma-design-kit}

[DWC Figma 库](https://www.figma.com/community/file/1144573845612007198/dwc-design-kit) 是创建现代企业级 web 应用程序的官方设计资源。它包含了一整套与 DWC 设计系统对齐的组件、排版样式和颜色令牌。设计师和开发人员可以使用这个库来构建视觉一致、用户友好的界面，具有可预测的组件行为、精确的间距和可访问的颜色对比。

<img src="/img/dwc.png" alt="Figma 设计工具包" style={{borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}} />

>
<!-- > ![Figma 设计工具包截图](./path-to-your-screenshot.png) -->

## 主题 {#topics}

<DocCardList className="topics-section" />
