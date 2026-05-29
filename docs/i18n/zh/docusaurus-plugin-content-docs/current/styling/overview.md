---
title: Styling
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: abb693dec702e4a253cf4e1228fb2d7e
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

webforJ 附带一个全面的设计系统，名为 **DWC**。这不仅仅是一个主题，它是一个结构化、可扩展的系统，规范了您的应用程序的视觉语言。DWC 的构建旨在帮助开发者和设计师快速而自信地创建一致的、与品牌对齐的界面。

在其核心，DWC 提供了一组精心设计的 CSS 变量（设计令牌），涵盖了颜色、排版、边框和间距等关键视觉元素。这些令牌为所有组件样式提供基础构建块，并允许以最小的努力进行全局自定义。

为了支持更高级的样式，webforJ 利用 CSS Shadow Parts，允许组件内部在不破坏封装的情况下被选择性地样式化。这使团队能够对组件的外观进行细粒度控制，即使在大型应用程序中也是如此。

DWC 还包括一个可自定义的色彩调色板，默认使用干净、明亮的视觉主题，但每个方面都可以根据您的品牌或产品风格进行调整。

<AISkillTip skill="webforj-styling-apps" />

## Figma 设计工具包 {#figma-design-kit}

[DWC Figma 库](https://www.figma.com/community/file/1144573845612007198/dwc-design-kit) 是创建现代企业级 Web 应用程序的官方设计资源。它包括一套全面的组件、排版样式和颜色令牌，符合 DWC 设计系统。设计师和开发者可以使用此库构建视觉一致、用户友好的接口，具有可预测的组件行为、精确的间距和可访问的颜色对比。

<img src="/img/dwc.png" alt="Figma 设计工具包" style={{borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}} />

## 主题 {#topics}

<DocCardList className="topics-section" />
