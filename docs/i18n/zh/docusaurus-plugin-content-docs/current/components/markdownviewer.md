---
title: MarkdownViewer
sidebar_position: 74
_i18n_hash: dcbc11ba7581a82ae6857abfe11a62c1
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-markdown-viewer" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="markdown-viewer" location="com/webforj/component/markdown/MarkdownViewer" top='true'/>

`MarkdownViewer` 组件将 markdown 文本渲染为 HTML。它支持标准 markdown 语法，包括标题、列表、代码块、链接、图片和表情符号渲染。该组件还提供渐进式渲染，逐字符显示内容，形成打字机效果。

## 设置内容 {#setting-content}

创建一个带有或不带初始内容的 `MarkdownViewer`，然后使用 `setContent()` 更新它：

```java
MarkdownViewer viewer = new MarkdownViewer("# Hello World");

// 完全替换内容
viewer.setContent("""
    ## 新内容

    - 项目 1
    - 项目 2
    """);

// 获取当前内容
String content = viewer.getContent();
```
:::tip
该组件实现了 `HasText`，因此 `setText()` 和 `getText()` 都可以作为内容方法的别名使用。
:::
<ComponentDemo 
path='/webforj/markdownviewer?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerView.java'
height='650px'
/>

## 附加内容 {#appending-content}

`append()` 方法逐步添加内容，而不替换已存在的内容：

```java
viewer.append("## 新章节\n\n");
viewer.append("这里还有更多内容...");
```

默认情况下，附加内容会立即显示。当启用[渐进式渲染](#progressive-rendering) 时，附加内容会进入缓冲区，并逐字符显示。

## 自动滚动 {#auto-scroll}

启用自动滚动以在内容增长时保持视口在底部。这适用于任何添加内容的方法，无论是 `setContent()`、`append()` 还是渐进式渲染。如果用户手动向上滚动以查看较早的内容，自动滚动会暂停，当他们滚回底部时恢复。

```java
viewer.setAutoScroll(true);
```

## 渐进式渲染 {#progressive-rendering}

渐进式渲染逐字符显示内容，而不是一次性显示所有内容，从而产生打字机效果。 AI 聊天界面通常使用这种方式逐渐显示响应：

```java
MarkdownViewer viewer = new MarkdownViewer();
viewer.setProgressiveRender(true);
```

当启用时，通过 `setContent()` 或 `append()` 添加的内容会进入缓冲区并逐步显示。当禁用时，内容会立即显示。

<ComponentDemo 
path='/webforj/markdownviewerprogressive?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerProgressiveView.java'
height='650px'
/>

### 渲染速度 {#render-speed}

`setRenderSpeed()` 方法控制每个动画帧渲染多少个字符。更高的值意味着更快的渲染。在 60fps 下，默认速度 4 大约转换为每秒 240 个字符：

| 速度 | 字符/秒 |
|-------|-------------------|
| 4（默认） | ~240 |
| 6 | ~360 |
| 10 | ~600 |

```java
viewer.setRenderSpeed(6);
```

:::tip 匹配您的数据速率
如果您的服务器发送内容的速度超过查看器的渲染速度，缓冲区会增加，并且显示的内容会滞后。增加 `renderSpeed` 以保持同步，或在接收到所有内容时调用 `flush()` 以立即显示剩余内容。
:::

### 渲染状态 {#render-state}

当启用渐进式渲染时，`isRendering()` 方法在组件主动显示缓冲内容时返回 `true`。聊天界面通常使用此方法来显示或隐藏停止按钮：

```java
if (viewer.isRendering()) {
  stopButton.setVisible(true);
}
```

当禁用渐进式渲染时，该方法始终返回 `false`。

### 控制渲染 {#controlling-rendering}

两个方法控制渐进式渲染停止的方式：

- **`stop()`** 中止渲染并丢弃任何尚未显示的缓冲内容。当用户取消时调用此方法。
- **`flush()`** 中止渲染，但立即显示所有剩余的缓冲内容。当接收到所有内容并希望立即显示时调用此方法。

```java
// 用户点击“停止生成”
viewer.stop();

// 所有内容已接收，现在显示所有内容
viewer.flush();
```

当禁用渐进式渲染时，这些方法无效。

### 等待完成 {#waiting-for-completion}

`whenRenderComplete()` 方法返回一个 `PendingResult`，该结果在渐进式渲染完成显示所有缓冲内容时完成：

```java
viewer.whenRenderComplete().thenAccept(v -> {
  inputField.setEnabled(true);
  inputField.focus();
});
```

如果未启用渐进式渲染或没有正在渲染的内容，`PendingResult` 会立即完成。

:::tip 用户界面协调
使用渐进式渲染时，不要仅根据何时完成调用 `append()` 来重新启用输入字段。渲染器可能仍在显示缓冲内容。等待 `whenRenderComplete()` 以确保在用户可以再次交互之前显示所有内容。
:::

以下演示模拟了一个 AI 聊天界面，使用带有启用渐进式渲染的 `append()`：

<ComponentDemo 
path='/webforj/markdownviewerstreaming?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerStreamingView.java'
height='700px'
/>

## 清除内容 {#clearing-content}

使用 `clear()` 删除所有内容：

```java
viewer.clear();
```

如果激活了渐进式渲染，`clear()` 还会停止渲染并完成任何待处理的 `whenRenderComplete()` 结果。

## 语法高亮 {#syntax-highlighting}

`MarkdownViewer` 支持代码块的语法高亮，当 [Prism.js](https://prismjs.com/) 可用时。使用 `@JavaScript` 和 `@StyleSheet` 注解将 Prism.js 添加到您的应用程序：

```java
@StyleSheet("https://cdn.jsdelivr.net/npm/prismjs@1/themes/prism-tomorrow.min.css")
@JavaScript(
  value = "https://cdn.jsdelivr.net/combine/npm/prismjs@1/prism.min.js,npm/prismjs@1/plugins/autoloader/prism-autoloader.min.js",
  top = true
)
public class Application extends App {
  // ...
}
```

自动加载器插件根据需要加载语言定义，因此带有语言提示的代码块，如 ` ```java ` 或 ` ```python ` 会自动高亮。

<TableBuilder name="MarkdownViewer" />
