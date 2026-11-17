---
title: "Eclipse RAP alternative: move SWT screens to webforJ"
description: "Looking for an Eclipse RAP alternative? A decision guide for teams with an SWT web UI: when to stay, and how to move screens to webforJ one at a time."
slug: eclipse-rap-alternative
date: 2026-09-26
authors: webforJ
tags: [insights, modernization, spring]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-09-26-eclipse-rap-alternative/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-09-26-eclipse-rap-alternative/cover.jpg)

Your team has a business app that runs in the browser through Eclipse RAP. The screens are written with the SWT and JFace API, the developers who built it know Java well, and now someone is asking what comes next. If you are weighing an Eclipse RAP alternative, webforJ, the full-stack Java framework, keeps your team in Java: screens are Java objects with listeners, they render as modern web components, and the whole app can plug into your Spring Boot services.

This guide helps you decide whether to stay or move, maps the concepts you already know to their webforJ counterparts, and lays out a plan to migrate an Eclipse RAP app one screen at a time.

<!-- truncate -->

## What RAP gives you today {#what-rap-is}

RAP is built for modular business apps on desktop, browser and mobile, and you can write the whole app in Java. You build the UI with the SWT API, and JFace viewers simplify access to complex data models.

The part of RAP that does the browser work is RWT. RWT largely implements the same API as the Standard Widget Toolkit (SWT), so code that compiles against it generally works, and it ships more than 40 widgets with their events and layout managers. Appearance is controlled with CSS theming. RAP runs on any JEE servlet container, integrates with OSGi and can also be used stand-alone.

This is the reason RAP appeals to desktop Java teams. The same SWT knowledge that built the Eclipse-based client now builds the web version, and code written for Eclipse RCP can run in a browser. For the desktop toolkits side, see [Java GUI frameworks compared](/blog/java-gui-frameworks-compared).

## When staying on RAP makes sense {#stay}

A move has a cost, so start with the honest cases for staying put:

- **You single-source one code base for RCP and the browser.** RAP's pitch is reuse across both targets. If your users still need the desktop client from the same code, a web-only framework removes that reuse.
- **The app is stable and the team is small.** If screens rarely change and nobody is waiting on new features, the best migration is the one you postpone.
- **Your architecture is built around OSGi bundles.** Moving means leaving that packaging for a Maven project, and that work is real even when the screens are simple.
- **Your UI depends on SWT-specific behavior.** Custom drawing on a Canvas or heavy use of a rarely used widget deserves a careful review before you commit.

If none of these describe you, or if one of them is about to stop being true, keep reading.

## Signs it is time to move {#signs}

Teams start looking at an alternative when the app has to grow in directions the original design did not plan for. These are the usual triggers:

- The business wants new screens quickly and the UI no longer looks like the rest of the company's web products.
- The backend has moved to Spring Boot and you want the UI to call those services directly instead of through an extra layer.
- New hires know Maven and Spring, and fewer of them know OSGi and Eclipse tooling.
- You want an AI coding assistant to help with the port and with new screens.

None of these says RAP is broken. They say the app needs a different base for the next five years.

## Why webforJ fits a Java team {#webforj}

webforJ is built for the same kind of team RAP serves: Java developers building business apps. You write the screens in Java, the code runs on the server, and the browser renders it with modern web components. webforJ comes from BASIS International and covers the whole app.

Three properties matter for a RAP team:

- **The programming model feels familiar.** A screen is a Java class, a button is a Java object, and behavior is a listener. You read the [events guide](/docs/building-ui/events) in an afternoon.
- **Everything stays in Java.** There is no JavaScript to write, no template language and no REST layer for the UI. The [Spring Boot integration](/docs/integrations/spring/spring-boot) lets a screen receive a service through its constructor.
- **Screens are routes.** A `@Route` class owns a URL, so you can ship one migrated screen while the rest stay where they are. The [full-stack Java framework post](/blog/java-full-stack-framework) covers the rest of the stack, from data binding to route security.

## How SWT concepts map to webforJ {#concept-mapping}

The names change and the shape stays recognizable. This table is a starting point, not a one-to-one translation.

