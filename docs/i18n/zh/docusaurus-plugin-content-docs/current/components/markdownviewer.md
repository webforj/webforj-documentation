---
title: MarkdownViewer
sidebar_position: 74
_i18n_hash: e50beb488f343e35da80b6d4f9ceddf5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-markdown-viewer" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="markdown-viewer" location="com/webforj/component/markdown/MarkdownViewer" top='true'/>

`MarkdownViewer` 组件将 Markdown 文本呈现为 HTML。它支持标准的 Markdown 语法，包括标题、列表、代码块、链接、图像和表情符号渲染。该组件还提供渐进渲染功能，以逐字符显示内容，产生打字机效果。

## 设置内容 {#setting-content}

创建一个带或不带初始内容的 `MarkdownViewer`，然后使用 `setContent()` 更新它：

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
该组件实现了 `HasText`，因此 `setText()` 和 `getText()` 作为内容方法的别名可用。
:::
<ComponentDemo
path='/webforj/markdownviewer'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerView.java']}
height='650px'
/>

## 添加内容 {#appending-content}

`append()` 方法逐步添加内容，而不替换已有的内容：

```java
viewer.append("## 新章节\n\n");
viewer.append("更多内容在这里...");
```

默认情况下，追加的内容会立即显示。当启用 [渐进渲染](#progressive-rendering) 时，追加的内容会进入缓冲区，并逐字符显示。

## 自动滚动 {#auto-scroll}

启用自动滚动以保持视口在底部，随着内容的增长。这适用于任何添加内容的方法，无论是 `setContent()`、`append()` 还是渐进渲染。如果用户手动向上滚动以查看早期内容，自动滚动会暂停，并在他们滚回到底部时恢复。

```java
viewer.setAutoScroll(true);
```

## 渐进渲染 {#progressive-rendering}

渐进渲染逐字符显示内容，而不是一次性显示，产生打字机效果。AI 聊天界面通常使用此方法逐步显示响应：

```java
MarkdownViewer viewer = new MarkdownViewer();
viewer.setProgressiveRender(true);
```

启用时，通过 `setContent()` 或 `append()` 添加的内容进入缓冲区并逐步显示。当禁用时，内容会立即出现。

<ComponentDemo
path='/webforj/markdownviewerprogressive'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerProgressiveView.java']}
height='650px'
/>

### 渲染速度 {#render-speed}

`setRenderSpeed()` 方法控制每个动画帧渲染的字符数。较高的值意味着更快的渲染。在 60fps 下，默认速度 4 大约相当于每秒 240 个字符：

| 速度 | 字符/秒 |
|-------|-------------------|
| 4（默认） | ~240 |
| 6 | ~360 |
| 10 | ~600 |

```java
viewer.setRenderSpeed(6);
```

:::tip 匹配您的数据速率
如果您的服务器发送内容的速度快于视图器的渲染速度，则缓冲区会增长，显示的内容会滞后。增加 `renderSpeed` 来跟上，或在接收所有内容后调用 `flush()` 以立即显示剩余内容。
:::

### 渲染状态 {#render-state}

启用渐进渲染时，`isRendering()` 方法返回 `true`，表示组件正在主动显示缓冲的内容。聊天界面通常使用此方法来显示或隐藏停止按钮：

```java
if (viewer.isRendering()) {
  stopButton.setVisible(true);
}
```

当禁用渐进渲染时，该方法始终返回 `false`。

### 控制渲染 {#controlling-rendering}

两个方法控制渐进渲染如何停止：

- **`stop()`** 停止渲染并丢弃所有尚未显示的缓冲内容。当用户取消时调用此方法。
- **`flush()`** 停止渲染，但立即显示所有剩余的缓冲内容。当接收所有内容且希望立即显示时调用此方法。

```java
// 用户点击 "停止生成"
viewer.stop();

// 所有内容已接收，现在显示所有内容
viewer.flush();
```

当禁用渐进渲染时，这些方法没有效果。

### 等待完成 {#waiting-for-completion}

`whenRenderComplete()` 方法返回一个 `PendingResult`，当渐进渲染完成显示所有缓冲内容时完成：

```java
viewer.whenRenderComplete().thenAccept(v -> {
  inputField.setEnabled(true);
  inputField.focus();
});
```

如果未启用渐进渲染或没有内容正在渲染，`PendingResult` 会立即完成。

:::tip UI 协调
使用渐进渲染时，不要仅根据 `append()` 完成的时间重新启用输入字段。渲染器可能仍在显示缓冲内容。等待 `whenRenderComplete()` 以确保所有内容出现后再让用户再次进行交互。
:::

以下演示模拟使用启用渐进渲染的 `append()` 的 AI 聊天界面：

<ComponentDemo
path='/webforj/markdownviewerstreaming'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerStreamingView.java']}
height='700px'
/>

## 清除内容 {#clearing-content}

使用 `clear()` 移除所有内容：

```java
viewer.clear();
```

如果渐进渲染处于活动状态，`clear()` 还会停止渲染并完成任何未完成的 `whenRenderComplete()` 结果。

## 语法高亮 {#syntax-highlighting}

`MarkdownViewer` 支持在 [Prism.js](https://prismjs.com/) 可用时对代码块进行语法高亮。使用 `@JavaScript` 和 `@StyleSheet` 注释将 Prism.js 添加到您的应用中：

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

自动加载插件根据需要加载语言定义，因此带有语言提示的代码块如 ` ```java ` 或 ` ```python ` 会自动高亮。

<TableBuilder name="MarkdownViewer" />
