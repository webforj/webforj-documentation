---
title: MarkdownViewer
sidebar_position: 74
sidebar_class_name: new-content
---

<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-markdown-viewer" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="markdown-viewer" location="com/webforj/component/markdown/MarkdownViewer" top='true'/>

The `MarkdownViewer` component renders markdown text as HTML. It supports standard markdown syntax including headers, lists, code blocks, links, images, and emoji rendering. The component also provides progressive rendering, which displays content character-by-character for a typewriter effect.

## Setting content {#setting-content}

Create a `MarkdownViewer` with or without initial content, then update it using `setContent()`:

```java
MarkdownViewer viewer = new MarkdownViewer("# Hello World");

// Replace content entirely
viewer.setContent("""
    ## New Content

    - Item 1
    - Item 2
    """);

// Get current content
String content = viewer.getContent();
```

The component implements `HasText`, so `setText()` and `getText()` work as aliases for the content methods.

<ComponentDemo 
path='/webforj/markdownviewer?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerView.java'
height='600px'
/>

## Appending content {#appending-content}

The `append()` method adds content incrementally without replacing what's already there:

```java
viewer.append("## New Section\n\n");
viewer.append("More content here...");
```

By default, appended content appears immediately. When [progressive rendering](#progressive-rendering) is enabled, appended content goes into a buffer and displays character-by-character instead.

## Auto-scroll {#auto-scroll}

Enable auto-scroll to keep the viewport at the bottom as content grows. This works with any method of adding content, whether `setContent()`, `append()`, or progressive rendering. If a user manually scrolls up to review earlier content, auto-scroll pauses and resumes when they scroll back to the bottom.

```java
viewer.setAutoScroll(true);
```

## Progressive rendering {#progressive-rendering}

Progressive rendering displays content character-by-character rather than all at once, creating a typewriter effect. AI chat interfaces commonly use this to show responses appearing gradually:

```java
MarkdownViewer viewer = new MarkdownViewer();
viewer.setProgressiveRender(true);
```

When enabled, content added via `setContent()` or `append()` goes into a buffer and displays incrementally. When disabled, content appears immediately.

<ComponentDemo 
path='/webforj/markdownviewerprogressive?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerProgressiveView.java'
height='650px'
/>

### Render speed {#render-speed}

The `setRenderSpeed()` method controls how many characters render per animation frame. Higher values mean faster rendering. At 60fps, the default speed of 4 translates to approximately 240 characters per second:

| Speed | Characters/Second |
|-------|-------------------|
| 4 (default) | ~240 |
| 6 | ~360 |
| 10 | ~600 |

```java
viewer.setRenderSpeed(6);
```

:::tip Matching your data rate
If your server sends content faster than the viewer renders, the buffer grows and displayed content lags behind. Increase `renderSpeed` to keep pace, or call `flush()` when all content has been received to display remaining content immediately.
:::

### Render state {#render-state}

When progressive rendering is enabled, the `isRendering()` method returns `true` while the component is actively displaying buffered content. Chat interfaces often use this to show or hide a stop button:

```java
if (viewer.isRendering()) {
  stopButton.setVisible(true);
}
```

This method always returns `false` when progressive rendering is disabled.

### Controlling rendering {#controlling-rendering}

Two methods control how progressive rendering stops:

- **`stop()`** halts rendering and discards any buffered content not yet displayed. Call this when the user cancels.
- **`flush()`** halts rendering but immediately displays all remaining buffered content. Call this when all content has been received and you want to show it without waiting.

```java
// User clicked "Stop generating"
viewer.stop();

// All content received, show everything now
viewer.flush();
```

These methods have no effect when progressive rendering is disabled.

### Waiting for completion {#waiting-for-completion}

The `whenRenderComplete()` method returns a `PendingResult` that completes when progressive rendering finishes displaying all buffered content:

```java
viewer.whenRenderComplete().thenAccept(v -> {
  inputField.setEnabled(true);
  inputField.focus();
});
```

If progressive rendering isn't enabled or no content is being rendered, the `PendingResult` completes immediately.

:::tip UI coordination
When using progressive rendering, don't re-enable input fields based solely on when you finish calling `append()`. The renderer may still be displaying buffered content. Wait for `whenRenderComplete()` to ensure users see everything before they can interact again.
:::

The following demo simulates an AI chat interface using `append()` with progressive rendering enabled:

<ComponentDemo 
path='/webforj/markdownviewerstreaming?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerStreamingView.java'
height='450px'
/>

## Clearing content {#clearing-content}

Remove all content with `clear()`:

```java
viewer.clear();
```

If progressive rendering is active, `clear()` also stops rendering and completes any pending `whenRenderComplete()` results.

## Syntax highlighting {#syntax-highlighting}

The `MarkdownViewer` supports syntax highlighting for code blocks when [Prism.js](https://prismjs.com/) is available. Add Prism.js to your app using the `@JavaScript` and `@StyleSheet` annotations:

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

The autoloader plugin loads language definitions as needed, so code blocks with language hints like ` ```java ` or ` ```python ` get highlighted automatically.