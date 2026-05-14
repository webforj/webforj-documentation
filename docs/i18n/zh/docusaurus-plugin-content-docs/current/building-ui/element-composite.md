---
sidebar_position: 6
title: Element Composite
sidebar_class_name: has-new-content
slug: element_composite
_i18n_hash: c64ec386d273ab7facb974f5577afecf
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

`ElementComposite` 类作为一个多功能的基础，旨在管理 webforJ 应用中的复合元素。其主要目的是通过提供结构化的方法来处理属性、特性和事件监听器，以便与 `Element` 类表示的 HTML 元素进行交互。它允许在应用中实施和重用元素。在为 webforJ 应用实现 Web 组件时，请使用 `ElementComposite` 类。

在使用 `ElementComposite` 类时，`getElement()` 方法使您能够访问底层的 `Element` 组件。类似地，`getNodeName()` 方法会提供该节点在 DOM 中的名称。

:::tip
可以仅使用 `Element` 类自己完成所有操作，而无需使用 `ElementComposite` 类。但是，`ElementComposite` 中的方法为您重用工作提供了一种方式。
:::

本页的示例演示如何使用 `ElementComposite` 类实现 [Shoelace QR 代码 Web 组件](https://shoelace.style/components/qr-code)。

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## 属性和特性描述符 {#property-and-attribute-descriptors}

Web 组件中的属性和特性表示组件的状态。它们通常用于管理数据或配置。`ElementComposite` 类提供了一种便捷的方法来处理属性和特性。

可以将属性和特性声明和初始化为正在编写的 `ElementComposite` 类的 `PropertyDescriptor` 成员，然后在代码中使用。要定义属性和特性，请使用 `set()` 方法设置属性的值。例如，`set(PropertyDescriptor<V> property, V value)` 将属性设置为指定的值。

:::info
属性在组件的代码内部访问和操作，并不会反映在 DOM 中。另一方面，特性是组件外部接口的一部分，可以用于从外部向组件传递信息，为外部元素或脚本提供配置组件的方法。
:::

```java
// ElementComposite 类中名为 TITLE 的示例属性
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
// ElementComposite 类中名为 VALUE 的示例特性
private final PropertyDescriptor<String> VALUE = PropertyDescriptor.attribute("value", "");
//...
set(TITLE, "我的标题");
set(VALUE, "我的值");
```

在 `ElementComposite` 类中使用 `get()` 方法访问和读取属性。`get()` 方法可以接受一个可选的 `boolean` 值，默认值为 false，用以指示该方法是否应前往客户端检索值。这会影响性能，但如果属性只能在客户端修改，这可能是必要的。

也可以将一个 `Type` 传递给该方法，指示要将检索的结果转换成什么类型。

:::tip
这个 `Type` 并不是特别必要，并且在检索数据时增加了一层额外的规范。
:::

```java
// ElementComposite 类中名为 TITLE 的示例属性
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

在下面的演示中，根据 Web 组件的文档为 QR 代码添加了属性。然后实现了一些方法，使用户能够获取和设置已实现的各种属性。

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## 事件注册 {#event-registration}

事件使您的 webforJ 应用不同部分之间能够进行通信。`ElementComposite` 类提供事件处理，支持去抖动、节流、过滤和自定义事件数据收集。

使用 `addEventListener()` 方法注册事件监听器：

```java
// 示例：添加点击事件监听器
addEventListener(ElementClickEvent.class, event -> {
  // 处理点击事件
});
```

:::info
`ElementComposite` 事件与 `Element` 事件不同，后者不允许任何类，仅允许指定的 `Event` 类。
:::

### 内置事件类 {#built-in-event-classes}

webforJ 提供了带有类型数据访问的预构建事件类：

- **ElementClickEvent**: 包含坐标的鼠标点击事件 (`getClientX()`，`getClientY()`)，按钮信息 (`getButton()`)，和修饰键 (`isCtrlKey()`，`isShiftKey()` 等)
- **ElementDefinedEvent**: 在自定义元素在 DOM 中定义并准备好使用时触发
- **ElementEvent**: 基础事件类，提供对原始事件数据、事件类型 (`getType()`)、事件 ID (`getId()`) 的访问

### 事件有效载荷 {#event-payloads}

事件携带数据从客户端到您的 Java 代码。通过 `getData()` 访问原始事件数据或使用内置事件类上的类型化方法以获取更多信息。有关有效使用事件有效载荷的详细信息，请参阅 [事件指南](../building-ui/events)。

## 自定义事件类 {#custom-event-classes}

对于专业的事件处理，使用 `@EventName` 和 `@EventOptions` 注解创建自定义事件类，并配置有效载荷。

在下面的示例中，创建了一个点击事件，并将其添加到 QR 代码组件中。此事件在触发时将显示鼠标在组件点击时的 "X" 坐标，该数据将提供给 Java 事件。然后实现了一个方法，使用户能够访问这些数据，这就是如何在应用中展示这些数据。

<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## `ElementEventOptions` {#elementeventoptions}

`ElementEventOptions` 允许您通过配置要收集的数据、事件何时触发以及如何处理事件来自定义事件行为。以下是展示所有配置选项的全面代码片段：

```java
ElementEventOptions options = new ElementEventOptions()
  // 从客户端收集自定义数据
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")
  
  // 在事件触发前执行 JavaScript
  .setCode("component.classList.add('processing');")
  
  // 仅在满足条件时触发
  .setFilter("component.value.length >= 2")
  
  // 等待用户停止输入后延迟执行（300ms）
  .setDebounce(300, DebouncePhase.TRAILING);

addEventListener("input", this::handleSearch, options);
```

### 性能控制 {#performance-control}

控制事件何时以及多频繁地触发：

**去抖动**延迟执行，直到活动停止：

```java
options.setDebounce(300, DebouncePhase.TRAILING); // 在最近事件后等待 300ms
```

**节流**限制执行频率：

```java
options.setThrottle(100); // 每 100ms 最多触发一次
```

可用的去抖动阶段：

- `LEADING`: 立即触发，然后等待
- `TRAILING`: 等待安静期，然后触发（默认）
- `BOTH`: 立即触发并在安静期后触发

## 选项合并 {#options-merging}

使用 `mergeWith()` 合并来自不同来源的事件配置。基础选项为所有事件提供公共数据，而特定选项则添加了专门的配置。后来的选项覆盖冲突的设置。

```java
ElementEventOptions merged = baseOptions.mergeWith(specificOptions);
```

## 与插槽交互 {#interacting-with-slots}

Web 组件通常使用插槽允许开发人员从外部定义组件的结构。插槽是在 Web 组件中可以在使用组件时填充内容的占位符。在 `ElementComposite` 类的上下文中，插槽提供了一种自定义组件内内容的方法。提供了以下方法，允许开发人员与插槽进行交互和操作：

1. **`findComponentSlot()`**: 该方法用于在组件系统中搜索特定组件。它返回组件所在插槽的名称。如果在任何插槽中找不到该组件，则返回空字符串。

2. **`getComponentsInSlot()`**: 该方法检索分配给组件系统中给定插槽的组件列表。可选择性地传递特定类类型以过滤方法的结果。

3. **`getFirstComponentInSlot()`**: 该方法旨在获取分配给插槽的第一个组件。可选择性地传递特定类类型以过滤该方法的结果。

也可以使用 `add()` 方法并传递一个 `String` 参数来指定要添加组件的插槽。

这些交互使开发人员能够利用 Web 组件的强大功能，通过提供一个清晰且简洁的 API 来操作插槽、属性，并在 `ElementComposite` 类中处理事件。
