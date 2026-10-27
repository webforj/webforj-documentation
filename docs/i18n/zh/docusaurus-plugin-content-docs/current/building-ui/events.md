---
sidebar_position: 7
title: 事件
description: >-
  Listen for component events, read the event payload, configure element events,
  and dispatch your own custom events with the EventDispatcher.
slug: events
sidebar_class_name: new-content
_i18n_hash: 5ceda90a316ff6a1528a686565011f88
---
组件，无论是自定义的还是框架的一部分，都支持事件处理。您可以添加事件侦听器以捕获各种类型的事件，例如用户交互、状态变化或您自己定义的事件。这些侦听器允许您针对应用程序中发生的事情触发特定行为。

## 添加事件 {#adding-events}

使用组件上的特定事件方法添加侦听器。每个组件都会暴露一对方法：`addXxxListener` 方法，并且在大多数情况下，都会有一个短一些的 `on` 别名，完成相同的功能。例如，一个 `Button` 同时暴露 `addClickListener` 和 `onClick`。

您可以将侦听器作为 Lambda 表达式传递：

```java
Button button = new Button("保存");
button.onClick(event -> {
  // 处理点击
});
```

或者作为方法引用：

```java
button.onClick(this::handleSave);
```

并非每个事件都有 `on` 别名。例如，值的变化仅通过 `addValueChangeListener` 添加：

```java
TextField name = new TextField("名称");
name.addValueChangeListener(event -> {
  String value = event.getValue();
  // 处理新值
});
```

## 移除事件 {#removing-an-event}

添加侦听器返回一个 `ListenerRegistration`。保留它以便后续移除侦听器。

```java
ListenerRegistration<ButtonClickEvent> registration =
    button.onClick(event -> {
      // 处理点击
    });

// 之后，当不再需要侦听器时
registration.remove();
```

## 使用事件负载 {#using-event-payload}

事件携带有关发生了什么的信息负载。在处理程序中读取该负载，使您可以获得相关数据，而无需进行客户机的往返。

例如，`TextField` 的 `ModifyEvent` 携带字段的当前文本。您可以查询组件来获取它：

```java
TextField field = new TextField("搜索");
field.onModify(event -> {
  String text = field.getText();
  // 使用文本
});
```

同样的值已经在事件中，因此从负载中读取它会避免再次访问组件：

```java
field.onModify(event -> {
  String text = event.getText();
  // 使用文本
});
```

在事件暴露所需数据的地方，从负载中读取。有关为什么这很重要的更多信息，请参阅 [客户端/服务器交互](../architecture/client-server)。

## 配置元素事件 {#configuring-element-events}

当您直接与 <JavadocLink type="foundation" location="com/webforj/component/element/Element" code='true'>Element</JavadocLink> 工作时，其事件通过 <JavadocLink type="foundation" location="com/webforj/component/element/event/ElementEventOptions" code='true'>ElementEventOptions</JavadocLink> 进行配置。这可以控制事件携带的数据、是否触发及触发的频率，所有这些都在事件到达服务器之前在客户端进行评估。

### 事件数据 {#event-data}

事件数据将客户端的值附加到事件上，因此在服务器上可以获得信息，而无需额外请求。您可以使用 `addData()` 添加它，为每个条目指定一个键和生成值的 JavaScript 表达式。

在这些表达式中有两个可用变量：`event`，客户端事件对象，以及 `component`，侦听器附加到的元素。

```java
ElementEventOptions options = new ElementEventOptions()
    .addData("value", "component.value")
    .addData("key", "event.key");
```

在服务器上，可以通过其键从事件中读取每个值。

### 执行 JavaScript {#executing-javascript}

`setCode()` 在事件触发之前在客户端运行一段 JavaScript 代码。这对于准备事件数据或在客户端反应而不进行服务器往返非常有用。

```java
ElementEventOptions options = new ElementEventOptions()
    .setCode("event.target.value = event.target.value.trim();");
```

### 过滤事件 {#filtering-events}

`setFilter()` 设置一个 JavaScript 表达式，以决定事件是否触发。如果它的值为 false，事件将不会到达服务器。这在您仅在某些条件下关心事件时非常有用，例如输入通过最小长度检查。

```java
ElementEventOptions options = new ElementEventOptions()
    .setFilter("event.target.value.length > 2");
```

### 防抖和节流 {#debouncing-and-throttling}

防抖和节流限制事件到达服务器的频率，这对于快速事件（如输入或滚动）非常有用。

防抖等到活动稳定后再触发。`setDebounce()` 接受一个以毫秒为单位的超时和一个可选的 <JavadocLink type="foundation" location="com/webforj/component/element/event/DebouncePhase" code='true'>DebouncePhase</JavadocLink>：`LEADING` 在突发开始时触发，`TRAILING` 在结束后触发，`BOTH` 在每个边缘触发。省略阶段时，默认为 `TRAILING`。

