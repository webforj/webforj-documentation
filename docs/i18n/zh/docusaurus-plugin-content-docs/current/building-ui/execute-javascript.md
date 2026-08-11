---
sidebar_position: 11
title: Execute JavaScript
sidebar_class_name: new-content
description: >-
  Run client-side JavaScript from Java with executeJs, executeJsAsync, and
  executeJsVoidAsync at the app or element level.
slug: execute-javascript
_i18n_hash: c1d5b030c6f39ac6c83afc05ca4bb398
---
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

webforJ 在服务器上运行，但有时你需要访问客户端：滚动窗口、聚焦字段、读取浏览器值或调用 web 组件上的方法。<JavadocLink type="foundation" location="com/webforj/concern/HasJsExecution" code='true'>HasJsExecution</JavadocLink> 接口提供了这座桥。它在两个层次上实现：

- [`Page`](#app-level-execution) 在整个页面的上下文中运行脚本。
- [`Element`](#element-level-execution) 在单个客户端元素的范围内运行脚本。

这两个层次都暴露出相同的三个方法，因此一旦你了解了下面的形状，无论是在 `Page` 还是 `Element` 上调用它们，它们的读法都是相同的。

## 执行方法 {#execution-methods}

每个层次提供一个同步方法和两个异步方法。区别在于调用线程是否等待，以及是否有结果返回。

1. **`executeJs(String script)`**: 同步运行脚本。**执行线程被阻塞**，直到客户端返回，这需要一个服务器到客户端的往返时间。结果作为 `Object` 返回，你可以在 Java 中进行转换和使用。

2. **`executeJsAsync(String script)`**: 异步运行脚本，**不会阻塞执行线程**。它返回一个 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>，该结果在脚本结束时完成，因此你可以在稍后处理结果。

3. **`executeJsVoidAsync(String script)`**: 异步运行脚本，并且不向服务器返回任何内容。对那些你不需要结果的火后即忘的工作使用它。自 `24.11` 以来可用。

:::tip 选择方法
当你只是在客户端造成副作用（滚动、聚焦、调用方法）时，默认使用 `executeJsVoidAsync`。当你需要值但希望保持非阻塞时，使用 `executeJsAsync`，而将同步的 `executeJs` 保留给那些必须在下一行 Java 代码之前获得结果的罕见情况，因为它需要线程保持完整的往返时间。
:::

### 读取结果 {#reading-results}

当脚本返回一个值时，webforJ 将其转换为匹配的 Java 类型：

| JavaScript 值           | Java 类型                             |
| ----------------------- | ------------------------------------- |
| number                  | `Integer`、`Long` 或 `Double`        |
| string                  | `String`                              |
| boolean                 | `Boolean`                             |
| `null` 或 `undefined`   | `null`                                |
| 任何其他类型           | 其字符串表示                         |

使用 `executeJsAsync` 读取值，该方法可靠地应用转换。返回的数字可以作为 `Integer`、`Long` 或 `Double` 返回，因此通过 `Number` 读取它：

```java
Page.getCurrent()
    .executeJsAsync("return window.innerWidth;")
    .thenAccept(result -> {
      int width = ((Number) result).intValue();
      // 使用宽度
    });
```

:::warning 当你需要值时优先考虑异步形式
同步的 `executeJs` 在执行上下文未准备好时返回 `null`，例如在组件未附加之前调用。使用 `executeJsAsync` 每当你依赖返回值时，并避免将同步结果转换为特定类型。
:::

## 应用级执行 {#app-level-execution}

当脚本涉及整个页面而不是一个组件时，可以在 <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page</JavadocLink> 上调用方法。使用 `Page.getCurrent()` 获取当前页面。

一个常见的情况是在路由更改后滚动回顶部。没有内容需要返回，因此 `executeJsVoidAsync` 正好适合：

```java
Page.getCurrent().executeJsVoidAsync(
    "window.scrollTo({ top: 0, behavior: 'smooth' });");
```

当你需要在服务器上获取客户端值时，异步读取它，并在结果到达时处理：

```java
Page.getCurrent()
    .executeJsAsync("return navigator.language;")
    .thenAccept(language -> {
      // language 是浏览器的语言环境，例如 "en-US"
      applyLocale(String.valueOf(language));
    });
```

:::info 页面与元素范围
当脚本需要在特定客户端元素上执行时，请使用 [元素级执行](#element-level-execution)，而不是整页操作。
:::

在下面的演示中，选择 **复制链接** 通过 `executeJsVoidAsync` 在 `Page` 上运行脚本，将邀请链接写入访问者的剪贴板。复制是一个副作用，没有内容返回，因此火后即忘的方法正好适合。

<ComponentDemo
path='/webforj/executejavascript'
files={[
  'src/main/java/com/webforj/samples/views/javascript/ExecuteJavaScriptView.java',
]}
height='260px'
/>

## 元素级执行 {#element-level-execution}

在 <JavadocLink type="foundation" location="com/webforj/component/element/Element" code='true'>Element</JavadocLink> 上调用相同的方法将脚本作用域缩小到该元素，而不是页面。返回值和同步及异步行为与前面的页面级方法相匹配。

元素脚本在元素附加到 DOM 时排队执行，因此你可以在设置期间调用它们，而无需自己等待附加。

### 在元素上调用函数 {#calling-a-function}

当你想调用一个命名的客户端函数而不是运行脚本字符串时，`Element` 提供了一组并行的方法。你传递的是函数名称及其参数，而不是脚本，webforJ 将其序列化并传递。两种参数类型特别处理：`this` 被替换为客户端元素，任何 `Component` 参数在附加后被替换为其客户端实例。

这些与执行方法镜像，仅在于线程是否等待和结果是否返回有所不同：

1. **`callJsFunction(String name, Object... args)`**: 同步调用函数并将结果作为 `Object` 返回。执行线程阻塞一个往返时间。

2. **`callJsFunctionAsync(String name, Object... args)`**: 异步调用函数，不会阻塞，返回 `PendingResult`，该结果完成时带有函数的结果。自 `24.11` 以来可用。

3. **`callJsFunctionVoidAsync(String name, Object... args)`**: 异步调用函数，并不向服务器返回任何内容。对那些不需要返回值的火后即忘调用使用它。自 `24.11` 以来可用。

因为调用等待每个 `Component` 参数附加后运行，因此传递一个从未附加的组件的调用将永远不会完成。

```java
// 通过调用其客户端方法聚焦 web 组件的输入
searchElement.callJsFunctionVoidAsync("focus");
```
