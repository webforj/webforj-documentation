---
title: Styling
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: dfbf68fb580d6fb1622f513be8983739
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

webforJ 附带一个全面的设计系统，名为 **DWC**。这不仅仅是一个主题，它是一个结构化、可扩展的系统，管理着您应用的视觉语言。DWC 旨在帮助开发者和设计师快速自信地创建一致且符合品牌的界面。

DWC 的核心提供了一套精心设计的 CSS 变量（设计令牌），涵盖了关键的视觉元素，如颜色、排版、边框和间距。这些令牌作为所有组件样式的基础构建块，允许以最小的努力进行全局自定义。

为了支持更高级的样式，webforJ 利用 CSS Shadow Parts，使组件内部可以选择性地进行样式设置，而不会破坏封装。这使团队能够对组件的外观进行细致的控制，即使在更大的应用程序中也同样适用。

DWC 还包括一个可定制的颜色调色板，默认采用干净、明亮的视觉主题，但每个方面都可以根据您的品牌或产品风格进行调整。

## Figma 设计工具包 {#figma-design-kit}

[DWC Figma 库](https://www.figma.com/community/file/1144573845612007198/dwc-design-kit) 是创建现代企业级 Web 应用程序的官方设计资源。它包括与 DWC 设计系统一致的全面组件、排版样式和颜色令牌。借助此库，设计师和开发者可以构建视觉上一致、用户友好的界面，平衡功能性与精致的用户体验。

<img src="/img/dwc.png" alt="Figma 设计工具包" style={{borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}} />

## 主题 {#topics}

<DocCardList className="topics-section" />
