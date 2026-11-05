---
title: "Render markdown in Java with the webforJ MarkdownViewer"
description: "Render markdown in a Java web app with webforJ's MarkdownViewer. Set content, append text, and stream answers with progressive rendering in pure Java."
slug: render-markdown-java
date: 2026-08-20
authors: Matthew Hawkins
tags: [components, tutorial, front end]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-08-20-render-markdown-java/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-08-20-render-markdown-java/cover.jpg)

Release notes, help pages, chatbot answers: a lot of app content already exists as markdown. Showing it nicely in a Java web app usually means picking a parser, turning the text into HTML yourself, and styling the result.

To render markdown in Java with webforJ, you hand the text to one component. `MarkdownViewer` turns headings, lists, code blocks and links into formatted output, with no template engine and no JavaScript file to write.

<!-- truncate -->

:::tip Try it live
The [MarkdownViewer documentation](/docs/components/markdownviewer) has live, interactive samples you can click through in the browser, with the full Java source next to each one.
:::

:::tip See it in a full app
[ghost:ai](https://github.com/webforj/built-with-webforj/tree/main/webforj-ghostai) is a chat app built with webforJ and Spring AI that shows every answer in a `MarkdownViewer`. Answers render progressively, and a stop button ends them early. View the code on GitHub and run it with your own Gemini API key.
:::

In this post you'll fill a viewer with markdown, update it as content changes, and turn on the settings that make streamed text read smoothly.

## Render markdown in a Java view {#render-markdown-in-a-java-view}

Markdown is plain text with a few marks for structure. A `#` starts a heading, a `-` starts a list item, and backticks wrap code. A markdown viewer reads those marks and shows the formatted result, so your Java code only has to supply the text.

In webforJ, that job belongs to the `MarkdownViewer` component. It was added in version 25.11, and it implements `HasText`, so you can also read and write its content through `setText()` and `getText()`. The examples below use the `setContent()` and `getContent()` pair because the name says what the method does.

This is the first example from the docs. It builds a viewer with starting content, replaces it, and reads it back:

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

The constructor `new MarkdownViewer("...")` accepts starting content. If you create the viewer first and fill it later, use `new MarkdownViewer()` instead. The text block in `setContent()` keeps the markdown readable in Java, and the indentation inside the block is stripped by the compiler, so the markdown starts at column one.

## Replace and append content {#replace-and-append-content}

Two methods change what the viewer shows, and they behave differently:

- `setContent(String)` replaces everything in the viewer.
- `append(String)` adds text to the end and keeps what is already there.

Use `setContent()` when you load a new document, and `append()` when text arrives in pieces:

```java
viewer.append("## New Section\n\n");
viewer.append("More content here...");
```

The `clear()` method removes all content, and `getContent()` returns the markdown the viewer holds.

A common mistake is to call `setContent()` for every chunk of a streaming answer. Each call throws away the text that came before it, so the reader watches the answer restart over and over. Call `append()` for each chunk instead.

## Stream an answer as it arrives {#stream-an-answer}

Large language models send text in small pieces. If you append each piece the moment it arrives, the page updates in bursts, and it jumps around as the text grows. `MarkdownViewer` has a setting for this case called progressive rendering. When it is on, appended text goes into a buffer, and the viewer shows that buffer one character at a time.

Turn it on, and set how fast the buffer drains, with the calls from the docs:

```java
MarkdownViewer viewer = new MarkdownViewer();
viewer.setProgressiveRender(true);
viewer.setRenderSpeed(6);
viewer.setAutoScroll(true);
```

A few details matter here:

- The render speed controls how fast the buffer drains. The default is 4, and at 60 frames per second that is about 240 characters per second. The value 6 gives about 360 characters per second. If your server sends text faster than the viewer draws it, the buffer grows and the text falls further behind. Raise the speed to catch up.
- `stop()` ends rendering and throws away the text still in the buffer. Use it when the reader cancels. `flush()` ends rendering and shows the rest of the buffer at once. Use it when the stream has finished and you want the full answer on screen without waiting.
- `whenRenderComplete()` returns a `PendingResult` that finishes when the buffer is empty. Re-enable an input field inside its callback, not right after the last `append()`, because the buffer can still hold text when `append()` returns.

`isRendering()` returns `true` while the buffer is still drawing, which is handy for showing a Stop button only during an answer. It always returns `false` when progressive rendering is off.

For a full walkthrough that connects a model's streamed reply to the viewer, read [Stream ChatGPT responses into a Java web UI with markdown](/blog/stream-llm-response-java).

## Add syntax highlighting to code blocks {#syntax-highlighting}

Code blocks render in plain text by default. To color them, the `MarkdownViewer` uses [Prism.js](https://prismjs.com/) when Prism is available in the page. You bring Prism in through the webforJ frontend bundler: declare the package on your `App` class and write a small entry file that imports Prism, its autoloader plugin, and a theme. The autoloader loads each language as the page needs it, so a fenced block tagged `java` or `python` gets highlighted without extra setup. The [bundler overview](/docs/managing-resources/bundler/overview) explains where those files go.

## Frequently asked questions {#faq}

### Which webforJ version adds MarkdownViewer? {#which-version}

`MarkdownViewer` is available from webforJ 25.11. The component reference page lists the same version in its since chip, so check that page if you are on an older release. The class uses the `dwc-markdown-viewer` element, which the framework provides for you.

### Can I show code blocks with color? {#code-highlighting}

Yes. The viewer uses Prism.js for syntax highlighting when Prism is loaded in the app. Declare the `prismjs` package with `@BundlePackage`, add an entry file that imports Prism and its autoloader, and then fenced code blocks with a language tag are colored.

### What is the difference between stop and flush? {#stop-or-flush}

`stop()` ends progressive rendering and discards the buffered text that has not been shown yet. `flush()` ends rendering and shows all buffered text immediately. Use `stop()` when the reader cancels, and `flush()` when the stream has finished and the rest of the answer should appear at once.

## Next step {#next-step}

Create a project with [startforJ](https://docs.webforj.com/startforj), add the two classes from this post to a view, and open it in the browser. The [getting started guide](/docs/introduction/getting-started) covers the project layout, and the [MarkdownViewer reference](/docs/components/markdownviewer) lists every method on the component.
