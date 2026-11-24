---
title: "A modern look and feel for Java Swing, in light and dark"
description: "Give a Java Swing app a modern look and feel and a dark theme with one JAR: the webforJ colors, fonts and buttons, ready for the move to the web."
slug: modern-swing-look-and-feel
date: 2026-11-24
authors: Hyyan Abo Fakher
tags: [integrations, modernization, webswing]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-11-24-modern-swing-look-and-feel/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-11-24-modern-swing-look-and-feel/cover.jpg)

Your Swing app works, and it still looks like the year it was written. Gray buttons, tiny fonts, no dark mode. Once part of it runs next to new web screens, the gap is hard to miss, and users feel like they switch apps every time they open a dialog.

A modern look and feel for Java Swing closes most of that gap without touching your screens. The DWC Look and Feel for Swing is one JAR that gives a Swing app the colors, fonts, buttons and focus outlines of webforJ. It comes with a light and a dark palette. You select it with one call at startup.

<!-- truncate -->

:::tip Try it in one minute
[Download the Order Desk demo](https://cdn.webforj.com/dwc-swing-laf/0.2.0/dwc-swing-laf-demo.jar) and run `java -jar dwc-swing-laf-demo.jar` with Java 21 or newer. Switch palettes and primary colors from the **View** menu.
:::

Here is the Order Desk demo with the theme installed. Switch the palette and the primary color below the screenshot, and select it to enlarge.

<!-- vale off -->
<ThemeGallery alt="The Order Desk Swing demo" items={[
  { label: "Default", color: "#134ddf", light: "/img/swing-laf/order-desk-light-default.webp", dark: "/img/swing-laf/order-desk-dark-default.webp" },
  { label: "Violet", color: "#7c3aed", light: "/img/swing-laf/order-desk-light-violet.webp", dark: "/img/swing-laf/order-desk-dark-violet.webp" },
  { label: "Teal", color: "#0d9488", light: "/img/swing-laf/order-desk-light-teal.webp", dark: "/img/swing-laf/order-desk-dark-teal.webp" },
  { label: "Orange", color: "#ea580c", light: "/img/swing-laf/order-desk-light-orange.webp", dark: "/img/swing-laf/order-desk-dark-orange.webp" }
]} />
<!-- vale on -->

Every control in those windows is a standard Swing component: a `JTree`, a `JTable`, a `JTabbedPane`, combo boxes, a slider, and a `JCheckBox` drawn as a switch. Only the look and feel changed.

## Install it with one call {#install}

[Download the theme JAR](https://cdn.webforj.com/dwc-swing-laf/0.2.0/dwc-swing-laf.jar) and add it to the classpath. On the event dispatch thread, select it before the first window opens:

```java
try {
  UIManager.setLookAndFeel(new DwcLookAndFeel());
} catch (UnsupportedLookAndFeelException e) {
  throw new IllegalStateException(e);
}
```

For dark mode, use `DwcDarkLookAndFeel` instead. If your app already reads the look and feel from a setting, enter the class name `com.basis.dwc.swing.laf.DwcLookAndFeel` or `com.basis.dwc.swing.laf.DwcDarkLookAndFeel`, and no code changes at all.

The JAR includes both palettes and the Inter font, and it needs Java 21 or newer.

## Use your brand color {#brand-color}

The theme reads the same token names as webforJ. One token sets the primary color for buttons, selections and focus outlines:

```java
DwcLookAndFeel.setToken("--dwc-color-primary-seed", new Color(117, 67, 188));
```

Danger buttons keep their own color, so a Delete button still reads as a warning. Compare the Delete and Save order buttons after switching to violet, teal, or orange in the gallery.

## Labels, validation and emphasis {#client-properties}

A Swing text field doesn't carry its own label, helper text, or invalid state. The theme adds them as client properties on the control:

```java
email.putClientProperty(DwcLookAndFeel.LABEL, "Delivery email");
email.putClientProperty(DwcLookAndFeel.REQUIRED, true);
save.putClientProperty(DwcLookAndFeel.THEME, ButtonTheme.PRIMARY);
```

On Save, the field shows its error right under the input:

```java
boolean missing = email.getText().isBlank();
email.putClientProperty(DwcLookAndFeel.INVALID, missing);
email.putClientProperty(DwcLookAndFeel.HELPER_TEXT,
    missing ? "Enter a delivery email." : "");
```

Other properties draw a checkbox as a switch, add stripes to a table, show tabs as a segmented control or blur the window behind a dialog. The [full theme guide](https://dwc.style/docs/#/swing/) lists them all.

## One look from desktop to web {#one-look}

The theme is the first step of a move to the web, not a replacement for it. A Swing app modernizes in three steps:

1. **Give it the new look.** Install the theme, and the Swing screens share the webforJ palette.
2. **Run it in the browser.** Webswing runs the Swing app in the browser, and `WebswingConnector` embeds it in a webforJ app.
3. **Replace screens one at a time.** Each new screen is written in Java with webforJ, and it already matches the Swing screens around it.

Without the theme, users notice every border between old and new. With it, the embedded Swing screens and the new webforJ screens share one palette, so the switch from one to the other is a lot less visible. The [Swing look and feel docs](/docs/integrations/webswing/look-and-feel) show how to match the primary color and font of your webforJ theme.

For the full path, read how to [migrate a Swing app to the web](/blog/migrate-swing-app-to-web).

## Where AI helps {#ai}

The theme needs no porting, but step 3 does. An AI assistant can draft each new webforJ screen from the Swing original, and [the AI verification loop for Java](/blog/ai-verification-loop-java) checks every screen with a test before your team reviews it.

## Frequently asked questions {#faq}

### Do I have to change my Swing screens? {#faq-change-screens}

No. The theme is selected once at startup. Client properties for labels, helper text or a primary button are optional, and you add them where you want them.

### Is there a dark theme for Java Swing? {#faq-dark-mode}

Yes. `DwcDarkLookAndFeel` is the Swing dark theme. A primary color set with `setToken` stays when you switch between light and dark.

## Put the new look on your Swing app {#next-step}

Run the Order Desk demo, then add the JAR to your own app and open its busiest screen. To plan the move to the web from there, start on the [Swing modernization page](/modernization).