```java
ElementEventOptions options = new ElementEventOptions()
    .setDebounce(300, DebouncePhase.TRAILING);
```

节流在活动持续时以稳定的最大速率触发。`setThrottle()` 接受以毫秒为单位的超时。

```java
ElementEventOptions options = new ElementEventOptions()
    .setThrottle(300);
```

一个事件使用其中一种。设置防抖会清除相同选项上的任何节流，而设置节流会清除任何防抖。

### 注解 {#annotations}

元素事件选项也可以通过注解进行设置，这是一种更简洁的配置侦听器的方法。`@EventOptions` 注解包含数据条目，以及过滤器、防抖和节流设置。

```java
@EventOptions(
    data = {@EventData(key = "value", exp = "component.value")},
    debounce = @DebounceSettings(value = 200))
```

当您在调用位置同时传递一个 `ElementEventOptions` 时，其数据与注解的数据结合，并且其代码、过滤器、防抖和节流会覆盖注解的数据。

## 派发您自己的事件 {#dispatching-your-own-events}

到目前为止涵盖的事件来自您正在监听的组件。您编写的组件可以以相同的方式发布自己的事件，以便使用它的代码可以在不直接访问组件内部的情况下做出反应。

:::tip 何时派发自定义事件
当您的组件决定发生某些事情时，比如表单报告完成提交或编辑器报告已保存记录时，派发自定义事件。源自 `Element` 上的客户端交互的事件则会使用 [元素事件选项](#configuring-element-events) 进行配置。
:::

组件没有随附事件调度器，因此发布自己事件的组件持有自己的 <JavadocLink type="foundation" location="com/webforj/dispatcher/EventDispatcher" code='true'>EventDispatcher</JavadocLink>，并通过它进行发布。

### 定义事件 {#defining-the-event}

将事件定义为扩展 `EventObject` 的类。将发布事件的对象作为源传递给超类，并添加访问器以供侦听器需要的数据。

```java
public class OrderSubmittedEvent extends EventObject {
  private final String orderId;
  private final double total;

  public OrderSubmittedEvent(Object source, String orderId, double total) {
    super(source);
    this.orderId = orderId;
    this.total = total;
  }

  public String getOrderId() {
    return orderId;
  }

  public double getTotal() {
    return total;
  }
}
```

从事件中读取数据遵循与 [使用事件负载](#using-event-payload) 相同的逻辑。侦听器从事件获取所需的数据，而不是在之后查询源。

### 注册和派发 {#registering-and-dispatching}

创建一个调度器，为事件类型注册侦听器，并在事件发生时派发该类型的实例。注册返回一个 `ListenerRegistration`，您应保留它以便后续移除侦听器。

```java
EventDispatcher dispatcher = new EventDispatcher();

ListenerRegistration<OrderSubmittedEvent> registration =
    dispatcher.addListener(OrderSubmittedEvent.class, event -> {
      String id = event.getOrderId();
      // 处理事件
    });

dispatcher.dispatchEvent(new OrderSubmittedEvent(this, "ORD-1001", 49.99));
```

每个为该事件类型注册的侦听器都会在事件被派发时运行。

发布事件的组件在内部持有调度器，并暴露一个 `onXxx` 方法，而不是调度器本身，因此调用者以与内置事件相同的方式进行订阅：

```java
public ListenerRegistration<OrderSubmittedEvent> onSubmit(
    EventListener<OrderSubmittedEvent> listener) {
  return dispatcher.addListener(OrderSubmittedEvent.class, listener);
}
```

### 移除侦听器 {#removing-listeners}

通过注册移除侦听器，或者将侦听器传回调度器：

```java
registration.remove();

// 或

dispatcher.removeListener(OrderSubmittedEvent.class, registration.getListener());
```

要一次清除为事件类型注册的所有侦听器：

```java
dispatcher.removeAllListeners(OrderSubmittedEvent.class);
```

### 避免内存泄漏 {#avoiding-memory-leaks}

调度器保留其侦听器，每个侦听器保留它捕获的所有内容。一个 Lambda 表达式或内部类隐式捕获 `this` 及它使用的任何局部变量，因此侦听器背后的对象将在调度器持有它时保持可达。

当侦听器的生命周期超出其引用时，这就会成为问题。如果一个对话框注册了一个读取其自身模型的侦听器并在关闭时未将其移除，调度器仍然持有该侦听器，而该侦听器仍然持有对话框，因此两者都不能被垃圾回收。在一个创建许多短期视图的应用程序中，这种保留的侦听器会累积。

在以下情况下移除侦听器：

- 注册它的对象完成时，例如关闭的对话框或已导航离开的视图。
- 订阅与短期任务或一次性流程相关。

在清理期间将返回的 `ListenerRegistration` 保持在可以访问的地方，而不是注册一个您随后无法移除的侦听器。在组件中，`onDidDestroy()` 是清理的重点。
