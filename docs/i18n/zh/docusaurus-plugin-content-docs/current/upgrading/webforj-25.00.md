---
title: Upgrade to 25.00
description: Upgrade from 24.00 to 25.00
pagination_next: null
_i18n_hash: 71f184a02c2552f5af34bfc3ec47c385
---
此文档作为升级 webforJ 应用程序从 24.00 到 25.00 的指南。
以下是现有应用程序继续平稳运行所需的更改。
与往常一样，请查看 [GitHub 发布概述](https://github.com/webforj/webforj/releases) 以获取版本之间更全面的更改列表。

## Jetty 12 网页服务器 {#jetty-12-web-servers}

webforJ 25.00 及更高版本使用 Jetty 12，采用 Jakarta EE10 servlet 架构。如果您在开发中使用 Jetty Maven 插件，请从 Jakarta EE8 迁移到 Jakarta EE10。此升级还需要用 `Jakarta.servlet` 包替换任何依赖于 `javax.servlet` 包的内容。

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

在 25.00 中，几个已弃用的 `App` 方法被移除。以下部分概述了被替换的方法和推荐的替换方案。

### 控制台日志记录 {#console-logging}

专用于创建样式化日志到浏览器控制台的工具类 [`BrowserConsole`](../advanced/browser-console.md) 替代了 `consoleLog()` 和 `consoleError()` 方法。通过使用 `console()` 方法获取 `BrowserConsole`：

```java
public class Application extends App{
  
  @Override
  public void run() throws WebforjException {
    console().log("日志信息");
    console().error("错误信息");
  }
}
```

### 网络存储 {#web-storage}

对于 webforJ 25.00 之前的版本，`App` 类有 `getLocalStorage()`、`getSessionStorage()` 和 `getCookieStorage()` 方法，分别获取 `LocalStorage`、`SessionStorage` 和 `CookieStorage` 类的实例。今后，每个类都有一个 `getCurrent()` 方法。

有关更多信息，请参见 [Web 存储](../advanced/web-storage.md)。

### `Request` 类 {#request-class}

`Request` 类现在负责获取应用程序的 URL、端口、主机和协议。因此，不再使用 `App.getUrl()`，而是使用 `App.getCurrent().getUrl()`。`getCurrent()` 方法还替代了 `getRequest()` 方法以获取 `Request` 类的实例。

:::info
`Request` 类也已移除方法，跳转到 [`Request`](#request-changes) 查看。
:::

### `Page` 类 {#page-class}

`getPage()` 方法已被 `Page.getCurrent()` 替代，以获取当前页面实例。

### 选项对话框 {#option-dialogs}

请使用 [`OptionDialog.showMessageDialog()`](../components/option-dialogs/message) 来创建消息对话框，而不是使用 `msgbox()` 方法。

### 应用程序终止 {#app-termination}

`cleanup()` 方法已被移除。现在有两个用于终止的方法，`onWillTerminate()` 和 `onDidTerminate()`。

有关更多信息，请参见 [终止钩子](../advanced/terminate-and-error-actions.md#hooks-for-termination)。

## 表格排序 {#table-sorting}

对于 webforJ 25.00 及更高版本，表格默认使用单列排序。列将仅按最近选择的列标题进行排序。要使表格使用多列排序，可以调用 [`setMultiSorting()`](../components/table/sorting#multi-sorting) 方法：

```java
table.setMultiSorting(true);
```

## 隐藏的 `TabbedPane` 主体 {#hidden-tabbedpane-body}

`hideBody()` 方法已被 `setBodyHidden()` 替代，以保持一致的命名约定。

## 在组件中渲染 HTML {#rendering-html-inside-components}

在 webforJ 25.00 和更高版本中，提供了 `setHtml()` 方法以帮助区分在组件内部设置普通文本和 HTML 文本。
使用 `setText()` 方法设置 HTML 仍然是可能的，但现在要求显式用 `<html>` 标签包裹。

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

`com.webforj.component.htmlcontainer` 包在 webforJ 中不再使用。请改用功能更强大的 `com.webforj.component.element` 包。有关标准 HTML 元素的 webforJ 类列表，请访问 [HTML 元素组件](../building-ui/web-components/html-elements.md)。

## `Request` 更改 {#request-changes}

- 就像 `App` 类的 `getCookieStorage()` 方法的移除一样，`Request` 也不再有 `getCookie()` 方法。这加强了使用 `CookieStorgage.getCurrent()` 来获取 `CookieStorage` 类实例的要求。

- `getQueryParam()` 方法现在更名为 `getQueryParameter()`。

## `WebforjBBjBridge` 更改 {#webforjbbjbridge-changes}

### 获取 `WebforjBBjBridge` 实例 {#getting-an-instance-of-webforjbbjbridge}

`Environment` 类不再具备 `getWebforjHelper()` 方法，因此请改用 `getBridge()`。

### 使用 `ConfirmDialog` 组件替代 `msgbox()` 方法 {#using-the-confirmdialog-component-for-the-msgbox-method}

以前的 webforJ 版本直接使用字符串和整数作为 `WebforjBBjBridge` 的 `msgbox()` 方法。然而，webforJ 25.00 及更高版本的 `WebforjBBjBridge` 消息使用 [`ConfirmDialog`](../components/option-dialogs/confirm.md) 组件。这提供了更多控制可以显示的按钮和消息类型。

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

## `PasswordMediation` 拼写修正 {#passwordmediation-typo-correction}

用于指示用户是否需要在每次访问具有 `Login` 组件的应用程序时登录的枚举类 `PasswordMediation` 在之前的 webfroJ 版本中存在拼写错误。`SILENT` 替代了拼写错误 `SILIENT`，适用于 webforJ 25.00 及更高版本。

## 自动聚焦方法 {#auto-focusing-methods}

为了保持 webforJ 一致，像 `setAutofocus()` 和 `isAutofocus()` 这样的方法现在具有与 HasAutoFocus 接口一致的大小写。因此，像 `Dialog` 和 `Drawer` 的组件在 25.00 及更高版本中使用 `setAutoFocus()` 和 `isAutoFocus()`。

## `BBjWindowAdapter` 和 `Panel` 被标记为 `final` {#bbjwindowadapter-and-panel-marked-as-final}

`BBjWindowAdapter` 和 `Panel` 类现在被声明为 `final`，这意味着它们不能再被子类化。这一变化提高了稳定性并强制执行一致的使用模式。
