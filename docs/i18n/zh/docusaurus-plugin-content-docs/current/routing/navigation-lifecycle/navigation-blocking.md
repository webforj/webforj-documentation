---
sidebar_position: 3
title: Navigation Blocking
description: >-
  Intercept navigation with WillLeaveObserver veto handlers and the beforeunload
  event to guard unsaved changes.
_i18n_hash: 0deeb3e0583fdd425fe2a604ee1e9164
---
导航阻止为整个基础路由器API添加了一层或多层控制。如果存在任何阻止处理程序，导航将按如下方式被阻止：

如果导航是由路由级别控制的某些内容触发的，您可以执行任何任务或向用户显示UI提示以确认该操作。每个在[路由树](../route-hierarchy/overview)中实现`WillLeaveObserver`的组件将被调用。实现者必须调用`accept`以继续导航或`reject`以阻止它。如果路由树中多个组件实现了`WillLeaveObserver`，则拒绝处理程序将按逆序顺序依次执行。

:::info 拒绝处理的实际示例
要查看拒绝操作在实践中的工作原理，请参考[使用生命周期观察者示例](observers#example-handling-unsaved-changes-with-willleaveobserver)
:::

对于无法直接控制的页面事件，路由器不干预或强制特定行为。然而，开发人员仍然可以监听[`beforeunload`](https://developer.mozilla.org/en-US/docs/Web/API/Window/beforeunload_event)事件，以便在必要时最后一次尝试警告用户有关未保存数据的信息。

```java
PageEventOptions options = new PageEventOptions();
options.setCode("""
  event.preventDefault();
  return true;
  """);
Page.getCurrent().addEventListener("beforeunload", e -> {}, options);
```

## 浏览器后退按钮 {#browser-back-button}

后退按钮在Web应用程序的控制之外，因此很难在所有浏览器中一致地拦截或防止其操作。与其尝试阻止后退按钮，不如更有效地设计您的UI/UX，以减少影响。考虑采用策略，例如将未保存的数据保存在[会话存储](../../advanced/web-storage#session-storage)中，这样如果用户导航离开并返回，他们的进度将被安全恢复。这种方法确保了数据保护，而不依赖于不可靠的浏览器行为。
