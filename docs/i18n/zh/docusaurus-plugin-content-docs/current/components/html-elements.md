---
sidebar_position: 52
title: HTML组件
description: >-
  Compose pages with typed Java wrappers for standard HTML elements like Div,
  Anchor, Paragraph, Img, headings, and semantic containers.
_i18n_hash: b8c2cd3c3c48b561e6796ef23aa73fe7
---
webforJ 提供了一组与标准 HTML 元素直接映射的组件，为最常见的网页构建块提供了一个类型化的 Java API。有关每个基础 HTML 元素的全面参考，请参阅 [MDN HTML 元素参考](https://developer.mozilla.org/en-US/docs/Web/HTML/Element)。

## 可用组件 {#available-components}

以下组件可用，并映射到其对应的 HTML 元素：

| webforJ 类 | HTML 元素 | 描述 | 子元素 |
|:--|:--:|:--|:--:|
| `Anchor` | `<a>` | 创建指向 URL、电子邮件地址或页面内位置的超链接。 | ✔️ |
| `Article` | `<article>` | 表示一个自包含的内容，如博客文章、新闻文章或论坛条目。 | ✔️ |
| `Aside` | `<aside>` | 表示与主内容间接相关的内容，通常以侧边栏形式呈现。 | ✔️ |
| `Break` | `<hr>` | 表示段落级元素之间的主题分隔，渲染为水平规则。 | ❌ |
| `Div` | `<div>` | 用于分组和样式化内容的通用块级容器。 | ✔️ |
| `Emphasis` | `<em>` | 标记具有重音强调的文本，改变句子的意义。 | ✔️ |
| `Fieldset` | `<fieldset>` | 分组相关的表单控件及其标签。 | ✔️ |
| `Footer` | `<footer>` | 表示其最近的分section 祖先的页脚，通常包含作者或导航信息。 | ✔️ |
| `FormattedText` | `<pre>` | 显示预格式化的文本，精确保留空格和换行符。 | ✔️ |
| `H1` – `H6` | `<h1>` – `<h6>` | 表示六个级别的章节标题，`H1` 为最重要，`H6` 为最不重要。 | ✔️ |
| `Header` | `<header>` | 表示介绍性内容，通常包含标题、徽标或导航链接。 | ✔️ |
| `Iframe` | `<iframe>` | 嵌入另一个 HTML 页面作为当前页面内的嵌套浏览上下文。 | ❌ |
| `Img` | `<img>` | 将图像嵌入文档中。 | ❌ |
| `Legend` | `<legend>` | 为 `Fieldset` 的内容提供标题。 | ❌ |
| `ListEntry` | `<li>` | 表示 `OrderedList` 或 `UnorderedList` 中的单个项目。 | ✔️ |
| `Main` | `<main>` | 表示文档主体的主要内容，是页面唯一的。 | ✔️ |
| `NativeButton` | `<button>` | 一种本地交互按钮元素，可以触发操作或提交表单。 | ✔️ |
| `Nav` | `<nav>` | 表示包含导航链接的页面部分。 | ✔️ |
| `OrderedList` | `<ol>` | 表示一个编号的有序项目列表。 | ✔️ |
| `Paragraph` | `<p>` | 表示一段文本。 | ✔️ |
| `Section` | `<section>` | 表示文档的一个通用独立部分，通常有一个标题。 | ✔️ |
| `Span` | `<span>` | 一个通用的行内容器，用于文本和其他行内内容。 | ✔️ |
| `Strong` | `<strong>` | 表示具有重要性的内容，通常以粗体呈现。 | ✔️ |
| `UnorderedList` | `<ul>` | 表示一个带项目符号的无序项目列表。 | ✔️ |

## 处理子元素 {#working-with-children}

每个 HTML 组件都扩展了 `HtmlComponent`。在 **子元素** 列中标记为 ✔️ 的组件扩展了 `HtmlComponentContainer`，它添加了添加、删除和访问子组件的方法。没有子元素的组件，如 `Break`，直接扩展 `HtmlComponent`。

有关创建此处未列出的任意 HTML 元素，或嵌入自定义网页组件的信息，请参阅 [`Element`](../building-ui/element) 文档。
