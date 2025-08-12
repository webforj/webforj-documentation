---
sidebar_position: 15
title: Interval
_i18n_hash: dc02bb8f8bb43ee67f300071d3ab4ec7
---
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/Interval" top='true'/>

类 <JavadocLink type="foundation" location="com/webforj/Interval" code='true' >Interval</JavadocLink> 表示一个计时器，它以固定的时间延迟触发一个[event](../building-ui/events)。

`Interval` 类提供了一种简单的方法来在指定的延迟后触发事件。可以根据需要启动、停止和重新启动 `Interval`。此外，Intervals 可以支持多个侦听器以处理已过去的事件。
针对 webforJ 框架进行了优化，它比标准的 Java 计时器或 Swing 计时器提供了更好的性能。

## 用法 {#usages}
`Interval` 类在固定的时间延迟下触发事件。通过创造性地利用 Intervals，您可以增强网站上的用户互动和参与感，同时保持体验动态有趣：

1. **检查非活动状态**：如果在给定时间内没有在表单上进行任何互动，则显示一个[`Dialog`](../components/dialog) 组件。

2. **推荐内容**：在每个 Interval 上轮换显示推荐的文章、产品或促销信息在您的首页。这使内容保持动态并吸引人。

3. **实时数据**：在每个 Interval 上刷新您的应用中的数据，例如股票价格、新闻源或天气更新，以保持数据的最新性。

## 管理 `Interval` 状态：启动、停止和重新启动 {#managing-interval-states-starting-stopping-and-restart}
Interval 需要手动激活；使用 `start()` 方法来启动它。要停止 Interval，请使用 `stop()` 方法。可以使用 `restart()` 方法重新启动 Interval。

## 调整 `Interval` 延迟 {#adjusting-the-interval-delay}

要修改 Interval 的延迟，请使用 `setDelay(float delay)` 方法。新延迟值在 Interval 被停止或重新启动后应用。

```java
//改变延迟
Interval.setDelay(2f);
Interval.restart();
```

:::tip
延迟可以是分数秒，达到毫秒的分辨率，但非常小的超时值会导致事件涌入，速度快于程序能响应的速度。
:::

## 添加侦听器 {#adding-listeners}

您可以使用 `addElapsedListener(EventListener\<Interval.ElapsedEvent\> listener)` 方法将额外的侦听器附加到一个 Interval。添加侦听器后，如果 Interval 已在运行，它会在下一个间隔自动触发。

```java
// 添加侦听器
float delay = 2f;

EventListener<Interval.ElapsedEvent> firstListener = (e -> {
// 可执行代码
});

Interval interval = new Interval(delay, firstListener);

EventListener<Interval.ElapsedEvent> secondListener = (e -> {
// 可执行代码
});

interval.addElapsedListener(secondListener);
```
