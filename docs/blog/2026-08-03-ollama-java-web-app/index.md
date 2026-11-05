---
title: "Ollama in a Java Spring Boot UI, built with webforJ"
description: "Run a local chatbot in Java: Spring AI calls an Ollama model on your machine while a webforJ view shows the chat. No JavaScript, no HTML templates."
slug: ollama-java-web-app
date: 2026-08-03
authors: Matthew Hawkins
tags: [ai, spring-ai, spring, chat]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-08-03-ollama-java-web-app/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-08-03-ollama-java-web-app/cover.jpg)

Sending every prompt to a cloud model isn't always an option. Maybe the data can't leave the building, maybe you want to experiment without an API bill, or maybe you just want a chatbot that keeps working when the network doesn't.

Ollama in a Java Spring Boot UI covers all three. Ollama runs the model on your own machine, Spring AI talks to it like any other provider, and a webforJ view shows the chat, so the whole app stays in Java.

<!-- truncate -->

In this post you'll set up a local model, write a small service that talks to it, and build a view that stays responsive while the model thinks.

:::tip See a full chat app
[ghost:ai](https://github.com/webforj/built-with-webforj/tree/main/webforj-ghostai) is a complete chat app built with webforJ and Spring AI. It streams each answer and lets the user stop it. It talks to its model through the same `ChatClient` API as this post, so it's a good next step once your local model answers. Out of the box it's set up for Gemini.
:::

## What is Ollama? {#what-is-ollama}

Ollama is a program that runs large language models on your own computer. You pick a model by name, and Ollama serves it over HTTP. By default the server listens on `127.0.0.1` port `11434`, so a request from your app goes to the same machine.

The model used in this post is `llama3.2`. The Ollama library lists it with a default size of 3 billion parameters and a smaller 1 billion parameter variant. Start it once from a terminal:

```bash
ollama run llama3.2
```

That opens an interactive prompt. Type a question to confirm the model answers. If the server is not already running, `ollama serve` starts it on port `11434`.

## How do I run an LLM locally in Java? {#run-an-llm-locally-in-java}

Your app needs two things: a client that speaks to the model, and a place to configure it. Spring AI provides both. Its Ollama starter adds the client, and Spring Boot creates the `ChatClient.Builder` for you. You only need the dependencies and a few properties.

These are the Maven entries for this setup. The webforJ BOM sets the webforJ version, the Spring AI BOM sets the Spring AI version, and the Spring Boot BOM lines up the Spring modules:

```xml
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-bom</artifactId>
      <version>26.01</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
    <dependency>
      <groupId>org.springframework.ai</groupId>
      <artifactId>spring-ai-bom</artifactId>
      <version>2.0.0</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-dependencies</artifactId>
      <version>4.1.0</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>

<dependencies>
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-spring-boot-starter</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-ollama</artifactId>
  </dependency>
</dependencies>
```

The settings go in `application.properties`:

```properties
spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.chat.model=llama3.2
```

The base URL is already the default, so you can leave that line out. Keep it if you want the address visible in the config. The model line is the one that matters. Change it to any model name from the Ollama library and restart the app.

For the webforJ side, the Spring Boot starter lets routes receive Spring beans through their constructors. The next section uses that to hand the model service to the view.

## How does Spring AI talk to Ollama? {#spring-ai-talks-to-ollama}

The service class owns the model. It builds one `ChatClient` at startup and exposes a single method that takes a prompt and returns the answer:

```java
@Service
public class LocalChatService {

  private final ChatClient chatClient;

  public LocalChatService(ChatClient.Builder builder) {
    this.chatClient = builder
        .defaultSystem("You are a concise assistant. Answer in plain text.")
        .build();
  }

  public String reply(String prompt) {
    return chatClient.prompt()
        .user(prompt)
        .call()
        .content();
  }
}
```

The `defaultSystem` text applies to every request, so the view never has to repeat it. `call()` sends the request and waits for the complete answer, and `content()` gives you the reply as a `String`. If you want the text as it arrives, `stream()` returns a `Flux` of chunks instead. This example keeps `call()` because the chat view shows the full answer at once.

Notice that the class has no Ollama-specific code. The starter and the properties choose the provider behind the builder, so the provider settings live in configuration rather than in this class.

## Why does the reply need runLater? {#why-runlater}

A model reply takes seconds. If the click handler calls `reply()` directly, the page freezes until the answer comes back. The fix is to run the call on a background thread and send the result back to the UI when it is ready.

webforJ requires UI work to happen on the Environment thread. A background thread that was started from inside an Environment thread inherits that context, and it can post updates with `Environment.runLater`. A thread created outside the Environment, such as a plain `Timer` task or a static initializer thread, throws `IllegalStateException` when it calls `runLater`. The [asynchronous updates](/docs/advanced/asynchronous-updates) page covers the thread model in detail.

The view below creates its executor in the constructor. The click handler runs on the Environment thread, so each task it submits inherits the context:

```java
@Route("/")
public class ChatView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private final Paragraph transcript = new Paragraph();
  private final TextArea input = new TextArea();
  private final Button send = new Button("Send");
  private final ExecutorService worker = Executors.newCachedThreadPool();

  public ChatView(LocalChatService chat) {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(720);
    self.setStyle("margin", "1em auto");

    send.onClick(e -> {
      String prompt = input.getValue();
      input.setValue("");
      send.setEnabled(false);

      worker.submit(() -> {
        String text;
        try {
          text = chat.reply(prompt);
        } catch (RuntimeException ex) {
          text = "The local model did not answer: " + ex.getMessage();
        }
        String answer = text;
        Environment.runLater(() -> {
          transcript.setText(answer);
          send.setEnabled(true);
        });
      });
    });

    self.add(input, send, transcript);
  }
}
```

Spring supplies `LocalChatService` through the constructor, so the view never creates the model client itself. `Composite<FlexLayout>` gives the view a layout through `getBoundComponent()`, and the layout holds the input, the button, and the reply. The `TextArea` and `Button` APIs match the [TextArea](/docs/components/textarea) and [Button](/docs/components/button) pages.

The `catch` block matters. If Ollama is not running, `reply()` throws, and the page shows the error in the reply area. The button comes back to life in the same `runLater` call, so the user can retry without reloading.

## How do I try the local chatbot? {#try-the-local-chatbot}

Start the app and open `http://localhost:8080`, the default Spring Boot port. Type a question and press Send. The button stays disabled while the model works, and the answer appears below the input when Ollama finishes.

If the answer never comes, check two things first. Confirm that `ollama run llama3.2` works in a terminal. Then confirm that the server is reachable on port `11434`, because the property file points there.

The quickest way to start from zero is [startforJ](https://docs.webforj.com/startforj). Choose Spring Boot as the runtime, then add the two classes and the properties from this post. The [Spring Boot integration](/docs/integrations/spring/spring-boot) page covers the project layout, and the [routing guide](/docs/integrations/spring/routing) explains how a view receives Spring beans through its constructor.

## Frequently asked questions {#faq}

### Does the prompt leave my machine? {#does-the-prompt-leave-my-machine}

By default the service calls `http://localhost:11434`, and Ollama binds to `127.0.0.1` on port `11434`, so the request and the reply stay on the same machine. Set `spring.ai.ollama.base-url` only if your Ollama server runs on another host. Then the prompt travels to that host instead.

### Can I switch to a different model? {#can-i-switch-models}

Change `spring.ai.ollama.chat.model` to another name from the Ollama library. The library lists `llama3.2:1b` as the smaller variant of the same model. Run `ollama run` with that name once, then restart the app. The Java code does not change.
