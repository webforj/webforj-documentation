---
sidebar_position: 4
title: Element Composite
slug: element_composite
_i18n_hash: 88eca7b854822f9d78ac20731ac5a857
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

`ElementComposite` 类作为在 webforJ 应用程序中管理复合元素的多功能基础。它的主要目的是通过提供结构化的方法来处理属性、特性和事件监听器，促进与由 `Element` 类表示的 HTML 元素的交互。它使得在应用程序中实现和重用元素成为可能。在实现 Web 组件以便在 webforJ 应用程序中使用时，请使用 `ElementComposite` 类。

在使用 `ElementComposite` 类时，使用 `getElement()` 方法将提供对底层 `Element` 组件的访问。同样，`getNodeName()` 方法将提供该节点在 DOM 中的名称。

:::tip
可以单独使用 `Element` 类完成所有操作，而无需使用 `ElementComposite` 类。然而，`ElementComposite` 中提供的方法为用户提供了一种重用已完成工作的方式。
:::

在本指南中，我们将使用 `ElementComposite` 类实现 [Shoelace QR 代码网页组件](https://shoelace.style/components/qr-code)。

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## 属性和特性描述符 {#property-and-attribute-descriptors}

网页组件中的属性和特性表示组件的状态。它们通常用于管理数据或配置。`ElementComposite` 类提供了一种方便的方法来处理属性和特性。

属性和特性可以声明并初始化为正在编写的 `ElementComposite` 类的 `PropertyDescriptor` 成员，然后在代码中使用。要定义属性和特性，请使用 `set()` 方法设置属性的值。例如，`set(PropertyDescriptor<V> property, V value)` 将属性设置为指定值。

:::info
属性在组件的代码内部访问和操作，并不会反映在 DOM 中。另一方面，特性是组件的外部接口的一部分，可以用于从外部将信息传递到组件，为外部元素或脚本提供配置组件的方式。
:::

```java
// ElementComposite 类中的示例属性 TITLE
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
// ElementComposite 类中的示例特性 VALUE
private final PropertyDescriptor<String> VALUE = PropertyDescriptor.attribute("value", "");
//...
set(TITLE, "My Title");
set(VALUE, "My Value");
```

除了设置属性外，还可以利用 `ElementComposite` 类中的 `get()` 方法访问和读取属性。`get()` 方法可以接收一个可选的 `boolean` 值，默认为 false，以决定该方法是否应访问客户端以检索值。这会影响性能，但如果属性可以纯粹在客户端修改，则可能是必要的。

可以还将一个 `Type` 传递给该方法，以指示将检索结果转换为什么类型。

:::tip
这个 `Type` 并非绝对必要，并为数据检索添加了一层额外的规格。
:::

```java
// ElementComposite 类中的示例属性 TITLE
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

在下面的演示中，基于网页组件的文档为 QR 代码添加了属性。然后实现了方法，允许用户获取和设置已实现的各种属性。

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## 事件注册 {#event-registration}

事件是网页组件的重要组成部分，允许应用程序不同部分之间的通信。`ElementComposite` 类简化了事件注册和处理。要注册事件监听器，请使用 `addEventListener()` 方法为特定事件类型注册事件监听器。指定事件类、监听器和可选的事件选项。

```java
// 示例：添加单击事件监听器
addEventListener(ClickEvent.class, event -> {
    // 处理单击事件
});
```

:::info
`ElementComposite` 事件与 `Element` 事件不同，因为它不允许任何类，而仅允许指定的 `Event` 类。
:::

在下面的演示中，创建了一个单击事件并添加到 QR 代码组件中。此事件在触发时，将显示单击组件时鼠标的 "X" 坐标，该坐标作为数据提供给 Java 事件。然后实现了一个方法，以允许用户访问这些数据，这就是它在应用程序中显示的方式。
<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## 与插槽交互 {#interacting-with-slots}

网页组件通常使用插槽来允许开发者从外部定义组件的结构。插槽是网页组件内部的一个占位符，可以在使用组件时填充内容。在 `ElementComposite` 类的上下文中，插槽提供了一种自定义组件内部内容的方法。提供以下方法，使开发者能够与插槽进行交互和操作：

1. **`findComponentSlot()`**：此方法用于在组件系统中的所有插槽中搜索特定组件。它返回组件所在插槽的名称。如果组件未在任何插槽中找到，则返回空字符串。

2. **`getComponentsInSlot()`**：此方法检索分配给组件系统中给定插槽的组件列表。可选择性地传递特定类类型以过滤该方法的结果。

3. **`getFirstComponentInSlot()`**：此方法旨在获取分配给插槽的第一个组件。可选择性地传递特定类类型以过滤此方法的结果。

使用 `add()` 方法也可以指定插槽的名称作为 `String` 参数，以将传递的组件添加到所需的插槽中。

这些交互允许开发者利用网页组件的强大功能，通过提供一个干净且简单的 API 来操作插槽、属性和处理 `ElementComposite` 类中的事件。
