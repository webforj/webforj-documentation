---
sidebar_position: 5
title: 元素
sidebar_class_name: updated-content
description: >-
  Integrate raw HTML tags and custom web components in webforJ using the Element
  class to add children, set content, and call JavaScript functions.
slug: element
_i18n_hash: 988b2a49584036eee3b0475215a707ae
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

webforJ 开发者不仅可以选择丰富的组件库，还可以集成其他地方的组件。为实现这一目的，可以使用 `Element` 组件来简化从简单 HTML 元素到更复杂自定义网页组件的集成。

:::important
`Element` 组件无法扩展，并且不是 webforJ 中所有组件的基础组件。要了解有关 webforJ 组件层次结构的更多信息，请阅读 [这篇文章](../architecture/controls-components.md)。
:::

<ComponentDemo
path='/webforj/elementmeter'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementMeterView.java',
  'src/main/resources/static/css/element/elementMeter.css',
]}
height='240px'
/>

## 添加事件 {#adding-events}

为了利用可能随你的元素而来的事件，可以使用 `Element` 组件的 `addEventListener` 方法。添加事件至少需要组件期望的事件类型/名称，以及一个要添加到事件的监听器。

还可以通过使用事件选项配置进一步自定义事件。

<ComponentDemo
path='/webforj/elementtaginput'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementTagInputView.java',
  'src/main/resources/static/css/element/elementTagInput.css',
]}
height='240px'
/>

## 组件交互 {#component-interaction}

`Element` 组件充当其他组件的容器。它提供了一种组织和检索子组件信息的方法，并提供了一套清晰的功能，以根据需要添加或删除这些子组件。


### 添加子组件 {#adding-child-components}

`Element` 组件支持子组件的组合。开发者可以通过将组件作为子组件添加到 `Element` 来组织和管理复杂的 UI 结构。`Element` 内部的内容可以通过三种方法设置：

1. **`add(Component... components)`**：此方法允许将一个或多个组件添加到使用 Web 组件时指定的可选 `String` 中的特定插槽。省略插槽将使组件添加到 HTML 标签之间。

2. **`setHtml(String html)`**：此方法接受一个传递给该方法的 `String`，并将其作为 HTML 注入到组件中。根据 `Element` 的不同，这可能以不同的方式进行渲染。

3. **`setText(String text)`**：此方法与 `setHtml()` 方法类似，但将文本字面量注入到 `Element` 中。


<ComponentDemo
path='/webforj/elementfigure'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementFigureView.java',
  'src/main/resources/static/css/element/elementFigure.css',
]}
height='240px'
/>

:::warning 替换内容
调用 `setHtml()` 或 `setText()` 将替换当前包含在元素开闭标签之间的内容。
:::

### 移除组件 {#removing-components}

除了向 `Element` 添加组件外，还实现了以下方法来移除各种子组件：

1. **`remove(Component... components)`**：此方法接受一个或多个组件，并将其作为子组件移除。

2. **`removeAll()`**：此方法从 `Element` 中移除所有子组件。

### 访问组件 {#accessing-components}

要访问 `Element` 中存在的各种子组件或有关这些组件的信息，可以使用以下方法：

1. **`getComponents()`**：此方法返回 `Element` 所有子组件的 Java `List`。

2. **`getComponents(String id)`**：此方法与上述方法类似，但采用特定组件的服务器端 ID 并在找到时返回。

3. **`getComponentCount()`**：返回 `Element` 中存在的子组件的数量。


## 调用 JavaScript 函数 {#calling-javascript-functions}

`Element` 组件提供了两个 API 方法，可以在 HTML 元素上调用 JavaScript 函数。

1. **`callJsFunction(String functionName, Object... arguments)`**：此方法将函数名称作为字符串传递，并可以可选地传入一个或多个对象作为函数的参数。此方法是同步执行的，这意味着 **正在执行的线程会被阻塞**，直到 JS 方法返回，并导致往返调用。函数的结果作为 `Object` 返回，可以在 Java 中进行转换和使用。

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**：与之前的方法一样，可以传递函数名称和可选参数。此方法异步执行并 **不会阻塞执行线程**。它返回一个 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>，允许进一步与函数及其有效负载进行交互。

### 传递参数 {#passing-parameters}

传递给这些方法的参数在执行 JS 函数时会序列化为 JSON 数组。有两个显著的参数类型被如下处理：
- `this`：使用 `this` 关键字将为方法提供调用组件的客户端版本的引用。
- `Component`：传递给任何 JsFunction 方法的 Java 组件实例将被替换为组件的客户端版本。

:::warning 等待组件参数
同步和异步函数调用都会等待 `Element` 被添加到 DOM 后执行函数，但 `callJsFunction()` 不会等待任何 `component` 参数附加，这可能导致失败。相反，如果组件参数未附加，调用 `callJsFunctionAsync()` 可能永远不会完成。
:::

在下面的演示中，选择 **Focus search** 会通过 `callJsFunctionAsync()` 调用搜索输入的原生 `focus()` 方法。生成的 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> 用于在异步函数完成后通过 Toast 确认调用。

<ComponentDemo
path='/webforj/elementsearch'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementSearchView.java',
  'src/main/resources/static/css/element/elementSearch.css',
]}
height='240px'
/>

## 执行 JavaScript {#executing-javascript}

除了调用命名的函数外，`Element` 还可以使用 `executeJs`、`executeJsAsync` 和 `executeJsVoidAsync` 在该元素范围内运行原始脚本。有关这些方法、它们的同步和异步行为以及如何将返回值转换为 Java 类型，请参见 [Execute JavaScript](./execute-javascript.md)。
