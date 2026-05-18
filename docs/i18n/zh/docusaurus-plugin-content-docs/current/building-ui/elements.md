---
sidebar_position: 5
title: Elements
slug: element
_i18n_hash: 820bed6c059dad74a523673f245f3b2a
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

webforJ 开发人员不仅可以从提供的丰富组件库中选择，还可以集成来自其他地方的组件。为此，可以使用 `Element` 组件来简化从简单 HTML 元素到更复杂的自定义 Web 组件的集成。

:::重要
`Element` 组件无法被扩展，并且不是 webforJ 中所有组件的基础组件。要了解更多关于 webforJ 组件层次结构的信息，请阅读 [这篇文章](../architecture/controls-components.md)。
:::

<ComponentDemo
path='/webforj/elementinputdemo'
files={[
  'src/main/java/com.webforj/samples/views/element/ElementInputDemoView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
/>

## 添加事件 {#adding-events}

为了利用您的元素可能附带的事件，可以使用 `Element` 组件的 `addEventListener` 方法。添加事件至少需要组件期望的事件类型/名称和要添加到事件的侦听器。

还有其他选项可以通过使用事件选项配置进一步自定义事件。

<ComponentDemo
path='/webforj/elementinputevent'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputEventView.java',
  'src/main/resources/static/css/element/elementInputEvent.css',
]}
height='240px'
/>

## 组件交互 {#component-interaction}

`Element` 组件充当其他组件的容器。它提供了一种组织和检索子组件信息的方法，并提供了一组清晰的函数以根据需要添加或删除这些子组件。

### 添加子组件 {#adding-child-components}

`Element` 组件支持子组件的组合。开发人员可以通过将组件作为子项添加到 `Element` 中来组织和管理复杂的用户界面结构。有三种方法可以在 `Element` 中设置内容：

1. **`add(Component... components)`**：此方法允许将一个或多个组件添加到一个可选的 `String` 中，用于指定的插槽，当与 Web 组件一起使用时。如果省略插槽，则组件将添加在 HTML 标签之间。

2. **`setHtml(String html)`**：此方法将传递给它的 `String` 注入作为 HTML 在组件内。根据 `Element` 的不同，可能以不同的方式呈现。

3. **`setText(String text)`**：此方法的行为类似于 `setHtml()` 方法，但将文字文本注入到 `Element` 中。

<ComponentDemo
path='/webforj/elementinputtext'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputTextView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
height='175px'
/>

:::提示
调用 `setHtml()` 或 `setText()` 将替换当前包含在元素开闭标签之间的内容。
:::

### 移除组件 {#removing-components}

除了向 `Element` 添加组件之外，还实现了以下方法来移除各种子组件：

1. **`remove(Component... components)`**：此方法接受一个或多个组件并将其作为子组件移除。

2. **`removeAll()`**：此方法从 `Element` 中移除所有子组件。

### 访问组件 {#accessing-components}

要访问 `Element` 中存在的各种子组件或有关这些组件的信息，可以使用以下方法：

1. **`getComponents()`**：此方法返回 `Element` 的所有子项的 Java `List`。

2. **`getComponents(String id)`**：此方法类似于上述方法，但接受特定组件的服务器端 ID 并在找到时返回。

3. **`getComponentCount()`**：返回 `Element` 中存在的子组件数量。

## 调用 JavaScript 函数 {#calling-javascript-functions}

`Element` 组件提供两种 API 方法，允许在 HTML 元素上调用 JavaScript 函数。

1. **`callJsFunction(String functionName, Object... arguments)`**：此方法将函数名称作为字符串传递，并可选择性地接受一个或多个对象作为函数参数。此方法是同步执行，这意味着 **执行线程被阻塞**，直到 JS 方法返回，并导致一次往返。函数的结果作为 `Object` 返回，可以在 Java 中进行类型转换和使用。

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**：与上一方法类似，可以传递函数名称和可选参数。此方法异步执行，**不会阻塞执行线程**。它返回一个 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>，允许对函数及其有效负载进行进一步交互。

### 传递参数 {#passing-parameters}

传递给这些方法的参数在执行 JS 函数时序列化为 JSON 数组。有两种显著的参数类型处理如下：
- `this`：使用 `this` 关键字将为该方法提供对调用组件的客户端版本的引用。
- `Component`：传递到 JsFunction 方法中的任何 Java 组件实例将被替换为组件的客户端版本。

:::信息
同步和异步函数调用将在 `Element` 被添加到 DOM 后执行函数，但 `callJsFunction()` 不会等待任何 `component` 参数附加，这可能导致失败。相反，如果组件参数从未附加，调用 `callJsFunctionAsync()` 可能永远不会完成。
:::

在下面的演示中，将事件添加到 HTML `Button`。然后通过调用 `callJsFunctionAsync()` 方法以编程方式触发该事件。结果 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> 被用来在异步函数完成后创建另一个消息框。

<ComponentDemo
path='/webforj/elementinputfunction'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
height='240px'
/>

## 执行 JavaScript {#executing-javascript}

除了从应用程序级别执行 JavaScript 外，还可以从 `Element` 级别执行 JavaScript。以 `Element` 级别执行此操作允许将 HTML 元素的上下文包含在执行中。这是一个强大的工具，作为开发人员与客户端环境交互能力的通道。

与函数执行类似，执行 JavaScript 可以通过以下方法进行同步或异步执行：

1. **`executeJs(String script)`**：此方法接受一个 `String`，将在客户端作为 JavaScript 代码执行。此脚本是同步执行的，这意味着 **执行线程被阻塞**，直到 JS 执行返回，并导致一次往返。函数的结果作为 `Object` 返回，可以在 Java 中进行类型转换和使用。

2. **`executeJsAsync(String script)`**：与前一个方法类似，传入的 `String` 参数将在客户端作为 JavaScript 代码执行。此方法异步执行，**不会阻塞执行线程**。它返回一个 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>，允许对函数及其有效负载进行进一步交互。

:::提示
这些方法可以访问 `component` 关键字，这使 JavaScript 代码能够访问执行 JavaScript 的组件的客户端实例。
:::
