---
title: Browser History
sidebar_position: 20
_i18n_hash: 877c6513ffd8f2b3ed8d4199bc2f5b39
---
<DocChip chip='since' label='24.12' />
<JavadocLink type="foundation" location="com/webforj/router/history/BrowserHistory" top='true'/>

`BrowserHistory` 类在 webforJ 中提供了与浏览器历史记录交互的高层 API。浏览器历史记录允许 web 应用程序跟踪用户在应用内的导航。通过利用浏览器历史，开发人员可以启用返回和前进导航、状态保存和动态 URL 管理等功能，而无需进行完整页面重新加载。

## 导航历史 {#navigating-through-history}

管理浏览器的历史记录是大多数 web 应用程序的核心功能。`BrowserHistory` API 使开发人员能够控制用户如何在应用程序的页面和状态之间导航，从而模仿或改变标准的浏览器行为。

### 初始化或获取历史实例 {#initializing-or-retrieving-a-history-instance}

要使用 `BrowserHistory` API，您有两种主要选择来获取历史实例：

1) **创建新的历史对象**：如果您独立于路由上下文工作，可以直接创建 `BrowserHistory` 类的新实例。

```java
BrowserHistory history = new BrowserHistory();
```
这种方法适用于需要在路由框架外显式管理历史的场景。

2) **从 `Router` 中检索历史**：如果您的应用使用 webforJ 的 [路由解决方案](../routing/overview)，则 `Router` 组件创建并管理一个共享的 `BrowserHistory` 实例。您可以直接从路由器访问此实例，确保您的应用中历史管理方式一致。

```java
BrowserHistory history = Router.getCurrent().getHistory();
```
当您的应用依赖于路由时，建议使用这种方法，因为它在所有视图和导航操作中保持历史管理的一致性。

### 管理历史 {#managing-history}
可以使用以下方法在 webforJ 应用中进行历史导航：

- `back()`: 将浏览器历史回退一步，模拟用户在浏览器中按下后退按钮。如果历史堆栈中没有更多条目，则停留在当前页面。

  ```java
  history.back();
  ```

- `forward()`: 将浏览器历史前进一步，模拟用户在浏览器中按下前进按钮。仅在历史堆栈中存在前项时有效。

  ```java
  history.forward();
  ```

- `go(int index)`: 根据索引导航到历史堆栈中的特定点。正数往前移动，负数往后移动，零则重新加载当前页面。此方法相比 `back()` 和 `forward()` 提供了更细粒度的控制。

  ```java
  history.go(-2); // 在历史堆栈中回退两个条目
  ```

- `size()`: 检索会话历史堆栈中的条目总数，包括当前加载的页面。这对于理解用户的导航路径或实现自定义导航控件非常有用。

  ```java
  int historySize = history.size();
  System.out.println("历史长度: " + historySize);
  ```

- `getLocation()`: 返回相对于应用程序源的当前 URL 路径。此方法帮助开发人员获取当前路径，对于管理单页面应用程序中的基于 URL 的路由非常有用。

  ```java
  Optional<Location> location = history.getLocation();
  location.ifPresent(loc -> System.out.println("当前路径: " + loc.getFullURI()));
  ```

高效导航的理解是构建动态应用的基石。一旦掌握了导航的基础知识，了解如何访问和更新与这些导航事件相关的 URL 也是至关重要的。

## 访问和更新 URL {#accessing-and-updating-url}

导航和管理浏览器历史的一个核心方面是能够高效地访问和更新当前 URL 路径。这在现代 web 应用中至关重要，因为 URL 变化对应于应用中不同的视图或状态。`BrowserHistory` API 提供了一种简单的方法来检索和操作相对于应用根目录的当前路径。

:::tip webforJ `Router`
查看 [`Router` 文章](../routing/overview) 以了解有关全面 URL 和路由管理的更多信息
:::

`getLocation()` 检索相对于应用程序源的当前 URL 路径。`getLocation()` 方法返回一个 `Optional<Location>`，允许您在不包含域名的情况下获取 URL 的路径部分。

```java
Optional<Location> location = history.getLocation();
location.ifPresent(loc -> System.out.println("当前路径: " + loc.getFullURI()));
```

## 管理状态 {#managing-state}

`BrowserHistory` 允许您使用 `pushState()` 和 `replaceState()` 方法保存和管理自定义状态信息。通过使用状态管理方法，您可以控制作为历史条目一部分存储的信息，这有助于在应用内来回导航时保持一致的用户体验。以下方法可以用来在您的 webforJ 应用中管理状态。

- `pushState(Object state, Location location)`: 向历史堆栈添加新条目。接受一个状态对象和一个表示新 URL 的 `Location` 对象。

```java
Location location = new Location("/new-page");
history.pushState(myStateObject, location);
```

- `replaceState(Object state, Location location)`: 替换当前的历史条目。与上面的方法不同，这不会在堆栈中创建新条目。

```java
Location location = new Location("/updated-page");
history.replaceState(myStateObject, location);
```

- `getState(Class<T> classOfT)`: 检索与当前历史条目相关联的状态对象。此方法返回一个包含状态对象的 Optional，该对象被反序列化为指定类。

```java
Optional<MyState> currentState = history.getState(MyState.class);
currentState.ifPresent(state -> System.out.println("当前页面: " + state.getViewName()));
```

### 监听状态变化 {#listening-for-state-changes}
`BrowserHistory` 类提供了注册事件监听器的能力，以响应历史状态的变化。

`addHistoryStateChangeListener(EventListener<HistoryStateChangeEvent> listener)` 注册一个在状态变化时触发的监听器，例如，当用户点击浏览器的后退或前进按钮时。此方法为浏览器的 `popstate` 事件设置监听器，允许您的应用响应用户的操作或程序matically 触发的状态变化。

```java
history.addHistoryStateChangeListener(event -> {
    System.out.println("历史状态改变为: " + event.getLocation().getFullURI());
});
```

有效的状态管理使您能够创建响应用户操作的应用。用户可以在您的应用中导航而不会失去上下文，从而提供更流畅和直观的体验。此外，保存状态使得恢复视图位置、维护过滤或排序设置以及支持深度链接等高级功能成为可能——这些都为应用的参与性和可靠性贡献了更多。
