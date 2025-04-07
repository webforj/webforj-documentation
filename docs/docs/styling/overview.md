---
title: Styling
hide_table_of_contents: true
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


webforJ includes a flexible and evolving default theme called **DWC**, designed to make it easy to customize the look and feel of any app or component to match your visual style and brand identity. The UI is built with CSS and comes with pre-baked styles that are straightforward to adjust and keep a consistent and professional appearance across your projects.

The DWC theme defines a set of CSS variables - also known as design tokens. These variables cover key visual aspects like colors, typography, borders, and spacing, and serve as the foundational building blocks for all component styles. By modifying these variables, you can adapt the entire UI to your specific design needs with minimal effort.

To give developers even more control, webforJ leverages CSS Shadow Parts. These allow elements inside a component’s shadow DOM to be selectively exposed and styled from the outside, enabling deeper customization without breaking encapsulation.

DWC also provides a customizable color palette to help align your app's look with your brand or preferred color scheme. While the default appearance uses a light background, every part of the theme can be customized.

## Topics

<DocCardList className="topics-section" />