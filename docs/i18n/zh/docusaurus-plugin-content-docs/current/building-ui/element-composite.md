---
sidebar_position: 6
title: Element Composite
sidebar_class_name: has-new-content
slug: element_composite
_i18n_hash: 1fc82a7db864ec48118fb611a94a57fc
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

`ElementComposite` 类作为管理 webforJ 应用中复合元素的多功能基础。它的主要用途是通过提供一种结构化的方法来处理属性、特性和事件监听器，从而方便与 `Element` 类表示的 HTML 元素进行交互。它允许在应用中实现和重用元素。在实现 Web 组件以供 webforJ 应用使用时，请使用 `ElementComposite` 类。

在使用 `ElementComposite` 类时，使用 `getElement()` 方法将使您能够访问底层的 `Element` 组件。同样，`getNodeName()` 方法将提供该节点在 DOM 中的名称。

:::tip
可以仅使用 `Element` 类本身完成所有操作，而无需使用 `ElementComposite` 类。然而，`ElementComposite` 中提供的方法为用户提供了一种重用所做工作的方式。
:::

本指南演示如何使用 `ElementComposite` 类实现 [Shoelace QR 代码 Web 组件](https://shoelace.style/components/qr-code)。

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## 属性和特性描述符 {#property-and-attribute-descriptors}

Web 组件中的属性和特性表示组件的状态。它们通常用于管理数据或配置。`ElementComposite` 类提供了一种方便的方式来处理属性和特性。

可以将属性和特性声明和初始化为 `ElementComposite` 类的 `PropertyDescriptor` 成员，然后在代码中使用。要定义属性和特性，请使用 `set()` 方法设置属性的值。例如，`set(PropertyDescriptor<V> property, V value)` 将属性设置为指定的值。

:::info
属性在组件代码内部访问和操作，并不反映在 DOM 中。另一方面，特性是组件的外部接口的一部分，可以从外部传递信息到组件，为外部元素或脚本提供配置组件的一种方式。
:::

```java
// ElementComposite 类中的示例属性 TITLE
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
// ElementComposite 类中的示例属性 VALUE
private final PropertyDescriptor<String> VALUE = PropertyDescriptor.attribute("value", "");
//...
set(TITLE, "My Title");
set(VALUE, "My Value");
```

除了设置属性外，请使用 `ElementComposite` 类中的 `get()` 方法访问和读取属性。可以将一个可选的 `boolean` 值传递给 `get()` 方法，该值默认为 false，指示该方法是否应进行一次网络请求以检索值。这会影响性能，但如果属性仅能在客户端进行修改，则可能是必要的。

还可以向方法传递一个 `Type`，指示要将检索到的结果强制转换为何种类型。

:::tip
这个 `Type` 不是绝对必要的，并且在数据被检索时增加了一层额外的规范。
:::

```java
// ElementComposite 类中的示例属性 TITLE
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

在下面的演示中，已经根据 Web 组件的文档为 QR 代码添加了属性。然后实现了允许用户获取和设置实施的各种属性的方法。

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## 事件注册 {#event-registration}

事件使您的 webforJ 应用不同部分之间能够进行通信。`ElementComposite` 类提供了支持防抖、节流、过滤和自定义事件数据收集的事件处理。

使用 `addEventListener()` 方法注册事件监听器：

```java
// 示例：添加点击事件监听器
addEventListener(ElementClickEvent.class, event -> {
  // 处理点击事件
});
```

:::info
`ElementComposite` 事件与 `Element` 事件不同，因为这不允许任何类，而仅允许指定的 `Event` 类。
:::

### 内置事件类 {#built-in-event-classes}

webforJ 提供了预构建的事件类，具有类型数据访问：

- **ElementClickEvent**：带有坐标的鼠标点击事件（`getClientX()`，`getClientY()`），按钮信息（`getButton()`）和修饰键（`isCtrlKey()`，`isShiftKey()` 等）
- **ElementDefinedEvent**：当自定义元素在 DOM 中被定义并准备好使用时触发
- **ElementEvent**：基本事件类，提供对原始事件数据的访问、事件类型（`getType()`）和事件 ID（`getId()`）

### 事件有效负载 {#event-payloads}

事件将数据从客户端传送到您的 Java 代码。通过 `getData()` 获取原始事件数据，或在内置事件类上使用可用的类型化方法以获取更多详细信息。有关有效使用事件有效负载的更多信息，请参见 [事件指南](../building-ui/events)。

## 自定义事件类 {#custom-event-classes}

要进行专门的事件处理，请使用 `@EventName` 和 `@EventOptions` 注释创建具有配置有效负载的自定义事件类。

在下面的示例中，创建了一个点击事件，然后将其添加到 QR 代码组件。当此事件触发时，将显示鼠标在点击组件时的 "X" 坐标，该数据将提供给 Java 事件。一种方法随后被实现，以允许用户访问此数据，这就是它在应用中显示的方式。

<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## `ElementEventOptions` {#elementeventoptions}

`ElementEventOptions` 使您能够通过配置所收集的数据、事件触发的时机以及它们的处理方式来自定义事件行为。以下是展示所有配置选项的全面代码片段：

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
  
  // 延迟执行，直到用户停止输入（300ms）
  .setDebounce(300, DebouncePhase.TRAILING);

addEventListener("input", this::handleSearch, options);
```

### 性能控制 {#performance-control}

控制事件触发的时机和频率：

**防抖** 会延迟执行，直到活动停止：

```java
options.setDebounce(300, DebouncePhase.TRAILING); // 在最后一个事件后等待 300ms
```

**节流** 限制执行频率：

```java
options.setThrottle(100); // 每 100ms 至多触发一次
```

可用的防抖阶段：

- `LEADING`：立即触发，然后等待
- `TRAILING`：等待安静期，然后触发（默认）
- `BOTH`：立即触发，随后在安静期后触发

## 选项合并 {#options-merging}

使用 `mergeWith()` 从不同来源合并事件配置。基本选项为所有事件提供公共数据，而特定选项添加专门配置。后来的选项会覆盖冲突设置。

```java
ElementEventOptions merged = baseOptions.mergeWith(specificOptions);
```

## 与插槽交互 {#interacting-with-slots}

Web 组件通常使用插槽来允许开发人员从外部定义组件的结构。插槽是 Web 组件内部的一个占位符，可以在使用组件时填充内容。在 `ElementComposite` 类的上下文中，插槽提供了一种自定义组件内部内容的方法。提供以下方法以允许开发人员与插槽进行交互和操作：

1. **`findComponentSlot()`**：此方法用于在组件系统的所有插槽中搜索特定组件。它返回组件所在插槽的名称。如果在任何插槽中未找到该组件，则返回空字符串。

2. **`getComponentsInSlot()`**：此方法检索分配到组件系统中给定插槽的组件列表。可选择传递特定的类类型以过滤该方法的结果。

3. **`getFirstComponentInSlot()`**：此方法旨在获取分配给该插槽的第一个组件。可选择传递特定的类类型以过滤该方法的结果。

还可以使用带有 `String` 参数的 `add()` 方法指定要添加所传组件的插槽。

这些交互允许开发人员利用 Web 组件的功能，通过提供一个清晰直接的 API 来操作插槽、属性，并在 `ElementComposite` 类中处理事件。
