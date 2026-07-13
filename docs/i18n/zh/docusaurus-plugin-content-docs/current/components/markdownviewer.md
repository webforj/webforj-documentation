---
title: MarkdownViewer
sidebar_position: 74
description: >-
  Render markdown as HTML with the MarkdownViewer component, supporting append,
  auto-scroll, and progressive typewriter rendering.
_i18n_hash: fbd31d2317bf5de95c282a1319f35cf6
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-markdown-viewer" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="markdown-viewer" location="com/webforj/component/markdown/MarkdownViewer" top='true'/>

`MarkdownViewer` 组件将 markdown 文本渲染为 HTML。它支持标准的 markdown 语法，包括标题、列表、代码块、链接、图像和 emoji 渲染。该组件还提供渐进渲染，按字符逐步显示内容，以产生打字机效果。

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
该组件实现了 `HasText`，因此 `setText()` 和 `getText()` 可作为内容方法的别名使用。
:::
<ComponentDemo
path='/webforj/markdownviewer'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerView.java']}
height='650px'
/>

## 追加内容 {#appending-content}

`append()` 方法以增量方式添加内容，而不替换已有内容：

```java
viewer.append("## 新章节\n\n");
viewer.append("更多内容在这里...");
```

默认情况下，追加的内容会立即显示。当启用[渐进渲染](#progressive-rendering)时，追加的内容会进入缓冲区并逐字符显示。

## 自动滚动 {#auto-scroll}

启用自动滚动，使视口在内容增加时保持在底部。这适用于任何添加内容的方法，无论是 `setContent()`、`append()` 还是渐进渲染。如果用户手动向上滚动以查看早期内容，自动滚动会暂停，当他们滚动回到底部时自动滚动恢复。

```java
viewer.setAutoScroll(true);
```

## 渐进渲染 {#progressive-rendering}

渐进渲染按字符逐步显示内容，而不是一次性显示，创造打字机效果。AI 聊天界面通常使用此效果逐渐显示响应：

```java
MarkdownViewer viewer = new MarkdownViewer();
viewer.setProgressiveRender(true);
```

启用时，通过 `setContent()` 或 `append()` 添加的内容会进入缓冲区并逐步显示。禁用时，内容会立即显示。

<ComponentDemo
path='/webforj/markdownviewerprogressive'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerProgressiveView.java']}
height='650px'
/>

### 渲染速度 {#render-speed}

`setRenderSpeed()` 方法控制每个动画帧渲染的字符数量。较高的值意味着更快的渲染。在 60fps 下，默认速度 4 转换为大约每秒 240 个字符：

| 速度 | 每秒字符数 |
|-------|-------------------|
| 4 (默认) | ~240 |
| 6 | ~360 |
| 10 | ~600 |

```java
viewer.setRenderSpeed(6);
```

:::tip 匹配您的数据速率
如果您的服务器发送内容的速度超过查看器的渲染速度，缓冲区会增加，显示的内容会滞后。增加 `renderSpeed` 以保持同步，或在接收所有内容后调用 `flush()` 以立即显示剩余内容。
:::

### 渲染状态 {#render-state}

启用渐进渲染时，`isRendering()` 方法在组件积极显示缓冲内容时返回 `true`。聊天界面通常使用此方法显示或隐藏停止按钮：

```java
if (viewer.isRendering()) {
  stopButton.setVisible(true);
}
```

当禁用渐进渲染时，此方法始终返回 `false`。

### 控制渲染 {#controlling-rendering}

有两种方法控制渐进渲染的停止：

- **`stop()`** 会停止渲染并丢弃任何尚未显示的缓冲内容。当用户取消时调用此方法。
- **`flush()`** 会停止渲染，但立即显示所有剩余的缓冲内容。当所有内容都已接收且要立即显示时调用此方法。

```java
// 用户点击 "停止生成"
viewer.stop();

// 所有内容已接收，现在显示所有内容
viewer.flush();
```

这些方法在禁用渐进渲染时没有效果。

### 等待完成 {#waiting-for-completion}

`whenRenderComplete()` 方法返回一个 `PendingResult`，该结果在渐进渲染完成并显示所有缓冲内容时完成：

```java
viewer.whenRenderComplete().thenAccept(v -> {
  inputField.setEnabled(true);
  inputField.focus();
});
```

如果未启用渐进渲染或没有内容在渲染，`PendingResult` 会立即完成。

:::tip UI 协调
使用渐进渲染时，不要仅仅依据完成调用 `append()` 来重新启用输入字段。渲染器可能仍在显示缓冲内容。等待 `whenRenderComplete()` 以确保所有内容在用户可以再次交互之前都已显示。
:::

以下示例模拟了一个 AI 聊天界面，使用 `append()` 启用渐进渲染：

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

如果渐进渲染处于活动状态，`clear()` 还会停止渲染并完成任何待处理的 `whenRenderComplete()` 结果。

## 语法高亮 {#syntax-highlighting}

`MarkdownViewer` 支持代码块的语法高亮，当 [Prism.js](https://prismjs.com/) 可用时。通过 [前端打包工具](/docs/managing-resources/bundler/overview) 将 Prism 引入您的应用中：在您的 `App` 类中声明该包，并编写一个导入 Prism、自动加载插件和主题的条目。

```java title="Application.java"
@BundlePackage(value = "prismjs", version = "^1.29.0")
@BundleEntry("prism/entry.ts")
public class Application extends App {
  // ...
}
```

```ts title="src/main/frontend/prism/entry.ts"
import "prismjs";
import "prismjs/plugins/autoloader/prism-autoloader";
import "prismjs/themes/prism-tomorrow.min.css";
```

自动加载插件根据需要加载语言定义，因此带有语言提示的代码块诸如 ` ```java ` 或 ` ```python ` 脚本会自动高亮显示。

<TableBuilder name="MarkdownViewer" />
