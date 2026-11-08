---
title: "Low code to pro code in Java: visual editing with no lock-in"
description: "Go from low code to pro code in Java. craftforJ edits your running webforJ app visually and writes every change back as plain Java you own and can review."
slug: low-code-to-pro-code-java
date: 2026-09-03
authors: webforJ
tags: [community, components]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-09-03-low-code-to-pro-code-java/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-09-03-low-code-to-pro-code-java/cover.jpg)

The way to go from low code to pro code in Java without lock-in is to keep the Java as the only source of truth and let the visual tool write to it. That is how craftforJ works. It runs inside your webforJ app in development mode, lets you select components and change their properties on the live page, and writes the changes you keep into the Java file that created them. There is no separate model to export and no generated layer to fight with.

<!-- truncate -->

## The lock-in problem with low code {#the-lock-in-problem}

Low-code and visual builders solve a real problem. Seeing the screen while you shape it is faster than guessing at spacing and variants in a text editor. The trouble starts a few months later, when the app needs behavior the builder has no box for.

At that point there are usually two exits. You stay inside the tool and bend the requirement to fit it, or you leave and rebuild by hand. Either way the visual work you did earlier is stranded, because it lives in a format only that tool understands.

A low code Java UI builder is only worth having if you still end up owning an ordinary Java project that any developer can open, diff, and extend. Drag and drop for its own sake is not the goal.

## Why generated code does not fix it {#why-generated-code-does-not-fix-it}

The common answer to lock-in is "the builder generates Java code, so you can take it with you." That sounds safe and often is not. When a visual builder generates code from its own model, the model stays the source of truth. Edit the generated file by hand and the next generation can overwrite it. Leave the generated file alone and your custom logic has to live somewhere awkward beside it.

The question to ask any tool is where the truth lives after you click. If the answer is "in the tool's model," you have a dependency. If the answer is "in the Java file you already had," you have an editor.

## How craftforJ keeps Java as the source {#how-craftforj-keeps-java-as-the-source}

craftforJ starts from the other end. It does not own a model of your screen. It reads the app webforJ is already running, so the component tree it shows is the one your code built. A `Composite` appears as the class you wrote, with its children in the order webforJ holds them. The [craftforJ overview](/docs/craftforj) covers the full set of tools.

Open any route in development, select a component on the page and change a property. The running app updates straight away. Nothing in your files has changed yet, and that is deliberate. A live edit affects only the app in front of you.

<div class="videos-container">
<video controls preload="metadata">
  <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/property-edit.mp4#t=0.5" type="video/mp4"/>
</video>
</div>

## Changes reach your source as a diff {#changes-reach-your-source-as-a-diff}

To keep an edit, press <kbd>Cmd/Ctrl</kbd> + <kbd>S</kbd>. craftforJ opens a review that groups the pending changes by the file they will land in. Each one shows the property with its old and new value and expands into the diff of that file. Nothing is written until you apply, and you can revert or discard each change on its own.

<div class="videos-container">
<video controls preload="metadata">
  <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/apply-changes.mp4#t=0.5" type="video/mp4"/>
</video>
</div>

If a change would replace a computed value with a fixed one, craftforJ warns you and names the expression it is about to replace. The full flow is on the [applying changes](/docs/craftforj/source-changes) page.

Because the result is a normal edit to a normal Java file, it belongs in your pull request like any other. Keep the project in version control and read the diff before you apply it, and again before you commit.

## Where a change is written {#where-a-change-is-written}

Visual tools often struggle with reuse. If you tweak one instance of a component, did you change this screen or every screen? craftforJ makes you choose, and it shows which choice applies to each pending change:

- **The usage.** The place the component is used. Only the screen in front of you changes. This is the default.
- **The definition.** The place the component is built. Every screen that uses it changes.

Some properties can only be written at the definition, because the component sets them itself. craftforJ marks those before you apply. That is the kind of detail a pro-code workflow needs, and it falls out of the fact that your reusable components are real Java classes.

## More than properties {#more-than-properties}

The same rule applies to the other things you tune visually:

- **Routes.** craftforJ shows the routing table of the running app and can change the access rule on a route. It writes the [security annotation](/docs/security/annotations) into the route's class, such as `@RolesAllowed`, and the change goes through the same review.
- **Themes.** You adjust the design tokens your app is built from, and saving writes the theme into your app's stylesheet, where it appears in your diff and ships with the app.
- **The AI assistant.** It writes Java, compiles it before offering it to you, and applies it with the approval level you set, so what it produces is ordinary code too.

<div class="videos-container">
<video controls preload="metadata">
  <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/route-security.mp4#t=0.5" type="video/mp4"/>
</video>
</div>

<div class="videos-container">
<video controls preload="metadata">
  <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/theme-knobs.mp4#t=0.5" type="video/mp4"/>
</video>
</div>

## Low code versus craftforJ {#low-code-versus-craftforj}

| Question | Typical visual builder | craftforJ with webforJ |
| --- | --- | --- |
| Where does the truth live? | In the tool's model | In your Java files |
| What happens when you need custom logic? | Work around the tool or leave it | Write it in the same class |
| How do changes reach the project? | Regenerate or export | A diff you review and apply |
| Can a developer without the tool continue? | Depends on the tool | Yes, it is a Java project |
| Reuse | Depends on the tool | Usage or definition, your choice |

The right column describes what craftforJ is built around. It works on the Java files in your project, so the views, routes, and styles are yours whether or not the tool is running.

## Keep it out of production {#keep-it-out-of-production}

A tool that can write to your source belongs on your machine and nowhere else. craftforJ needs two separate settings to be enabled, `webforj.debug` and `webforj.devtools.craftforj.enabled`, and by default it answers only to a browser on the same machine as the app:

```ini title="webforj.conf"
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

Projects created with startforJ or a webforJ archetype enable both for you. In production, leave `webforj.devtools.craftforj.enabled` unset. You can also narrow what craftforJ may do with feature flags, for example turning off `webforj.devtools.craftforj.source-changes` so property editing stays live but never touches a file. The [security page](/docs/craftforj/security) lists what it can reach and how to confirm it is off in the builds you deploy.

## A note on restarts {#a-note-on-restarts}

Writing Java changes a file your app was built from, so the app has to rebuild before the change comes from your code. With [live reload](/docs/configuration/deploy-reload/overview) configured, that happens on its own and craftforJ reconnects with your selection and remaining pending changes intact. Without it, restart the app yourself. Live property edits never need a rebuild.

## Try it on a new project {#try-it}

Generate a project with [startforJ](https://docs.webforj.com/startforj), run it, and open craftforJ on any view. Change a property, press <kbd>Cmd/Ctrl</kbd> + <kbd>S</kbd>, and read the diff. The [getting started](/docs/craftforj/getting-started) page walks through the first run.

## Frequently asked questions {#faq}

### Can I use craftforJ as a low-code Java UI builder? {#faq-low-code-ui-builder}

craftforJ edits the UI of a running webforJ app visually and writes what you keep into your Java source. It does not replace writing the views. It removes the guesswork from tuning them, and what it produces is code your team owns.

### Does a visual tool that generates Java code lock me in? {#faq-generated-java-lock-in}

It can, when the tool's own model stays the source of truth and the Java is regenerated from it. With craftforJ the Java file is the source, and a change is a reviewed diff to that file. Your views, routes, and styles stay ordinary Java and CSS in your project.
