---
sidebar_position: 10
title: BBj Controls and webforJ Components
_i18n_hash: 929625ea8b8335de7326ecb067dca773
---
webforJ框架旨在在BBj语言的DWC周围提供Java API，提供构建和管理组件的强大架构。

## 将BBj控件映射到webforJ组件 {#mapping-bbj-controls-to-webforj-components}
webforJ的一个基本原则是将BBj控件与webforJ组件绑定。在此架构中，产品中附带的每个webforJ组件与一个基础BBj控件之间都有一一对应的映射。此映射确保Java组件无缝地反映其BBj对应物的行为和属性。

webforJ组件和BBj控件之间的紧密对应简化了开发，并允许Java开发人员在构建基于Web的应用程序时使用熟悉的概念，而无需编写任何BBj代码。

## `DwcComponent`基础类 {#the-dwccomponent-base-class}
webforJ组件架构的核心是DWCComponent基础类。所有webforJ组件都继承自此类。此继承为每个webforJ组件提供其基础BBj控件的访问权限，为Java组件与其代表的BBj控件之间提供直接链接。

然而，重要的是要注意，开发人员不能进一步扩展DWCComponent类。尝试这样做将导致运行时异常，不允许这种扩展。此限制是为了保持基础BBj控件的完整性，并确保开发人员不会无意中以可能导致意外后果的方式进行操作。

### 最终类和扩展限制 {#final-classes-and-extension-restrictions}
在webforJ中，除了内置HTML元素和任何扩展这些的类之外，大多数组件类都被声明为`final`。这意味着它们不可用于扩展或子类化。这个设计选择是故意的，服务于多个目的：

1. **对基础BBj控件的控制**：如前所述，扩展webforJ组件类将赋予开发人员对基础BBj控件的控制。为了保持组件行为的一致性和可预测性，此级别的控制是受到限制的。

2. **防止意外修改**：将组件类设为`final`可以防止对核心组件的无意修改，降低引入意外行为或漏洞的风险。

3. **促进使用组合**：为了扩展组件的功能，webforJ框架鼓励开发人员使用组合方法。组合组件是包含其他webforJ组件或标准HTML元素的Java类。虽然不鼓励传统的继承，但组合组件提供了一种创建新自定义组件的方法，封装现有组件。

## 组合组件：通过组合进行扩展 {#composite-components-extending-through-composition}
在webforJ框架中，组合组件的概念在扩展组件功能方面发挥着重要作用。组合组件是未被final关键字限制的Java类，允许开发人员创建新的组件，扩展单个组件的行为，或通过组合现有组件，将多个组件合并为一个。为开发人员使用创建了促进此行为的类。请参阅`Composite`和`ElementComposite`部分以了解如何正确创建组合组件。

这种方法鼓励更加模块化和灵活的开发风格，使开发人员能够构建满足特定要求的定制组件。
