---
title: "Apache Wicket alternative: component based, no templates"
description: "Looking for an Apache Wicket alternative? webforJ keeps the component tree and server side Java, drops the paired HTML files, and fits Spring Boot."
slug: wicket-alternative
date: 2026-09-23
authors: webforJ
tags: [insights, modernization, spring]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-09-23-wicket-alternative/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-09-23-wicket-alternative/cover.jpg)

If your team has built with Apache Wicket, you already like the idea that a page is a tree of Java components and that the server owns the state. What you may want to change is keeping every Java class paired with an HTML file next to it. webforJ, the [full-stack Java framework](/blog/java-full-stack-framework), is an Apache Wicket alternative that keeps the component tree and the server side Java, and removes the markup files: the whole screen is Java, with modern web components, annotation based routing, Spring Boot integration and data binding.

If you plan to migrate from Apache Wicket, the work is narrower than a rewrite. You rebuild the UI in Java, keep the Java backend and the Java team, and move one page at a time. This post covers what carries over from Wicket, what disappears, and the order that keeps both apps running.

<!-- truncate -->

## What Wicket teams already know {#what-wicket-teams-know}

The Apache Wicket project describes itself as [a component-oriented Java web framework](https://wicket.apache.org/). A page is a tree of components, state lives on the server, and each page and panel keeps its markup in an HTML file that mirrors the tree. That model has real strengths: reusable pieces, server side state, and a clear place for behavior.

The trade-off is the pairing. Each page and panel has a Java class and a markup file, and the two have to agree on nesting and ids. If that pairing is the part that slows reviews and refactors, a component based Java web framework without templates is the natural next step. The [best Java web framework in 2026](/blog/java-web-frameworks-2026) depends on the job, and our guide goes through them.

## What stays the same {#what-stays-the-same}

webforJ is component based and server side too. UI code is Java that runs in the same process as your services, and the browser renders the result and sends events back. Your mental model of a page as a tree of components, each with its own state, carries over unchanged.

A reusable panel becomes a `Composite`, which wraps a bound component:

```java title="BasicComposite.java"
public class BasicComposite extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public BasicComposite() {
    // Access the bound component to configure it
    self.setDirection(FlexDirection.COLUMN)
      .setSpacing("3px")
      .add(new TextField(), new Button("Submit"));
  }
}
```

The tree is built where the component is defined, and there is no second file to keep in sync. Read more in [composing components](/docs/building-ui/composing-components).

## What changes: no markup files {#what-changes}

In webforJ the layout, the fields and the buttons are Java objects, so structure and behavior live in one place. Renaming a field is a refactor your IDE understands, and a missing component is a compile error.

A few habits go away with the markup:

- Components are Java references you hold, not ids matched across two files.
- Layout is composed from layout components instead of inherited from a parent template.
- There is no template step between your code and the page.

If you need a component that webforJ does not ship, bring in any web component through the `Element` class. See [elements](/docs/building-ui/element) for details.

## How Wicket concepts map over {#concept-mapping}

| In Wicket | In webforJ |
| --- | --- |
| Page class plus HTML file | One Java class extending `Composite` |
| Panel for reuse | `Composite` component |
| Mounting a page at a path | `@Route` annotation on the view |
| Models behind form components | `BindingContext` over a bean |
| Listeners and behaviors | Event listeners on components |
| Services injected into pages | Spring Boot constructor injection |

The mapping is conceptual. The APIs differ, so treat each row as a place to look in the docs, not a rename.

## Forms and Spring services {#forms-and-spring}

For forms, `BindingContext` links bean properties to UI components and handles reads, writes, validation, and transformation. If you are used to models behind form components, this is the closest match. See the [data binding docs](/docs/data-binding/overview).

With Spring Boot, your webforJ app runs as an executable JAR with an embedded Tomcat server, and you inject Spring beans into views through constructor injection. A screen calls your service layer directly. The [Spring Boot docs](/docs/integrations/spring/spring-boot) walk through generating a project with startforJ.

## Port panels before pages {#migration}

You do not need a rewrite on day one. Because shared panels are what most Wicket pages are built from, the order matters:

1. Create a webforJ Spring Boot project with [startforJ](https://docs.webforj.com/startforj).
2. Move business logic into Spring services if it is not there yet, so both apps call the same code.
3. Port the shared panels first, each as one `Composite`.
4. Port a self contained page that uses them, such as a settings screen, and give it a `@Route`.
5. Replace the form model with a `BindingContext` and port the validators.
6. Route traffic to the new screen, retire the old page, and repeat.

Porting a panel once means every page that uses it benefits. It also keeps the first migrated pages small, because they mostly assemble components that already exist. Avoid porting a page and its panels in the same step, since two moving parts make a review harder.

## Reviewing AI-drafted panels {#ai-port}

Some teams let an AI coding assistant draft the `Composite` for each panel. That only helps when the draft is checked against the framework it targets: webforJ API lookups from the real docs, a compiler on each edit, and a test that renders the panel. [The AI verification loop for Java](/blog/ai-verification-loop-java) explains those checks. The review of each panel stays with your team.

## Is webforJ right for your team? {#choosing}

webforJ fits when:

- You want Java as the only language for the UI.
- The Java and HTML pairing costs you time in reviews.
- You run or plan to run Spring Boot and want screens to call services directly.

Staying put can be right when the app is stable and no screen is changing soon. Moving one screen at a time lets you test the fit before committing to more.

## Rebuild one small page {#next-step}

Generate a Spring Boot project with [startforJ](https://docs.webforj.com/startforj), or follow [getting started](/docs/introduction/getting-started), then rebuild one small page as a `Composite` view.

## Frequently asked questions {#faq}

### Is webforJ component based like Wicket? {#faq-component-based}

Yes. A screen is a tree of Java components that run on the server, and you reuse pieces by wrapping them in a `Composite`. The difference is that the tree is the only artifact, with no HTML files to pair with it.

### Do I need to rewrite my whole Wicket app at once? {#faq-rewrite}

No. Move one screen at a time, keep your existing Spring services, and retire old pages as their replacements go live.

### How do components link up without markup ids? {#faq-ids}

You create a component, keep it in a field, and add it to a layout. The compiler checks that link, so there is no id to mismatch.

## Plan your Wicket migration with us {#plan-your-migration}

Once a page or two runs in webforJ, [see how webforJ moves Java apps to the web](/modernization) and talk to us about the rest of your Wicket app.
