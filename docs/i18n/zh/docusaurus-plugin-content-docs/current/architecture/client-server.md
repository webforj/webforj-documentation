---
sidebar_position: 5
title: Client/Server Interaction
_i18n_hash: e5eafeb3f76c9a412d5a124f2eed2da8
---
以下部分讨论了 webforJ 的各种性能特性和最佳实践，以及框架的实施细节。

在创建 webforJ 应用程序时，客户端和服务器共同工作，数据在客户端和服务器之间的操作可以分为以下广泛类型：

## 1. 服务器到客户端 {#1-server-to-client}

包括诸如 `setText()` 的 webforJ 方法属于这一类别。运行在服务器上的 webforJ 应用程序将数据发送到客户端，而不等待响应。webforJ 自动优化此类别中的操作批次以提高性能。

## 2. 客户端到服务器 {#2-client-to-server}

该类别涵盖事件流量，例如 `Button.onClick()` 方法。在大多数情况下，客户端发送事件到服务器而不等待任何响应。事件对象通常包含与事件相关的其他参数，例如哈希码。由于这些信息作为事件传递的一部分发送给服务器，因此在事件收到后，程序会立即获得这些信息。

## 3. 服务器到客户端再到服务器（往返） {#3-server-to-client-to-server-round-trip}

往返通信是在应用程序向客户端查询一些无法在服务器上缓存的动态信息时执行的。诸如 `Label.getText()` 和 `Checkbox.isChecked()` 的方法属于此类别。当 webforJ 应用程序执行如下代码 `String title = myLabel.getText()` 时，它会完全停止，等待服务器将该请求发送给客户端，然后等待客户端发送响应回来。

如果应用程序先是发送多条无需响应的消息（类别 1），然后再发送一条需要往返的消息（类别 3），则应用程序必须等待客户端处理所有待处理消息，然后再响应需要响应的最终消息。在某些情况下，这可能会增加延迟。如果没有引入这次往返，客户端将能够继续处理那些积压的消息，而运行在服务器上的应用程序则可以继续进行新的工作。

## 提高性能 {#improve-performance}

尽可能避免第三类别的往返，可以显著提高应用程序的响应能力。例如，将 ComboBox 的 onSelect 功能从这个：

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

在第一段代码中，在组件上执行 `ComboBox.getSelectedIndex()` 会导致返回客户端的往返，从而引入延迟。在第二个版本中，使用事件的 `ListSelectEvent.getSelectedIndex()` 方法检索值，该值作为原始事件的一部分发送给服务器。

## 缓存 {#caching}

webforJ 通过利用缓存进一步优化性能。一般来说，在这种情况下有两种类型的数据：用户可以直接更改的数据，以及用户无法更改的数据。在第一种情况下，当检索用户将直接交互的信息时，必须向服务器查询该信息。

然而，不能被用户更改的信息可以被缓存，以避免额外的性能损失。这确保了不必要的往返不需要被执行，从而提供了更高效的用户体验。webforJ 通过这种方式优化应用程序，以确保最佳性能。

## 加载时间 {#loading-time}

当用户启动 webforJ 应用程序时，它只加载一小部分（约 2.5kB gzip）的 JavaScript 来引导会话。之后，它根据应用程序使用相应功能的需求动态下载单独的消息或 JavaScript 块。例如，服务器仅在应用程序创建第一个 `Button` 组件时向客户端发送构建 webforJ `Button` 所需的 JavaScript。这导致初始加载时间的显著改善，从而提供更好的用户体验。
