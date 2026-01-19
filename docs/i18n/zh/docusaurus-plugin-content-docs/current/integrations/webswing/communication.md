---
title: Communication
sidebar_position: 3
_i18n_hash: 06bf57e08ee82a4970539b73215c1540
---
`WebswingConnector` 提供了您与嵌入式 Swing 应用之间的双向通信。这使您能够向 Swing 应用发送命令，并在其中发生事件时接收通知。

## 发送动作到 Swing {#sending-actions-to-swing}

`performAction()` 方法允许您的 webforJ 应用触发 Swing 应用中的功能。这对于同步状态、触发更新或从 Web 界面控制 Swing 应用的行为非常有用。

例如，如果您的 Swing 应用有一个用于刷新数据的自定义动作处理器：

```java
// 从 webforJ 触发 Swing 应用中的刷新
connector.performAction("refresh");
```

您还可以随动作发送数据。Swing 应用通过其 Webswing API 集成接收这些数据：

```java
// 从 webforJ 发送带有数据的命令
connector.performAction("selectRecord", "12345");

// 发送二进制数据
byte[] fileContent = Files.readAllBytes(path);
connector.performAction("uploadDocument", "invoice.pdf", new String(fileContent));
```

动作名称和预期的数据格式由您的 Swing 应用的实现定义。

## 接收来自 Swing 的事件 {#receiving-events-from-swing}

连接器会触发三种类型的事件，通知您的 webforJ 应用关于 Swing 应用的状态和动作。

### 生命周期事件 {#lifecycle-events}

**初始化事件** 在 Webswing 连接建立并准备好通信时触发：

```java
connector.onInitialize(event -> {
  // 连接已建立
  connector.getInstanceId().ifPresent(id ->
      console.log("连接到 Webswing 实例: " + id)
  );
});
```

**启动事件** 在 Swing 应用完全加载并正在运行时触发：

```java
connector.onStart(event -> {
  // Swing 应用现在可见且可交互
  console.log("应用程序准备好供用户交互");
});
```

### 自定义动作事件 {#custom-action-events}

当您的 Swing 应用使用 [Webswing Java API](https://www.webswing.org/docs/25.1/integrate/api) 向 Web 界面发送自定义动作时，这些将作为动作事件接收：

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
