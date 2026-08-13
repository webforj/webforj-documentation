---
sidebar_position: 52
title: HTML Components
description: >-
  Compose pages with typed Java wrappers for standard HTML elements like Div,
  Anchor, Paragraph, Img, headings, and semantic containers.
_i18n_hash: 40b5b7346cf57ebc6795c87e25fe3a74
---
webforJ 提供一套组件，这些组件直接映射到标准 HTML 元素，为网页的最常见构建块提供类型化的 Java API。有关每个基础 HTML 元素的全面参考，请参见 [MDN HTML 元素参考](https://developer.mozilla.org/en-US/docs/Web/HTML/Element)。

## 可用组件 {#available-components}

以下组件可用，并映射到相应的 HTML 元素：

| webforJ 类 | HTML 元素 | 描述 | 子组件 |
|:--|:--:|:--|:--:|
| `Anchor` | `<a>` | 创建到 URL、电子邮件地址或页面内位置的超链接。 | ✔️ |
| `Article` | `<article>` | 代表一个自包含的组成部分，例如博客文章、新闻文章或论坛条目。 | ✔️ |
| `Aside` | `<aside>` | 代表与主要内容间接相关的内容，通常呈现为侧边栏。 | ✔️ |
| `Break` | `<hr>` | 代表段落级元素之间的主题分隔，呈现为水平规则。 | ❌ |
| `Div` | `<div>` | 用于分组和样式化内容的通用块级容器。 | ✔️ |
| `Emphasis` | `<em>` | 用于标记具有重音强调的文本，改变句子的意义。 | ✔️ |
| `Fieldset` | `<fieldset>` | 分组相关的表单控件及其标签。 | ✔️ |
| `Footer` | `<footer>` | 代表其最近的分区祖先的页脚，通常包含作者或导航信息。 | ✔️ |
| `FormattedText` | `<pre>` | 显示预格式化文本，精确保留空格和换行符。 | ✔️ |
| `H1` – `H6` | `<h1>` – `<h6>` | 代表六级章节标题，其中 `H1` 最重要，`H6` 最不重要。 | ✔️ |
| `Header` | `<header>` | 代表介绍性内容，通常包含标题、徽标或导航链接。 | ✔️ |
| `Iframe` | `<iframe>` | 嵌入另一个 HTML 页面作为当前页面内的嵌套浏览上下文。 | ❌ |
| `Img` | `<img>` | 将图像嵌入文档中。 | ❌ |
| `Legend` | `<legend>` | 为 `Fieldset` 的内容提供标题。 | ❌ |
| `ListEntry` | `<li>` | 代表 `OrderedList` 或 `UnorderedList` 中的单个项目。 | ✔️ |
| `Main` | `<main>` | 代表文档主体的主要内容，对页面唯一。 | ✔️ |
| `NativeButton` | `<button>` | 本机交互按钮元素，可以触发操作或提交表单。 | ✔️ |
| `Nav` | `<nav>` | 代表包含导航链接的页面部分。 | ✔️ |
| `OrderedList` | `<ol>` | 代表一个编号的有序列表。 | ✔️ |
| `Paragraph` | `<p>` | 代表一段文本。 | ✔️ |
| `Section` | `<section>` | 代表文档的通用独立部分，通常带有标题。 | ✔️ |
| `Span` | `<span>` | 用于文本和其他内联内容的通用内联容器。 | ✔️ |
| `Strong` | `<strong>` | 表示强重要性的内容，通常以粗体显示。 | ✔️ |
| `UnorderedList` | `<ul>` | 代表一个带项目符号的无序列表。 | ✔️ |

## 处理子组件 {#working-with-children}

在 **子组件** 列中标记为 ✔️ 的组件支持添加、删除和访问子组件。这些方法通过 [`Element`](../building-ui/element#component-interaction) 类提供。

要创建超出此处列出的任意 HTML 元素，或嵌入自定义网络组件，请参见 [`Element`](../building-ui/element) 文档。
