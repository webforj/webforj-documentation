---
title: "Edit a running Java app with AI in craftforJ"
description: "Use the craftforJ AI assistant to edit a running Java app from a prompt. It writes the Java, compiles each change, and applies it after you approve."
slug: ai-visual-editor-java
date: 2026-10-22
authors: Matthew Hawkins
tags: [ai]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-10-22-ai-visual-editor-java/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-10-22-ai-visual-editor-java/cover.jpg)

You can edit a running Java app with AI in webforJ by using the craftforJ AI assistant. The assistant works inside the app that is open in your browser. It writes the Java for a change, compiles that Java before you see it, and applies the change to your source once you approve it. You describe the change in plain language, and the assistant works from the live components rather than a guess made from your files.

This post walks through how that loop works. You will turn on the two settings that enable craftforJ, pick a component in the running app, and see what happens when you ask the assistant for a change.

<!-- truncate -->

## Can an AI assistant edit a live UI? {#can-an-ai-assistant-edit-a-live-ui}

craftforJ is the visual development environment that ships with webforJ. It runs inside your app in development mode and reads the app through webforJ itself. The component tree lists the components your Java code created, the properties are the ones those components declare, and the routes are the ones your router registered.

With craftforJ you can select a component, change one of its properties, and watch the running app update straight away. Property edits stay in the running app until you choose to write them to your source. The AI assistant works one level up. It is a coding agent that reads the same live tree, and when a property change is not enough, it writes Java. Ask it to add a method, change some logic, or restructure a view, and it does that work against the app running in front of you.

The [visual UI designer post](/blog/java-visual-ui-designer) covers the inspector side of the same tool. This one stays with the assistant.

## Set up the first run {#set-up-the-first-run}

craftforJ needs the `webforj-devtools` dependency and two settings before it responds. The settings go in `webforj.conf`, or in `application.properties` on Spring:

- `webforj.debug`
- `webforj.devtools.craftforj.enabled`

Neither setting does anything alone. Projects created with [startforJ](https://docs.webforj.com/startforj) or from a webforJ archetype have both enabled, so the first run already works. By default, only a browser on the same machine can reach craftforJ. To allow another machine, list its address in [`webforj.devtools.craftforj.hosts-allowed`](/docs/craftforj/configuration#access).

Start the app and press <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> to open craftforJ. Turn on pick mode and click any component on the page to select it in the inspector. The tree shows the components your Java code created, in the order it created them.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/pick-mode.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

## Ask for a change in plain language {#ask-for-a-change-in-plain-language}

Open the [assistant](/docs/craftforj/ai) and describe the change the way you would to a colleague, for example: show the total as a dollar amount. The assistant looks at the live components, plans the change, and writes the Java for it. Expect a small, focused edit, about the size you would make by hand, rather than a rewrite of the view.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/ai-conversation.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

## How does the compile gate work? {#how-the-compile-gate-works}

Every edit the assistant writes is staged rather than saved to disk. Staged edits go to a real Java compiler. The assistant reads the diagnostics that come back and fixes its own mistakes before the change reaches you. What you review has already compiled against your running app.

Full validation needs a JDK. On a JRE, craftforJ parses the code instead, marks the edit as unverified, and the assistant is told to say so rather than present the change as checked. Look for that marker before you keep a change.

The compile gate catches code that does not compile. It does not decide whether the change is what you meant, so read what the assistant did before you keep it.

## When does a change reach your files? {#when-a-change-reaches-your-files}

Changing a property touches only the running app. Writing to source is a separate step. Press <kbd>Cmd/Ctrl</kbd> + <kbd>S</kbd> to open the review. Changes are grouped by the file they land in, and each one expands into its diff. Nothing is written until you apply. What lands in your files is plain Java, which is why [visual editing here does not lock you in](/blog/low-code-to-pro-code-java).

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/apply-changes.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

Applying a change restarts the app. The assistant waits for the restart, reconnects, and picks its plan up where it left off, so a task that spans several edits and restarts still finishes. Keep your work in version control before you apply, and read the diff again before you commit.

## How much can the assistant do alone? {#how-much-it-can-do-alone}

You set the limit before the assistant starts. There are three modes:

- **Ask before acting** stops for approval on every action that has an effect.
- **Apply edits automatically** lets the assistant work freely, but it still asks before it removes something or runs a script.
- **Run autonomously** lets it work without stopping.

Start with the first mode. Once you have watched the assistant work, the second mode removes most of the interruptions and keeps the decisions that matter with you.

## Where does the model key go? {#where-the-model-key-goes}

craftforJ ships no model of its own. You add an API key for a supported provider, or point craftforJ at a model running on your machine. The key is stored on the machine running your app. The assistant holds it in memory while the page is open and never writes it to browser storage. It talks to the provider from the browser, not through your server.

What reaches the provider is the conversation. That includes the parts of your app the assistant looked at and any screenshots it took. A screenshot shows whatever data is on screen, so think before you point a hosted model at an app running against real data. A local model keeps everything on your machine, but local models often start with a small context window, so give it as much room as your hardware allows.

## Frequently asked questions {#faq}

Start with a project from [startforJ](https://docs.webforj.com/startforj), which enables craftforJ from the first run. Then open the [AI assistant page](/docs/craftforj/ai), choose your model, and set the approval mode before you ask for anything.

### Can craftforJ run in production? {#can-craftforj-run-in-production}

No, and it is off unless you turn it on. Before you deploy, make sure `webforj.devtools.craftforj.enabled` and `webforj.debug` are unset or `false` in the configuration you deploy. The [production checklist](/docs/craftforj/security#in-production) lists the remaining steps.

### How do I stop the assistant from writing Java? {#stop-the-assistant-writing-java}

Set `webforj.devtools.craftforj.ai.freeform-changes` to `false`. The assistant stays available, but it stops writing Java of its own. To remove the assistant entirely, set `webforj.devtools.craftforj.ai.enabled` to `false`. The [configuration page](/docs/craftforj/configuration#feature-flags) describes each flag.

### Do I need a JDK for this? {#do-i-need-a-jdk}

You need one for full checks. Without a JDK, craftforJ still runs, but the assistant parses edits instead of compiling them, and it marks those edits as unverified. A JDK lets every staged edit pass through a real compiler before you see it.

### Can I move a conversation to my editor? {#move-a-conversation-to-my-editor}

Yes. When the work outgrows craftforJ, summarize the conversation and hand it to your editor's assistant. With the [webforJ AI plugin](/docs/ai-tooling) installed, that assistant picks up the work more accurately. The [AI coding assistant post](/blog/ai-coding-assistant-java) compares the two.
