---
title: Styling
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 4e709dc1db793a4ae1ed6f944375b512
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

webforJ 配备了一个全面的设计系统，名为 **DWC**。这不仅仅是一个主题，它是一个结构化、可扩展的系统，管理着您应用的视觉语言。DWC 的构建旨在帮助开发人员和设计师快速而自信地创建一致的、与品牌对齐的界面。

DWC 的核心提供了一套经过精心设计的 CSS 变量（设计令牌），涵盖了关键的视觉元素，如颜色、排版、边框和间距。这些令牌作为所有组件样式的基础构建块，允许全球自定义，几乎不需要额外努力。

为了支持更高级的样式，webforJ 利用 CSS Shadow Parts，允许组件内部被有选择性地样式化，而不会破坏封装性。这为团队提供了对组件外观的细粒度控制，即使在大型应用程序中也适用。

DWC 还包括一个可自定义的调色板，并默认为干净、明亮的视觉主题，但每个方面都可以根据您的品牌或产品风格进行调整。

## Figma Design Kit {#figma-design-kit}

[DWC Figma 库](https://www.figma.com/community/file/1144573845612007198/dwc-design-kit) 是创建现代企业级 Web 应用的官方设计资源。它包括一整套组件、排版样式和与 DWC 设计系统对齐的颜色令牌。利用这个库，设计师和开发人员可以构建视觉一致、用户友好的界面，平衡功能与精致的用户体验。

<img src="/img/dwc.png" alt="Figma Design Kit" style={{borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}} />

>  
<!-- > ![Figma Design Kit Screenshot](./path-to-your-screenshot.png) -->

## Topics {#topics}

<DocCardList className="topics-section" />
