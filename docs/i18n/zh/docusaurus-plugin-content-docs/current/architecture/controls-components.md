---
sidebar_position: 10
title: 组件架构
description: >-
  See how webforJ components are built on the DwcComponent base class, why
  component classes are final, and why composition replaces inheritance.
_i18n_hash: 13c29b780cb00a918fe6e0a98c5e783a
---
webforJ 为你提供一个用于组件库的 Java API，并在服务器端管理每个组件。本页将介绍组件的结构以及如何扩展组件。

## 组件的结构 {#how-components-are-structured}
产品中随附的每个 webforJ 组件都是一个 Java 类，与 DWC 组件库中的一个客户端 Web 组件一一对应。Java 类在服务器端保存组件的状态并提供其 API，框架则确保浏览器中的 Web 组件与其保持同步。

你只需使用 Java 类。组件的属性、事件和浏览器中的渲染均由框架处理，因此你可以仅使用 Java 构建 Web 应用。

## `DwcComponent` 基类 {#the-dwccomponent-base-class}
DWCComponent 基类是 webforJ 组件架构的核心。所有 webforJ 组件都继承自此类。它负责管理 Java 组件与其客户端对应组件之间的关联，包括组件的创建、属性更新和事件处理。

不过，需要注意的是，开发者不得进一步扩展 DWCComponent 类。尝试这样做会导致运行时异常，从而禁止此类扩展。这项限制旨在维护两者之间关联的完整性，并确保开发者不会无意中以可能造成意外后果的方式操纵该关联。

### final 类和扩展限制 {#final-classes-and-extension-restrictions}
在 webforJ 中，除内置 HTML 元素及其子类外，大多数组件类都声明为 `final`。这意味着无法扩展这些类或创建它们的子类。此设计是有意为之，主要有以下几个目的：

1. **控制客户端关联**：如前所述，扩展 webforJ 组件类会暴露 Java 组件与其客户端对应组件之间的内部关联。为保持组件行为的一致性和可预测性，这种程度的控制受到限制。

2. **防止意外修改**：将组件类声明为 `final`，可以防止意外修改核心组件，从而降低引入意外行为或漏洞的风险。

3. **鼓励使用组合组件**：为扩展组件的功能，webforJ 框架鼓励开发者采用组合方式。组合组件是包含其他 webforJ 组件或标准 HTML 元素的 Java 类。虽然不鼓励传统的继承方式，但组合组件可以让你创建封装现有组件的全新自定义组件。

<!-- ## 将组件添加到 DOM
TODO: 介绍 webforJ 如何解析组件的客户端对应组件 -->

## 组合组件：通过组合进行扩展 {#composite-components-extending-through-composition}
在 webforJ 框架中，组合组件在扩展组件功能方面发挥着关键作用。组合组件是不会受到 `final` 关键字限制的 Java 类。开发者可以通过组合现有组件，创建能够扩展单个组件行为或将多个组件合并为一个组件的新组件。框架提供了用于实现此功能的类，供开发者使用。请参阅 `Composite` 和 `ElementComposite` 部分，了解如何正确创建组合组件。

这种方式鼓励采用更模块化、更灵活的开发风格，使开发者能够构建满足特定需求的定制组件。

<!--
## 组件层级 {#component-hierarchy}
TODO: 创建并展示示意图 -->
