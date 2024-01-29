---
sidebar_position: 3
title: HTML Element Components
slug: html_components
---

While the DWCj's `Element` class allows users to create HTML elements in their applications, a suite of standard HTML elements has been included with the DWCj's core components for ease of use. 

The following components can be used, and map to the corresponding HTML elements:

<!-- |DWCj Class|HTML Element|MDN Article|
|:--:|:--:|:------:|
|`Anchor`|`<a>`|[MDN Documentation](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/a)| -->
|DWCj Class|HTML Element|Can Add Components?|
|:--:|:--:|:--:|
|`Anchor`|`<a>`| ✔️ |
|`Article`|`<article>`| ✔️ |
|`Aside`|`<aside>`| ✔️ |
|`Break`|`<hr>`| ❌ |
|`Div`|`<div>`| ✔️ |
|`Emphasis`|`<em>`| ✔️ |
|`Fieldset`|`<fieldset>`| ✔️ |
|`Footer`|`<footer>`| ✔️ |
|`FormattedText`|`<pre>`| ✔️ |
|`H1 - H6`|`<h1> - <h6>`| ✔️ |
|`Header`|`<header>`| ✔️ |
|`Iframe`|`<iframe>`| ❌ |
|`Img`|`<img>`| ❌ |
|`Legend`|`<legend>`| ❌ |
|`ListEntry`|`<li>`| ✔️ |
|`Main`|`<main>`| ✔️ |
|`Nav`|`<nav>`| ✔️ |
|`OrderedList`|`<ol>`| ✔️ |
|`Paragraph`|`<p>`| ✔️ |
|`Section`|`<section>`| ✔️ |
|`Span`|`<span>`| ✔️ |
|`Strong`|`<strong>`| ✔️ |
|`UnorderedList`|`<ul>`| ✔️ |

These components have been implemented to provide an API for interaction with the various properties, attributes and functionalities expected from these elements. 

Methods are also provided to add to, remove from, and access components, as outlined in this section of the [`Element`](../element.md#component-interaction) class. 