---
sidebar_position: 4
title: Element Composite
slug: element_composite
_i18n_hash: 78629dd08e77cbd5f111aabb094f8db8
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

`ElementComposite` 类作为一个多功能的基础，旨在管理 webforJ 应用程序中的复合元素。它的主要目的是通过提供一种结构化的方法来处理属性、特性和事件监听器，以便与由 `Element` 类表示的 HTML 元素进行交互。它允许在应用程序中实现和重用元素。在为 webforJ 应用程序实现 Web 组件时，请使用 `ElementComposite` 类。

使用 `ElementComposite` 类时，使用 `getElement()` 方法将使您能够访问底层的 `Element` 组件。类似地，`getNodeName()` 方法将为您提供该节点在 DOM 中的名称。

:::tip
实际上可以只使用 `Element` 类本身，而无需使用 `ElementComposite` 类。但是，`ElementComposite` 中提供的方法为用户提供了一种重用已完成工作的方式。
:::

在本指南中，我们将使用 `ElementComposite` 类实现 [Shoelace QR 代码 Web 组件](https://shoelace.style/components/qr-code)。

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## 属性和属性描述符 {#property-and-attribute-descriptors}

Web 组件中的属性和属性表示组件的状态。它们通常用于管理数据或配置。`ElementComposite` 类提供了一种方便的方式来处理属性和属性。

可以将属性和属性声明为 `ElementComposite` 类中的 `PropertyDescriptor` 成员，并在代码中使用。要定义属性和属性，请使用 `set()` 方法设置属性的值。例如，`set(PropertyDescriptor<V> property, V value)` 将属性设置为指定值。

:::info
属性在组件代码内部访问和操作，并不会反映在 DOM 中。而属性则是组件外部接口的一部分，可以用于将信息从外部传递到组件，为外部元素或脚本配置组件提供了一种方式。
:::

```java
// 在 ElementComposite 类中名为 TITLE 的示例属性
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
// 在 ElementComposite 类中名为 VALUE 的示例属性
private final PropertyDescriptor<String> VALUE = PropertyDescriptor.attribute("value", "");
//...
set(TITLE, "我的标题");
set(VALUE, "我的值");
```

除了设置属性外，还可以在 `ElementComposite` 类中利用 `get()` 方法访问和读取属性。`get()` 方法可以传递一个可选的 `boolean` 值，默认为 false，用于指示该方法是否应该请求客户端以检索值。这会影响性能，但如果属性可以在客户端单独修改，则可能是必要的。

还可以将一个 `Type` 传递给该方法，以指示将检索结果类型转换为什么类型。

:::tip
这个 `Type` 并不是明确必须的，并且在数据被检索时增加了额外的指定层次。
:::

```java
// 在 ElementComposite 类中名为 TITLE 的示例属性
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

在下面的演示中，根据 Web 组件的文档，为 QR 代码添加了属性。然后实现了允许用户获取和设置已实现的各种属性的方法。

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## 事件注册 {#event-registration}

事件是 web 组件的重要组成部分，允许应用程序不同部分之间进行通信。`ElementComposite` 类简化了事件注册和处理。要注册事件监听器，请使用 `addEventListener()` 方法为特定事件类型注册事件监听器。指定事件类、监听器和可选的事件选项。

```java
// 示例：添加单击事件监听器
addEventListener(ClickEvent.class, event -> {
    // 处理单击事件
});
```

:::info
`ElementComposite` 的事件与 `Element` 的事件不同，因为这不允许任何类，而只允许指定的 `Event` 类。
:::

在下面的演示中，创建了一个单击事件，并将其添加到 QR 代码组件中。该事件在触发时将显示单击组件时鼠标的 "X" 坐标，该坐标作为数据提供给 Java 事件。然后实现了一个方法，允许用户访问此数据，这就是它在应用程序中显示的方式。
<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## 与插槽的交互 {#interacting-with-slots}

Web 组件通常使用插槽来允许开发者从外部定义组件的结构。插槽是 Web 组件内部的占位符，可以在使用组件时填充内容。在 `ElementComposite` 类的上下文中，插槽提供了一种在组件内自定义内容的方法。下面提供了一些方法，允许开发者与插槽交互和操作：

1. **`findComponentSlot()`**：此方法用于在组件系统中搜索特定组件，返回组件所在插槽的名称。如果未在任何插槽中找到组件，则返回空字符串。

2. **`getComponentsInSlot()`**：此方法检索分配给组件系统中给定插槽的组件列表。可以选择性地传递特定的类类型以过滤该方法的结果。

3. **`getFirstComponentInSlot()`**：此方法旨在获取分配给插槽的第一个组件。可以选择性地传递特定的类类型以过滤该方法的结果。

还可以使用带有 `String` 参数的 `add()` 方法来指定要添加传递组件的插槽。

这些交互使开发者能够利用 Web 组件的强大功能，通过为 `ElementComposite` 类中的插槽、属性和事件处理提供简单明了的 API。
