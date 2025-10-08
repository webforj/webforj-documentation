---
sidebar_position: 1
title: Element Composite
sidebar_class_name: has-new-content
slug: element_composite
_i18n_hash: 6e201040e3dfd4be12037094eb9e978e
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

`ElementComposite` 类作为管理 webforJ 应用中复合元素的多功能基础。其主要目的是通过提供结构化的方法来处理属性、特性和事件监听器，以促进与由 `Element` 类表示的 HTML 元素的交互。它允许在应用中实现和重用元素。在实现 Web 组件以供 webforJ 应用程序使用时，请使用 `ElementComposite` 类。

使用 `ElementComposite` 类时，使用 `getElement()` 方法将使您能够访问底层的 `Element` 组件。类似地，`getNodeName()` 方法将提供该节点在 DOM 中的名称。

:::tip
可以仅使用 `Element` 类本身完成所有操作，而无需使用 `ElementComposite` 类。然而，在 `ElementComposite` 中提供的方法为用户提供了一种重用正在进行的工作的方式。
:::

本指南演示了如何使用 `ElementComposite` 类实现 [Shoelace QR 代码 web 组件](https://shoelace.style/components/qr-code)。

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## 属性和特性描述符 {#property-and-attribute-descriptors}

web 组件中的属性和特性表示组件的状态。它们通常用于管理数据或配置。`ElementComposite` 类提供了一种方便的方式来处理属性和特性。

可以在编写的 `ElementComposite` 类中声明和初始化为 `PropertyDescriptor` 成员，然后在代码中使用。要定义属性和特性，请使用 `set()` 方法设置属性的值。例如，`set(PropertyDescriptor<V> property, V value)` 将属性设置为指定的值。

:::info
属性在组件的代码内部访问和操作，不反映在 DOM 中。另一方面，特性是组件的外部接口的一部分，可以用来从外部传递信息到组件中，从而为外部元素或脚本配置组件提供了一种方式。
:::

```java
// ElementComposite 类中的示例属性 TITLE
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
// ElementComposite 类中的示例特性 VALUE
private final PropertyDescriptor<String> VALUE = PropertyDescriptor.attribute("value", "");
//...
set(TITLE, "我的标题");
set(VALUE, "我的值");
```

除了设置属性外，使用 `ElementComposite` 类中的 `get()` 方法来访问和读取属性。可以向 `get()` 方法传递一个可选的 `boolean` 值，默认值为 false，以决定该方法是否需要访问客户端以检索值。这会影响性能，但如果属性仅在客户端中被修改，这可能是必要的。

还可以将 `Type` 传递给该方法，以指示要将检索的结果转换为何种类型。

:::tip
这个 `Type` 并不是绝对必要的，并为检索数据时增加了额外的规范。
:::

```java
// ElementComposite 类中的示例属性 TITLE
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

在下面的演示中，基于 web 组件的文档，为 QR 代码添加了属性。然后实现了允许用户获取和设置各种已实现属性的方法。

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## 事件注册 {#event-registration}

事件使您的 webforJ 应用程序中的不同部分之间能够进行通信。`ElementComposite` 类提供事件处理，支持防抖、节流、过滤和自定义事件数据收集。

使用 `addEventListener()` 方法注册事件监听器：

```java
// 示例：添加点击事件监听器
addEventListener(ElementClickEvent.class, event -> {
    // 处理点击事件
});
```

:::info
`ElementComposite` 事件与 `Element` 事件不同，后者不允许任何类，只有指定的 `Event` 类。
:::

### 内置事件类 {#built-in-event-classes}

webforJ 提供了预构建的事件类，支持类型数据访问：

- **ElementClickEvent**：鼠标点击事件，带有坐标 (`getClientX()`，`getClientY()`)、按钮信息 (`getButton()`)，和修饰键 (`isCtrlKey()`，`isShiftKey()` 等)
- **ElementDefinedEvent**：当自定义元素在 DOM 中被定义并准备好使用时触发
- **ElementEvent**：基础事件类，提供对原始事件数据、事件类型 (`getType()`) 和事件 ID (`getId()`) 的访问

### 事件有效负载 {#event-payloads}

事件将数据从客户端传递到您的 Java 代码。使用 `getData()` 访问原始事件数据，或在可用时使用内置事件类上的类型化方法。有关有效使用事件有效负载的更多详细信息，请参见 [事件指南](../building-ui/events)。

## 自定义事件类 {#custom-event-classes}

对于专门的事件处理，可以使用 `@EventName` 和 `@EventOptions` 注解创建具有配置有效负载的自定义事件类。

在下面的示例中，创建了一个点击事件并将其添加到 QR 代码组件。当触发该事件时，将显示在组件被点击时鼠标的 "X" 坐标，该数据将传递给 Java 事件。然后实现了一个方法，允许用户访问这些数据，从而在应用中显示。

<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## `ElementEventOptions` {#elementeventoptions}

`ElementEventOptions` 让您通过配置要收集的数据、何时触发事件及如何处理事件来定制事件行为。以下是展示所有配置选项的综合代码片段：

```java
ElementEventOptions options = new ElementEventOptions()
    // 从客户端收集自定义数据
    .addData("query", "component.value")
    .addData("timestamp", "Date.now()")
    .addData("isValid", "component.checkValidity()")
    
    // 在事件触发前执行 JavaScript
    .setCode("component.classList.add('processing');")
    
    // 仅在条件满足时触发
    .setFilter("component.value.length >= 2")
    
    // 用户停止输入后延迟执行（300毫秒）
    .setDebounce(300, DebouncePhase.TRAILING);

addEventListener("input", this::handleSearch, options);
```

### 性能控制 {#performance-control}

控制事件触发的时间和频率：

**防抖** 延迟执行，直到活动停止：

```java
options.setDebounce(300, DebouncePhase.TRAILING); // 在最后一次事件后等待300毫秒
```

**节流** 限制执行频率：

```java
options.setThrottle(100); // 每100毫秒最多触发一次
```

可用的防抖阶段：

- `LEADING`：立即触发，然后等待
- `TRAILING`：等待安静期，然后触发（默认）
- `BOTH`：立即触发和在安静期后触发

## 选项合并 {#options-merging}

使用 `mergeWith()` 合并来自不同来源的事件配置。基础选项为所有事件提供公共数据，而特定选项则添加了专门配置。后来的选项覆盖冲突的设置。

```java
ElementEventOptions merged = baseOptions.mergeWith(specificOptions);
```

## 与槽交互 {#interacting-with-slots}

Web 组件经常使用插槽，允许开发人员从外部定义组件的结构。插槽是 Web 组件内部的占位符，可以在使用组件时填充内容。在 `ElementComposite` 类的上下文中，插槽提供了一种自定义组件内部内容的方法。以下方法提供了与插槽进行交互和操作的能力：

1. **`findComponentSlot()`**：此方法用于在组件系统中的所有插槽中搜索特定组件。它返回组件所在插槽的名称。如果在任何插槽中未找到组件，则返回一个空字符串。

2. **`getComponentsInSlot()`**：此方法检索分配给组件系统中给定插槽的组件列表。可选地，传递特定的类类型以过滤该方法的结果。

3. **`getFirstComponentInSlot()`**：此方法旨在获取分配给插槽的第一个组件。可选地传递特定的类类型以过滤此方法的结果。

还可以使用 `add()` 方法和 `String` 参数来指定要添加传递组件的插槽。

这些交互允许开发人员通过提供清晰简洁的 API 来利用 Web 组件的强大功能，以操纵插槽、属性和处理 `ElementComposite` 类中的事件。
