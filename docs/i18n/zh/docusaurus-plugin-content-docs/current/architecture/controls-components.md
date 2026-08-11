---
sidebar_position: 10
title: BBj Controls and webforJ Components
description: >-
  See how webforJ components map one-to-one to BBj controls through the
  DwcComponent base class and why composition replaces inheritance.
_i18n_hash: 83f48323774737067ddd9a6bebb0373d
---
webforJ框架旨在提供围绕BBj语言的DWC的Java API，提供构建和管理组件的强大架构。

## 将BBj控件映射到webforJ组件 {#mapping-bbj-controls-to-webforj-components}
webforJ的基本原则之一是将BBj控件与webforJ组件绑定。在这种架构中，产品随附的每个webforJ组件都与底层BBj控件一对一映射。此映射确保Java组件无缝反映其BBj对应物的行为和属性。

webforJ组件与BBj控件之间的这种紧密对应简化了开发，允许Java开发人员在构建基于Web的应用程序时使用熟悉的概念，而无需编写任何BBj代码。

## `DwcComponent` 基类 {#the-dwccomponent-base-class}
在webforJ的组件架构中，DWCComponent基类是核心。所有webforJ组件都继承自此类。这种继承使每个webforJ组件能够访问其底层BBj控件，为Java组件与其代表的BBj控件之间提供了直接链接。

然而，重要的是要注意，开发人员被限制不得进一步扩展DWCComponent类。尝试这样做将导致运行时异常，禁止此类扩展。此限制旨在维护底层BBj控件的完整性，并确保开发人员不会以可能导致意外后果的方式不慎操纵它。

### 最终类和扩展限制 {#final-classes-and-extension-restrictions}
在webforJ中，除了内置的HTML元素和任何扩展这些元素的类之外，大多数组件类都被声明为`final`。这意味着它们不可用于扩展或子类化。此设计选择是故意的，服务于多个目的：

1. **控制底层BBj控件**：如前所述，扩展webforJ组件类将使开发人员控制底层BBj控件。为了保持组件行为的一致性和可预测性，此级别的控制受到限制。

2. **防止意外修改**：将组件类设置为`final`可以防止对核心组件的意外修改，从而降低引入意外行为或漏洞的风险。

3. **促进复合的使用**：为了扩展组件的功能，webforJ框架鼓励开发人员采用复合方法。复合组件是包含其他webforJ组件或标准HTML元素的Java类。虽然不鼓励传统继承，但复合组件提供了一种创建新型、定制组件的方法，封装现有组件。

## 复合组件：通过组合扩展 {#composite-components-extending-through-composition}
在webforJ框架中，复合组件的概念在扩展组件功能方面发挥着关键作用。复合组件是指不受final关键字限制的Java类，允许开发人员创建新组件，以扩展单一组件的行为，或通过组合现有组件将多个组件合并为一个。已为开发人员使用创建了促进这种行为的类。请参见`Composite`和`ElementComposite`部分，以了解如何正确创建复合组件。

这种方法鼓励更模块化和灵活的开发风格，使开发人员能够构建满足特定需求的定制组件。
