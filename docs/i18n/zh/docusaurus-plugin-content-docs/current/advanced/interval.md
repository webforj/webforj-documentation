---
sidebar_position: 20
title: Interval
_i18n_hash: 1fd4c3fc2bf38df65a68d909a6ff77a3
---
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/Interval" top='true'/>

类 <JavadocLink type="foundation" location="com/webforj/Interval" code='true'>Interval</JavadocLink> 代表一个定时器，它以固定的时间延迟触发一个 [事件](../building-ui/events)。

您可以根据需要 [开始、停止和重新启动](#managing-interval-states-starting-stopping-and-restart) 一个 `Interval`，并为经过的事件添加多个 [监听器](#adding-listeners)。
在 webforJ 中，`Interval` 的 [性能更佳](#performance)，与标准的 Java 定时器或 Swing 定时器相比。

## 用法 {#usages}
`Interval` 类以固定的时间延迟触发事件。通过创造性地使用间隔，您可以在应用程序中创建动态且有趣的体验：

1. **检查不活动**：如果在给定时间内表单上没有任何交互，则显示一个 [`Dialog`](../components/dialog) 组件。

2. **特色内容**：在每个间隔内轮换突出显示的文章、产品或促销内容。这使内容动态且引人入胜。

3. **实时数据**：在每个间隔内刷新应用程序中的数据，例如股票价格、新闻数据或天气更新，以保持数据的时效性。

## 管理 `Interval` 状态：开始、停止和重新启动 {#managing-interval-states-starting-stopping-and-restart}
一个 Interval 需要手动激活；使用 `start()` 方法来启动它。要停止一个 Interval，使用 `stop()` 方法。`restart()` 方法可用于重新启动 Interval。

## 调整 `Interval` 延迟 {#adjusting-the-interval-delay}

要修改 Interval 的延迟，请使用 `setDelay(float delay)` 方法。新的延迟值在 Interval 被停止或重新启动后应用。

```java
// 修改延迟
Interval.setDelay(2f);
Interval.restart();
```

:::tip
延迟可以是小数秒，具有毫秒的分辨率，但非常小的超时值会导致事件的涌入速度快于程序的响应速度。
:::

## 添加监听器 {#adding-listeners}

您可以使用 `addElapsedListener(EventListener\<Interval.ElapsedEvent\> listener)` 方法将额外的监听器附加到 Interval。一旦添加了监听器，如果 Interval 已在运行，则会在下一个间隔自动触发。

```java
// 添加监听器
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

## 性能 {#performance}

`Interval` 类专门设计用于为 web 应用程序所面临的大负载提供更好的性能和可靠性。
在 Java Swing 中，使用 `Timer` 或新线程可以有效管理相同的行为，但这种方法在 web 应用中扩展性较差。
Web 应用程序可能有许多并发用户，如果每个用户创建一个新的 Timer 或线程，当线程耗尽时，系统可能会迅速崩溃。

有几种可行的选项可以在这种规模下工作： [**虚拟线程**](https://docs.oracle.com/en/java/javase/21/core/virtual-threads.html)、 [**Spring TaskExecutor 和 TaskScheduler**](https://docs.spring.io/spring-framework/reference/integration/scheduling.html)，以及 **`Interval`**。
根据您的应用程序和用例，任何这些可能是您的最佳选择。
作为默认，`Interval` 是一个可靠的选择，专门设计用于与 webforJ 一起使用，无需额外配置。
