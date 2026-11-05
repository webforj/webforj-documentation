---
title: "Spring AI chat memory: keep chat history per user in webforJ"
description: "Add Spring AI chat memory to a webforJ app: keep recent messages per conversation, give every user their own conversation, and control how much history the model sees."
slug: spring-ai-chat-memory
date: 2026-08-10
authors: Matthew Hawkins
tags: [ai, chat, spring]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-08-10-spring-ai-chat-memory/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-08-10-spring-ai-chat-memory/cover.jpg)

You ask a chatbot about two plans, then follow up with "and what about the second one?" A good assistant knows what you mean. A forgetful one asks you to start over, because each request reached the model on its own, with nothing from the conversation before it.

Spring AI chat memory fixes that on the server. Spring AI keeps the recent messages for each conversation and adds them to every request, so the model sees the whole thread. In a webforJ app, the one thing left for you to decide is which conversation belongs to which user, and a session-scoped bean handles that in a few lines.

<!-- truncate -->

In this guide you'll add memory to a Spring AI chat service, give every user a conversation of their own, and decide how much history the model should see.

## Why the chat forgets {#why-the-chat-forgets}

An AI model has no memory between requests. It only knows what arrives in the current request. If the app sends just the newest message, the model gets "What about the second one?" with no earlier turns, so it can't answer.

The view can't hold the history either. Each navigation creates a new route instance, so a field in the view resets on every visit. The [Spring routing guide](/docs/integrations/spring/routing) states that each user navigation receives a clean route instance with no shared state. The history needs a home outside the view.

## Let Spring AI remember {#let-spring-ai-remember}

Spring AI ships the memory you need. A `ChatMemory` stores messages per conversation ID, and `MessageChatMemoryAdvisor` adds the stored messages to each request and saves the new ones afterwards. The service below follows the chat service in [ghost:ai](https://github.com/webforj/built-with-webforj/tree/main/webforj-ghostai), with one change: the conversation ID is a parameter instead of a fixed value.

```java
@Service
public class ChatService {
  private final ChatClient chatClient;
  private final ChatMemory chatMemory;

  public ChatService(ChatModel chatModel) {
    this.chatMemory = MessageWindowChatMemory.builder().build();
    this.chatClient = ChatClient.builder(chatModel)
        .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
        .build();
  }

  public Flux<String> stream(String conversationId, String message) {
    return chatClient.prompt()
        .user(message)
        .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
        .stream()
        .content();
  }

  public void clear(String conversationId) {
    chatMemory.clear(conversationId);
  }
}
```

Here's what each part does:

1. `MessageWindowChatMemory` keeps the most recent messages of each conversation in memory.
2. `MessageChatMemoryAdvisor` is registered once as a default advisor, so every prompt from this client uses the memory.
3. `ChatMemory.CONVERSATION_ID` tells the advisor which conversation the request belongs to.
4. `clear` forgets one conversation and leaves every other one alone.

The view never handles history. It sends the new message and a conversation ID, and Spring AI does the rest.

## Give every user a conversation {#one-conversation-per-user}

ghost:ai is a single-user demo, so it uses one fixed conversation ID for everyone. In an app with real users, a shared ID would mix everyone's messages into one thread. Each user needs an ID of their own.

A session-scoped bean is the natural place for it. The [scopes guide](/docs/integrations/spring/scopes) says each user's browser session receives its own instance of a `@WebforjSessionScope` bean. Session-scoped beans are stored in the HTTP session, so they must be `Serializable`:

```java
@Service
@WebforjSessionScope
public class UserConversation implements Serializable {
  private final String id = UUID.randomUUID().toString();

  public String id() {
    return id;
  }
}
```

The bean holds only the ID. The messages stay in Spring AI's chat memory, so the session stays small.

## Use it in the view {#use-it-in-the-view}

Spring injects both beans into the view's constructor. Every send passes the user's conversation ID, and the clear action forgets that one conversation:

```java
private void handleSend(String message) {
  currentSubscription = chatService.stream(conversation.id(), message)
      .doOnNext(chunk -> markdownViewer.append(chunk))
      .subscribe();
}

private void handleClear() {
  chatService.clear(conversation.id());
  markdownViewer.clear();
}
```

The streaming part is the same as in ghost:ai: each chunk goes into a `MarkdownViewer` as it arrives. Because the history lives on the server, a user who navigates away and comes back can keep asking follow-up questions, and the model still has the earlier turns.

## Choose the scope {#choose-the-scope}

The annotation on `UserConversation` decides who shares a conversation:

- `@WebforjSessionScope` gives one conversation per user, shared across that user's tabs. Use it for an assistant that remembers the user.
- `@EnvironmentScope` gives one conversation per browser tab. Use it for separate chat threads in each tab. Swap the annotation, and the rest of the code stays the same.

Skip [namespaces](/docs/advanced/namespaces) for this. Private namespaces share state between clients that use the same name, and a chat should stay private to one user.

## Limit the context size {#limit-the-context-size}

`MessageWindowChatMemory` keeps the 20 most recent messages of each conversation by default. One exchange is two messages, a question and an answer, so the model sees about the last 10 exchanges. To change the window, set it on the builder:

```java
this.chatMemory = MessageWindowChatMemory.builder()
    .maxMessages(10)
    .build();
```

A smaller window means shorter, cheaper requests, but the model forgets sooner. If the first message sets up the task, it drops out of the window after a while. When that matters, repeat the key instructions in a system prompt, which is sent with every request.

## Checklist {#checklist}

- The chat client registers `MessageChatMemoryAdvisor` with a `ChatMemory`.
- Every request passes a conversation ID through `ChatMemory.CONVERSATION_ID`.
- The ID comes from a `@WebforjSessionScope` bean that implements `Serializable`.
- The window size fits your model and your budget.
- A clear action calls `chatMemory.clear` for the user's conversation.

## Next step {#next-step}

Start a project with [startforJ](https://docs.webforj.com/startforj), or follow the [getting started guide](/docs/introduction/getting-started). Then read the [state management guide](/docs/routing/state-management) before you pick a scope.

## Frequently asked questions {#faq}

### Does the history survive a server restart? {#survive-restart}

Not with `MessageWindowChatMemory` on its own. It keeps the messages in memory, so a restart clears them. Spring AI lets you pass a `ChatMemoryRepository` to the builder when the history has to be stored somewhere permanent.

### Why not keep the history in a field of the view? {#why-not-a-view-field}

Each navigation creates a new view instance, so a field resets on every visit. The chat memory lives in a Spring service, outside the view, and survives those visits.

### Can two users see each other's chats? {#two-users}

Not when each user has their own conversation ID. The memory keeps every conversation separate, and the session-scoped bean makes sure each user gets a different ID. A single fixed ID, as in a demo, would put every user in one thread.

### Does the same conversation work across tabs? {#across-tabs}

Yes, with `@WebforjSessionScope`. Every tab in the same user session reads the same `UserConversation`, so all tabs share one conversation. Use `@EnvironmentScope` if each tab needs its own thread.
