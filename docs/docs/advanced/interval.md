---
sidebar_position: 20
title: Interval
---

<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/Interval" top='true'/>

The <JavadocLink type="foundation" location="com/webforj/Interval" code='true' >Interval</JavadocLink> class represents a timer that triggers an [event](../building-ui/events) with a fixed time delay between each triggering.

You can [start, stop, and restart](#managing-interval-states-starting-stopping-and-restart) an `Interval` as needed and add multiple [listeners](#adding-listeners) for the elapsed event.
In webforJ, an `Interval` has [better performance](#performance) compared to a standard Java timer or Swing timer.

## Usages {#usages}
The `Interval` class triggers events at a fixed time delay. By using Intervals creatively, you can create dynamic and interesting experiences in your app:

1. **Check for Inactivity**: Display a [`Dialog`](../components/dialog) component if there hasn't been any interaction on a form within a given time.

2. **Featured Content**: Rotate through featured articles, products, or promotions on your homepage on each Interval. This keeps the content dynamic and engaging.

3. **Live Data**: Refresh data on your app, such as stock prices, news feeds, or weather updates, on each Interval to keep the data current.

## Managing `Interval` states: starting, stopping, and restart {#managing-interval-states-starting-stopping-and-restart}
An Interval requires manual activation; use the `start()` method to initiate it. To stop an Interval, use the `stop()` method. The `restart()` method can be used to restart the Interval.

## Adjusting the `Interval` delay {#adjusting-the-interval-delay}

To modify the delay of an Interval, use the `setDelay(float delay)` method. The new delay value is applied after the Interval is either stopped or restarted.


```java
//Changing the Delay
Interval.setDelay(2f);
Interval.restart();
```

:::tip
The delay can be fractional seconds to millisecond in resolution, but a very small timeout value causes a flood of events faster than the program can respond to them.
:::

## Adding listeners {#adding-listeners}

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

## Performance {#performance}

The `Interval` class is specifically designed to provide better performance and reliability for the large loads encountered by web apps.
In Java Swing, the same behavior can be sufficiently managed by a `Timer` or a new thread, but that approach doesn't scale well for web apps.
Web apps are likely to have many concurrent users, and if each user creates a new Timer or thread, the system can quickly break when it runs out of threads.

There are several viable options that work at this scale: [**virtual threads**](https://docs.oracle.com/en/java/javase/21/core/virtual-threads.html), [**Spring TaskExecutor and TaskScheduler**](https://docs.spring.io/spring-framework/reference/integration/scheduling.html), and **`Interval`**.
Depending on your app and use-case, any of these may be the best option for you.
As a default, `Interval` is a reliable choice specifically designed to work with webforJ, and requires no additional configuration.