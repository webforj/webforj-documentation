---
title: Browser History
sidebar_position: 30
_i18n_hash: e0426f58e099d38fa58fa2b722ec0605
---
<DocChip chip='since' label='24.12' />
<JavadocLink type="foundation" location="com/webforj/router/history/BrowserHistory" top='true'/>

`BrowserHistory` 类在 webforJ 中提供了与浏览器历史记录交互的高级 API。浏览器历史记录允许 Web 应用程序跟踪用户在应用程序内的导航。通过使用浏览器历史记录，开发人员可以启用诸如后退和前进导航、状态保留和动态 URL 管理等功能，而无需进行完整的页面重新加载。

## 历史导航 {#navigating-through-history}

管理浏览器历史记录是大多数 Web 应用程序的核心功能。`BrowserHistory` API 使开发人员能够控制用户如何在应用程序的页面和状态之间导航，模仿或改变标准的浏览器行为。

### 初始化或检索历史实例 {#initializing-or-retrieving-a-history-instance}

要使用 `BrowserHistory` API，您有两个主要选项来获取历史实例：

1) **创建新的历史对象**：如果您独立于路由上下文工作，可以直接创建一个 `BrowserHistory` 类的新实例。

```java
BrowserHistory history = new BrowserHistory();
```
这种方法适用于您需要在路由框架之外显式管理历史的场景。

2) **从 `Router` 中检索历史**：如果您的应用使用 webforJ 的 [路由解决方案](../routing/overview)，`Router` 组件会创建并管理一个共享的 `BrowserHistory` 实例。您可以直接从路由器访问此实例，以在应用程序中保持一致的历史管理方法。

```java
BrowserHistory history = Router.getCurrent().getHistory();
```
当您的应用依赖路由时，推荐使用此方法，因为它在所有视图和导航操作中保持历史管理的一致性。

### 管理历史 {#managing-history}
以下方法可以用于在 webforJ 应用程序中进行历史导航：

- `back()`：将浏览器历史向后移动一步，模拟用户在其浏览器中按下后退按钮。如果历史堆栈中没有更多条目，它将停留在当前页面。

  ```java
  history.back();
  ```

- `forward()`：将浏览器历史向前移动一步，模拟用户在其浏览器中按下前进按钮。仅当历史堆栈中前方有条目时，此操作才有效。

  ```java
  history.forward();
  ```

- `go(int index)`：根据索引导航到历史堆栈中的特定点。正数向前移动，负数向后移动，零则重新加载当前页面。与 `back()` 和 `forward()` 相比，此方法提供了更细粒度的控制。

  ```java
  history.go(-2); // 向后移动历史堆栈中的两个条目
  ```

- `size()`：检索会话历史堆栈中条目的总数，包括当前加载的页面。这对于了解用户的导航路径或实现自定义导航控件非常有用。

  ```java
  int historySize = history.size();
  System.out.println("历史长度: " + historySize);
  ```

- `getLocation()`：返回相对于应用程序来源的当前 URL 路径。此方法可以帮助开发人员获取当前路径，这对管理单页面应用程序中的基于 URL 的路由非常有用。

  ```java
  Optional<Location> location = history.getLocation();
  location.ifPresent(loc -> System.out.println("当前路径: " + loc.getFullURI()));
  ```

了解如何有效地导航是构建动态应用程序的基石。掌握导航基础知识后，了解如何访问和更新与这些导航事件相关的 URL 至关重要。

## 访问和更新 URL {#accessing-and-updating-url}

导航和管理浏览器历史的核心方面是能够高效访问和更新当前 URL 路径。这在现代 Web 应用程序中至关重要，因为 URL 的更改对应于应用程序内不同的视图或状态。`BrowserHistory` API 提供了一种简单的方法来检索和操作相对于应用程序根的当前路径。

:::tip webforJ `Router`
请参阅 [`Router` 文章](../routing/overview) 以了解有关全面的 URL 和路由管理的更多信息
:::

`getLocation()` 检索相对于应用程序来源的当前 URL 路径。`getLocation()` 方法返回一个 `Optional<Location>`，允许您获取 URL 的路径部分而无需域名。

```java
Optional<Location> location = history.getLocation();
location.ifPresent(loc -> System.out.println("当前路径: " + loc.getFullURI()));
```

## 管理状态 {#managing-state}

`BrowserHistory` 允许您使用 `pushState()` 和 `replaceState()` 方法保存和管理自定义状态信息。通过使用状态管理方法，您可以控制作为历史条目一部分的信息，从而帮助维护在应用程序内前后导航时的一致用户体验。以下方法可以用于管理您在 webforJ 应用程序中的状态。

- `pushState(Object state, Location location)`：向历史堆栈添加新条目。接受一个状态对象和一个表示新 URL 的 `Location` 对象。

```java
Location location = new Location("/new-page");
history.pushState(myStateObject, location);
```

- `replaceState(Object state, Location location)`：替换当前历史条目。此方法不会在堆栈中创建新条目，像上面的方法一样。

```java
Location location = new Location("/updated-page");
history.replaceState(myStateObject, location);
```

- `getState(Class<T> classOfT)`：检索与当前历史条目关联的状态对象。此方法返回一个 Optional，包含状态对象，该状态对象被反序列化为指定的类。

```java
Optional<MyState> currentState = history.getState(MyState.class);
currentState.ifPresent(state -> System.out.println("当前页面: " + state.getViewName()));
```

### 监听状态变化 {#listening-for-state-changes}
`BrowserHistory` 类提供了注册事件监听器的能力，这些监听器对历史状态的变化做出响应。

`addHistoryStateChangeListener(EventListener<HistoryStateChangeEvent> listener)` 注册一个监听器，当状态变化时会被触发，例如当用户点击浏览器的后退或前进按钮时。此方法设置了一个监听器以响应浏览器的 `popstate` 事件，使您的应用能够对用户操作或程序触发的状态变化作出响应。

```java
history.addHistoryStateChangeListener(event -> {
    System.out.println("历史状态已改变为: " + event.getLocation().getFullURI());
});
```

有效地管理状态可以创建响应用户操作的应用程序。用户可以通过您的应用程序进行导航而不会失去上下文，从而使体验更加顺畅和直观。此外，保存状态使高级功能成为可能，例如恢复视图位置、维持过滤或排序设置以及支持深度链接——所有这些都为更具吸引力和可靠的应用程序贡献了力量。
