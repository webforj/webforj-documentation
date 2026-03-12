---
sidebar_position: 7
title: Event Options
_i18n_hash: 4311668d9a6bb9e9cebcf988e515d91a
---
<!-- sidebar_class_name: sidebar--item__hidden -->
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="com/webforj/component/element/event/ElementEventOptions" top='true'/>

`ElementEventOptions` 是一个多功能的 webforJ 工具，旨在封装和管理 webforJ 应用程序中 `Element` 事件的配置设置。作为各种选项的容器，它允许开发人员精确地指定与元素相关的事件应如何处理。

## 事件数据 {#event-data}

事件数据是 `ElementEventOptions` 的一个关键特性，允许开发人员将特定信息附加到事件选项中。此功能便于在事件被触发时将自定义数据从客户端传递到服务器。这种能力对于传达与事件相关的附加上下文或参数至关重要，并允许在没有额外请求客户端的情况下访问和利用信息。

例如，考虑一个按钮点击事件的场景，你希望在事件中传递当前用户的用户名。与其每次都从客户端查询用户的用户名，不如将此信息作为数据与事件一起发送。

:::tip
有关更多信息，请参见 [events](/docs/building-ui/events) 和 [Client/Server Interaction](/docs/architecture/client-server) 页面。
::: 

要向事件选项添加数据，可以使用 `addData()` 方法。

<!-- ### 示例 -->

## 执行 JavaScript {#executing-javascript}

`ElementEventOptions` 类允许开发人员指定在客户端事件触发之前要评估的 JavaScript 代码。此功能使客户端能够在需要时准备事件数据或触发额外的事件。在许多情况下，这非常有帮助，例如在通过表单提交事件之前验证客户端的表单数据。

### 用法 {#usage}
要设置事件代码，请使用 `setCode()` 方法。

## 过滤事件 {#filtering-events}

`ElementEventOptions` 包含一个功能，可以设置在事件触发之前在客户端评估的过滤表达式。此过滤表达式使客户端能够确定事件是否应继续或被中止，基于某些条件。考虑一个输入字段，只有在输入文本满足特定标准（例如最小长度）时，你才想触发事件。

### 用法 {#usage-1}
要设置事件过滤器，请使用 `setFilter()` 方法。

## 防抖和节流 {#debouncing-and-throttling}

### 目的 {#purpose}
`ElementEventOptions` 提供防抖和节流事件的机制。这些功能对于控制事件监听器的频率非常有用，确保它们仅在特定条件下触发。

### 用法 {#usage-2}
- 要设置防抖，请使用 `setDebounce` 方法。
- 要设置节流，请使用 `setThrottle` 方法。

### 示例 {#example}
在处理快速用户输入的场景中，例如搜索输入字段时，你可以使用防抖来延迟执行，直到用户完成输入。

## 合并事件选项 {#merging-event-options}

`ElementEventOptions` 类支持与其他实例合并，允许开发人员聚合各种选项。此功能在结合来自不同来源的设置时非常有用。

## 注解 {#annotations}

### 目的 {#purpose-1}
为方便起见，可以使用注解配置 `ElementEventOptions`。这些注解提供了一种更简洁和富有表现力的方式来设置事件选项。

### 示例 {#example-1}
考虑以下示例注解：

```java
@EventOptions(data = {@EventData(key = "value", exp = "component.value")},
      debounce = @DebounceSettings(value = 200))
```
