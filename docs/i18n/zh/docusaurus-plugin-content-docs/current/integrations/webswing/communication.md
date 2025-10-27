---
title: Communication
sidebar_position: 3
_i18n_hash: 4a12006d21bb2a0bd6e82f2f0ff8fa78
---
`WebswingConnector` 提供了您的 webforJ 应用程序与嵌入式 Swing 应用程序之间的双向通信。这使您能够向 Swing 应用程序发送命令，并在其中发生事件时接收通知。

## 向 Swing 发送操作

`performAction()` 方法允许您的 webforJ 应用程序触发 Swing 应用程序中的功能。这对同步状态、触发更新或从 Web 界面控制 Swing 应用程序的行为非常有用。

例如，如果您的 Swing 应用程序具有用于刷新数据的自定义操作处理程序：

```java
// 从 webforJ 触发 Swing 应用程序中的刷新
connector.performAction("refresh");
```

您还可以随操作发送数据。Swing 应用程序通过其 Webswing API 集成接收这些数据：

```java
// 从 webforJ 发送带数据的命令
connector.performAction("selectRecord", "12345");

// 发送二进制数据
byte[] fileContent = Files.readAllBytes(path);
connector.performAction("uploadDocument", "invoice.pdf", new String(fileContent));
```

操作名称和预期数据格式由您的 Swing 应用程序的实现定义。

## 从 Swing 接收事件

连接器触发三种类型的事件，通知您的 webforJ 应用程序有关 Swing 应用程序的状态和操作。

### 生命周期事件

**初始化事件** 在 Webswing 连接建立并准备好通信时触发：

```java
connector.onInitialize(event -> {
  // 连接已建立
  connector.getInstanceId().ifPresent(id ->
      console.log("连接到 Webswing 实例: " + id)
  );
});
```

**启动事件** 在 Swing 应用程序完全加载并运行时触发：

```java
connector.onStart(event -> {
  // Swing 应用程序现在可见且可交互
  console.log("应用程序已准备好进行用户交互");
});
```

### 自定义操作事件

当您的 Swing 应用程序使用 [Webswing Java API](https://www.webswing.org/docs/25.1/integrate/api) 向 Web 界面发送自定义操作时，这些将作为操作事件接收：

```java
connector.onAction(event -> {
  String actionName = event.getActionName();

  switch(actionName) {
      case "dataUpdated":
        event.getActionData().ifPresent(data -> {
            // 处理更新通知
            updateWebInterface(data);
        });
        break;

      case "fileReady":
        event.getActionBinaryData().ifPresent(data -> {
            // 二进制数据
            saveFile(fileData);
        });
        break;
  }
});
```
