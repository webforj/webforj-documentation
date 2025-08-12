---
sidebar_position: 10
title: BBj Controls and webforJ Components
_i18n_hash: 7fd4306a016d3734d34336b8136c6e11
---
webforJ框架旨在提供一个围绕BBj语言的DWC的Java API，提供了一种构建和管理组件的强大架构。

## 将BBj控件映射到webforJ组件 {#mapping-bbj-controls-to-webforj-components}
webforJ的一个基本原则是将BBj控件与webforJ组件绑定。在这种架构中，随着产品发布的每个webforJ组件与一个基础的BBj控件都有一一对应的映射。这种映射确保Java组件无缝地镜像其BBj对应的行为和属性。

webforJ组件与BBj控件之间的这种紧密对应简化了开发，并允许Java开发人员在构建基于Web的应用程序时使用熟悉的概念，而无需编写任何BBj代码。

## `DwcComponent` 基类 {#the-dwccomponent-base-class}
在webforJ的组件架构的核心是DWCComponent基类。所有webforJ组件都继承自这个类。这种继承赋予每个webforJ组件访问其底层BBj控件的权限，提供Java组件与其所表示的BBj控件之间的直接链接。

然而，需要注意的是，开发人员被限制不得进一步扩展DWCComponent类。尝试这样做将导致运行时异常，禁止此类扩展。这一限制旨在维护底层BBj控件的完整性，并确保开发人员不会无意中以可能导致意外后果的方式操纵它。

### 最终类和扩展限制 {#final-classes-and-extension-restrictions}
在webforJ中，大多数组件类，除了内置的HTML元素和任何扩展这些类的类外，都是声明为`final`的。这意味着它们无法被扩展或子类化。这一设计选择是经过深思熟虑的，服务于多个目的：

1. **控制底层BBj控件**: 如前所述，扩展webforJ组件类将授予开发人员对底层BBj控件的控制。为了维护组件行为的一致性和可预测性，这种控制级别受到限制。

2. **防止意外修改**: 将组件类声明为`final`可以防止对核心组件的无意修改，从而降低引入意外行为或漏洞的风险。

3. **促进复合组件的使用**: 为了扩展组件的功能，webforJ框架鼓励开发人员使用复合方法。复合组件是包含其他webforJ组件或标准HTML元素的Java类。虽然传统的继承不被鼓励，但复合组件提供了一种创建新自定义组件的方法，这些组件封装了现有组件。

## 复合组件：通过组合扩展 {#composite-components-extending-through-composition}
在webforJ框架中，复合组件的概念在扩展组件功能中发挥着关键作用。复合组件是没有被final关键字限制的Java类，允许开发人员创建新的组件，这些组件扩展了单个组件的行为，或将多个组件组合成一个，方法是组合现有组件。为开发人员使用而创建的类支持这种行为。请参见`Composite`和`ElementComposite`部分，以了解如何正确创建复合组件。

这种方法鼓励更模块化和灵活的开发风格，使开发人员能够构建满足特定要求的定制组件。
