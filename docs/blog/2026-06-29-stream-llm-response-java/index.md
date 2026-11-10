---
title: "Stream ChatGPT responses into a Java web UI with markdown"
description: "Stream ChatGPT responses in a Java web app. Append chunks to webforJ's MarkdownViewer, render markdown as it arrives, and stop generation with one call."
slug: stream-llm-response-java
date: 2026-06-29
authors: Eric Handtke
tags: [ai, spring-ai, chat]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-06-29-stream-llm-response-java/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-06-29-stream-llm-response-java/cover.jpg)

To stream ChatGPT responses in a Java web app, send each chunk from the model into a webforJ `MarkdownViewer`. The component renders Markdown as it arrives, keeps the newest text in view, and gives you `stop()` and `whenRenderComplete()` for the moments when the user cancels or the answer ends. The model call stays in a plain service, and the view only appends text.

<!-- truncate -->

:::tip Try it live
The [MarkdownViewer documentation](/docs/components/markdownviewer) has live, interactive samples you can click through in the browser, with the full Java source next to each one.
:::

:::tip Run a complete chat app
[ghost:ai](https://github.com/webforj/built-with-webforj/tree/main/webforj-ghostai) is a full chat app built with webforJ and Spring AI. It streams every answer into a `MarkdownViewer` and lets the user stop it mid-reply, the same pattern this post walks through. View the code on GitHub and run it with your own Gemini API key.
:::

## Why plain text fails for answers {#why-plain-text-fails}

Models answer in Markdown. A chunk can end in the middle of a bold marker, an open code fence, or a list item. Write those chunks into a plain text component and the page shows raw asterisks and backticks. Rebuild the whole page on every chunk and the layout jumps.

The view needs to render Markdown on every update, and the user needs a way to stop the answer halfway through.

## Stream a ChatGPT response in Java {#stream-from-spring-ai}

Spring AI's `ChatClient` returns a Reactor `Flux<String>` from `stream().content()`. Each emitted string is the next piece of the answer. The service below only returns text and knows nothing about the UI. Point Spring AI at an OpenAI model and this is a ChatGPT stream. Point it at another provider and the same code streams that model instead.

```java
@Service
public class ChatService {
  private final ChatClient chatClient;

  public ChatService(ChatClient.Builder builder) {
    this.chatClient = builder.build();
  }

  public Flux<String> stream(String message) {
    return chatClient.prompt()
        .user(message)
        .stream()
        .content();
  }
}
```

The provider details stay out of the view. Swap the chat model later, and the view code doesn't change. Keep the return type as `Flux<String>`. A method that returns one finished `String` makes the view wait for the full answer, and the streaming effect is gone.

## Build it in four steps {#build-steps}

1. Add a Spring AI chat model starter to your Spring Boot project and configure its credentials. The [Spring Boot guide](/docs/integrations/spring/spring-boot) covers the project setup.
2. Create `ChatService` and expose the `stream()` method.
3. Create a view that holds one `MarkdownViewer` and an input with send and stop actions, and wire those actions to the two methods below.
4. Start the app and send a question. The answer appears as it streams.

## Render chunks in a MarkdownViewer {#render-chunks}

The code below comes from [ghost:ai](https://github.com/webforj/built-with-webforj/tree/main/webforj-ghostai), trimmed to the parts that use the viewer. Set the viewer up once, before the first chunk arrives. Progressive rendering shows text character by character instead of all at once, and auto-scroll follows the newest text and pauses when the user scrolls up. Both are plain setters on the component:

```java
markdownViewer.setAutoScroll(true)
    .setProgressiveRender(true);
```

When the user sends a message, start the stream and append each chunk as it arrives:

```java
private void handleSend(String message) {
  currentSubscription = chatService.stream(message)
      .doOnNext(chunk -> markdownViewer.append(chunk))
      .doOnError(error -> {
        markdownViewer.append("*Error: " + error.getMessage() + "*");
        currentSubscription = null;
      })
      .doOnComplete(() -> {
        currentSubscription = null;
        markdownViewer.whenRenderComplete().thenAccept(v -> {
          // the answer is fully on screen, so re-enable the input here
        });
      })
      .subscribe();
}
```

The Stop action disposes the subscription and stops the viewer:

```java
private void handleStop() {
  if (currentSubscription != null) {
    currentSubscription.dispose();
    currentSubscription = null;
  }
  markdownViewer.stop();
}
```

A few details in this code matter:

- `append()` adds to the text already shown. `setContent()` replaces the whole answer, so calling it for each chunk throws away the previous text on every update.
- The error callback writes the message into the answer, so the user sees what went wrong instead of a stalled reply.
- The input is re-enabled in `whenRenderComplete()`, not when the stream completes, because the viewer may still be showing buffered text.
- At the default render speed of 4, the viewer renders about 240 characters per second, based on 60 frames per second. If the model sends faster than the viewer renders, the buffer grows and the text lags behind. Call `setRenderSpeed(6)` for about 360 characters per second. Set it too high and the typewriter effect disappears.

## Stop, flush and finish {#stop-flush-finish}

Three calls cover the ends of an answer:

- `stop()` ends rendering and drops buffered text that hasn't appeared yet. Use it for the Stop button.
- `flush()` ends rendering and shows the buffered text at once. Use it when you want the full answer on screen immediately.
- `whenRenderComplete()` returns a `PendingResult` that finishes when the viewer has shown the last character. Re-enable Send there, not when the stream ends, because the viewer may still be showing buffered text.

`isRendering()` returns `true` while text is still arriving. Use it to show the Stop button only during an answer.

The Stop path works in a fixed order. The handler disposes the subscription first, so no new chunks arrive. Then it calls `stop()`, which discards the buffered text. The answer stays on screen as it looks at that moment, and Send becomes available again. A user who clicks Stop gets their partial answer back, not an empty panel.

## Highlighting code blocks {#highlighting-code-blocks}

Answers often include code. The viewer renders fenced code blocks, but it highlights them only when Prism.js is available. The [frontend bundler overview](/docs/managing-resources/bundler/overview) shows how to add Prism to your app. Without it, code blocks still render, just without colors.

## Checklist {#checklist}

- Pass the `Flux<String>` from the model call straight to the view.
- Turn on progressive rendering and auto-scroll before the first chunk.
- Add each chunk with `append()`, not `setContent()`.
- Re-enable Send inside `whenRenderComplete()`.
- Dispose the subscription and call `stop()` when the user clicks Stop.

## Where to go next {#where-to-go-next}

Start a project with [startforJ](https://docs.webforj.com/startforj), or add the Spring Boot integration to an existing app. The [MarkdownViewer reference](/docs/components/markdownviewer) lists every method used here. The [Button](/docs/components/button) and [TextField](/docs/components/fields/textfield) pages cover the input controls in the view.
