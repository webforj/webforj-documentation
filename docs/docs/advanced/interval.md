---
sidebar_position: 45
title: Interval
---
import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

The <JavadocLink type="foundation" location="com/webforj/Interval" code='true' >Interval</JavadocLink> class represents a timer that triggers an [event](../building-ui/events) with a fixed time delay between each triggering.

The `Interval` class provides a straightforward way to trigger events after a specified delay. It's possible to start, stop, and restart an `Interval` as needed. Additionally, Intervals can support multiple listeners for the elapsed event.
Optimized for the webforJ framework, it offers better performance compared to the standard Java timer or the Swing timer.

## Usages
The `Interval` class triggers event(s) at a fixed time delay. Leveraging Intervals creatively, you can enhance user interaction and engagement on your website while keeping the experience dynamic and interesting.:

1. **Check for Inactivity**: Display a [`Dialog`](../components/dialog) component if there hasn't been any interaction on a form within a given time.

2. **Featured Content**: Rotate through featured articles, products, or promotions on your homepage on each Interval. This keeps the content dynamic and engaging.

3. **Live Data**: Refresh data on your app, such as stock prices, news feeds, or weather updates, on each Interval to keep the data current.

## Managing `Interval` states: starting, stopping, and restart
An Interval requires manual activation; use the `start()` method to initiate it. To stop an Interval, use the `stop()` method. The `restart()` method can be used to restart the Interval.

## Adjusting the `Interval` delay

To modify the delay of an Interval, use the `setDelay(float delay)` method. The new delay value is applied after the Interval is either stopped or restarted.


```java
//Changing the Delay
Interval.setDelay(2f);
Interval.restart();
```

:::tip
The delay can be fractional seconds to millisecond in resolution, but a very small timeout value causes a flood of events faster than the program can respond to them.
:::

## Adding listeners

You can attach additional listeners to an Interval using the `addElapsedListener(EventListener\<Interval.ElapsedEvent\> listener)` method. Once a listener is added, it automatically triggers on the next interval if the Interval is already running.

```java
// Adding Listeners
float dealy = 2f;

EventListener<Interval.ElapsedEvent> firstListener = (e -> {
// Executable code
});

Interval interval = new Interval(delay, firstListener);

EventListener<Interval.ElapsedEvent> secondListener = (e -> {
// Executable code
});

interval.addElapsedListener(secondListener);
```