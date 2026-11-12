---
title: "Build an AI agent in Java that uses your web app like a user"
description: "Build an AI agent in Java with Spring AI and webforJ that reads the screen, clicks, types and fills forms in your running app while the user watches each step."
slug: java-ai-agent
date: 2026-11-03T11:00:00Z
authors: Hyyan Abo Fakher
tags: [ai, spring-ai, spring]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-11-03-java-ai-agent/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-11-03-java-ai-agent/cover.jpg)

To build an AI agent in Java that does real work in a web app, give it tools that use the app the way a person does. With Spring AI running the model and webforJ's automation layer as the tools, the agent reads the screen as text, finds a field by its label, types into it, picks from a list, and saves the form. The user watches every step happen in their own browser, and the agent can do nothing the user couldn't.

This is a different thing from a chatbot that answers questions, or from retrieval over your documents. The agent takes a goal such as "ship every pending order for Acme" and works through it in several steps, on the same screens the user works on. This post walks through how such an agent is put together in a Spring Boot app with webforJ.

<!-- truncate -->

## What the agent does {#what-the-agent-does}

The agent lives in a panel inside the app. The user types a request. The agent writes a short plan, then works through it: it opens the orders view, filters by status, reads the table, and ships each order with the same buttons the user would click. Each step shows in the panel as it happens, and the answer streams in as markdown when it is done.

Three parts make that work:

- **The model and the loop.** A Spring AI `ChatClient` holds the system prompt, the conversation memory, and the tools. When the model asks for a tool, Spring AI calls it, sends back the result, and repeats until the model answers.
- **The tools.** Plain Java methods annotated with `@Tool`. They see the screen and act on it through the webforJ automation layer.
- **The panel.** A webforJ component that sends the request off the UI thread, streams the answer back, and shows each step.

## Tools that see and act {#tools-that-see-and-act}

The automation layer gives an agent the three abilities a user has. `Snapshot.render` describes the screen as plain text, one line per component, with what each one shows. `ComponentLocator` finds a component by its type and what it shows, such as a field by its label. `UserInteraction` makes the gestures: clicking, typing, choosing, checking.

Wrapped as Spring AI tools, those abilities look like this:

```java
public class ScreenTools {
  private final Environment environment = Environment.getCurrent();
  private final Frame frame;
  private final StepObserver observer;

  public ScreenTools(Frame frame, StepObserver observer) {
    this.frame = frame;
    this.observer = observer;
  }

  @Tool(description = "Returns the screen as text, one line per component.")
  public String see() {
    return onSession(() -> Snapshot.render(frame, new SnapshotOptions().setBrowserState(true)));
  }

  @Tool(description = "Types text into the text field with the given label.")
  public String type(
      @ToolParam(description = "The label of the field") String label,
      @ToolParam(description = "The text to type") String text) {
    return onSession(() -> {
      TextField field = ComponentLocator.of(TextField.class).within(frame).byLabel(label).locate();
      UserInteraction.type(field, text);
      return Snapshot.render(frame);
    });
  }
}
```

Each action returns the fresh screen, so the model reads what changed before it decides the next step. The snapshot reads like this, which a model handles the way it handles any text:

```text
- Button "Save" [theme=primary]
- TextField [label=Customer] [value=Acme]
- Button "Ship" [disabled]
```

`setBrowserState(true)` includes what lives in the browser, such as the value a user typed, so the agent sees the form exactly as the user does.

## Run every tool on the session's thread {#run-tools-on-the-session-thread}

This is the part that decides whether the agent works at all. In webforJ, components belong to the user's session and are changed on that session's thread. The [asynchronous updates guide](/docs/advanced/asynchronous-updates) explains the rule. Spring AI calls your tools on whatever thread runs the request, which is not that thread.

So each tool hands its work to the session with `Environment.runLater` and waits for the result. The tools capture the session's `Environment` when they are created, on the session's thread, and pass it along:

```java
<T> T onSession(Supplier<T> task) {
  CompletableFuture<T> result = new CompletableFuture<>();
  PendingResult<T> pending = Environment.runLater(environment, task);
  pending.thenAccept(result::complete);
  pending.exceptionally(error -> {
    result.completeExceptionally(error);
    return null;
  });
  return result.join();
}
```

The tool call blocks the worker thread until the session has done the work, then returns the outcome to the model. The session thread itself never waits, so the page stays live while the agent works, and every change the agent makes reaches the screen the moment it happens.

## Give the agent task tools {#task-tools}

`see` and a few generic gestures are enough for an agent to reach any part of the app. They are also slow, because the agent has to find its way one gesture at a time. For the jobs users ask for most, write a task tool that does the whole job on the real screen in one call:

```java
@Tool(description = "Filters the orders by status and returns the orders the table shows.")
public String filterOrders(
    @ToolParam(description = "Pending, Shipped or Cancelled") String status) {
  return onSession(() -> {
    ChoiceBox statuses = ComponentLocator.of(ChoiceBox.class).within(frame).byLabel("Status").locate();
    ListItem item = statuses.getItems().stream()
        .filter(i -> i.getText().equalsIgnoreCase(status)).findFirst()
        .orElseThrow(() -> new IllegalArgumentException("There is no status " + status));
    UserInteraction.select(statuses, item);
    return Snapshot.render(frame);
  });
}
```

