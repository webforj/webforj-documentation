---
sidebar_position: 5
title: Elements
description: >-
  Integrate raw HTML tags and custom web components in webforJ using the Element
  class to add children, set content, and call JavaScript functions.
slug: element
_i18n_hash: dff3b1c4df821aad3c4c7a4c66cfff65
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

webforJ 开发人员不仅可以选择提供的丰富组件库，还可以集成其他地方的组件。为了方便起见，可以使用 `Element` 组件来简化从简单 HTML 元素到更复杂自定义网络组件的集成。

:::important
`Element` 组件不能被扩展，并且不是 webforJ 中所有组件的基础组件。要了解更多关于 webforJ 组件层次结构的信息，请阅读 [这篇文章](../architecture/controls-components.md)。
:::

<ComponentDemo
path='/webforj/elementmeter'
files={[
  'src/main/java/com.webforj/samples/views/element/ElementMeterView.java'
]}
height='250px'
/>

## 添加事件 {#adding-events}

为了利用可能随元素而来的事件，可以使用 `Element` 组件的 `addEventListener` 方法。添加事件至少需要组件期望的事件类型/名称，以及要添加到该事件的监听器。

此外，还可以通过使用事件选项配置进一步自定义事件。

<ComponentDemo
path='/webforj/elementtaginput'
files={[
  'src/main/java/com.webforj/samples/views/element/ElementTagInputView.java',
  'src/main/frontend/css/element/elementtaginput.css',
]}
height='250px'
/>

## 组件交互 {#component-interaction}

`Element` 组件充当其他组件的容器。它提供一种组织和检索子组件信息的方法，并提供一套明确的功能，以根据需要添加或删除这些子组件。

### 添加子组件 {#adding-child-components}

`Element` 组件支持子组件的组合。开发人员可以通过将组件作为子组件添加到 `Element` 来组织和管理复杂的 UI 结构。有三种方法可以在 `Element` 中设置内容：

1. **`add(Component... components)`**：此方法允许将一个或多个组件添加到指定的可选 `String`，该 `String` 在与 Web 组件一起使用时指定插槽。省略插槽将组件添加到 HTML 标签之间。
  
2. **`setHtml(String html)`**：此方法将传递给该方法的 `String` 作为 HTML 注入到组件中。根据 `Element` 的不同，它可能以不同方式呈现。

3. **`setText(String text)`**：此方法的行为类似于 `setHtml()`，但将字面文本注入到 `Element` 中。

<ComponentDemo
path='/webforj/elementfigure'
files={[
  'src/main/java/com.webforj/samples/views/element/ElementFigureView.java',
  'src/main/frontend/css/element/elementfigure.css',
]}
height='250px'
/>

:::warning 替换内容
调用 `setHtml()` 或 `setText()` 将替换元素开闭标签之间当前包含的内容。
:::

### 移除组件 {#removing-components}

除了向 `Element` 添加组件外，还实现了以下方法以移除各种子组件：

1. **`remove(Component... components)`**：此方法接受一个或多个组件并将其作为子组件移除。

2. **`removeAll()`**：此方法将 `Element` 中的所有子组件移除。

### 访问组件 {#accessing-components}

要访问 `Element` 中存在的各种子组件，或有关这些组件的信息，可使用以下方法：

1. **`getComponents()`**：此方法返回 `Element` 的所有子组件的 Java `List`。

2. **`getComponents(String id)`**：此方法与上述方法类似，但接受特定组件的服务器端 ID 并在找到时返回它。

3. **`getComponentCount()`**：返回 `Element` 中存在的子组件数量。

## 调用 JavaScript 函数 {#calling-javascript-functions}

`Element` 组件提供两个 API 方法，允许在 HTML 元素上调用 JavaScript 函数。

1. **`callJsFunction(String functionName, Object... arguments)`**：此方法接受一个函数名称作为字符串，并可选择接受一或多个对象作为函数参数。此方法是同步执行的，意味着 **执行线程被阻塞** 直到 JS 方法返回，从而导致往返。函数的结果作为 `Object` 返回，可以在 Java 中转换和使用。

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**：与上述方法一样，可以传递函数名称和可选参数。此方法是异步执行的，**不会阻塞执行线程**。它返回一个 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>，允许对函数及其负载进行进一步交互。

### 传递参数 {#passing-parameters}

传递给这些方法的参数在执行 JS 函数时将序列化为 JSON 数组。有两种显著的参数类型处理如下：
- `this`：使用 `this` 关键字将为方法提供对调用组件的客户端版本的引用。
- `Component`：传递给 JsFunction 方法的任何 Java 组件实例将被替换为该组件的客户端版本。

:::warning 等待组件参数
同步和异步函数调用都将等待 `Element` 添加到 DOM 后再执行函数，但 `callJsFunction()` 不会等待任何 `component` 参数附加，这可能导致失败。相反，如果组件参数从未附加，则调用 `callJsFunctionAsync()` 可能永远不会完成。
:::

在下面的演示中，选择 **Focus search** 会使用 `callJsFunctionAsync()` 调用搜索输入的原生 `focus()` 方法。结果 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> 用于在异步函数完成后通过 toast 确认调用。

<ComponentDemo
path='/webforj/elementsearch'
files={[
  'src/main/java/com.webforj/samples/views/element/ElementSearchView.java',
  'src/main/frontend/css/element/elementsearch.css',
]}
height='250px'
/>

## 执行 JavaScript {#executing-javascript}

除了调用命名函数，`Element` 还可以使用 `executeJs`、`executeJsAsync` 和 `executeJsVoidAsync` 执行限定于该元素的原始脚本。有关这些方法的详细信息、它们的同步和异步行为，以及返回值如何转换为 Java 类型，请参见 [Execute JavaScript](./execute-javascript.md)。
