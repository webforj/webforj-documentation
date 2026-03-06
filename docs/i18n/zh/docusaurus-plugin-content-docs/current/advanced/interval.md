---
sidebar_position: 20
title: Interval
_i18n_hash: a220fb1607867630d6bfc03a1ce5d3e9
---
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/Interval" top='true'/>

类 <JavadocLink type="foundation" location="com/webforj/Interval" code='true'>Interval</JavadocLink> 代表一个定时器，它在每次触发之间具有固定的时间延迟触发一个 [event](../building-ui/events)。

`Interval` 类提供了一种简单的方法来在指定延迟后触发事件。可以根据需要启动、停止和重新启动 `Interval`。在 webforJ 中，`Interval` 的性能优于标准 Java 定时器或 Swing 定时器。它还支持多个侦听器用于经过事件。

## Usages {#usages}
`Interval` 类以固定的时间延迟触发事件。通过创造性地使用间隔，可以为您的应用程序创建动态而有趣的体验：

1. **检查非活动状态**：如果在给定时间内没有在表单上进行任何交互，则显示 [`Dialog`](../components/dialog) 组件。

2. **特色内容**：在每个 Interval 上旋转特色文章、产品或促销活动，在您的主页上保持内容动态和吸引人。

3. **实时数据**：在每个 Interval 上刷新应用程序中的数据，如股票价格、新闻提要或天气更新，以保持数据的当前性。

## Managing `Interval` states: starting, stopping, and restart {#managing-interval-states-starting-stopping-and-restart}
Interval 需要手动激活；使用 `start()` 方法来启动它。要停止一个 Interval，使用 `stop()` 方法。可以使用 `restart()` 方法重新启动 Interval。

## Adjusting the `Interval` delay {#adjusting-the-interval-delay}

要修改 Interval 的延迟，请使用 `setDelay(float delay)` 方法。新的延迟值在 Interval 被停止或重新启动后应用。

```java
//Changing the Delay
Interval.setDelay(2f);
Interval.restart();
```

:::tip
延迟可以是小数秒到毫秒的分辨率，但非常小的超时值会导致事件以快于程序响应的速度涌入。
:::

## Adding listeners {#adding-listeners}

您可以使用 `addElapsedListener(EventListener\<Interval.ElapsedEvent\> listener)` 方法将其他侦听器附加到 Interval。添加侦听器后，如果 Interval 已经在运行，它将在下一个间隔自动触发。

```java
// Adding Listeners
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
