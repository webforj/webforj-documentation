---
sidebar_position: 155
title: HTML Element Components
_i18n_hash: b2842b1d092327e8364bbe72fc09ac49
---
webforJ 提供了一组组件，这些组件直接映射到标准 HTML 元素，为您提供最常见网页构建块的类型化 Java API。有关每个基础 HTML 元素的全面参考，请参阅 [MDN HTML 元素参考](https://developer.mozilla.org/en-US/docs/Web/HTML/Element)。

## 可用组件

以下组件可用，并映射到其对应的 HTML 元素：

| webforJ 类 | HTML 元素 | 描述 | 子组件 |
|:--|:--:|:--|:--:|
| `Anchor` | `<a>` | 创建指向 URL、电子邮件地址或页面内位置的超链接。 | ✔️ |
| `Article` | `<article>` | 表示一个独立的组成部分，例如博客文章、新闻文章或论坛条目。 | ✔️ |
| `Aside` | `<aside>` | 表示与主要内容间接相关的内容，通常渲染为侧边栏。 | ✔️ |
| `Break` | `<hr>` | 表示段落级元素之间的主题分隔，渲染为水平规则。 | ❌ |
| `Div` | `<div>` | 用于分组和样式化内容的通用块级容器。 | ✔️ |
| `Emphasis` | `<em>` | 标记文本以强调重音，改变句子的意义。 | ✔️ |
| `Fieldset` | `<fieldset>` | 分组相关的表单控件及其标签。 | ✔️ |
| `Footer` | `<footer>` | 表示其最近的分区祖先的页脚，通常包含作者信息或导航信息。 | ✔️ |
| `FormattedText` | `<pre>` | 显示预格式化的文本，准确保留空格和换行符。 | ✔️ |
| `H1` – `H6` | `<h1>` – `<h6>` | 表示六个级别的章节标题，其中 `H1` 重要性最高，`H6` 重要性最低。 | ✔️ |
| `Header` | `<header>` | 表示介绍性内容，通常包含标题、徽标或导航链接。 | ✔️ |
| `Iframe` | `<iframe>` | 将另一个 HTML 页面作为嵌套浏览上下文嵌入当前页面。 | ❌ |
| `Img` | `<img>` | 将图像嵌入到文档中。 | ❌ |
| `Legend` | `<legend>` | 为 `Fieldset` 的内容提供标题。 | ❌ |
| `ListEntry` | `<li>` | 表示 `OrderedList` 或 `UnorderedList` 中的单个项目。 | ✔️ |
| `Main` | `<main>` | 表示文档主体的主要内容，唯一于页面。 | ✔️ |
| `NativeButton` | `<button>` | 原生互动按钮元素，可以触发操作或提交表单。 | ✔️ |
| `Nav` | `<nav>` | 表示包含导航链接的页面部分。 | ✔️ |
| `OrderedList` | `<ol>` | 表示项目的编号有序列表。 | ✔️ |
| `Paragraph` | `<p>` | 表示一段文本。 | ✔️ |
| `Section` | `<section>` | 表示文档的通用独立部分，通常带有标题。 | ✔️ |
| `Span` | `<span>` | 文本及其他内联内容的通用内联容器。 | ✔️ |
| `Strong` | `<strong>` | 表示内容的重要性，通常以粗体显示。 | ✔️ |
| `UnorderedList` | `<ul>` | 表示项目的项目符号无序列表。 | ✔️ |

## 处理子组件

在 **子组件** 列中标记为 ✔️ 的组件支持添加、移除和访问子组件。这些方法通过 [`Element`](../building-ui/element#component-interaction) 类提供。

有关创建此处列出的 HTML 元素以外的任意 HTML 元素或嵌入自定义 web 组件的文档，请参见 [`Element`](../building-ui/element) 文档。
