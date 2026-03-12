---
title: Upgrade to 25.00
description: Upgrade from 24.00 to 25.00
pagination_next: null
_i18n_hash: cbb2bd70fa3e51df1096018ff2519878
---
本文件作为指导，帮助将 webforJ 应用从 24.00 升级到 25.00。
以下是现有应用继续顺利运行所需的更改。
如往常一样，请查看 [GitHub 发布概述](https://github.com/webforj/webforj/releases) 以获取版本之间更改的更全面列表。

## Jetty 12 网络服务器 {#jetty-12-web-servers}

webforJ 25.00 及更高版本使用 Jetty 12，基于 Jakarta EE10 Servlet 架构。如果您在开发中使用 Jetty Maven 插件，将从 Jakarta EE8 迁移到 Jakarta EE10。该升级还将要求将任何依赖于 `javax.servlet` 包的内容替换为 `Jakarta.servlet` 包。

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

在 25.00 中移除了几个已弃用的 `App` 方法。以下部分概述了被替换的方法及推荐的替代品。

### 控制台日志记录 {#console-logging}

实用类 [`BrowserConsole`](/docs/advanced/browser-console) 用于创建样式化的浏览器控制台日志，替代了 `consoleLog()` 和 `consoleError()` 方法。您可以使用 `console()` 方法获取 `BrowserConsole`：

```java
public class Application extends App{
  
  @Override
  public void run() throws WebforjException {
    console().log("日志消息");
    console().error("错误消息");
  }
}
```

### Web 存储 {#web-storage}

在 webforJ 25.00 之前，`App` 类具有 `getLocalStorage()`、`getSessionStorage()` 和 `getCookieStorage()` 方法，以获取 `LocalStorage`、`SessionStorage` 和 `CookieStorage` 类的实例。在将来，每个类都有一个 `getCurrent()` 方法。

有关更多信息，请参见 [Web 存储](/docs/advanced/web-storage)。

### `Request` 类 {#request-class}

`Request` 类现在负责获取应用的 URL、端口、主机和协议。因此，使用 `App.getUrl()` 的地方，请改用 `App.getCurrent().getUrl()`。`getCurrent()` 方法也替代了 `getRequest()` 方法，以获取 `Request` 类的实例。

:::info
`Request` 类也移除了某些方法，跳转到 [`Request`](#request-changes) 查看详细信息。
:::

### `Page` 类 {#page-class}

`getPage()` 方法被 `Page.getCurrent()` 替代，以获取当前页面实例。

### 选项对话框 {#option-dialogs}

请使用 [`OptionDialog.showMessageDialog()`](/docs/components/option-dialogs/message) 来创建消息对话框，而不是使用 `msgbox()` 方法。

### 应用终止 {#app-termination}

`cleanup()` 方法已被移除。现在有两个终止方法，`onWillTerminate()` 和 `onDidTerminate()`。

有关更多信息，请参见 [终止钩子](/docs/advanced/terminate-and-error-actions#hooks-for-termination)。

## 表格排序 {#table-sorting}

对于 webforJ 25.00 及更高版本，表格默认使用单列排序。列只会根据最近选择的列标题进行排序。要使表格使用多列排序，请调用 [`setMultiSorting()`](/docs/components/table/sorting#multi-sorting) 方法：

```java
table.setMultiSorting(true);
```

## 隐藏的 `TabbedPane` 主体 {#hidden-tabbedpane-body}

`hideBody()` 方法被 `setBodyHidden()` 替代，以保持方法命名的一致性。

## 在组件内渲染 HTML {#rendering-html-inside-components}

在 webforJ 25.00 及更高版本中，使用 `setHtml()` 方法来帮助区分组件内设置文本和 HTML 文本。
使用 `setText()` 方法设置 HTML 仍然可以，但现在需要显式用 `<html>` 标签包裹。

```java
// 使用 setText() 和 setHtml() 的有效示例
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
// 使用 setText() 和 setHtml() 的无效示例
Button home = new Button();
home.setText("<h1>首页</h1>");
```

## HTML 容器 {#html-containers}

`com.webforj.component.htmlcontainer` 包在 webforJ 中不再存在。请改为使用功能更丰富的 `com.webforj.component.element` 包。有关标准 HTML 元素的 webforJ 类列表，请访问 [HTML 元素组件](/docs/components/html-elements)。

## `Request` 更改 {#request-changes}

- 正如 `App` 类的 `getCookieStorage()` 方法被移除一样，`Request` 也不再具有 `getCookie()` 方法。这加强了使用 `CookieStorage.getCurrent()` 获取 `CookieStorage` 类实例的做法。

- `getQueryParam()` 方法现在改为 `getQueryParameter()`。

## `WebforjBBjBridge` 更改 {#webforjbbjbridge-changes}

### 获取 `WebforjBBjBridge` 实例 {#getting-an-instance-of-webforjbbjbridge}

`Environment` 类不再具有 `getWebforjHelper()` 方法，因此请改用 `getBridge()`。

### 使用 `ConfirmDialog` 组件替代 `msgbox()` 方法 {#using-the-confirmdialog-component-for-the-msgbox-method}

以前版本的 webforJ 直接使用字符串和整数来调用 `WebforjBBjBridge` 的 `msgbox()` 方法。然而，webforJ 25.00 及更高版本中的 `WebforjBBjBridge` 消息使用 [`ConfirmDialog`](/docs/components/option-dialogs/confirm) 组件。这提供了更多控制，用于选择显示的按钮和消息类型。

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

## `PasswordMediation` 拼写更正 {#passwordmediation-typo-correction}

枚举类 `PasswordMediation` 用于指示用户是否在每次访问带有 `Login` 组件的应用时需要登录。之前版本的 webforJ 存在一个拼写错误。`SILENT` 替代了拼写错误的 `SILIENT`，仅在 webforJ 25.00 及更高版本中有效。

## 自动聚焦方法 {#auto-focusing-methods}

为了保持 webforJ 的一致性，像 `setAutofocus()` 和 `isAutofocus()` 的方法现在与 HasAutoFocus 接口具有统一的大小写。因此，像 `Dialog` 和 `Drawer` 等组件在 25.00 及更高版本中使用 `setAutoFocus()` 和 `isAutoFocus()`。

## `BBjWindowAdapter` 和 `Panel` 标记为 `final` {#bbjwindowadapter-and-panel-marked-as-final}

`BBjWindowAdapter` 和 `Panel` 类现在被声明为 `final`，这意味着它们不能再被子类化。此更改提高了稳定性并强制执行一致的使用模式。
