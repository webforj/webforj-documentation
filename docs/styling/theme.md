---
sidebar_position: 2
displayed_sidebar: documentationSidebar
title: Themes
---

webforJ ships three themes by default, as well as with the ability to create your own, custom theme. The default themes are light theme, which is the default with a light background, dark which has a dark background tinted with the primary color and dark-pure which has a pure dark background (gray tones, not fully black).

To change the theme of your application, you can use the `@AppTheme` annotation or the `App.setTheme()` method. When using this annotation, the theme name should be one of the following: `system`, `light`, `dark`, `dark-pure` or the name of a custom theme.

```java
@AppTheme("dark-pure")
//or
App.setTheme("dark-pure");
```

:::info
Using the API's `App.setTheme()` results in runtime theme assignment - for users wishing to enable the ability to change themes dynamically while the program is run, the API's `App.setTheme()` method will be the proper tool.
:::

### Overriding Themes

It is possible to override the light theme of your application by overriding the CSS Properties defined in [`:root`](https://developer.mozilla.org/en-US/docs/Web/CSS/:root).

:::note
The `:root` CSS pseudo-class matches the root element of a tree representing the document. In HTML, `:root` represents the element and is identical to the selector html, except that its specificity is higher.
:::

An example of this behavior would be as follows:

```css
:root {
  --dwc-color-primary-h: 215;
  --dwc-color-primary-s: 100%;
  --dwc-color-primary-c: 50;
  --dwc-font-size: var(--dwc-font-size-m);
}
```

To override the `dark` or `dark-pure` theme, you should define your variables using the `html[data-app-theme='dark']` and `html[data-app-theme='dark-pure']` rules respectively. For example:

```css
html[data-app-theme='dark'] {
  --dwc-color-primary-s: 9%;
  --dwc-color-white: hsl(210, 17%, 82%);
}
```

### Creating Custom Themes

In addition to the themes that come with webforJ, it is also possible to create your own application themes. Themes can co-exist in the same application, allowing you to switch between themes dynamically.

New themes should be defined in `html[data-app-theme='THEME_NAME']` in your application stylesheet:

```css
html[data-app-theme='new-theme'] {
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
  --dwc-color-primary-c: 60;
}
```

### Using the Themer

The [Themer tool](https://demo.webforj.com/webapp/DWCThemer) allows you to modify the various palettes and colors supported by webforJ to either override an existing theme, or creating your own.

To do this, open the tool, and modify the various color, typography, spacing and other options to your preferred values. Once this has been done, export the theme by clicking the "Export" button at the top left of the screen.

This will open a dialog with various options which will allow you to change the name of the generated `.css` file, give the theme a name, etc. 

In particular, note the **"Output Format"** section - this section allows the user to decide whether to override the current theme, or generate a new one that can be switched to dynamically. If the **"root"** option is chosen, the corresponding CSS that is generated will apply to the `:root` CSS pseudo-class and override existing theme rules. Selecting **named_html** will instead create a new theme in the `html[data-app-theme='YOUR-THEME']` format.  

