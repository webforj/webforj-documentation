---
sidebar_position: 5
title: Elements
description: >-
  Integrate raw HTML tags and custom web components in webforJ using the Element
  class to add children, set content, and invoke JavaScript.
slug: element
_i18n_hash: 2af99ca4f1e5c8c2f7c31b3d7f647b41
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

webforJ 开发者可以选择不仅仅使用提供的丰富组件库，还可以集成其他地方的组件。为了方便这一点，`Element` 组件可以简化从简单 HTML 元素到更复杂的自定义 Web 组件的集成。

:::important
`Element` 组件不能被扩展，并且不是 webforJ 中所有组件的基类。要了解有关 webforJ 组件层次结构的更多信息，请阅读 [这篇文章](../architecture/controls-components.md)。
:::

<ComponentDemo
path='/webforj/elementinputdemo'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java',
  'src/main/frontend/css/element/elementInput.css',
]}
/>

## 添加事件 {#adding-events}

为了利用可能随您的元素一起提供的事件，您可以使用 `Element` 组件的 `addEventListener` 方法。添加事件至少需要事件的类型/名称和添加到事件的监听器。

还有其他选项可以通过使用事件选项配置进一步自定义事件。

<ComponentDemo
path='/webforj/elementinputevent'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputEventView.java',
  'src/main/frontend/css/element/elementInputEvent.css',
]}
height='240px'
/>

## 组件交互 {#component-interaction}

`Element` 组件充当其他组件的容器。它提供了一种组织和检索子组件信息的方式，并提供了一套明确的功能，以根据需要添加或移除这些子组件。

### 添加子组件 {#adding-child-components}

`Element` 组件支持子组件的组合。开发者可以通过将组件作为子级添加到 `Element` 中来组织和管理复杂的 UI 结构。存在三种方法可以在 `Element` 内设置内容：

1. **`add(Component... components)`**：此方法允许添加一个或多个组件到一个可选的 `String` 中，当与 Web 组件一起使用时，指定一个特定的槽位。省略槽位将把组件添加到 HTML 标签之间。

2. **`setText(String text)`**：此方法的行为与 `setHtml()` 方法类似，但将文字文本注入到 `Element` 中。

  ```java
  // 显示为字面字符 "<b>Status: ready</b>"
  element.setText("<b>Status: ready</b>");
  ```

  :::note 使用 `<html>` 标签
  webforJ 的早期版本将传递给 `setText()` 的值与`<html>` 包裹视为 HTML。此行为已被弃用，并将在 webforJ 27.00 中删除。

  当第一个包含 `<html>` 的值到达 `setText()` 时，将记录一个警告，命名组件和调用站点，以便可以移动到 `setHtml()`。

  为了提前采用 webforJ 27.00 的默认设置，将 `webforj.legacyHtmlInText` 设置为 `false`。在 Spring 应用中，使用 `webforj.legacy-html-in-text` 设置相同的值。

  ```java
  // webforj.legacyHtmlInText = true (默认)
  element.setText("<html><b>Status: ready</b></html>"); // 渲染为粗体

  // webforj.legacyHtmlInText = false
  element.setText("<html><b>Status: ready</b></html>"); // 显示字符 <b>Status: ready</b>
  ```
  :::

3. **`setHtml(String html)`**：此方法将传递给方法的 `String` 作为 HTML 注入组件内。根据 `Element` 的不同，这可能以不同的方式呈现。

  :::danger 跨站脚本攻击 (XSS)
  作为对 [跨站脚本攻击 (XSS) 的预防措施](/docs/security/application-security/common-threats#cross-site-scripting-xss)，仅使用 `setHtml()` 处理您直接控制的内容。
  :::

<ComponentDemo
path='/webforj/elementinputtext'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputTextView.java',
  'src/main/frontend/css/element/elementInput.css',
]}
height='175px'
/>

:::tip
调用 `setHtml()` 或 `setText()` 将替换元素的开闭标签之间当前包含的内容。
:::

### 移除组件 {#removing-components}

除了向 `Element` 添加组件外，以下方法用于移除各种子组件：

1. **`remove(Component... components)`**：此方法接受一个或多个组件，并将它们作为子组件移除。

2. **`removeAll()`**：此方法从 `Element` 中移除所有子组件。

### 访问组件 {#accessing-components}

要访问 `Element` 中存在的各种子组件，或有关这些组件的信息，可以使用以下方法：

1. **`getComponents()`**：此方法返回 `Element` 所有子级的 Java `List`。

2. **`getComponents(String id)`**：此方法与上面的方式类似，但接受特定组件的服务端 ID 并在找到时返回。

3. **`getComponentCount()`**：返回 `Element` 中存在的子组件数量。

## 调用 JavaScript 函数 {#calling-javascript-functions}

`Element` 组件提供了两个 API 方法，可用于在 HTML 元素上调用 JavaScript 函数。

1. **`callJsFunction(String functionName, Object... arguments)`**：此方法接受一个函数名称作为字符串，并可选择性地接受一个或多个对象作为函数参数。此方法同步执行，这意味着 **执行线程会被阻塞**，直到 JS 方法返回，导致往返调用。函数的结果作为 `Object` 返回，可以在 Java 中进行类型转换和使用。

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**：与前一个方法一样，可以传递一个函数名称和可选的函数参数。此方法异步执行，并且 **不会阻塞执行线程**。它返回一个 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>，可用于与函数及其有效负载进行进一步交互。

### 传递参数 {#passing-parameters}

传递给这些方法的参数在执行 JS 函数时作为 JSON 数组进行序列化。有两种显著的参数类型处理如下：
- `this`：使用 `this` 关键字将为方法提供对调用组件客户端版本的引用。
- `Component`：传递给 JsFunction 方法的任何 Java 组件实例将替换为该组件的客户端版本。

:::info
无论是同步还是异步函数调用，都会在 `Element` 被添加到 DOM 后再执行函数，但 `callJsFunction()` 不会等待任何 `component` 参数附加，这可能导致失败。相反，如果组件参数从未附加，则调用 `callJsFunctionAsync()` 可能永远不会完成。
:::

在下面的演示中，向 HTML `Button` 添加了事件。然后通过调用 `callJsFunctionAsync()` 方法以编程方式触发该事件。结果 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> 将在异步函数完成后用于创建另一个消息框。

<ComponentDemo
path='/webforj/elementinputfunction'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java',
  'src/main/frontend/css/element/elementInput.css',
]}
height='240px'
/>

## 执行 JavaScript {#executing-javascript}

除了从应用程序级别执行 JavaScript，还可以从 `Element` 级别执行 JavaScript。以 `Element` 级别执行此操作允许将 HTML 元素的上下文包含在执行中。这是一个强大的工具，可以作为开发人员与客户端环境交互能力的通道。

类似于函数执行，执行 JavaScript 可以使用以下方法同步或异步进行：

1. **`executeJs(String script)`**：此方法接受一个 `String`，该字符串将在客户端作为 JavaScript 代码执行。此脚本同步执行，这意味着 **执行线程会被阻塞**，直到 JS 执行返回，导致往返调用。函数的结果作为 `Object` 返回，可以在 Java 中进行类型转换和使用。

2. **`executeJsAsync(String script)`**：与前一个方法一样，传递的 `String` 参数将在客户端作为 JavaScript 代码执行。此方法异步执行，并且 **不会阻塞执行线程**。它返回一个 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>，可用于与函数及其有效负载进行进一步交互。

:::tip
这些方法具有访问 `component` 关键字的权限，使 JavaScript 代码能够访问执行 JavaScript 的组件的客户端实例。
:::
