---
sidebar_position: 7
title: Event Options
_i18n_hash: 64cfa37f974517956ccb3fd75618df50
---
<!-- sidebar_class_name: sidebar--item__hidden -->
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="com/webforj/component/element/event/ElementEventOptions" top='true'/>

`ElementEventOptions` 是一个多功能的 webforJ 工具，旨在封装和管理 webforJ 应用程序中的 `Element` 事件的配置设置。作为各种选项的容器，它允许开发人员精确地指示与元素关联的事件应该如何被处理。

## 事件数据 {#event-data}

事件数据是 `ElementEventOptions` 的一个关键特性，使开发人员能够将特定信息附加到事件选项中。这一功能促进了当事件被触发时，从客户端向服务器传递自定义数据的过程。这个能力在传达与事件相关的附加上下文或参数时至关重要，并允许在不需要额外访问客户端的情况下访问和利用信息。

例如，考虑一个场景，您有一个按钮点击事件，想要传递当前用户的用户名以及该事件。与每次从客户端查询用户的用户名不同，可以将此信息与事件一起作为数据发送。

:::tip
有关更多信息，请参见 [events](/docs/building-ui/events) 和 [Client/Server Interaction](/docs/architecture/client-server) 页面。
:::

要将数据添加到事件选项中，可以使用 `addData()` 方法。

<!-- ### 示例 -->

## 执行 JavaScript {#executing-javascript}

`ElementEventOptions` 类允许开发人员指定在客户端事件被触发之前要评估的 JavaScript 代码。这一特性使客户端能够根据需要准备事件数据或触发额外事件。这在许多情况下是非常有帮助的，例如在通过表单提交事件提交之前，需要在客户端验证表单数据。

### 用法 {#usage}
要设置事件代码，请使用 `setCode()` 方法。

## 过滤事件 {#filtering-events}

`ElementEventOptions` 包含一个特性，允许设置在事件触发之前在客户端评估的过滤表达式。该过滤表达式使客户端能够根据特定条件判断事件是否应继续或停止。考虑一个输入字段，您想要仅在输入的文本符合特定标准（例如最小长度）时触发事件。

### 用法 {#usage-1}
要设置事件过滤器，请使用 `setFilter()` 方法。

## 防抖和节流 {#debouncing-and-throttling}

### 目的 {#purpose}
`ElementEventOptions` 提供了防抖和节流事件的机制。这些特性对于控制事件监听器的触发频率非常有用，确保它们仅在特定条件下被触发。

### 用法 {#usage-2}
- 要设置防抖，请使用 `setDebounce` 方法。
- 要设置节流，请使用 `setThrottle` 方法。

### 示例 {#example}
在处理快速用户输入的场景中，例如搜索输入字段，可以使用防抖来延迟执行，直到用户停止输入为止。

## 合并事件选项 {#merging-event-options}

`ElementEventOptions` 类支持与其他实例合并，允许开发人员聚合各种选项。该特性在结合来自不同来源的设置时非常有用。

## 注解 {#annotations}

### 目的 {#purpose-1}
为了方便起见，可以使用注解配置 `ElementEventOptions`。这些注解提供了一种更简洁和直观的方式来设置事件选项。

### 示例 {#example-1}
考虑以下示例注解：

```java
@EventOptions(data = {@EventData(key = "value", exp = "component.value")},
debounce = @DebounceSettings(value = 200))
```
