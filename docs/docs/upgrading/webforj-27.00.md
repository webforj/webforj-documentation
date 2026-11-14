---
title: Upgrade to 27.00
description: Upgrade from 26.00 to 27.00
sidebar_position: 10
sidebar_class_name: new-content
draft: true
---

This documentation serves as a guide to upgrade webforJ apps from 26.00 to 27.00.
Here are the changes needed for existing apps to continue running smoothly.
As always, see the [GitHub release overview](https://github.com/webforj/webforj/releases) for a more comprehensive list of changes between releases.

<!-- INTRO_END -->

<AutomatedUpgradeTip />

## Text and HTML content {#text-and-html-content}

Earlier versions treated a value wrapped in `<html>` and passed to `setText()` as HTML. This behavior is deprecated and is removed in webforJ 27.00. The `webforj.legacyHtmlInText` setting controls it:

- `true` (default): a value wrapped in `<html>` renders its content as HTML.
- `false`: The same value is shown literally.

The `<html>` wrapper is never displayed in either case.

The first time an `<html>` wrapped value reaches `setText()`, a warning is logged that names the component and the call site, so the call can be moved to `setHtml()`.

## Required binding using `@Size` {#required-binding-using-size-annotation}

In versions earlier than 27.00, using the [Jakarta annotation](/docs/data-binding/validation/jakarta-validation) `@Size` automatically marks the bound component as required.
Going forward, `@Size` only marks a bound component as required if it has a defined `min` value.

```java
// no longer marks the field as required
@Size(max = 80, message = "Street must be shorter than 80 characters")
private String street;
```

```java
// marks the field as required
@Size(min = 2, max = 80, message = "Street must be between 2 and 80 characters")
private String street;
```