---
sidebar_position: 15
title: Interval
_i18n_hash: 07054545ea64670e83423a6b11a5cce3
---
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/Interval" top='true'/>

<JavadocLink type="foundation" location="com/webforj/Interval" code='true' >Interval</JavadocLink> 类表示一个定时器，它在每次触发之间以固定时间间隔触发一个 [事件](../building-ui/events)。

`Interval` 类提供了一种简单的方法，用于在指定的延迟后触发事件。可以根据需要开始、停止和重新启动 `Interval`。此外，Intervals 可以支持多个监听器用于经过的事件。
针对 webforJ 框架进行了优化，提供了比标准 Java 定时器或 Swing 定时器更好的性能。

## 使用方法 {#usages}
`Interval` 类以固定时间延迟触发事件。巧妙利用 Intervals，您可以增强网站的用户交互和参与感，同时保持体验的动态性和趣味性：

1. **检查不活动状态**：如果在给定时间内没有在表单上的任何交互，则显示 [`Dialog`](../components/dialog) 组件。

2. **推荐内容**：在您的主页上根据每个 Interval 轮换推荐的文章、产品或促销内容。这保持了内容的动态和吸引力。

3. **实时数据**：在每个 Interval 上刷新您应用中的数据，例如股票价格、新闻源或天气更新，以保持数据的时效性。

## 管理 `Interval` 状态：启动、停止和重启 {#managing-interval-states-starting-stopping-and-restart}
Interval 需要手动激活；使用 `start()` 方法来启动它。要停止 Interval，请使用 `stop()` 方法。可以使用 `restart()` 方法重新启动 Interval。

## 调整 `Interval` 延迟 {#adjusting-the-interval-delay}

要修改 Interval 的延迟，请使用 `setDelay(float delay)` 方法。新延迟值在 Interval 被停止或重新启动后应用。

```java
//Changing the Delay
Interval.setDelay(2f);
Interval.restart();
```

:::tip
延迟可以是小数秒至毫秒的分辨率，但非常小的超时值会导致事件的洪流速度快于程序的响应能力。
:::

## 添加监听器 {#adding-listeners}

您可以使用 `addElapsedListener(EventListener\<Interval.ElapsedEvent\> listener)` 方法将额外的监听器附加到 Interval。一旦添加了监听器，如果 Interval 已经在运行，它会在下一个时间间隔自动触发。

```java
// Adding Listeners
float delay = 2f;

EventListener<Interval.ElapsedEvent> firstListener = (e -> {
// Executable code
});

Interval interval = new Interval(delay, firstListener);

EventListener<Interval.ElapsedEvent> secondListener = (e -> {
// Executable code
});

interval.addElapsedListener(secondListener);
```
