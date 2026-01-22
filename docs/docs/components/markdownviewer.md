---
title: MarkdownViewer
sidebar_position: 74
sidebar_class_name: new-content
---

<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-markdown-viewer" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="markdown" location="com/webforj/component/markdown/MarkdownViewer" top='true'/>

The `MarkdownViewer` component renders markdown text as HTML. It supports standard markdown syntax including headers, lists, code blocks, links, images, and emoji rendering. The component also provides progressive rendering for streaming scenarios like AI chat interfaces, where content arrives in chunks and displays incrementally.

## Setting content {#setting-content}

Create a `MarkdownViewer` with or without initial content, then update it using `setContent()`:

```java
MarkdownViewer viewer = new MarkdownViewer("# Hello World");

// Replace content entirely
viewer.setContent("## New Content\n\n- Item 1\n- Item 2");

// Get current content
String content = viewer.getContent();
```

The component implements `HasText`, so `setText()` and `getText()` work as aliases for the content methods.

<ComponentDemo 
path='/webforj/markdownviewer?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerView.java'
height='200px'
/>

## Progressive rendering {#progressive-rendering}

Progressive rendering displays content character-by-character rather than all at once. This creates a typewriter effect useful for AI chat interfaces where the server streams responses in chunks.

```java
MarkdownViewer viewer = new MarkdownViewer();
viewer.setProgressiveRender(true);
```

### Render speed {#render-speed}

Control how fast content appears with `setRenderSpeed()`, which sets the number of characters rendered per animation frame. Higher values result in faster rendering. At 60fps, the default speed of 4 translates to approximately 240 characters per second:

| Speed | Characters/Second |
|-------|-------------------|
| 4 (default) | ~240 |
| 6 | ~360 |
| 10 | ~600 |

```java
viewer.setRenderSpeed(6);
```

:::tip Matching your stream
If your server sends content faster than the viewer renders, the buffer grows and displayed content lags behind. Increase `renderSpeed` to keep pace, or call `flush()` when the stream completes to display remaining content immediately.
:::

### Render state {#render-state}

Use `isRendering()` to check if progressive rendering is in progress. This is useful for showing or hiding stop buttons in chat interfaces:

```java
if (viewer.isRendering()) {
  stopButton.setVisible(true);
}
```

## Auto-scroll {#auto-scroll}

When enabled, auto-scroll keeps the viewport at the bottom as new content is added. The behavior pauses when the user manually scrolls up and resumes when they scroll back to the bottom.

```java
viewer.setAutoScroll(true);
```

## Streaming methods {#streaming-methods}

The `MarkdownViewer` provides methods for managing streaming content in real-time AI responses or live data feeds.

### Appending content {#appending-content}

Use `append()` to add content chunks as they arrive:

```java
chatService.stream(message)
    .doOnNext(chunk -> viewer.append(chunk))
    .subscribe();
```

### Stopping and flushing {#stopping-and-flushing}

Two methods control how rendering stops:

- **`stop()`** halts progressive rendering and discards any buffered content not yet displayed. Use this when the user cancels a stream.
- **`flush()`** stops progressive rendering but immediately displays all remaining buffered content. Use this when the stream completes.

```java
// User clicked "Stop generating"
viewer.stop();

// Stream complete - show everything now
viewer.flush();
```

### Clearing content {#clearing-content}

Remove all content with `clear()`:

```java
viewer.clear();
```

The following example simulates a streaming response. Click "Start Stream" to see progressive rendering in action, or "Stop" to halt mid-stream:

<ComponentDemo 
path='/webforj/markdownviewerstreaming?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerStreamingView.java'
height='500px'
/>

## Waiting for render completion {#waiting-for-render-completion}

The `whenRenderComplete()` method returns a `PendingResult` that completes when progressive rendering finishes. Use this to coordinate UI state changes after all content has been displayed:

```java
viewer.whenRenderComplete().thenAccept(v -> {
  inputField.setEnabled(true);
  inputField.focus();
});
```

If progressive rendering is not active, the `PendingResult` completes immediately.

:::tip UI coordination
Don't re-enable input fields or hide loading states based solely on stream completion. The progressive renderer may still be displaying buffered content. Wait for `whenRenderComplete()` to ensure users see all content before interacting again.
:::

## Syntax highlighting {#syntax-highlighting}

The `MarkdownViewer` supports syntax highlighting for code blocks when Prism.js is available. Add Prism.js to your application using the `@JavaScript` and `@StyleSheet` annotations:

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

The autoloader plugin loads language definitions as needed, so code blocks with language hints like ` ```java ` or ` ```python ` are highlighted appropriately.
