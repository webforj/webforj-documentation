---
sidebar_position: 6
title: Element Composite
sidebar_class_name: has-new-content
slug: element_composite
_i18n_hash: de075e855ba84ee82ec08c2bef771ea8
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

`ElementComposite` 类作为管理 webforJ 应用中复合元素的多功能基础，其主要目的是通过提供结构化的方法来处理属性、特性和事件监听器，从而方便与由 `Element` 类表示的 HTML 元素的交互。它允许在应用中实现和重用元素。在实现 Web 组件以用于 webforJ 应用时，请使用 `ElementComposite` 类。

在使用 `ElementComposite` 类时，`getElement()` 方法让您访问底层的 `Element` 组件。同样，`getNodeName()` 方法可以获取该节点在 DOM 中的名称。

:::tip
实际上，您可以仅使用 `Element` 类本身，而不使用 `ElementComposite` 类来完成所有操作。然而，`ElementComposite` 中的方法为您重用工作提供了一种途径。
:::

本页的示例演示如何使用 `ElementComposite` 类实现 [Shoelace QR 代码 Web 组件](https://shoelace.style/components/qr-code)。

<ComponentDemo
path='/webforj/qrdemo'
files={['src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java']}
height='175px'
/>

## 属性和特性描述符 {#property-and-attribute-descriptors}

Web 组件中的属性和特性代表组件的状态。它们通常用于管理数据或配置。`ElementComposite` 类提供了一种方便的方式来处理属性和特性。

可以将属性和特性声明和初始化为正在编写的 `ElementComposite` 类的 `PropertyDescriptor` 成员，然后在代码中使用。要定义属性和特性，请使用 `set()` 方法设置属性的值。例如，`set(PropertyDescriptor<V> property, V value)` 将属性设置为指定的值。

:::info
属性在组件的代码中被访问和操纵，并不会反映在 DOM 中。另一方面，特性是组件的外部接口的一部分，可以用来从外部向组件传递信息，为外部元素或脚本配置组件提供了一种方式。
:::

```java
// 在 ElementComposite 类中的示例属性称为 TITLE
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
// 在 ElementComposite 类中的示例属性称为 VALUE
private final PropertyDescriptor<String> VALUE = PropertyDescriptor.attribute("value", "");
//...
set(TITLE, "My Title");
set(VALUE, "My Value");
```

在 `ElementComposite` 类中使用 `get()` 方法来访问和读取属性。`get()` 方法可以接受一个可选的 `boolean` 值，默认值为 false，用于指示该方法是否应访问客户端以获取值。这会影响性能，但如果属性可以在客户端中纯粹修改，则可能是必要的。

还可以将 `Type` 传递给该方法，以指示将检索到的结果转换为什么类型。

:::tip
这个 `Type` 不是绝对必要的，并且在检索数据时增加了一层额外的规范。
:::

```java
// 在 ElementComposite 类中的示例属性称为 TITLE
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

在下面的演示中，根据 Web 组件的文档为 QR 代码添加了属性。然后实现了一些方法，允许用户获取和设置已经实现的各种属性。

<ComponentDemo
path='/webforj/qrproperties'
files={['src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java']}
height='250px'
/>

## 事件注册 {#event-registration}

事件使您的 webforJ 应用中不同部分之间能够通信。`ElementComposite` 类提供支持防抖、节流、过滤和自定义事件数据收集的事件处理。

使用 `addEventListener()` 方法注册事件监听器：

```java
// 示例：添加点击事件监听器
addEventListener(ElementClickEvent.class, event -> {
  // 处理点击事件
});
```

:::info
`ElementComposite` 事件与 `Element` 事件不同，因为它们不允许任何类，只有指定的 `Event` 类。
:::

### 内置事件类 {#built-in-event-classes}

webforJ 提供了预构建的事件类，具有类型化数据访问：

- **ElementClickEvent**：鼠标点击事件，带有坐标 (`getClientX()`，`getClientY()`)、按钮信息 (`getButton()`) 和修饰键 (`isCtrlKey()`，`isShiftKey()` 等)
- **ElementDefinedEvent**：在 DOM 中定义自定义元素并准备使用时触发
- **ElementEvent**：提供原始事件数据、事件类型 (`getType()`) 和事件 ID (`getId()`) 的基础事件类

### 事件负载 {#event-payloads}

事件将数据从客户端传递到您的 Java 代码。通过 `getData()` 访问原始事件数据，或者在可用时使用内置事件类上的类型化方法。有关有效使用事件负载的更多详细信息，请参阅 [事件指南](../building-ui/events)。

## 自定义事件类 {#custom-event-classes}

对于特殊事件处理，使用 `@EventName` 和 `@EventOptions` 注解创建带有配置负载的自定义事件类。

在下面的示例中，创建了一个点击事件，然后将其添加到 QR 代码组件。此事件在触发时将显示在点击组件时鼠标的 "X" 坐标，并将其作为数据传递给 Java 事件。然后实现了一个方法，允许用户访问这些数据，这就是如何在应用中显示它。

<ComponentDemo
path='/webforj/qrevent'
files={['src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java']}
height='300px'
/>

## `ElementEventOptions` {#elementeventoptions}

`ElementEventOptions` 让您通过配置要收集的数据、事件触发的时机以及如何处理它们来定制事件行为。以下是展示所有配置选项的完整代码片段：

```java
ElementEventOptions options = new ElementEventOptions()
  // 从客户端收集自定义数据
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")
  
  // 在事件触发之前执行 JavaScript
  .setCode("component.classList.add('processing');")
  
  // 仅在符合条件时触发
  .setFilter("component.value.length >= 2")
  
  // 延迟执行，直到用户停止输入（300ms）
  .setDebounce(300, DebouncePhase.TRAILING);

addEventListener("input", this::handleSearch, options);
```

### 性能控制 {#performance-control}

控制事件触发的时机和频率：

**防抖** 延迟执行直到活动停止：

```java
options.setDebounce(300, DebouncePhase.TRAILING); // 在最后一个事件后等待 300ms
```

**节流** 限制执行频率：

```java
options.setThrottle(100); // 最多每 100ms 触发一次
```

可用的防抖阶段：

- `LEADING`：立即触发，然后等待
- `TRAILING`：等待安静期，然后触发（默认）
- `BOTH`：立即触发并在安静期后触发

## 选项合并 {#options-merging}

使用 `mergeWith()` 结合来自不同来源的事件配置。基础选项为所有事件提供通用数据，而特定选项则添加专业配置。后面的选项将覆盖冲突设置。

```java
ElementEventOptions merged = baseOptions.mergeWith(specificOptions);
```

## 与插槽交互 {#interacting-with-slots}

Web 组件通常使用插槽允许开发人员从外部定义组件的结构。插槽是 Web 组件内部的一个占位符，可以在使用组件时填充内容。在 `ElementComposite` 类的上下文中，插槽提供了一种自定义组件内内容的方法。提供以下方法以允许开发人员与插槽进行交互和操纵：

1. **`findComponentSlot()`**：此方法用于搜索组件系统中所有插槽中的特定组件。返回组件所在插槽的名称。如果在任何插槽中未找到该组件，则返回空字符串。

2. **`getComponentsInSlot()`**：此方法检索分配给组件系统中给定插槽的组件列表。可选地，传递特定类类型以过滤方法的结果。

3. **`getFirstComponentInSlot()`**：此方法旨在获取分配给插槽的第一个组件。可选地传递特定类类型以过滤此方法的结果。

还可以使用 `add()` 方法和 `String` 参数来指定希望添加传入组件的插槽。

这些交互允许开发人员利用 Web 组件的力量，通过提供干净整洁的 API 来操纵插槽、属性以及在 `ElementComposite` 类中处理事件。
