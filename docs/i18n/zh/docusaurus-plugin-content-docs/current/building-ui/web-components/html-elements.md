---
sidebar_position: 3
title: HTML 元素组件
_i18n_hash: 86c5e2ee07360ad502e8857eb68cebe2
---
虽然 webforJ 的 `Element` 类允许用户在他们的应用程序中创建 HTML 元素，但 webforJ 的核心组件中包含了一套标准 HTML 元素以简化使用。

以下组件可供使用，并映射到相应的 HTML 元素：

|webforJ 类|HTML 元素|可以添加组件吗？|
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
|`NativeButton`|`<button>`| ✔️ |
|`Nav`|`<nav>`| ✔️ |
|`OrderedList`|`<ol>`| ✔️ |
|`Paragraph`|`<p>`| ✔️ |
|`Section`|`<section>`| ✔️ |
|`Span`|`<span>`| ✔️ |
|`Strong`|`<strong>`| ✔️ |
|`UnorderedList`|`<ul>`| ✔️ |

这些组件的实现旨在提供一个 API，以便与这些元素的各种属性、特性和功能进行交互。

还提供了方法来添加、删除和访问组件，如 [`Element`](../elements.md#component-interaction) 类的本节中所述。
