---
sidebar_position: 1000
title: Glossary
sidebar_class_name: sidebar--item__hidden
slug: glossary
_i18n_hash: ac52f13825f3d8139dbc48fba5c7f13e
---
## DOM {#dom}

DOM（文档对象模型）是一个用于网页文档的编程接口。它将页面结构表示为对象树，树中的每个节点对应一个HTML元素。JavaScript和网页框架使用DOM动态访问和操控网页的内容、结构和样式。

## Shadow DOM {#shadow-dom}

Shadow DOM是一种网络标准，允许在特定元素内封装DOM（文档对象模型）和CSS，这个特定元素被称为影子树。这个孤立的DOM和CSS与主文档DOM是分开的，有效地为组件创建了一个作用域边界。Shadow DOM有助于创建自包含的、可重用的网页组件，这些组件可以添加到网页中，而不用担心与页面上其他样式和脚本的冲突。

它还引入了一些特性，如影子部件和插槽，允许开发人员将影子树中的某些部分暴露出来，以便父页面进行自定义。这提供了一种灵活的方式来将内容传递到组件中，并自定义其外观，同时保持封装性。
