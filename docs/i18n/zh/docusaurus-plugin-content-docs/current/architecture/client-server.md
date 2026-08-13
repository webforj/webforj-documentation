---
sidebar_position: 5
title: Client/Server Interaction
description: >-
  Understand how webforJ batches server-to-client calls, avoids costly round
  trips, and uses caching and on-demand chunks for performance.
_i18n_hash: 893b34ce2601ff273d03ba4091b7bc51
---
以下部分讨论了 webforJ 的各种性能特性和最佳实践，以及该框架的实现细节。

在 webforJ 中创建应用程序时，客户端和服务器共同工作以处理数据，客户端与服务器之间的交互可以分为以下广泛类别：

## 1. 服务器到客户端 {#1-server-to-client}

webforJ 方法，例如 `setText()`，包含在此类别中。运行在服务器上的 webforJ 应用程序在不等待响应的情况下将数据发送到客户端。webforJ 自动优化此类别中的操作批次以提高性能。

## 2. 客户端到服务器 {#2-client-to-server}

此类别涵盖事件流量，例如 `Button.onClick()` 方法。在大多数情况下，客户端在不等待任何响应的情况下将事件发送到服务器。事件对象通常包含与事件相关的其他参数，例如哈希码。由于这些信息作为传递事件的一部分发送到服务器，因此在接收到事件后，它立即可供程序使用。

## 3. 服务器到客户端再到服务器（往返） {#3-server-to-client-to-server-round-trip}

当应用程序查询客户端某些无法缓存的动态信息时，会执行往返操作。方法如 `Label.getText()` 和 `Checkbox.isChecked()` 就属于此类别。当 webforJ 应用程序执行 `String title = myLabel.getText()` 这样的代码时，它会完全静止，直到服务器将请求发送到客户端，然后等待客户端返回响应。

如果应用程序向客户端发送了几条不需要响应的消息（第1类），之后发送一条需要往返响应的消息（第3类），应用程序必须等待客户端处理所有待处理消息，然后再对需要回应的最终消息做出响应。在某些情况下，这可能会增加延迟。如果没有引入这次往返，客户端将能够继续处理那些积压的消息，而运行在服务器上的应用程序则可以继续进行新工作。

## 提高性能 {#improve-performance}

通过尽可能避免第三类的往返，显著提高响应能力是可能的。例如，将 ComboBox 的 onSelect 功能从以下内容：

```java
private void comboBoxSelect(ListSelectEvent ev){
  ComboBox component = (ComboBox) ev.getComponent();

  // 发送到客户端
  int selected = component.getSelectedIndex();
}
```

更改为以下内容：

```java
private void comboBoxSelect(ListSelectEvent ev){
  // 从事件中获取值
  int selected = ev.getSelectedIndex();
}
```

在第一段代码中，对 `ComboBox.getSelectedIndex()` 的调用强制执行一个往返到客户端，导致延迟。在第二个版本中，使用事件的 `ListSelectEvent.getSelectedIndex()` 方法获取了作为原始事件一部分传递到服务器的值。

## 缓存 {#caching}

webforJ 进一步通过利用缓存来优化性能。在这种情况下，通常存在两种类型的数据：用户可以直接更改的数据和用户无法更改的数据。在第一种情况下，检索用户将直接交互的信息时，需要向服务器查询该信息。

然而，用户无法更改的信息可以被缓存，以避免额外的性能损失。这确保了不必不必要地进行往返，从而提供更高效的用户体验。webforJ 通过这种方式优化应用程序，以确保最佳性能。

## 加载时间 {#loading-time}

当用户启动 webforJ 应用程序时，它加载
仅一小部分（约 2.5 kB gzip）JavaScript 来引导会话。
之后，它根据应用程序使用相应功能的情况动态下载单个消息或 JavaScript 块。例如，服务器仅在应用程序创建其第一个 `Button` 组件时，将构建 webforJ `Button` 所需的 JavaScript 发送给客户端。这导致初始加载时间显著改善，从而改善用户体验。