| In your RAP app | In webforJ |
| --- | --- |
| A screen built from composites | A class that extends `Composite`, see [composing components](/docs/building-ui/composing-components) |
| A button with a listener | [`Button`](/docs/components/button) with `onClick(...)` |
| A text field and its modify handling | [`TextField`](/docs/components/fields/textfield) with `addValueChangeListener(...)` |
| Layout managers | [`FlexLayout`](/docs/components/flex-layout) and [`AppLayout`](/docs/components/app-layout) |
| A table fed by a JFace viewer | [`Table`](/docs/components/table/overview) fed by a list or a repository |
| A tree widget | [`Tree`](/docs/components/tree) |
| A modal dialog | [`Dialog`](/docs/components/dialog) and the [option dialogs](/docs/components/option-dialogs/overview) |
| CSS theming | [webforJ styling](/docs/styling/overview) with CSS variables |

One difference deserves attention. In RAP's JEE compatibility mode, dialogs do not block execution, and you read the result through the RWT-only `open(DialogCallback)` method. In webforJ, the [option dialogs](/docs/components/option-dialogs/overview) are modal and the server stops processing further requests until the dialog is dismissed or returns a value. If your code was reshaped around callbacks, that code can often become straight-line Java again.

## What a screen looks like {#screen}

Listeners work the way an SWT developer expects:

```java
Button button = new Button("Save");
button.onClick(event -> {
  // Handle the click
});
```

Each component exposes a listener method and, for most events, a shorter `on` alias. Adding a listener returns a registration you can keep to remove it later. There is no widget parent to pass at construction time and no separate layout data object. You add components to a layout and the layout arranges them.

## Move screens in five steps {#migration}

Treat the move as a screen-by-screen rewrite of the UI while the business logic stays as it is.

1. **Create the webforJ app beside the RAP one.** Generate a Spring Boot project with [startforJ](https://docs.webforj.com/startforj). Point it at the same database and reuse your service classes, or move them into a shared module.
2. **Pick one screen.** A read-only list or a small form is a good first target. Rebuild it with a `Table` or a `FlexLayout` and call the service directly.
3. **Convert listeners one for one.** A selection listener on a button becomes `onClick`. Modify handling on a text field becomes a value change listener.
4. **Replace CSS theming last.** RAP themes are CSS, and webforJ styling is CSS too, so the visual rules carry over in spirit. Start with the defaults and bring brand colors in once the screens work.
5. **Move users over.** Run both apps during the transition and link between them by URL until the last screen is done.

A team that tries the first screen this way learns more in a day than a long comparison document would tell it.

## Where AI helps rebuild a screen {#ai}

Converting listeners one for one is the most mechanical part of the plan, and an SWT screen has a regular shape: widgets, layout, listeners. That makes it a good brief for an AI coding assistant, as long as you hand it one RAP screen and the service it calls and review what comes back. The assistant writes a new webforJ screen, and the [webforJ AI plugin](/docs/ai-tooling) lets it check each class against the real webforJ API instead of guessing. Ask for a test with each screen as well, so it arrives with a pass or a fail. That cycle is [the AI verification loop for Java](/blog/ai-verification-loop-java).

## Which path fits your team {#choosing}

Stay on RAP if you still ship the same code to an RCP desktop client, or if the app is stable and the team has no pressure to change it. Move to webforJ if you want one Java language and one Maven build from the database to the browser, a UI that calls Spring services directly, and a migration you can do a screen at a time. If your team also maintains a GWT app, the [GWT alternative post](/blog/gwt-alternative) walks through the same decision for another Java web UI toolkit.

To try it, generate a project with [startforJ](https://docs.webforj.com/startforj) or follow the [getting started guide](/docs/introduction/getting-started). Then rebuild one list screen from your RAP app and compare the two.

## Plan the move off RAP {#plan-your-move}

To plan the screen order for your RAP app and talk to our team about it, see the [modernization path](/modernization).

## Frequently asked questions {#faq}

### What is the best Eclipse RAP alternative for a Java team? {#faq-best}

For a team that wants to keep writing the whole app in Java, webforJ is a full-stack Java framework worth evaluating. Screens are Java classes with listeners, they render as web components, and the app integrates with Spring Boot.

### Is webforJ an SWT web UI alternative? {#faq-swt}

It is a Java framework with its own component set, not an SWT implementation. The concepts carry over, so you rebuild each screen with webforJ components instead of porting SWT code unchanged.

### Can I move from RAP to webforJ gradually? {#faq-gradual}

Yes. Each screen is a route with its own URL, so you can run the RAP app and the webforJ app side by side and link between them while you migrate. Your service classes stay as plain Java.

### Do I need to learn JavaScript? {#faq-javascript}

No. You build the UI in Java, the same language your RAP team already uses. The browser renders the components and sends events back to your Java code.
