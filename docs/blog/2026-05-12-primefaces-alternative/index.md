---
title: "PrimeFaces alternative: rich Java components, no XHTML"
description: "Looking for a PrimeFaces alternative in Java? Move your JSF screens to webforJ and keep a rich component set, data binding and Spring Boot without XHTML views."
slug: primefaces-alternative
date: 2026-05-12
authors: webforJ
tags: [insights, modernization, spring]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-05-12-primefaces-alternative/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-05-12-primefaces-alternative/cover.jpg)

Your team has a JSF app with a rich component library on top. The data tables, dialogs and forms all work, but every screen means an XHTML file, a managed bean and a set of `update` attributes that decide which part of the page refreshes. Adding a screen feels heavier than it should, and nobody on the team wants to change the view layer more than necessary.

If you are searching for a **PrimeFaces alternative** that keeps the rich component idea and drops the XHTML, webforJ, the full-stack Java framework, is our answer. You build every screen as plain Java classes with a ready set of components (Table, Dialog, Drawer, form fields with data binding), route to them with an annotation, and plug the result into Spring Boot when you have it.

<!-- truncate -->

:::tip See a table with a dialog form
The [webforj-crud](https://github.com/webforj/built-with-webforj/tree/main/webforj-crud) app in built-with-webforj shows a music artist table with search, a dialog that adds, edits and deletes entries, data binding and validation on Spring Boot. It runs with Java and Maven.
:::

## Why teams look for a replacement {#why-teams-look}

A component library on JSF gives you a lot of widgets quickly. The cost shows up in the structure around them:

- Each screen is split across a Facelets file and a bean, and the link between the two is an expression string.
- Refreshing part of a page means naming component ids in an `update` attribute.

None of this is a defect in the idea of rich components. It is a consequence of splitting a screen into markup and Java. webforJ keeps the rich components and removes the split, because the screen is one Java class.

## A rich component set in Java {#rich-component-set}

webforJ ships components you would otherwise pull from a component library. The ones that most JSF screens need are all there:

- A [Table](/docs/components/table/overview) with sorting, filtering, row selection, custom cell renderers and large data handling.
- Dialog and Drawer components, plus option dialogs for message, confirm and input prompts.
- Form fields, a ComboBox, a DateField, checkboxes, radio groups and an upload component.
- Layouts such as FlexLayout, columns layout, tabbed pane and an app layout with a header, footer and drawer.
- Toasts, alerts, a tree and a toolbar for the pieces that make an app feel finished.

You create them with `new`, set their properties with method calls and add them to a layout. There is no tag library to look up and no expression language between your code and the widget.

## Concept map: JSF screen to webforJ {#concept-map}

This is the table we hand to teams who plan a port. It maps the pieces of a JSF screen to what takes their place.

| In a JSF screen | In webforJ |
|---|---|
| XHTML page | A Java class that extends `Composite` |
| Page URL and navigation rules | `@Route` annotation on the view |
| Managed bean or CDI bean holding screen state | Fields on the view, plus a Spring bean for services |
| Injected service in a bean | Constructor injection of a Spring bean |
| Component tag in the page | Component object created in Java |
| Data table tag with columns | Table with columns and a repository |
| Dialog or sidebar tag | Dialog or Drawer component |
| Input tags with a value expression | Form fields with a `BindingContext` |
| Validation attributes on the bean | Bean validation annotations on the model |
| `update="..."` after an AJAX call | Direct calls on the component, for example a new value or a table refresh |
| Theme and style classes | CSS design tokens and a theme |

The row that surprises people most is the `update` one. In webforJ the UI lives on the server and each component is an object you hold a reference to. When data changes you call a method on that object and the browser reflects it. There is no list of ids to keep in sync.

## What a ported view looks like {#ported-view}

A route is a class with an `@Route` annotation. The framework renders it whenever the URL matches. Route patterns can carry named parameters such as `:id`, so a screen that took a request parameter in JSF takes a route parameter here. The [routing documentation](/docs/routing/overview) covers patterns, aliases, nested layouts, and navigation lifecycle hooks.

Forms follow the same idea as input tags with value expressions, with a typed model behind them. You create a `BindingContext` for your bean, call `read` to fill the form and `write` to push the values back and get a validation result:

```java
BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
Hero bean = new Hero("Superman", "Fly");

// reflect the bean data in the form
context.read(bean);
```

Validation rules sit on the model as annotations such as `@NotEmpty` and `@Length`, so they travel with the data instead of the page. The [data binding guide](/docs/data-binding/overview) has the complete example.

## Theming with design tokens {#theming}

Appearance is controlled through CSS custom properties. Each color palette exposes a seed variable, so changing the primary color for the whole app means overriding one variable with any valid CSS color:

```css
:root {
  --dwc-color-primary-seed: #6366f1;
}
```

For a team coming from a component library theme, that replaces a theme build step with a few lines of CSS. The [styling guide](/blog/guide-webforj-themes) in our blog walks through themes in more depth.

## Spring Boot integration {#spring-boot}

Teams on JSF often run CDI or Spring services behind their beans. Those services do not need to change. With Spring Boot, webforJ views take Spring-managed beans through constructor injection, so a repository or service class you already have plugs into the new view directly. Setup details are in the [Spring integration docs](/docs/integrations/spring/overview).

## Migrate screen by screen {#migrate-screen-by-screen}

You do not need a big-bang rewrite. A workable order:

1. Pick one screen with moderate complexity, such as a list with an edit form.
2. Create the webforJ view with a route, a Table and a Drawer or Dialog for the edit form.
3. Reuse your existing service layer through constructor injection.
4. Bind the form fields to the model you already have.
5. Replace each `update` target with a direct call on the component that changed.
6. Repeat for the next screen and retire the XHTML page and its bean when the new one is live.

A first screen gives your team the pattern, and every later screen goes faster because the concept map in the preceding section does not change.

## Let an AI assistant do the port {#ai-port}

Porting screens is repetitive work that an AI coding assistant handles screen by screen. Give it one XHTML page and its bean and ask for the webforJ view. How reliable the result is depends on what the assistant knows about the framework.

The [AI plugin](/docs/ai-tooling) connects the assistant to the current webforJ documentation, API signatures and code samples through an [MCP server](/docs/ai-tooling/mcp), and ships [Agent Skills](/docs/ai-tooling/agent-skills) for tasks such as building forms with data binding. With those in place the assistant looks up real class names and method signatures instead of guessing them. You still review each ported screen.

## Decision guide {#decision-guide}

| If your situation is | Then |
|---|---|
| Many screens, each a table plus an edit form | Strong fit. Table, Drawer and data binding cover this directly |
| Heavy business logic in services behind the beans | Strong fit. Keep the services and inject them in Spring Boot |
| Frequent partial page refreshes through `update` | Strong fit. Direct component calls replace the id lists |
| A single small page that rarely changes | Leave it until you edit it for another reason |
| A team that wants to keep writing markup views | Consider whether the markup is the problem you want to solve |

If the pain is the markup and bean pairing on each screen, Java views remove the cause.

## Start with one screen {#next-step}

Generate a project with [startforJ](https://docs.webforj.com/startforj), or follow the [getting started guide](/docs/introduction/getting-started), and rebuild the screen that annoys your team most. One working table with a drawer form is enough to judge the approach on your own code.

## Frequently asked questions {#faq}

### What is the best PrimeFaces alternative for Java teams? {#faq-best-alternative}

If you want to keep a rich component set and drop XHTML views, webforJ is built for that. You get Table, Dialog, Drawer and bound forms as Java classes, with your Spring Boot services plugged in.

### Can I migrate from PrimeFaces one screen at a time? {#faq-migrate}

Yes. Each screen maps to one webforJ view with its own route, so you can port them in any order. Services you already have stay in place and are injected into the new views.

### Is there a modern PrimeFaces replacement that works with Spring Boot? {#faq-spring-boot}

webforJ integrates with Spring Boot and takes Spring beans through constructor injection. A PrimeFaces replacement built with it lets your repositories and services keep their current shape while the UI moves to Java.
