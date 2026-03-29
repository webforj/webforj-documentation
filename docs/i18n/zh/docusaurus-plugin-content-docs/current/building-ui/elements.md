---
sidebar_position: 5
title: Elements
slug: element
_i18n_hash: 2ea3ba8ae8756dcea1ee5d0eb9fb0cf9
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

webforJ 的开发者不仅可以选择丰富的组件库，还可以集成来自其他地方的组件。为此，可以使用 `Element` 组件来简化从简单 HTML 元素到更复杂的自定义 Web 组件的集成。

:::important
`Element` 组件无法被扩展，并且不是 webforJ 中所有组件的基础组件。要了解有关 webforJ 组件层次结构的更多信息，请阅读 [这篇文章](../architecture/controls-components.md)。
:::

<ComponentDemo 
path='/webforj/elementinputdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java'
cssURL='/css/element/elementInput.css'
/>

## 添加事件 {#adding-events}

为了利用可能伴随您的元素的事件，您可以使用 `Element` 组件的 `addEventListener` 方法。添加事件需要至少指定组件期望的事件类型/名称，并添加一个监听器到该事件。

还可以通过使用事件选项配置进一步自定义事件。

<ComponentDemo 
path='/webforj/elementinputevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputEventView.java'
cssURL='/css/element/elementInputEvent.css'
height='240px'
/>

## 组件交互 {#component-interaction}

`Element` 组件充当其他组件的容器。它提供了一种组织和检索子组件信息的方法，并提供了一组明确的功能来根据需要添加或删除这些子组件。

### 添加子组件 {#adding-child-components}

`Element` 组件支持子组件的组合。开发者可以通过将组件作为子组件添加到 `Element` 来组织和管理复杂的 UI 结构。存在三种方法可以在 `Element` 中设置内容：

1. **`add(Component... components)`**：此方法允许将一个或多个组件添加到一个可选的 `String` 中，当与 Web 组件结合使用时，指定一个插槽。省略插槽将把组件添加到 HTML 标签之间。

2. **`setHtml(String html)`**：此方法将传递给该方法的 `String` 注入为 HTML 组件内。根据 `Element` 的不同，这可以以不同的方式呈现。

3. **`setText(String text)`**：此方法的行为与 `setHtml()` 方法类似，但将文字文本注入到 `Element` 中。

<ComponentDemo 
path='/webforj/elementinputtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputTextView.java'
cssURL='/css/element/elementInput.css'
height='175px'
/>

:::tip
调用 `setHtml()` 或 `setText()` 将替换元素的开闭标签之间当前包含的内容。
:::

### 移除组件 {#removing-components}

除了向 `Element` 添加组件外，以下方法被实现以移除各种子组件：

1. **`remove(Component... components)`**：此方法接受一个或多个组件并将其移除为子组件。

2. **`removeAll()`**：此方法将移除 `Element` 的所有子组件。

### 访问组件 {#accessing-components}

要访问存在于 `Element` 中的各种子组件，或有关这些组件的信息，可以使用以下方法：

1. **`getComponents()`**：此方法返回一个 Java `List`，其中包含 `Element` 的所有子组件。

2. **`getComponents(String id)`**：此方法类似于上述方法，但将接受特定组件的服务器端 ID，并在找到时返回它。

3. **`getComponentCount()`**：返回 `Element` 中存在的子组件的数量。

## 调用 JavaScript 函数 {#calling-javascript-functions}

`Element` 组件提供了两个 API 方法，允许在 HTML 元素上调用 JavaScript 函数。

1. **`callJsFunction(String functionName, Object... arguments)`**：此方法接受一个函数名称作为字符串，并可选地接受一个或多个对象作为函数的参数。此方法同步执行，这意味着 **执行线程被阻塞** 直到 JS 方法返回，并导致一次往返。函数的结果作为 `Object` 返回，可以被转换并在 Java 中使用。

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**：与上一个方法类似，可以传递一个函数名称和可选的参数。此方法异步执行，并且 **不会阻塞执行线程**。它返回一个 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>，允许进一步与函数及其有效载荷进行交互。

### 传递参数 {#passing-parameters}

传递给这些用于执行 JS 函数的方法的参数序列化为 JSON 数组。有两种显著的参数类型处理如下：
- `this`：使用 `this` 关键字将使方法获得调用组件的客户端版本的引用。
- `Component`：任何传入一个 JsFunction 方法的 Java 组件实例将被客户端版本的组件替换。

:::info
无论是同步还是异步函数调用，都会等到 `Element` 被添加到 DOM 后再调用方法，但 `callJsFunction()` 不会等待任何 `component` 参数附加，这可能导致失败。相反，如果组件参数从未附加，则调用 `callJsFunctionAsync()` 可能永远不会完成。
:::

在下面的演示中，一个事件被添加到 HTML `Button`。然后，通过调用 `callJsFunctionAsync()` 方法以编程方式触发该事件。结果 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> 用于在异步函数完成后创建另一个消息框。

<ComponentDemo 
path='/webforj/elementinputfunction?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java'
cssURL='/css/element/elementInput.css'
height='240px'
/>

## 执行 JavaScript {#executing-javascript}

除了从应用程序层执行 JavaScript，还可以从 `Element` 层执行 JavaScript。在 `Element` 层执行此操作允许将 HTML 元素的上下文包括在执行中。这是一个强大的工具，作为开发者与客户端环境的交互能力的桥梁。

执行 JavaScript 类似于函数执行，可以通过以下方法同步或异步进行：

1. **`executeJs(String script)`**：此方法接受 `String`，将在客户端作为 JavaScript 代码执行。此脚本同步执行，这意味着 **执行线程被阻塞** 直到 JS 执行返回，并导致一次往返。函数的结果作为 `Object` 返回，可以被转换并在 Java 中使用。

2. **`executeJsAsync(String script)`**：与上一个方法一样，传递的 `String` 参数将在客户端作为 JavaScript 代码执行。此方法异步执行，并且 **不会阻塞执行线程**。它返回一个 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>，允许进一步与该函数及其有效载荷进行交互。

:::tip
这些方法可以访问 `component` 关键字，这使得 JavaScript 代码可以访问执行 JavaScript 的组件的客户端实例。
:::
