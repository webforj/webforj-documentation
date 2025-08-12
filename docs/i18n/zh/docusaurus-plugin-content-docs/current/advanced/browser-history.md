---
title: Browser History
sidebar_position: 20
_i18n_hash: 9b05a2e65e60a737d341a6bc37e9546f
---
<DocChip chip='since' label='24.12' />
<JavadocLink type="foundation" location="com/webforj/router/history/BrowserHistory" top='true'/>

`BrowserHistory` 类在 webforJ 中提供了一个高层次的 API，用于与浏览器的历史记录交互。浏览器历史记录允许 Web 应用程序跟踪用户在应用内的导航。通过利用浏览器历史记录，开发人员可以启用诸如后退和前进导航、状态保存以及动态 URL 管理的功能，而无需进行完整的页面重新加载。

## Navigating through history {#navigating-through-history}

管理浏览器历史记录是大多数 Web 应用程序的核心功能。`BrowserHistory` API 使开发人员能够控制用户如何在应用程序的页面和状态之间导航，模仿或更改标准浏览器行为。

### Initializing or retrieving a history instance {#initializing-or-retrieving-a-history-instance}

要使用 `BrowserHistory` API，您有两个主要选项来获取历史实例：

1) **创建新的历史对象**：如果您独立于路由上下文工作，您可以直接创建 `BrowserHistory` 类的新实例。

```java
BrowserHistory history = new BrowserHistory();
```
这种方法适用于需要在路由框架之外显式管理历史记录的场景。

2) **从 `Router` 中检索历史**：如果您的应用使用了 webforJ 的 [路由解决方案](../routing/overview)，`Router` 组件会创建并管理一个共享的 `BrowserHistory` 实例。您可以直接从路由器访问该实例，从而确保在应用程序中保持一致的历史管理方式。

```java
BrowserHistory history = Router.getCurrent().getHistory();
```
当您的应用依赖于路由时，建议使用这种方法，因为它保持了跨所有视图和导航动作的一致性。

### Managing history {#managing-history}
在 webforJ 应用中，可以使用以下方法进行历史导航：

- `back()`：将浏览器历史记录向后移动一步，模拟用户按下浏览器的后退按钮。如果历史堆栈中没有更多条目，则停留在当前页面。

  ```java
  history.back();
  ```

- `forward()`：将浏览器历史记录向前移动一步，模拟用户按下浏览器的前进按钮。仅在历史堆栈中存在前进条目时有效。

  ```java
  history.forward();
  ```

- `go(int index)`：根据索引导航到历史堆栈中的特定点。正数向前移动，负数向后移动，零重新加载当前页面。与 `back()` 和 `forward()` 相比，此方法提供了更细粒度的控制。

  ```java
  history.go(-2); // 向后移动两个历史条目
  ```

- `size()`：检索会话历史堆栈中的条目总数，包括当前加载的页面。这对于了解用户的导航路径或实现自定义导航控件非常有用。

  ```java
  int historySize = history.size();
  System.out.println("History Length: " + historySize);
  ```

- `getLocation()`：返回相对于应用源的当前 URL 路径。此方法帮助开发人员获取当前路径，对于管理单页面应用中的基于 URL 的路由非常有用。

  ```java
  Optional<Location> location = history.getLocation();
  location.ifPresent(loc -> System.out.println("Current Path: " + loc.getFullURI()));
  ```

了解如何有效地导航是构建动态应用的基石。一旦掌握了导航的基本知识，就需要知道如何访问和更新与这些导航事件相关的 URL。

## Accessing and updating URL {#accessing-and-updating-url}

浏览和管理浏览器历史的核心方面是能够高效地访问和更新当前 URL 路径。这在现代 Web 应用中至关重要，因为 URL 更改对应于应用内部的不同视图或状态。`BrowserHistory` API 提供了一种简单的方法来检索和操作相对于应用根的当前路径。

:::tip webforJ `Router`
请参阅 [`Router` 文章](../routing/overview) 了解更多关于全面的 URL 和路由管理的信息
:::

`getLocation()` 检索相对于应用源的当前 URL 路径。`getLocation()` 方法返回一个 `Optional<Location>`，允许您在不包含域名的情况下获取 URL 的路径部分。

```java
Optional<Location> location = history.getLocation();
location.ifPresent(loc -> System.out.println("Current Path: " + loc.getFullURI()));
```

## Managing state {#managing-state}

`BrowserHistory` 允许您使用 `pushState()` 和 `replaceState()` 方法保存和管理自定义状态信息。通过使用状态管理方法，您可以控制作为历史条目存储的信息，这有助于在用户在应用中前后导航时保持一致的用户体验。可以在您的 webforJ 应用中使用以下方法来管理状态。

- `pushState(Object state, Location location)`：向历史堆栈添加新条目。接受一个状态对象和一个表示新 URL 的 `Location` 对象。

```java
Location location = new Location("/new-page");
history.pushState(myStateObject, location);
```

- `replaceState(Object state, Location location)`：替换当前历史条目。此方法不会像上面的方法创建新的条目。

```java
Location location = new Location("/updated-page");
history.replaceState(myStateObject, location);
```

- `getState(Class<T> classOfT)`：检索与当前历史条目关联的状态对象。此方法返回一个包含状态对象的可选项，该对象被反序列化为指定的类。

```java
Optional<MyState> currentState = history.getState(MyState.class);
currentState.ifPresent(state -> System.out.println("Current Page: " + state.getViewName()));
```

### Listening for state changes {#listening-for-state-changes}
`BrowserHistory` 类提供了注册事件监听器的能力，以响应历史状态的变化。

`addHistoryStateChangeListener(EventListener<HistoryStateChangeEvent> listener)` 注册一个监听器，当状态变化时触发，例如当用户单击浏览器的后退或前进按钮时。此方法设置了一个浏览器的 `popstate` 事件的监听器，使您的应用能够响应用户行为或以编程方式触发的状态更改。

```java
history.addHistoryStateChangeListener(event -> {
    System.out.println("History state changed to: " + event.getLocation().getFullURI());
});
```

有效的状态管理使您能够创建响应用户操作的动态应用程序。用户可以在您的应用中导航而不会失去上下文，从而提供更顺畅和直观的体验。此外，保存状态还可以启用高级功能，如恢复视图位置、维护过滤或排序设置以及支持深度链接——所有这些都有助于创建更具吸引力和可靠性的应用。
