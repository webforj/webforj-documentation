---
sidebar_position: 5
title: Client/Server Interaction
_i18n_hash: ae7a34d844eee10906ce2230f95a05cc
---
以下部分讨论了webforJ的各种性能质量和最佳实践，以及该框架的实现细节。

在创建webforJ应用时，客户端和服务器一起工作，以在客户端和服务器之间操作数据，可以分为以下几个广泛类别：

## 1. 从服务器到客户端 {#1-server-to-client}

webforJ方法如`setText()`包含在这一类别中。运行在服务器上的webforJ应用在不等待响应的情况下将数据发送到客户端。webforJ自动优化此类别中的操作批次，以提高性能。

## 2. 从客户端到服务器 {#2-client-to-server}

该类别涵盖事件流量，例如`Button.onClick()`方法。在大多数情况下，客户端将事件发送到服务器而不等待任何响应。事件对象通常包含与事件相关的其他参数，例如哈希码。由于此信息作为传递事件的一部分发送到服务器，因此当事件被接收时，程序可以立即使用这些信息。

## 3. 从服务器到客户端再到服务器（往返） {#3-server-to-client-to-server-round-trip}

当应用查询客户端获取一些无法在服务器上缓存的动态信息时，往返操作将被执行。方法如`Label.getText()`和`Checkbox.isChecked()`属于这一类别。当webforJ应用执行诸如`String title = myLabel.getText()`的行时，它会在服务器将该请求发送到客户端时完全停止，然后等待客户端返回响应。

如果应用向客户端发送几条不需要响应的消息（类别1），然后再发送一条需要往返的消息（类别3），应用必须等待客户端处理所有待处理的消息，然后才响应最后一条需要响应的消息。在某些情况下，这可能会增加延迟。如果没有引入这个往返，客户端将能够在服务器运行的应用处理那些积压消息时继续工作。

## 提高性能 {#improve-performance}

通过尽量避免第三类的往返，可以显著提高响应能力。例如，将ComboBox的onSelect功能从以下内容：

```java
private void comboBoxSelect(ListSelectEvent ev){
    ComboBox component = (ComboBox) ev.getComponent();

    // 前往客户端
    int selected = component.getSelectedIndex();
}
```

更改为如下内容：

```java
private void comboBoxSelect(ListSelectEvent ev){
    // 从事件获取值
    int selected = ev.getSelectedIndex();
}
```

在第一段代码中，`ComboBox.getSelectedIndex()`在组件上执行时强制进行了往返，从而引入了延迟。在第二个版本中，使用事件的`ListSelectEvent.getSelectedIndex()`方法检索作为原始事件的一部分传递到服务器的值。

## 缓存 {#caching}

webforJ进一步通过利用缓存来优化性能。一般而言，在这种情况下存在两种类型的数据：用户可以直接更改的数据和用户不能更改的数据。在第一种情况下，当检索用户将直接互动的信息时，有必要向服务器查询该信息。

然而，不能被用户更改的信息可以被缓存，以避免额外的性能损失。这确保了不必无谓地进行往返，从而提供更高效的用户体验。webforJ以这种方式优化应用程序，以确保最佳性能。

## 加载时间 {#loading-time}

当用户启动webforJ应用时，它只加载一小部分（大约2.5 kB gzip）的JavaScript来引导会话。之后，它动态按需下载单个消息或JavaScript的块，因为应用使用相应的功能。例如，服务器只有在应用创建第一个`Button`组件时，才会向客户端发送构建webforJ `Button`所需的JavaScript。这导致初始加载时间的显着改善，从而提高了用户体验。
