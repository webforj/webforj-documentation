---
title: Upgrade to 25.00
description: Upgrade from 24.00 to 25.00
pagination_next: null
_i18n_hash: 53afcc2a74e5569086bcf7daeb6582d7
---
本 документация 是为了指导将 webforJ 应用程序从 24.00 升级到 25.00。以下是现有应用程序继续顺利运行所需的更改。像往常一样，请查看 [GitHub 发布概述](https://github.com/webforj/webforj/releases)，以获取不同版本之间更全面的更改列表。

## Jetty 12 网络服务器 {#jetty-12-web-servers}

webforJ 25.00 及更高版本使用 Jetty 12，采用 Jakarta EE10 servlet 架构。如果您在开发中使用 Jetty Maven 插件，请将其从 Jakarta EE8 迁移到 Jakarta EE10。此升级还将需要用 `Jakarta.servlet` 包替换依赖于 `javax.servlet` 包的任何内容。

### POM 文件更改 {#pom-file-changes}

**之前**

```xml {2-4}
<plugin>
  <groupId>org.eclipse.jetty.ee8</groupId>
  <artifactId>jetty-ee8-maven-plugin</artifactId>
  <version>10.x.xx</version>
```
**之后**

```xml {2-4}
<plugin>
  <groupId>org.eclipse.jetty.ee10</groupId>
  <artifactId>jetty-ee10-maven-plugin</artifactId>
  <version>12.x.xx</version>
```

## `App` 类的 API 更改 {#api-changes-for-the-app-class}

在 25.00 中删除了几个已弃用的 `App` 方法。以下部分概述了哪些方法被替换以及推荐的替代方法。

### 控制台日志记录 {#console-logging}

用于创建样式化日志到浏览器控制台的工具类 [`BrowserConsole`](/docs/advanced/browser-console) 替代了 `consoleLog()` 和 `consoleError()` 方法。通过使用 `console()` 方法获取 `BrowserConsole`：

```java
public class Application extends App{
  
  @Override
  public void run() throws WebforjException {
    console().log("日志信息");
    console().error("错误信息");
  }
}
```

### Web 存储 {#web-storage}

对于 webforJ 25.00 之前的版本，`App` 类有 `getLocalStorage()`、`getSessionStorage()` 和 `getCookieStorage()` 方法来获取 `LocalStorage`、`SessionStorage` 和 `CookieStorage` 类的实例。今后，每个类都有一个 `getCurrent()` 方法。

有关更多信息，请参阅 [Web Storage](/docs/advanced/web-storage)。

### `Request` 类 {#request-class}

`Request` 类现在负责获取应用程序的 URL、端口、主机和协议。因此，使用 `App.getUrl()` 的地方请使用 `App.getCurrent().getUrl()`。`getCurrent()` 方法还替代了 `getRequest()` 方法，用于获取 `Request` 类的实例。

:::info
`Request` 类还删除了方法，跳转到 [`Request`](#request-changes) 以查看它们。
:::

### `Page` 类 {#page-class}

`getPage()` 方法被 `Page.getCurrent()` 替换，以获取当前页面实例。

### 选项对话框 {#option-dialogs}

请使用 [`OptionDialog.showMessageDialog()`](/docs/components/option-dialogs/message) 创建消息对话框，而不是使用 `msgbox()` 方法。

### 应用程序终止 {#app-termination}

`cleanup()` 方法已被删除。现在有两个终止方法，即 `onWillTerminate()` 和 `onDidTerminate()`。

有关更多信息，请参阅 [Hooks for termination](/docs/advanced/terminate-and-error-actions#hooks-for-termination)。

## 表排序 {#table-sorting}

对于 webforJ 25.00 及更高版本，表格默认使用单列排序。列仅按最近选择的列标题进行排序。要使表格使用多列排序，请调用 [`setMultiSorting()`](/docs/components/table/sorting#multi-sorting) 方法：

```java
table.setMultiSorting(true);
```

## 隐藏的 `TabbedPane` 正文 {#hidden-tabbedpane-body}

`hideBody()` 方法被 `setBodyHidden()` 替换，以保持方法命名的一致性。

## 在组件中渲染 HTML {#rendering-html-inside-components}

在 webforJ 25.00 及更高版本中，存在一个 `setHtml()` 方法，以帮助区分在组件内部设置文本和 HTML 文本。使用 `setText()` 方法设置 HTML 仍然是可能的，但现在需要明确用 `<html>` 标签包裹它。

```java
// setText() 和 setHtml() 的有效用法
Button home = new Button();

home.setText("""
  <html>
    <h1>首页</h1>
  </html>
""");

home.setHtml("<h1>首页</h1>");

home.setText("首页");
```

```java
// setText() 和 setHtml() 的无效用法
Button home = new Button();
home.setText("<h1>首页</h1>");
```

## HTML 容器 {#html-containers}

`com.webforj.component.htmlcontainer` 包在 webforJ 中不再存在。请改用功能更丰富的 `com.webforj.component.element` 包。有关标准 HTML 元素的 webforJ 类的列表，请访问 [HTML Element Components](/docs/components/html-elements)。

## `Request` 更改 {#request-changes}

- 正如 `App` 类的 `getCookieStorage()` 方法被移除一样，`Request` 也不再具有 `getCookie()` 方法。这增强了使用 `CookieStorgage.getCurrent()` 获取 `CookieStorage` 类的实例的强制性。

- `getQueryParam()` 方法现在为 `getQueryParameter()`。

## `WebforjBBjBridge` 更改 {#webforjbbjbridge-changes}

### 获取 `WebforjBBjBridge` 实例 {#getting-an-instance-of-webforjbbjbridge}

`Environment` 类不再具有 `getWebforjHelper()` 方法，因此请改用 `getBridge()`。

### 使用 `ConfirmDialog` 组件替代 `msgbox()` 方法 {#using-the-confirmdialog-component-for-the-msgbox-method}

之前版本的 webforJ 使用字符串和整数直接传递给 `WebforjBBjBridge` 的 `msgbox()` 方法。然而，在 webforJ 25.00 及更高版本中，`WebforjBBjBridge` 的消息使用 [`ConfirmDialog`](/docs/components/option-dialogs/confirm) 组件。这提供了更多控制，以显示哪些按钮和消息类型。

**之前**
```java
Environment environment = Environment.getCurrent();
WebforjBBjBridge bridge = environment.getWebforjHelper();

int msgboxResult = bridge.msgbox("您确定要删除此文件吗？", 1, "删除");
```

**之后**
```java
Environment environment = Environment.getCurrent();
WebforjBBjBridge bridge = environment.getBridge();

ConfirmDialog dialog = new ConfirmDialog(
  "您确定要删除此文件吗？", "删除",
  ConfirmDialog.OptionType.OK_CANCEL, ConfirmDialog.MessageType.QUESTION);

int msgboxResult = bridge.msgbox(dialog);
```

<!-- ## Environment.logError removed -->

## `PasswordMediation` 拼写纠正 {#passwordmediation-typo-correction}

枚举类 `PasswordMediation` 用于指示用户是否需要在每次访问应用程序时登录，有一个拼写错误。`SILENT` 替换了之前 webforJ 版本中的拼写错误 `SILIENT`，适用于 webforJ 25.00 及更高版本。

## 自动聚焦方法 {#auto-focusing-methods}

为了保持 webforJ 一致性，`setAutofocus()` 和 `isAutofocus()` 等方法现在具有统一的大小写，就像 HasAutoFocus 接口一样。因此，像 `Dialog` 和 `Drawer` 这样的组件在 25.00 及更高版本中使用 `setAutoFocus()` 和 `isAutoFocus()`。

## `BBjWindowAdapter` 和 `Panel` 标记为 `final` {#bbjwindowadapter-and-panel-marked-as-final}

`BBjWindowAdapter` 和 `Panel` 类现在声明为 `final`，这意味着它们不再可以被子类化。此更改提高了稳定性并强制执行一致的使用模式。
