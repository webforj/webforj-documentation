---
sidebar_position: 5
title: Client/Server Interaction
_i18n_hash: ed7cdbde8cee6b173108326dfa7fce2a
---
以下部分讨论了webforJ的各种性能质量和最佳实践，以及框架的实现细节。

在webforJ中创建应用程序时，客户端和服务器协同工作，客户端与服务器之间的数据操作可以分为以下广泛类别：

## 1. 从服务器到客户端 {#1-server-to-client}

webforJ方法如`setText()`包含在此类别中。运行在服务器上的webforJ应用程序在不等待响应的情况下将数据发送到客户端。webforJ会自动优化此类别中的操作批处理以提高性能。

## 2. 从客户端到服务器 {#2-client-to-server}

此类别涵盖事件流量，例如`Button.onClick()`方法。在大多数情况下，客户端在没有等待任何响应的情况下将事件发送到服务器。事件对象通常包含与事件相关的额外参数，例如哈希码。由于此信息作为传递事件的部分直接送达服务器，因此在收到事件后，它立即可用于程序。

## 3. 从服务器到客户端再到服务器（往返） {#3-server-to-client-to-server-round-trip}

当应用程序查询客户端以获取一些无法在服务器上缓存的动态信息时，执行往返操作。`Label.getText()`和`Checkbox.isChecked()`这样的函数属于此类别。当webforJ应用程序执行类似`String title = myLabel.getText()`的代码行时，它会完全停止，等待服务器将请求发送给客户端，然后再等待客户端发送响应回来。

如果应用程序向客户端发送多个不需要响应的消息（类别1），然后再发送一条需要往返的消息（类别3），应用程序必须等待客户端处理所有待处理消息，然后对最后一条需要响应的消息作出回应。在某些情况下，这可能会造成延迟。如果没有引入往返，客户端本可以在处理这些滞留消息时继续工作，而运行在服务器上的应用程序可以转向新的工作。

## 提高性能 {#improve-performance}

通过尽量避免第三类别的往返，可以显著提高应用程序的响应能力。例如，将ComboBox的onSelect功能从以下代码：

```java
private void comboBoxSelect(ListSelectEvent ev){
    ComboBox component = (ComboBox) ev.getComponent();

    // 发送到客户端
    int selected = component.getSelectedIndex();
}
```

改为以下代码：

```java
private void comboBoxSelect(ListSelectEvent ev){
    // 从事件中获取值
    int selected = ev.getSelectedIndex();
}
```

在第一个代码段中，`ComboBox.getSelectedIndex()`在组件上执行，强制进行一次往返，引入了延迟。在第二个版本中，使用事件的`ListSelectEvent.getSelectedIndex()`方法检索随原始事件传递给服务器的值。

## 缓存 {#caching}

webforJ进一步通过利用缓存来优化性能。一般来说，在这个上下文中存在两种类型的数据：用户可以直接更改的数据和用户无法更改的数据。在第一种情况下，当检索用户直接交互的信息时，需要查询服务器以获取这些信息。

然而，无法被用户更改的信息可以进行缓存，以避免额外的性能损失。这确保了不必不必要地进行往返，从而提供更高效的用户体验。webforJ以这种方式优化应用程序，以确保最佳性能。

## 加载时间 {#loading-time}

当用户启动webforJ应用程序时，它加载的仅仅是一小块（约2.5kB gzip）JavaScript以引导会话。在此之后，它会根据应用程序使用相应功能的需要动态下载单个消息或JavaScript代码块。例如，服务器只在应用程序创建第一个`Button`组件时发送必要的JavaScript，这样可以显著改善初始加载时间，进而改善用户体验。
