---
sidebar_position: 5
title: Client/Server Interaction
_i18n_hash: a69f444ce2e5a9dea37304d466f4e6ac
---
以下部分讨论了webforJ的各种性能特性和最佳实践，以及框架的实施细节。

在webforJ中创建应用时，客户端和服务器协同工作以处理客户端和服务器之间的数据，可以分为以下几个广泛的类别：

## 1. 服务器到客户端 {#1-server-to-client}

webforJ 方法如 `setText()` 包含在此类别中。webforJ 在服务器上运行的应用程序将数据发送到客户端，而无需等待响应。webforJ 自动优化此类别中的操作批处理以提高性能。

## 2. 客户端到服务器 {#2-client-to-server}

此类别涵盖事件流量，例如 `Button.onClick()` 方法。在大多数情况下，客户端在不等待任何响应的情况下将事件发送到服务器。事件对象通常包含与事件相关的附加参数，例如哈希码。由于此信息作为传送事件的一部分交付给服务器，因此一旦事件被接收，程序即可立即获得该信息。

## 3. 服务器到客户端再到服务器（往返） {#3-server-to-client-to-server-round-trip}

当应用程序查询客户端以获取一些无法缓存于服务器的动态信息时，会执行往返操作。方法如 `Label.getText()` 和 `Checkbox.isChecked()` 属于这一类别。当webforJ应用执行类似 `String title = myLabel.getText()` 的行时，它会完全停滞，直到服务器将该请求发送给客户端，然后等待客户端将响应发送回来。

如果应用程序向客户端发送了几条不需要响应的消息（第1类），然后再发送一条需要往返的消息（第3类），则应用程序必须等待客户端处理所有待处理消息，然后响应最终需要响应的消息。在某些情况下，这可能会增加延迟。如果没有引入此往返操作，客户端可以继续处理那些积压消息，而运行在服务器上的应用可以继续进行新工作。

## 提高性能 {#improve-performance}

尽可能避免第三类的往返操作，可以显著提高响应速度。例如，将ComboBox的onSelect功能从以下内容：

```java
private void comboBoxSelect(ListSelectEvent ev){
  ComboBox component = (ComboBox) ev.getComponent();

  // Goes to the client
  int selected = component.getSelectedIndex();
}
```

更改为以下内容：

```java
private void comboBoxSelect(ListSelectEvent ev){
  //Gets value from the event
  int selected = ev.getSelectedIndex();
}
```

在第一个代码片段中，`ComboBox.getSelectedIndex()` 在组件上执行，迫使往返回到客户端，从而引入了延迟。在第二个版本中，使用事件的 `ListSelectEvent.getSelectedIndex()` 方法检索作为原始事件一部分传送到服务器的值。

## 缓存 {#caching}

webforJ通过使用缓存进一步优化性能。一般而言，在此上下文中存在两种类型的数据：用户可以直接修改的数据和用户不可更改的数据。在第一种情况下，当检索用户将直接交互的信息时，有必要查询服务器以获取该信息。

然而，用户无法更改的信息可以进行缓存，以避免额外的性能影响。这确保了不必要地进行往返，从而提供更高效的用户体验。webforJ 以这种方式优化应用程序，以确保最佳性能。

## 加载时间 {#loading-time}

当用户启动webforJ应用程序时，它仅加载一小部分（大约 2.5 kB gzip）的JavaScript以引导会话。之后，它 根据应用使用相应功能的情况，动态下载单独的消息或JavaScript块。例如，服务器仅在应用程序创建其第一个 `Button` 组件时向客户端发送构建webforJ `Button` 所需的JavaScript，这导致初始加载时间显著改善，从而提高用户体验。
