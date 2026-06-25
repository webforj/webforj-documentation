---
sidebar_position: 5
title: Elements
description: >-
  Integrate raw HTML tags and custom web components in webforJ using the Element
  class to add children, set content, and invoke JavaScript.
slug: element
_i18n_hash: d941e314cdd63d19471e80936ef5d6bc
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

webforJ 开发者不仅可以选择提供的丰富组件库，还可以集成其他地方的组件。为方便这一点，可以使用 `Element` 组件来简化从简单 HTML 元素到更复杂的自定义 Web 组件的集成。

:::important
`Element` 组件无法被扩展，并且不是 webforJ 中所有组件的基础组件。要了解有关 webforJ 组件层次结构的更多信息，请阅读 [这篇文章](../architecture/controls-components.md)。
:::

<ComponentDemo
path='/webforj/elementinputdemo'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
/>

## 添加事件 {#adding-events}

为了利用元素可能附带的事件，可以使用 `Element` 组件的 `addEventListener` 方法。添加事件至少需要指定组件所期望的事件类型/名称以及要添加到事件的监听器。

还有其他选项可用于通过使用事件选项配置进一步自定义事件。

<ComponentDemo
path='/webforj/elementinputevent'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputEventView.java',
  'src/main/resources/static/css/element/elementInputEvent.css',
]}
height='240px'
/>

## 组件交互 {#component-interaction}

`Element` 组件充当其他组件的容器。它提供了一种组织和检索子组件信息的方法，并提供了一组清晰的函数来根据需要添加或删除这些子组件。

### 添加子组件 {#adding-child-components}

`Element` 组件支持子组件的组合。开发者可以通过将组件作为子项添加到 `Element` 中来组织和管理复杂的 UI 结构。设置 `Element` 中内容的三种方法：

1. **`add(Component... components)`**：此方法允许将一个或多个组件添加到可选的 `String`，该字符串指定了与 Web 组件一起使用时的特定插槽。省略插槽将把组件添加到 HTML 标签之间。

2. **`setText(String text)`**：此方法的行为类似于 `setHtml()` 方法，但将文字文字注入到 `Element` 中。

  ```java
  // 显示为字面字符 "<b>Status: ready</b>"
  element.setText("<b>Status: ready</b>");
  ```

  :::note 使用 `<html>` 标签
  webforJ 的早期版本将传递给 `setText()` 的值视为 HTML，这个值以 `<html>` 包裹。此行为已被弃用，并将在 webforJ 27.00 中移除。

  第一次将以 `<html>` 包裹的值传递给 `setText()` 时，将记录一个警告，指出组件名称和调用位置，以便可以将调用移至 `setHtml()`。

  为提前采用 webforJ 27.00 默认值，将 `webforj.legacyHtmlInText` 设置为 `false`。在 Spring 应用中，通过 `webforj.legacy-html-in-text` 设置相同的值。

  ```java
  // webforj.legacyHtmlInText = true (默认)
  element.setText("<html><b>Status: ready</b></html>"); // 渲染为加粗文本

  // webforj.legacyHtmlInText = false
  element.setText("<html><b>Status: ready</b></html>"); // 显示字符 <b>Status: ready</b>
  ```
  :::

3. **`setHtml(String html)`**：此方法接受传递给方法的 `String`，并将其作为 HTML 注入到组件中。根据 `Element` 的不同，这可能以不同的方式呈现。

  :::danger 跨站脚本 (XSS)
  为防止 [跨站脚本 (XSS) 攻击](/docs/security/application-security/common-threats#cross-site-scripting-xss)，仅在您直接控制的内容上使用 `setHtml()`。
  :::

<ComponentDemo
path='/webforj/elementinputtext'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputTextView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
height='175px'
/>

:::tip
调用 `setHtml()` 或 `setText()` 将替换当前位于元素的开闭标签之间的内容。
:::

### 移除组件 {#removing-components}

除了将组件添加到 `Element`，还实现了以下方法以移除各种子组件：

1. **`remove(Component... components)`**：此方法接受一个或多个组件，并将其作为子组件移除。

2. **`removeAll()`**：此方法从 `Element` 中移除所有子组件。

### 访问组件 {#accessing-components}

要访问 `Element` 中存在的各种子组件或关于这些组件的信息，可以使用以下方法：

1. **`getComponents()`**：此方法返回 `Element` 的所有子项的 Java `List`。

2. **`getComponents(String id)`**：此方法与上述方法类似，但接受一个特定组件的服务器端 ID，当找到时返回该组件。

3. **`getComponentCount()`**：返回 `Element` 中存在的子组件数量。

## 调用 JavaScript 函数 {#calling-javascript-functions}

`Element` 组件提供了两个 API 方法，允许在 HTML 元素上调用 JavaScript 函数。

1. **`callJsFunction(String functionName, Object... arguments)`**：此方法接受一个函数名作为字符串，可以选择性地接受一个或多个 Object 作为函数的参数。该方法是同步执行的，这意味着 **执行线程被阻塞**，直到 JS 方法返回，导致一次往返。函数的结果作为 `Object` 返回，可以在 Java 中进行类型转换和使用。

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**：与上一个方法一样，可以传递函数名称和可选的函数参数。该方法异步执行，并且 **不会阻塞执行线程**。它返回一个 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>，允许与函数及其有效负载进一步交互。

### 传递参数 {#passing-parameters}

传递给这些方法的参数在执行 JS 函数时序列化为 JSON 数组。有两种显著的参数类型如下处理：
- `this`：使用 `this` 关键字将为方法提供对调用组件的客户端版本的引用。
- `Component`：任何传递给 JsFunction 方法的 Java 组件实例将被其客户端版本所替代。

:::info
同步和异步函数调用将等待直到 `Element` 被添加到 DOM 后再执行函数，但 `callJsFunction()` 不会等待任何 `component` 参数的附加，这可能导致失败。相反，如果从未附加组件参数，调用 `callJsFunctionAsync()` 可能永远不会完成。
:::

在下面的演示中，向 HTML `Button` 添加了一个事件。然后通过调用 `callJsFunctionAsync()` 方法以编程方式触发此事件。完成异步函数后，结果 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> 随后用于创建另一个消息框。

<ComponentDemo
path='/webforj/elementinputfunction'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
height='240px'
/>

## 执行 JavaScript {#executing-javascript}

除了从应用程序级别执行 JavaScript，还可以从 `Element` 级别执行 JavaScript。以 `Element` 级别执行此操作允许在执行中包含 HTML 元素的上下文。这是一个强大的工具，作为开发者与客户端环境交互能力的渠道。

类似于函数执行，执行 JavaScript 可以通过以下方法同步或异步进行：

1. **`executeJs(String script)`**：此方法接受一个 `String`，该字符串将作为 JavaScript 代码在客户端执行。此脚本同步执行，这意味着 **执行线程被阻塞**，直到 JS 执行返回，导致一次往返。函数的结果作为 `Object` 返回，可以在 Java 中进行类型转换和使用。

2. **`executeJsAsync(String script)`**：与上一个方法一样，传递的 `String` 参数将作为 JavaScript 代码在客户端执行。该方法异步执行，并且 **不会阻塞执行线程**。它返回一个 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>，允许与函数及其有效负载进一步交互。

:::tip
这些方法可以访问 `component` 关键字，使 JavaScript 代码能够访问执行 JavaScript 的组件的客户端实例。
:::
