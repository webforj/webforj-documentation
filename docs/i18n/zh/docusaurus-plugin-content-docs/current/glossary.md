---
sidebar_position: 1000
title: Glossary
sidebar_class_name: sidebar--item__hidden
slug: glossary
description: >-
  Definitions for terms used across webforJ documentation, including DOM, Shadow
  DOM, and other web platform and framework concepts.
_i18n_hash: 5567c28b0575afa1ce7a9fcafcbe429c
---
## DOM {#dom}

DOM（文档对象模型）是网页文档的编程接口。它将页面结构表示为一个对象树，其中每个节点对应于一个HTML元素。JavaScript和网页框架使用DOM动态访问和操控网页的内容、结构和样式。

## Shadow DOM {#shadow-dom}

Shadow DOM是一种网络标准，允许在特定元素内封装DOM（文档对象模型）和CSS，称为影子树。这个隔离的DOM和CSS与主文档DOM是分开的，有效地为组件创建了一个作用域边界。Shadow DOM有助于创建自包含的可重用网页组件，可以将其添加到网页中，而无需担心与页面上其他样式和脚本的冲突。

它还引入了像影子部分和插槽的功能，允许开发者为父页面自定义而暴露影子树的某些部分。这提供了一种灵活的方法将内容传入组件，并自定义其外观，同时保持封装性。
