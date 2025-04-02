---
sidebar_position: 2
title: Shadow Parts
---

CSS Shadow Parts give developers a way to style elements inside a component’s shadow DOM from the outside, while still preserving encapsulation.

## Introduction

The webforJ components are built using [Web Components](https://developer.mozilla.org/en-US/docs/Web/Web_Components), which rely on the [Shadow DOM](https://developer.mozilla.org/en-US/docs/Web/Web_Components/Using_shadow_DOM) to encapsulate a component’s internal structure and styles.

:::tip Web Components
Web Components are a suite of technologies that let you create reusable, encapsulated custom elements for use in web applications.
:::

The **Shadow DOM** prevents internal styles and markup from leaking out of the component or being affected by external styles. This encapsulation ensures that components remain self-contained, reducing the risk of styling conflicts.

:::tip  Web Components Encapsulation
Encapsulation is a key benefit of Web Components. By keeping the structure, styles, and behavior of a component separate from the rest of your app, you avoid clashes and maintain clean, maintainable code.
:::

However, because of this encapsulation, you **can't directly style** elements inside a shadow DOM using standard CSS selectors.

For example, the `dwc-button` component renders the following structure:

```html
<dwc-button>
  #shadow-root (open)
  <span class="control__prefix">...</span>
  <span class="control__label">Button</span>
  <span class="control__suffix">...</span>
  ...
</dwc-button>
```

If you try to style the `label` like this:

```css
/* Does NOT work */
dwc-button .control__label {
  color: pink;
}
```

it won’t have any effect, because the `.control__label` element lives inside the shadow root.

This is where **CSS Shadow Parts** come in.

## Styling with shadow parts

Shadow parts allow external stylesheets to target specific elements inside a shadow tree—but **only if** those elements are explicitly marked as “exposed” by the component.

### How parts are exposed

To expose an element for external styling, the component author must assign a `part` attribute to it inside the shadow DOM.

All webforJ components automatically expose relevant parts for styling. You can find the list of supported parts in the **Styling > Shadow parts** section of each component’s documentation.

For example, the `dwc-button` component exposes parts like `prefix`, `label`, and `suffix`:

```html
<dwc-button>
  #shadow-root (open)
  <span part="prefix" class="control__prefix">...</span>
  <span part="label" class="control__label">Button</span>
  <span part="suffix" class="control__suffix">...</span>
</dwc-button>
```

Once exposed, these parts can be styled from outside the component using the [`::part()`](https://developer.mozilla.org/en-US/docs/Web/CSS/::part) pseudo-element.


### The `::part()` pseudo-element

The `::part()` selector allows you to apply styles to elements within the shadow DOM that have been marked with a `part` attribute.

For example, to change the color of the `label` part in a `dwc-button`:

```css
dwc-button::part(label) {
  color: red;
}
```

You can combine `::part()` with other selectors, like `:hover`:

```css
dwc-button::part(label):hover {
  color: pink;
}
```

:::warning Limitations of ::part() Selector
You can't select *inside* a shadow part. The following will **not** work:

```css
/* Does NOT work */
dwc-button::part(label) span {
  /* CSS goes here */
}
```
:::