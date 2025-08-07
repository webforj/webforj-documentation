---
sidebar_position: 3
title: Navigation Blocking
_i18n_hash: c0d79c6ce266eb4b9f9fd28915dcc380
---
导航阻止为整个底层路由 API 增加了一层或多层控制。如果存在任何阻止处理程序，则导航将被阻止，如下所示：

如果导航是由路由级别控制的某些操作触发的，您可以执行任何任务或向用户显示用户界面提示以确认该操作。每个在[路由树](../route-hierarchy/overview)中实现 `WillLeaveObserver` 的组件将被调用。实现者必须调用 `accept` 以继续导航，或者调用 `reject` 以阻止它。如果多个组件在路由的树中实现 `WillLeaveObserver`，则否决处理程序将按相反顺序依次执行。

:::info 否决处理的实际示例
要查看否决工作原理的实际示例，请参考[使用生命周期观察者示例](observers#example-handling-unsaved-changes-with-willleaveobserver)。
:::

对于无法直接控制的页面事件，路由器不会干预或强制特定行为。然而，开发人员仍然可以监听 [`beforeunload`](https://developer.mozilla.org/en-US/docs/Web/API/Window/beforeunload_event) 事件，以便在必要时进行最后一次警告用户有关未保存数据的尝试。

```java
PageEventOptions options = new PageEventOptions();
options.setCode(""" 
  event.preventDefault();
  return true;
  """);
Page.getCurrent().addEventListener("beforeunload", e -> {}, options);
```

## 浏览器后退按钮 {#browser-back-button}

后退按钮在 Web 应用程序的控制之外操作，因此在所有浏览器中一致地拦截或阻止其操作是困难的。与其试图阻止后退按钮，不如更有效地设计您的 UI/UX，以缓解其影响。考虑使用[会话存储](../../advanced/web-storage#session-storage)等策略来保存未保存的数据，这样如果用户离开并返回，他们的进度就能安全地恢复。这种方法确保数据得到了保护，而不依赖于不可靠的浏览器行为。
