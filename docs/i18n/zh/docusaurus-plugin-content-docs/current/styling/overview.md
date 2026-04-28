---
title: Styling
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 04ddb356576ffb59456111d5b45fd4da
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

webforJ 附带一个全面的设计系统，名为 **DWC**。它不仅仅是一个主题，而是一个结构化、可扩展的系统，管理着您应用程序的视觉语言。DWC 的构建旨在帮助开发人员和设计师快速自信地创建一致、品牌对齐的界面。

DWC 的核心提供了一组精心设计的 CSS 变量（设计令牌），覆盖了颜色、排版、边框和间距等关键视觉元素。这些令牌作为所有组件样式的基础构建模块，允许以最小的努力进行全局自定义。

为了支持更高级的样式，webforJ 利用 CSS Shadow Parts，允许组件内部被选择性地样式化，而不打破封装。这使得团队可以细致地控制组件的外观，即使在更大的应用程序中也一样。

DWC 还包括一个可自定义的颜色调色板，并默认为干净、明亮的视觉主题，但每个方面都可以根据您的品牌或产品风格进行调整。

## Topics {#topics}

<DocCardList className="topics-section" />
