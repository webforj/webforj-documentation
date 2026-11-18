---
sidebar_position: 1000
title: 术语表
sidebar_class_name: sidebar--item__hidden
slug: glossary
description: >-
  Definitions for terms used across webforJ documentation, including DOM, Shadow
  DOM, and other web platform and framework concepts.
_i18n_hash: b8c6955dd483c1d5904668a163666015
---
## DOM {#dom}

DOM（文档对象模型）是用于处理 Web 文档的编程接口。它将页面结构表示为对象树，其中每个节点对应一个 HTML 元素。JavaScript 和 Web 框架使用 DOM 动态访问和操作网页的内容、结构和样式。

## Shadow DOM {#shadow-dom}

Shadow DOM 是一项 Web 标准，允许将 DOM（文档对象模型）和 CSS 封装在特定元素内，形成影子树。这个隔离的 DOM 和 CSS 与主文档的 DOM 分离，从而为组件创建作用域边界。借助 Shadow DOM，可以创建自包含且可复用的 Web 组件，并将其添加到网页中，而不必担心与页面上的其他样式和脚本发生冲突。

它还引入了影子部件和插槽等功能，开发者可以通过这些功能向父页面开放影子树中的特定部分，以供自定义。这样既能灵活地向组件传递内容并自定义其外观，又能保持封装性。

## 作用域组件 {#scoped-components}

样式被隔离、以避免与组件外部样式发生冲突的组件。这种隔离通过为组件样式添加唯一的数据属性实现，而不是依赖原生 Shadow DOM。
