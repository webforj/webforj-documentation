---
title: Upgrade to 25.00
description: Upgrade from 24.00 to 25.00
pagination_next: null
_i18n_hash: 2553d37a63c097b7520f2989849f016b
---
此文档作为从webforJ应用程序24.00升级到25.00的指南。以下是现有应用程序需要进行的更改，以确保其持续平稳运行。您可以参考[GitHub版本概述](https://github.com/webforj/webforj/releases)了解版本之间更改的更全面列表。

## Jetty 12 web服务器 {#jetty-12-web-servers}

webforJ 25.00及更高版本使用Jetty 12，基于Jakarta EE10 Servlet架构。如果您在开发中使用Jetty Maven插件，请将其从Jakarta EE8迁移到Jakarta EE10。此升级还需要将所有依赖于`javax.servlet`包的内容替换为`Jakarta.servlet`包。

### POM文件更改 {#pom-file-changes}

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

## `App`类的API更改 {#api-changes-for-the-app-class}

在25.00中删除了多个弃用的`App`方法。以下部分概述了哪些方法被替换以及推荐的替换方法。

### 控制台日志记录 {#console-logging}

用于创建样式化日志到浏览器控制台的实用类[`BrowserConsole`](../advanced/browser-console.md)，替代了`consoleLog()`和`consoleError()`方法。通过使用`console()`方法获取`BrowserConsole`：

```java
public class Application extends App{
  
  @Override
  public void run() throws WebforjException {
    console().log("日志消息");
    console().error("错误消息");
  }
}
```

### Web存储 {#web-storage}

对于webforJ 25.00之前的版本，`App`类具有`getLocalStorage()`、`getSessionStorage()`和`getCookieStorage()`方法，分别用于获取`LocalStorage`、`SessionStorage`和`CookieStorage`类的实例。今后，每个类都有一个`getCurrent()`方法。

有关更多信息，请参见[Web存储](../advanced/web-storage.md)。

### `Request`类 {#request-class}

`Request`类现在负责获取应用程序的URL、端口、主机和协议。因此，使用`App.getCurrent().getUrl()`代替`App.getUrl()`。`getCurrent()`方法还替代了`getRequest()`方法以获取`Request`类的实例。

:::info
`Request`类也移除了某些方法，跳转到[`Request`](#request-changes)查看详细信息。
:::

### `Page`类 {#page-class}

`getPage()`方法被`Page.getCurrent()`替代，以获取当前页面实例。

### 选项对话框 {#option-dialogs}

使用[`OptionDialog.showMessageDialog()`](../components/option-dialogs/message)替代`msgbox()`方法来创建消息对话框。

### 应用程序终止 {#app-termination}

`cleanup()`方法已被移除。现在有两个终止方法，`onWillTerminate()`和`onDidTerminate()`。

有关更多信息，请参见[终止钩子](../advanced/terminate-and-error-actions.md#hooks-for-termination)。

## 表格排序 {#table-sorting}

对于webforJ 25.00及更高版本，表格默认使用单列排序。仅按最近选择的列标题进行排序。要使表格使用多列排序，请调用[`setMultiSorting()`](../components/table/sorting#multi-sorting)方法：

```java
table.setMultiSorting(true);
```

## 隐藏`TabbedPane`主体 {#hidden-tabbedpane-body}

`hideBody()`方法被`setBodyHidden()`替代，以保持方法命名的一致性。

## 在组件内部渲染HTML {#rendering-html-inside-components}

在webforJ 25.00及更高版本中，有一个`setHtml()`方法，以帮助区分在组件内部设置文字和HTML文本。使用`setText()`方法设置HTML仍然可行，但现在需要将其显式地用`<html>`标签包裹。

```java
// setText()和setHtml()的有效用法
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
// setText()和setHtml()的无效用法
Button home = new Button();
home.setText("<h1>首页</h1>");
```

## HTML容器 {#html-containers}

`com.webforj.component.htmlcontainer`包在webforJ中已不再使用，取而代之的是功能更丰富的`com.webforj.component.element`包。有关标准HTML元素的webforJ类列表，请访问[HTML元素组件](../building-ui/web-components/html-elements.md)。

## `Request`的更改 {#request-changes}

- 正如`App`类的`getCookieStorage()`方法被移除一样，`Request`类也不再具有`getCookie()`方法。这加强了使用`CookieStorage.getCurrent()`获取`CookieStorage`类实例的使用。

- `getQueryParam()`方法现更名为`getQueryParameter()`。

## `WebforjBBjBridge`的更改 {#webforjbbjbridge-changes}

### 获取`WebforjBBjBridge`实例 {#getting-an-instance-of-webforjbbjbridge}

`Environment`类不再具有`getWebforjHelper()`方法，因此请使用`getBridge()`代替。

### 使用`ConfirmDialog`组件替代`msgbox()`方法 {#using-the-confirmdialog-component-for-the-msgbox-method}

早期版本的webforJ直接使用字符串和整数作为`WebforjBBjBridge`的`msgbox()`方法。然而，在webforJ 25.00及更高版本中的`WebforjBBjBridge`消息使用[`ConfirmDialog`](../components/option-dialogs/confirm.md)组件。这提供了更多控制以展示哪些按钮及消息类型。

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

## `PasswordMediation`拼写更正 {#passwordmediation-typo-correction}

枚举类`PasswordMediation`用于指示用户是否需要在每次访问有`Login`组件的应用程序时登录，早期的webforJ版本中存在拼写错误。`SILENT`替代了webforJ 25.00及更高版本中的拼写错误`SILIENT`。

## 自动聚焦方法 {#auto-focusing-methods}

为了使webforJ一致，像`setAutofocus()`和`isAutofocus()`的方法现在具有与HasAutoFocus接口一致的大小写。因此，像`Dialog`和`Drawer`这样的组件在25.00及更高版本中使用`setAutoFocus()`和`isAutoFocus()`。

## `BBjWindowAdapter`和`Panel`标记为`final` {#bbjwindowadapter-and-panel-marked-as-final}

`BBjWindowAdapter`和`Panel`类现在被声明为`final`，这意味着它们不能再被子类化。此更改提高了稳定性，并强制执行一致的使用模式。
