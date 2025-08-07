---
sidebar_position: 3
title: HTML Element Components
_i18n_hash: 6fa4ad451aa506a0ebdc669f73097879
---
虽然 webforJ 的 `Element` 类允许用户在其应用程序中创建 HTML 元素，但 webforJ 的核心组件中包含了一套标准 HTML 元素，以方便使用。

以下组件可以使用，并映射到相应的 HTML 元素：

| webforJ 类       | HTML 元素         | 可以添加组件？ |
|:----------------:|:----------------:|:--------------:|
| `Anchor`         | `<a>`            | ✔️             |
| `Article`        | `<article>`      | ✔️             |
| `Aside`          | `<aside>`        | ✔️             |
| `Break`          | `<hr>`           | ❌             |
| `Div`            | `<div>`          | ✔️             |
| `Emphasis`       | `<em>`           | ✔️             |
| `Fieldset`       | `<fieldset>`     | ✔️             |
| `Footer`         | `<footer>`       | ✔️             |
| `FormattedText`  | `<pre>`          | ✔️             |
| `H1 - H6`       | `<h1> - <h6>`    | ✔️             |
| `Header`         | `<header>`       | ✔️             |
| `Iframe`         | `<iframe>`       | ❌             |
| `Img`            | `<img>`          | ❌             |
| `Legend`         | `<legend>`       | ❌             |
| `ListEntry`      | `<li>`           | ✔️             |
| `Main`           | `<main>`         | ✔️             |
| `NativeButton`   | `<button>`       | ✔️             |
| `Nav`            | `<nav>`          | ✔️             |
| `OrderedList`    | `<ol>`           | ✔️             |
| `Paragraph`      | `<p>`            | ✔️             |
| `Section`        | `<section>`      | ✔️             |
| `Span`           | `<span>`         | ✔️             |
| `Strong`         | `<strong>`       | ✔️             |
| `UnorderedList`   | `<ul>`           | ✔️             |

这些组件已经实现，以提供与这些元素预期的不同属性、属性和功能进行交互的 API。

还提供了方法以添加、移除和访问组件，如 [`Element`](../elements.md#component-interaction) 类的这一部分所述。
