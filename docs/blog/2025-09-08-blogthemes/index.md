---
title: "Transform Your webforJ App with Professional Theming"
description: "Learn webforJ's theming system from built-in themes to custom color palettes and dark mode. "
slug: guide-webforj-themes
date: 2025-09-10
authors: Lauren Alamo
tags: [webforJ, themes, styling]
hide_table_of_contents: true
---

<!-- vale Google.FirstPerson = NO -->

![cover image](/img/webforjthemes.png)

Modern applications need flexible theming systems. Users expect dark mode support, but they also want apps that reflect brand identity and adapt to different contexts. Building themes traditionally meant maintaining separate stylesheets, duplicating CSS rules, and coordinating updates across multiple files.

webforJ takes a different approach with a theming system built around CSS variables and semantic color palettes. You can implement dark mode, create custom brand themes, and switch between them at runtime.

Let's walk through how it all works together.

<!-- truncate -->

## Starting with the basics

webforJ comes with three thoughtfully designed themes that cover most common scenarios. There's `light` for clean, bright interfaces, `dark` for those who prefer darker backgrounds with your brand colors, and `dark-pure` for a more neutral approach using grayscale tones.

Each theme takes care of the details you might not think about initially, such as ensuring text has proper contrast, adjusting component styling, and maintaining visual consistency throughout your app.

Getting dark mode is wonderfully simple:

```java
@AppTheme("dark")
public class MyApp extends App {
    // Your entire app now has a cohesive dark theme
}
```

With that single annotation you can transform an entire app. Every webforJ component automatically adapts its appearance to work harmoniously with the dark theme.

## Respecting system preferences

Even better, you can let your users' system preferences guide the experience:

```java
@AppTheme("system")
@AppLightTheme("light")
@AppDarkTheme("dark-pure")  
public class AdaptiveApp extends App {
    // Follows whatever the user has chosen in their operating system
}
```

When someone has dark mode enabled in their OS, your app loads with the dark theme you specified. Switch back to light mode, and the app follows along.

## Changing themes when needed

Sometimes you'll want to give users direct control over themes. Maybe you're building a settings screen, or you'd like to let people preview different options.

Here's how you can do that:

```java
public void switchToNightMode() {
    App.setTheme("dark");
    // Everything updates smoothly and immediately
}

public void switchToLightMode() {
    App.setTheme("light");
}
```

The transition happens instantly throughout your entire app. Components update their colors, backgrounds adjust, and text remains readable.

## Creating your own themes

While the built-in themes work well for many situations, you'll often want to incorporate your own brand colors or create a unique visual identity. webforJ makes this process much more approachable than traditional CSS theming.

The system uses something called DWC (Dynamic Web Client) colors, which generate complete, harmonious color palettes from just a few basic values. You provide the hue (position on the color wheel), saturation (how vibrant), and contrast threshold (which determines text color contrast), and it creates dozens of coordinated shades along with text colors that maintain proper readability.

```css
html[data-app-theme="corporate"] {
  --dwc-color-primary-h: 215;    /* A pleasant blue */
  --dwc-color-primary-s: 75%;    /* Moderately saturated */
  --dwc-color-primary-c: 55;     /* Good contrast balance */
  
  /* You can also adjust typography */
  --dwc-font-family: "Inter", "Segoe UI", sans-serif;
  --dwc-font-size: 0.95rem;
}
```

Then you activate it in your app:

```java
@AppTheme("corporate")
@StyleSheet("ws://corporate-theme.css")
public class CorporateApp extends App {
    // Now your app reflects your brand identity
}
```

:::tip
If you prefer keeping everything in your Java file, inline styles work nicely here.
:::

You can also create multiple theme variations for different contexts:

```css
/* Standard corporate theme */
html[data-app-theme="corporate"] {
  --dwc-color-primary-h: 215;
  --dwc-color-primary-s: 75%;
  --dwc-color-primary-c: 55;
}

/* Evening variant with softer colors */
html[data-app-theme="corporate-evening"] {
  --dwc-color-primary-h: 215;
  --dwc-color-primary-s: 65%;
  --dwc-color-primary-c: 65;
  --dwc-surface-1: hsl(215, 18%, 10%);
  --dwc-surface-2: hsl(215, 18%, 14%);
}
```

## Consistent dark mode

The way webforJ handles dark mode is worth understanding because it's not what you'd expect. Rather than just swapping colors around, it focuses on preserving the visual relationships in your design.

Your color choices are really about relationships between elements. One thing needs to be darker than another, this needs to stand out against that background. When you switch themes, those relationships stay the same even though the actual colors change.


```css
.notification-card {
  background: var(--dwc-color-primary-15);      /* A light background tone */
  color: var(--dwc-color-primary-text-15);      /* Appropriate text color */
  border: 1px solid var(--dwc-color-primary-35);
}
```

In light mode, this gives you a subtle background with dark text. If you switch to dark mode, you get an appropriately dark background with light text. The visual weight and relationships stay the same, but everything adjusts to work well against the new backdrop.

The `-text` variants provide coordinated text colors that work with their corresponding background shades, helping maintain readability across different theme modes.

## Component themes

Beyond the overall app theme, individual webforJ components support their own semantic themes, and these work with any app-level theme you choose:

```java
// These adapt gracefully to your chosen app theme
Button saveButton = new Button("Save Changes")
    .setTheme(ButtonTheme.PRIMARY);
    
Button deleteButton = new Button("Delete Item") 
    .setTheme(ButtonTheme.DANGER);
    
Spinner loadingSpinner = new Spinner()
    .setTheme(Theme.INFO);
```

When you change your app's primary color, all the primary-themed components update. Switch to dark mode, and the danger theme still conveys urgency while using colors that work well in the dark environment.

## Performance and efficiency

Theme switching happens instantly across the entire app. You won't see components updating at different rates or half your buttons still showing the old theme while everything else has switched.

webforJ uses one style sheet that adapts to different themes through CSS variables instead of requiring separate style sheets for each theme. This means fewer files to manage and faster loading since the client downloads a single stylesheet that handles all theme variations. 

## Starting your theming journey

webforJ's theming system scales with your needs, letting you start simple and grow more sophisticated over time. Start with `@AppTheme("system")` to respect user preferences. Add runtime theme switching when you need user control. Create custom themes when you need specific branding.

Each step builds on the previous one. The patterns that work for simple apps scale up to complex enterprise software with multiple brands and accessibility requirements.

Users notice when apps respect their preferences and look polished across different modes. webforJ's theming system helps you deliver that level of care without getting dragged down in CSS complexity.

When you're ready to explore more advanced features, the [Themes](/docs/styling/themes), [Colors](/docs/styling/colors), and [CSS Variables](/docs/styling/css-variables) documentation has all the details you'll need.

