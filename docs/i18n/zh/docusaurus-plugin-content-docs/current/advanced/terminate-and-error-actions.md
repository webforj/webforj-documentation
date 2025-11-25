---
title: 终止和错误操作
sidebar_position: 40
_i18n_hash: 1a250a51020b32c8b3471ae75ea8f750
---
<!-- vale off -->
# 终止和错误操作 <DocChip chip='since' label='23.06' />
<!-- vale on -->

在使用 webforJ 开发应用程序时，定义应用在终止或遇到错误时的行为至关重要。框架提供了通过 `terminate` 和 `error` 操作来定制这些行为的机制。

## 概述 {#overview}

`App` 类允许您定义在应用正常终止或遇到错误时执行的操作。这些操作是 `AppCloseAction` 接口的实例，可以使用以下方法设置：

- `setTerminateAction(AppCloseAction action)`：设置正常终止时执行的操作。
- `setErrorAction(AppCloseAction action)`：设置发生错误时执行的操作。

可用的 `AppCloseAction` 实现包括：

- `DefaultAction`：清除浏览器并显示提示用户重新加载应用的本地化消息。
- `NoneAction`：不执行任何操作，从而有效地重置任何先前设置的操作。
- `MessageAction`：显示自定义链接消息。
- `RedirectAction`：将用户重定向到指定的 URL。

:::info 在 webforJ 中区分终止操作和错误操作
webforJ 不将因抛出或未处理异常而终止视为错误操作，而是视为终止操作，因为应用正常关闭。当应用因外部错误（例如浏览器无法连接到运行应用的服务器）收到终止信号时，才发生错误操作。
:::

## 默认行为 {#default-behavior}

在 webforJ 版本 `24.11` 及更早版本中，应用默认使用 `DefaultAction` 处理终止和错误事件。这意味着当应用终止或遇到错误时，浏览器会显示提示用户重新加载应用的消息。

从版本 `24.12` 开始，webforJ 默认使用 `NoneAction` 处理终止和错误事件。这一变化意味着在应用终止或发生错误时不采取任何操作，允许 webforJ 将错误处理委托给配置的适当 `ErrorHandler`，或回退到其通用错误处理机制。通过使用 `NoneAction`，应用避免干扰默认的错误处理流程，使您能够定义自定义错误处理程序或依赖 webforJ 的内置错误管理。

## 自定义操作 {#customizing-actions}

要更改默认行为，请在您的 `App` 子类中使用 `setTerminateAction()` 和 `setErrorAction()` 方法。

### 设置自定义消息操作 {#setting-a-custom-message-action}

如果您希望在正常终止时显示自定义消息：

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    // 设置自定义消息操作
    setTerminateAction(new MessageAction(
        "感谢您使用我们的应用程序！点击重新加载"
    ));
  }
}
```

### 设置重定向操作 {#setting-a-redirect-action}

要在正常终止时将用户重定向到特定 URL：

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    // 在错误时设置重定向操作
    setTerminateAction(new RedirectAction(
        "https://example.com/error"
    ));
  }
}
```

## 终止应用 {#terminating-the-app}

您可以通过调用 `terminate()` 方法以编程方式终止应用：

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    // 在某些条件下终止应用
    if (someCondition) {
      terminate();
    }
  }
}
```

调用 `terminate()` 后，将执行 `setTerminateAction()` 定义的操作。

## 终止钩子 {#hooks-for-termination}

`App` 类提供了钩子方法，在终止前后执行操作：

- `onWillTerminate()`：在终止前调用。
- `onDidTerminate()`：在终止后调用。

```java
public class MyApp extends App {

  @Override
  protected void onWillTerminate() {
    // 执行清理任务
  }

  @Override
  protected void onDidTerminate() {
    // 终止后的操作
  }
}
```

:::tip 外部生命周期监听器
对于更高级的生命周期管理，考虑使用 `AppLifecycleListener` 来处理来自外部组件的终止事件，而无需修改 `App` 类。这在插件架构或多个组件需要响应应用终止时特别有用。了解有关 [生命周期监听器](lifecycle-listeners.md) 的更多信息。
:::

### 自定义终止页面 {#custom-termination-page}

在某些情况下，您可能希望在应用结束时显示自定义终止页面，为用户提供个性化消息或额外资源。这可以通过在 `App` 子类中重写 `onDidTerminate()` 方法并将自定义 HTML 注入页面来实现。

以下是创建自定义终止页面的示例：

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    setTerminateAction(new NoneAction());
    terminate();
  }

  @Override
  protected void onDidTerminate() {
    String html = """
    <div style="display: flex; justify-content: center; align-items: center; height: 100vh; flex-direction: column;">
        <h1>感谢您使用 webforJ</h1>
        <p>有关更多信息，请访问 <a href="https://webforj.com">webforj.com</a></p>
    </div>
    """;

    Page.getCurrent().executeJsVoidAsync(
        String.format("document.body.innerHTML = `%s`", html)
    );
  }
}
```
