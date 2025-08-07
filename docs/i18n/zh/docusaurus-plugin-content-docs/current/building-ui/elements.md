---
sidebar_position: 3
title: Elements
slug: element
_i18n_hash: d77ff55b483b72de9ee1d36473d7751d
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

webforJ 开发者不仅可以选择提供的丰富组件库，还可以集成其他地方的组件。为方便实现这一点，可以使用 `Element` 组件来简化从简单 HTML 元素到更复杂的自定义 Web 组件的集成。

:::important
`Element` 组件不能被扩展，并且不是 webforJ 中所有组件的基础组件。要了解有关 webforJ 组件层次结构的更多信息，请阅读 [这篇文章](../architecture/controls-components.md)。
:::

<ComponentDemo 
path='/webforj/elementinputdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java'
cssURL='/css/element/elementInput.css'
/>

## 添加事件 {#adding-events}

为了利用与您的元素一起提供的事件，可以使用 `Element` 组件的 `addEventListener` 方法。添加事件至少需要指定组件期望的事件类型/名称，并添加一个监听器到该事件。

此外，还有其他选项可以通过使用事件选项配置进一步自定义事件。

<ComponentDemo 
path='/webforj/elementinputevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputEventView.java'
cssURL='/css/element/elementInputEvent.css'
height='240px'
/>

## 组件交互 {#component-interaction}

`Element` 组件充当其他组件的容器。它提供了一种组织和检索子组件信息的方式，并提供了一组清晰的功能，以根据需要添加或移除这些子组件。

### 添加子组件 {#adding-child-components}

`Element` 组件支持子组件的组合。开发者可以通过将组件作为子组件添加到 `Element` 来组织和管理复杂的 UI 结构。 有三种方法可以在 `Element` 中设置内容：

1. **`add(Component... components)`**：此方法允许一个或多个组件添加到一个可选的 `String` 中，该 `String` 指定与 Web 组件一起使用时的特定插槽。省略插槽将使组件在 HTML 标签之间添加。

2. **`setHtml(String html)`**：此方法将传递给该方法的 `String` 注入为组件中的 HTML。根据 `Element` 的不同，这可能以不同的方式呈现。

3. **`setText(String text)`**：此方法的行为类似于 `setHtml()` 方法，但将字面文本注入到 `Element` 中。

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

除了向 `Element` 添加组件外，以下方法被实现用于移除不同的子组件：

1. **`remove(Component... components)`**：此方法接受一个或多个组件，并将其作为子组件移除。

2. **`removeAll()`**：此方法将移除 `Element` 中的所有子组件。

### 访问组件 {#accessing-components}

要访问 `Element` 中存在的各种子组件，或有关这些组件的信息，可以使用以下方法：

1. **`getComponents()`**：此方法返回 `Element` 所有子组件的 Java `List`。

2. **`getComponents(String id)`**：此方法类似于上述方法，但会接受特定组件的服务器端 ID 并在找到时返回。

3. **`getComponentCount()`**：返回 `Element` 中存在的子组件数量。

## 调用 JavaScript 函数 {#calling-javascript-functions}

`Element` 组件提供了两个 API 方法，允许在 HTML 元素上调用 JavaScript 函数。

1. **`callJsFunction(String functionName, Object... arguments)`**：此方法接受一个字符串作为函数名称，并可选择性地接受一个或多个对象作为函数的参数。此方法同步执行，这意味着 **执行线程被阻塞** 直到 JS 方法返回，并导致往返。函数的结果作为一个 `Object` 返回，可以在 Java 中进行类型转换和使用。

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**：与前一个方法一样，可以传递函数名称和可选的函数参数。此方法异步执行，并且 **不阻塞执行线程**。它返回一个 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>，允许进一步与函数及其负载进行交互。

### 传递参数 {#passing-parameters}

传递给这些方法的参数在执行 JS 函数时被序列化为 JSON 数组。有两个值得注意的参数类型处理如下：
- `this`：使用 `this` 关键字将为方法提供对调用组件的客户端版本的引用。
- `Component`：传递给其中一个 JsFunction 方法的任何 Java 组件实例将被替换为组件的客户端版本。

:::info
同步和异步函数调用在调用方法之前将等待 `Element` 被添加到 DOM，然后再执行函数，但 `callJsFunction()` 不会等待任何 `component` 参数附加，这可能导致失败。相反，如果从未附加组件参数，调用 `callJsFunctionAsync()` 可能永远不会完成。
:::

在下面的演示中，事件被添加到 HTML `Button`。通过调用 `callJsFunctionAsync()` 方法程序性地触发此事件。然后使用结果的 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> 创建另一个消息框，直到异步函数完成。

<ComponentDemo 
path='/webforj/elementinputfunction?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java'
cssURL='/css/element/elementInput.css'
height='240px'
/>

## 执行 JavaScript {#executing-javascript}

除了从应用程序级别执行 JavaScript，还可以从 `Element` 级别执行 JavaScript。以 `Element` 级别执行此操作允许将 HTML 元素的上下文包含在执行中。这是一个强大的工具，作为开发人员与客户端环境交互能力的通道。

与函数执行类似，可以使用以下方法同步或异步执行 JavaScript：

1. **`executeJs(String script)`**：此方法接受 `String`，将在客户端作为 JavaScript 代码执行。此脚本同步执行，这意味着 **执行线程被阻塞**，直到 JS 执行返回，并导致往返。函数的结果作为一个 `Object` 返回，可以进行类型转换和使用。

2. **`executeJsAsync(String script)`**：与前一个方法一样，传递的 `String` 参数将在客户端作为 JavaScript 代码执行。此方法异步执行，并且 **不阻塞执行线程**。它返回一个 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>，允许进一步与函数及其负载进行交互。

:::tip
这些方法可以访问 `component` 关键字，使 JavaScript 代码能够访问执行 JavaScript 的组件客户端实例。
:::