A task tool still drives the screen. It selects in the same `ChoiceBox` the user would, so the user sees the table filter, and the listeners of your view run exactly as they do for a click. The agent gets fewer, larger steps, and the generic tools stay available for anything the task tools don't cover.

When a lookup fails, throw with a message that tells the model what it can do instead, such as the list of valid statuses. The model reads that message as the tool's result and corrects itself on the next call.

## Plan the steps and show them {#plan-and-show-steps}

A request that takes several actions is easier to follow when the agent says what it will do first. Give it a `plan` tool, and tell it in the system prompt to call it before the first action:

```java
@Tool(description = "Records the steps of a request before the first action.")
public String plan(@ToolParam(description = "The steps in order") List<String> steps) {
  return onSession(() -> {
    observer.onPlan(steps);
    return "Planned " + steps.size() + " steps.";
  });
}
```

The observer is the panel. It shows the plan as a checklist, and each tool reports its own step the same way, so the user sees what the agent is about to do and watches each part get done on the screen beside it.

## Wire the conversation {#wire-the-conversation}

Build one `ChatClient` per browser session, with the system prompt, [a chat memory](/blog/spring-ai-chat-memory) so follow-up requests keep their context, and that session's tools:

```java
ChatMemory memory = MessageWindowChatMemory.builder().build();
ChatClient client = ChatClient.builder(model)
    .defaultSystem("""
        You operate the order app for the signed in user, who watches the screen.
        Call plan first when a request takes more than one step.
        Read what each tool returns before the next call. Never guess a name.
        When a tool refuses, report it instead of pretending.""")
    .defaultAdvisors(MessageChatMemoryAdvisor.builder(memory).build())
    .defaultTools(tools)
    .build();
```

The tools belong to one session, because they hold that session's `Environment` and frame. Creating the client per session keeps one user's agent from ever reaching another user's screen.

The system prompt carries most of the agent's reliability. Tell it to read every result before the next call, to take names from what the tools return instead of guessing, and to say so when something was refused.

## Stream the answer into the panel {#stream-the-answer}

The request blocks while the model and the tools work, so the panel runs it on an executor. Spring AI streams the answer, and each chunk goes back to the session with `Environment.runLater`, into a `MarkdownViewer`:

```java
public void ask(String message) {
  MarkdownViewer answer = new MarkdownViewer();
  getBoundComponent().add(answer);
  running = new FutureTask<>(() -> client.prompt().user(message)
      .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
      .stream().content()
      .doOnNext(chunk -> Environment.runLater(environment, () -> answer.append(chunk)))
      .blockLast(), null);
  executor.execute(running);
}
```

A stop button calls `running.cancel(true)`, which interrupts the worker. Check for the interrupt at the start of each tool call so the agent stops at the next step instead of finishing the plan.

## The agent has the user's permissions {#the-users-permissions}

Because every action goes through the UI, the agent is bound by the same rules as the user it works for. A disabled button stays disabled, and clicking it fails instead of running its listener. A route the user can't open, the agent can't open either, and data the user isn't allowed to see never reaches a screen the agent can read. There is no separate API for the agent, and so nothing extra to secure.

The same reasoning covers destructive actions. If your view asks the user to confirm before deleting something, it asks while the agent works too, and the user decides. The system prompt should tell the agent that changes to data happen only when the user asked for them, and that it reports what changed.

## Which model to use {#which-model}

Any chat model that Spring AI supports and that can call tools. The preceding code depends on `ChatModel` and `ChatClient`, so the provider is a starter dependency and a few properties, and switching providers doesn't change a tool. An agent that plans across many steps benefits from a capable hosted model, and [a model running locally](/blog/ollama-java-web-app) keeps your data on your own hardware.

## Build your own {#next-step}

Start with a webforJ Spring Boot project from [startforJ](https://docs.webforj.com/startforj) and add Spring AI with the starter for your model. Give the agent `see` and one task tool, and watch it use one screen before you add the tools that change data. The [finding components](/docs/testing/testbed/finding-components) and [user interaction](/docs/testing/testbed/user-interaction) pages list every locator and gesture the tools can use, and the same API runs in TestBed tests, so you can test your tools without a browser.

For a streaming chat feature without tools, read [Adding an AI chat feature to a Java app](/blog/add-ai-to-java-app).

## Frequently asked questions {#faq}

### What is an AI agent in Java? {#faq-what-is-an-agent}

It is a program where a model chooses which of your Java methods to call, over several steps, to reach a goal. With Spring AI those methods are `@Tool` methods, and with webforJ they can see and use the app's screens the way a person does.

### How does the agent use the UI without a browser driver? {#faq-no-browser-driver}

The automation layer works on the component tree on the server. `Snapshot.render` reads the screen as text, `ComponentLocator` finds a component, and `UserInteraction` performs the gesture. The user's browser shows each change as the components update.

### Why do the tools call Environment.runLater? {#faq-runlater}

Components belong to the user's session and change on its thread, while Spring AI runs tools on another thread. `Environment.runLater` hands each tool's work to the session and returns its result, so the agent can act without blocking the page.

### Can the agent do things the user isn't allowed to do? {#faq-permissions}

No. It acts through the same UI and the same session as the user, so disabled controls, protected routes and confirmation dialogs apply to the agent exactly as they apply to the user.
