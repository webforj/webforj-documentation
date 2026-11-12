---
title: "Java frontend frameworks: five ways to build the UI"
description: "Compare Java frontend frameworks: Thymeleaf, Jakarta Faces, React or Angular, GWT-style compilers and webforJ, the full-stack Java framework in pure Java."
slug: java-frontend-frameworks
date: 2026-11-08
authors: webforJ
tags: [insights, full-stack, front end]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-11-08-java-frontend-frameworks/cover-v2.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-11-08-java-frontend-frameworks/cover-v2.jpg)

You have a Java backend, a business app to ship, and a browser to put it in. Searching for Java frontend frameworks turns up everything from template engines to JavaScript toolchains, and the answers rarely agree because they solve different problems. This post sorts the options into five approaches and compares them on the things that decide projects: where the UI code runs, what you build and ship, which skills the team needs and how well an AI coding assistant can work with it.

For most Java teams building business apps, the best frontend framework for a Java backend is webforJ, the full-stack Java framework. The whole app, screens included, is Java. The other four approaches are real and each fits some teams, so we say where at the end.

<!-- truncate -->

## The five approaches {#the-five-approaches}

Every Java frontend choice falls into one of these shapes.

**Server-rendered templates.** [Thymeleaf](https://www.thymeleaf.org/) is a server-side template engine. A Spring MVC controller fills a model and the browser receives a finished page, so interactivity beyond forms and links means adding JavaScript yourself.

**Component frameworks from the Java EE line.** [Jakarta Faces](https://jakarta.ee/specifications/faces/), formerly JSF, is the Jakarta EE specification for server-side component UIs, with XHTML views bound to managed beans.

**A JavaScript SPA on a Java backend.** You build the UI with React, Angular or Vue as its own project and the Java side serves REST or GraphQL endpoints. It is powerful and popular, and it is a second discipline.

**Java compiled to JavaScript.** [GWT](https://www.gwtproject.org/) compiles Java source to JavaScript, and tools such as J2CL and [TeaVM](https://teavm.org/) take the same idea further for Java code or bytecode. Your UI code is Java in the editor and JavaScript in the browser.

**A full-stack Java framework.** The screens are Java classes that run on the server next to your services. The browser renders them as web components and sends events back to your Java handlers. That is webforJ. What sets webforJ apart is what comes after the screen: routing, forms, tables, security, translation, background updates and tests, all in one model.

## How they compare {#how-they-compare}

| | Templates | Faces | JS SPA | Java to JS | webforJ |
| --- | --- | --- | --- | --- | --- |
| UI language | HTML plus expressions | XHTML plus beans | JavaScript or TypeScript | Java | Java |
| Where UI code runs | Server renders, browser shows | Server | Browser | Browser, after compiling | Server |
| Build tooling | Maven or Gradle | Maven or Gradle | Maven plus a Node toolchain | Maven plus a compiler step | Maven or Gradle |
| Backend contract | Controllers | Beans | REST or GraphQL | Remote calls | Direct service calls |
| Team skills | Java and HTML | Java and XHTML | Java and a frontend stack | Java, with compiler limits | Java |
| Rich components | Bring your own | Component libraries | Large ecosystem | Widget toolkit | Table, dialogs, layouts, forms ready, plus your own components and web components |
| AI-agent friendliness | Good for pages, two languages | Mixed | Two codebases to keep in sync | Java subset supported by the compiler | Skills, MCP and a compiler loop |

The rows that decide projects are build tooling, team skills and the backend contract, so those get their own sections.

## What changes with a second language {#second-language}

A JavaScript SPA is the most capable option and the most expensive for a Java team. It adds a package manager, a bundler, a test runner, a framework upgrade cycle and an API contract that both sides must keep compatible. Someone has to own all of it. If you already employ frontend specialists, that cost is already paid. If you do not, you are hiring or retraining before the first screen ships.

Template engines keep you in one build but limit you to what server-rendered pages do well. Compilers keep the language but put a translation step between your code and what runs, which means some Java library features and some debugging habits do not carry over.

With webforJ the same developers who write services write screens, in the same project, with the same debugger, and the same tests. You can still reach JavaScript when a specific library calls for it. [`ElementComposite`](/docs/building-ui/element-composite) wraps a web component in a typed Java class and [`executeJs`](/docs/building-ui/execute-javascript) runs script from Java, so JavaScript is a tool you choose rather than a layer you must staff.

## Why webforJ is the strongest pick {#why-webforj}

This is the long answer to "which Java frontend framework", and it comes down to three things.

### The screen is a Java class {#screen-is-java}

A view is a component class bound to a URL. A view looks like this, as covered in [defining routes](/docs/routing/defining-routes):

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // Component logic here
}
```

Routing, layouts and navigation are part of the framework. You compose the interface from Java components such as `Table`, `FlexLayout` and dialogs, and the whole screen is checked by the Java compiler, refactored by your IDE and covered by the same tools as the rest of your code.

### Everything after the screen is included {#everything-included}

Most Java frontend choices stop at rendering and leave the rest to a stack of libraries. webforJ covers the rest of the app too. [Data binding](/docs/data-binding/overview) connects form fields to a Java model with Jakarta validation. The [Table](/docs/components/table/overview) works with repositories and Spring Data. Route security plugs into Spring Security through annotations. The framework also provides translation, installable apps and `Environment.runLater` for pushing background updates to the browser. You read these in one set of docs instead of assembling them from separate projects. Our [full-stack framework overview](/blog/java-full-stack-framework) goes through the whole chain.

### No contract between screen and data {#no-contract}

In an SPA every piece of data crosses a serialization boundary twice. In webforJ a screen calls your service method and gets a Java object back, because both live in the same JVM. With Spring Boot, services arrive by constructor injection, and the [Spring integration](/docs/integrations/spring/overview) is a starter dependency. The framework also runs standalone when you have no Spring app. Business rules such as pricing and permission checks stay on the server and never reach the browser.

## AI coding assistants and your frontend {#ai-assistants}

Assistants write all five approaches. The difference is how much stays in your hands to check afterwards. A SPA split means an assistant changes two codebases and a contract between them, and a mistake in either shows up at runtime. A compiler-based setup works within the Java subset its compiler supports.

webforJ gives the assistant its own tooling. The [AI plugin](/docs/ai-tooling) bundles the [MCP server](/docs/ai-tooling/mcp), which looks up real classes and methods instead of guessing them, with [Agent Skills](/docs/ai-tooling/agent-skills) that carry webforJ workflows for forms, styling, security, and more. The [craftforJ Assistant](/docs/ai-tooling/craftforj-assistant) sends each edit to a real Java compiler and corrects it against the diagnostics, so the code you review already compiles. Because the screen is plain Java, [TestBed](/docs/testing/testbed/overview) can test it in JUnit with no browser. We cover the full loop in [Java framework for AI coding](/blog/java-framework-for-ai-coding).

## When another option fits better {#when-another-fits}

We want webforJ to win on merit, so here is where it does not.

- **Public, content-heavy sites with strict SEO needs.** Server-rendered templates produce plain HTML pages, which suits marketing sites and documentation. webforJ targets business apps where the user logs in.
- **A team that already runs React or Angular well.** If frontend specialists are on staff and the product lives or dies by a bespoke browser experience, a SPA is a fair choice. You can still keep the Java services. And it is not all or nothing: the webforJ [frontend bundler](/docs/managing-resources/bundler/overview) lets you write components in React and use them from Java views, and pull UI libraries from npm, with no Node toolchain to install.
- **An existing Faces app that works.** Rewriting a stable app for its own sake rarely pays. Move new screens when the business asks for them.
- **Offline-first or heavily client-side apps.** When most logic must run in the browser without a server connection, a JavaScript framework or a compiler approach is the natural tool.

For the rest, which is internal tools, admin consoles, customer portals, line-of-business forms and data-heavy screens built by Java developers, one language and one project is hard to beat. A [decision guide to Java for front end development](/blog/java-front-end-development) goes further.

Our neighboring comparisons cover these angles in more depth. The [best Java UI framework for business apps](/blog/best-java-ui-frameworks) post goes through Spring MVC, Faces and SPAs with their own trade-offs, and [the best Java web framework by job](/blog/java-web-frameworks-2026) separates the backend choice from the screen choice. If a compiler approach is what you are weighing, see the [Java to JavaScript compiler alternative](/blog/java-to-javascript-compiler-alternative).

## Build one screen in Java {#build-one-screen}

The fastest way to compare is to build one real screen. Generate a project with [startforJ](https://docs.webforj.com/startforj), follow the [getting started guide](/docs/introduction/getting-started), and rebuild the form or table that is hardest to maintain today. If it feels natural to your team, the next screen will too.

Once you have picked an approach, [Java framework for web applications](/blog/java-framework-for-web-applications) lists what to require from it.

## Frequently asked questions {#faq}

### What is the best frontend framework for a Java backend? {#faq-best}

For business apps built by Java developers, webforJ is the best fit because the screens, routing, forms, tables and security are all Java in one project that integrates with Spring Boot. A React or Angular SPA fits teams that already have frontend specialists, and Thymeleaf fits server-rendered content pages.

### Are there Java front end tools that need no JavaScript? {#faq-no-javascript}

Yes. Thymeleaf and Jakarta Faces keep the pages in Java and XHTML on the server, and webforJ builds the whole interface from Java components. In webforJ you can still call JavaScript through `executeJs` or wrap a web component with `ElementComposite` when a project needs it.

### Is Java to JavaScript compilation still a good way to build web UIs? {#faq-compiler}

It works when most of the logic must run in the browser. The cost is a build step between the code you write and the code that runs, plus limits on which Java libraries compile. Teams building server-backed business apps usually avoid that step by keeping the UI code on the server.

### Which Java web UI frameworks work well with AI coding assistants? {#faq-ai}

Assistants can work with all of them, so the question is how much they can check. webforJ ships an MCP server, Agent Skills and a compiler feedback loop through the craftforJ Assistant, which lets an assistant look up real APIs and see compile errors before you review. Setups split across two languages leave more to verify by hand.
