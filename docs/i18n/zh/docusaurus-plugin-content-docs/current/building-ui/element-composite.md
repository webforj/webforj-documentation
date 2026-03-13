---
sidebar_position: 6
title: Element Composite
sidebar_class_name: has-new-content
slug: element_composite
_i18n_hash: 8d01fe0878cf3002fe34ef2e566c2837
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

`ElementComposite` 类作为在 webforJ 应用程序中管理复合元素的多功能基础。它的主要目的是通过提供一个结构化的方法来处理属性、属性和事件监听器，来便利地与由 `Element` 类表示的 HTML 元素进行交互。它允许在应用程序中实现和重用元素。当实现 Web 组件以在 webforJ 应用程序中使用时，请使用 `ElementComposite` 类。

使用 `ElementComposite` 类时，使用 `getElement()` 方法将使您能够访问底层的 `Element` 组件。类似地，`getNodeName()` 方法会返回该节点在 DOM 中的名称。

:::tip
可以仅使用 `Element` 类本身而无需使用 `ElementComposite` 类来完成所有操作。然而，`ElementComposite` 中提供的方法为用户提供了重用已完成工作的方式。
:::

本指南演示了如何使用 `ElementComposite` 类实现 [Shoelace QR 代码 Web 组件](https://shoelace.style/components/qr-code)。

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## 属性和属性描述符 {#property-and-attribute-descriptors}

Web 组件中的属性和属性表示组件的状态。它们通常用于管理数据或配置。`ElementComposite` 类提供了一种方便的方法来处理属性和属性。

可以将属性和属性声明并初始化为正在编写的 `ElementComposite` 类的 `PropertyDescriptor` 成员，然后在代码中使用。要定义属性和属性，请使用 `set()` 方法设置属性的值。例如，`set(PropertyDescriptor<V> property, V value)` 将属性设置为指定值。

:::info
属性在组件的代码内部访问和操作，不会反映在 DOM 中。而属性则是组件外部接口的一部分，可以用来从外部传递信息到组件，提供了一种让外部元素或脚本配置组件的方式。
:::

```java
// ElementComposite 类中的示例属性 TITLE
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
// ElementComposite 类中的示例属性 VALUE
private final PropertyDescriptor<String> VALUE = PropertyDescriptor.attribute("value", "");
//...
set(TITLE, "我的标题");
set(VALUE, "我的值");
```

除了设置属性外，使用 `ElementComposite` 类中的 `get()` 方法访问和读取属性。`get()` 方法可以传递一个可选的 `boolean` 值，默认值为 false，以决定该方法是否应该进行客户端访问以检索值。这会影响性能，但如果属性可以完全在客户端修改，则可能是必要的。

还可以将 `Type` 传递给方法，这决定了检索结果的转换类型。

:::tip
这个 `Type` 不是必需的，并在检索数据时添加了额外的指定层次。
:::

```java
// ElementComposite 类中的示例属性 TITLE
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

在下面的演示中，添加了基于 Web 组件文档的 QR 代码属性。然后实现了允许用户获取和设置已实现的各种属性的方法。

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## 事件注册 {#event-registration}

事件使 webforJ 应用程序的不同部分之间进行通信。`ElementComposite` 类提供了支持防抖、限流、过滤和自定义事件数据收集的事件处理。

使用 `addEventListener()` 方法注册事件监听器：

```java
// 示例：添加点击事件监听器
addEventListener(ElementClickEvent.class, event -> {
    // 处理点击事件
});
```

:::info
`ElementComposite` 事件与 `Element` 事件不同，因为这不允许任何类，而只允许指定的 `Event` 类。
:::

### 内置事件类 {#built-in-event-classes}

webforJ 提供了预构建的事件类，具有类型化数据访问：

- **ElementClickEvent**：带有坐标的鼠标点击事件（`getClientX()`，`getClientY()`），按钮信息（`getButton()`）和修饰键（`isCtrlKey()`，`isShiftKey()` 等）
- **ElementDefinedEvent**：在 DOM 中定义自定义元素并准备使用时触发
- **ElementEvent**：基础事件类，提供对原始事件数据、事件类型（`getType()`）和事件 ID（`getId()`）的访问

### 事件有效负载 {#event-payloads}

事件携带来自客户端到 Java 代码的数据。通过 `getData()` 访问原始事件数据，或在内置事件类上使用可用的类型化方法。有关有效使用事件有效负载的更多详细信息，请参阅 [事件指南](../building-ui/events)。

## 自定义事件类 {#custom-event-classes}

对于特定的事件处理，请使用 `@EventName` 和 `@EventOptions` 注解创建带有配置有效负载的自定义事件类。

在下面的示例中，创建了一个点击事件，然后将其添加到 QR 代码组件中。此事件在触发时将显示鼠标单击组件时的 "X" 坐标，该数据将提供给 Java 事件。然后实施了一种方法，以便用户可以访问此数据，这就是它在应用程序中显示的方式。

<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## `ElementEventOptions` {#elementeventoptions}

`ElementEventOptions` 允许您通过配置要收集的数据、事件何时触发以及如何处理事件来自定义事件行为。以下是一个全面的代码片段，展示所有配置选项：

```java
ElementEventOptions options = new ElementEventOptions()
    // 从客户端收集自定义数据
    .addData("query", "component.value")
    .addData("timestamp", "Date.now()")
    .addData("isValid", "component.checkValidity()")
    
    // 在事件触发之前执行 JavaScript
    .setCode("component.classList.add('processing');")
    
    // 仅在满足条件时触发
    .setFilter("component.value.length >= 2")
    
    // 用户停止输入后延迟执行（300毫秒）
    .setDebounce(300, DebouncePhase.TRAILING);

addEventListener("input", this::handleSearch, options);
```

### 性能控制 {#performance-control}

控制事件触发的时机和频率：

**防抖** 在活动停止之前延迟执行：

```java
options.setDebounce(300, DebouncePhase.TRAILING); // 在最后一个事件后等待300毫秒
```

**限流** 限制执行频率：

```java
options.setThrottle(100); // 每 100 毫秒最多触发一次
```

可用的防抖阶段：

- `LEADING`：立即触发，然后等待
- `TRAILING`：等待安静期，然后触发（默认）
- `BOTH`：立即触发并在安静期后触发

## 选项合并 {#options-merging}

通过使用 `mergeWith()` 合并来自不同来源的事件配置。基本选项为所有事件提供通用数据，而特定选项则添加特定配置。后来的选项覆盖冲突的设置。

```java
ElementEventOptions merged = baseOptions.mergeWith(specificOptions);
```

## 与插槽交互 {#interacting-with-slots}

Web 组件通常使用插槽允许开发者从外部定义组件的结构。插槽是 Web 组件内的占位符，可以在使用组件时填充内容。在 `ElementComposite` 类的上下文中，插槽提供了一种自定义组件内部内容的方式。提供以下方法以允许开发者与插槽进行交互和操作：

1. **`findComponentSlot()`**：此方法用于在组件系统中搜索特定组件。它返回该组件所在插槽的名称。如果在任何插槽中找不到组件，则返回空字符串。

2. **`getComponentsInSlot()`**：此方法检索分配给特定插槽的组件列表。可选地，传递特定类类型以过滤方法的结果。

3. **`getFirstComponentInSlot()`**：此方法旨在获取分配给插槽的第一个组件。可选地传递特定类类型以过滤该方法的结果。

也可以使用 `add()` 方法和一个 `String` 参数来指定要添加的组件的插槽。

这些交互使开发者能够利用 Web 组件的强大功能，通过提供一个清晰简洁的 API 来操作插槽、属性和处理 `ElementComposite` 类中的事件。
