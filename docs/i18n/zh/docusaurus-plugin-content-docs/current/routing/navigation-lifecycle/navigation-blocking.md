---
sidebar_position: 3
title: 导航阻塞
_i18n_hash: a08d56654914719e12d1401d263c7956
---
导航阻止为整个底层路由器 API 添加一个或多个控制层。如果存在任何阻止处理程序，则导航将按如下方式被阻止：

如果导航是由路由器级别控制的某种操作触发的，您可以执行任何任务或向用户显示 UI 提示以确认该操作。每个在 [路由树](../route-hierarchy/overview) 中实现 `WillLeaveObserver` 的组件将被调用。实现者必须调用 `accept` 以继续导航或 `reject` 以阻止导航。如果多个组件在路由树中实现了 `WillLeaveObserver`，则否决处理程序将按反向顺序依次执行。

:::info 否决处理的实际示例
要查看否决是如何实际工作的，请参考 [使用生命周期观察者示例](observers#example-handling-unsaved-changes-with-willleaveobserver)
:::

对于无法直接控制的页面事件，路由器不会干预或强制特定行为。然而，开发人员仍然可以监听 [`beforeunload`](https://developer.mozilla.org/en-US/docs/Web/API/Window/beforeunload_event) 事件，以在必要时最后尝试警告用户未保存的数据。

```java
PageEventOptions options = new PageEventOptions();
options.setCode(""" 
  event.preventDefault();
  return true;
  """);
Page.getCurrent().addEventListener("beforeunload", e -> {}, options);
```

## 浏览器后退按钮 {#browser-back-button}

后退按钮在 web 应用程序的控制范围之外，难以在所有浏览器中一致地拦截或阻止其操作。与其尝试阻止后退按钮，不如更有效地设计您的 UI/UX，以减轻其影响。考虑采取如在 [会话存储](../../advanced/web-storage#session-storage) 中保存未保存数据的策略，这样如果用户离开并返回，他们的进度会安全恢复。这种方法确保数据保护，而不依赖于不可靠的浏览器行为。
