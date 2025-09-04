---
sidebar_position: 1
title: CSS Variables
---

CSS Variables play a central role in customizing the appearance of webforJ components. These variables store reusable values such as colors, font sizes, and spacing, that can be applied consistently across your app.

Unlike traditional approaches that relied on CSS preprocessors like [SASS](https://sass-lang.com/) or [LESS](https://lesscss.org/), CSS variables allow for **dynamic styling at runtime**. They reduce repetition, improve maintainability, and make stylesheets easier to read and manage.

## Defining CSS variables {#defining-css-variables}

CSS variables are defined using a double-dash (`--`) prefix, and can be scoped within any CSS selector. However, the most common practice is to define them in the `:root` selector, which scopes them globally.

```css
:root {
  --app-background: orange;
}
```

:::tip The `:root` pseudo-class
The [`:root`](https://developer.mozilla.org/en-US/docs/Web/CSS/:root) pseudo-class targets the root element of the document—typically `<html>` in HTML. It behaves like `html`, but with higher specificity.
:::

CSS variables can hold any string, not just valid CSS values. This flexibility is particularly useful when working with JavaScript.

```css
html {
  --app-title: webforJ;
}
```

## Component-specific variables {#component-specific-variables}

To define or override a variable for a specific component, declare it within the component’s selector:

```css
dwc-button {
  --dwc-button-font-weight: 400;
}
```

:::tip Component-Specific Styling Reference
Each webforJ component supports a specific set of CSS variables that control its appearance. These are documented under the **Styling > CSS properties** section for each component. 
:::


## Using CSS variables {#using-css-variables}

Use the [`var()`](https://developer.mozilla.org/en-US/docs/Web/CSS/var()) function to apply the value of a variable in your styles:

```css
.panel {
  background-color: var(--app-background);
}
```

You can also specify a fallback value in case the variable isn't defined:

```css
.frame {
  background-color: var(--app-background, red);
}
```

## Manipulating variables with webforJ {#manipulating-variables-with-webforj}

CSS variables can be dynamically updated via webforJ API, enabling real-time styling:

```java
// Set a CSS variable
button.setStyle('--dwc-button-font-weight', '400');
```

:::tip Manipulating CSS Variables with JavaScript
webforJ allows you to execute JavaScript on the client side using the Page or Element API. This means you can dynamically manipulate CSS variables at runtime just like you would in standard web applications.

```javascript
// Set a CSS variable
const el = document.querySelector('dwc-button');
el.style.setProperty('--dwc-button-font-weight', '400');

// Get a CSS variable
const value = el.style.getPropertyValue('--dwc-font-size-m');
```
:::

## Additional resources {#additional-resources}

- [Using CSS Custom Properties (MDN)](https://developer.mozilla.org/en-US/docs/Web/CSS/Using_CSS_custom_properties)  
- [A Complete Guide to Custom Properties (CSS-Tricks)](https://css-tricks.com/a-complete-guide-to-custom-properties/)
