---
title: "Alternatives to htmx: server-driven UI without templates"
description: "Looking at alternatives to htmx? webforJ keeps the server-driven model but replaces templates and hx- attributes with Java components. See the trade-offs and when htmx is the better fit."
slug: htmx-spring-boot-alternative
date: 2026-07-31
authors: webforJ
tags: [community, ai, spring, web development, full-stack]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-07-31-htmx-spring-boot-alternative/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-07-31-htmx-spring-boot-alternative/cover.jpg)

You have a Spring Boot app, a few server-rendered pages and a growing list of things that should react to the user without a full reload. Someone suggested htmx, and it is a fair suggestion. Then you count what each interaction needs, and the template files and fragment endpoints start to add up.

If you are weighing an htmx alternative for Spring Boot, the short answer is webforJ, the full-stack Java framework. You build each screen as a Java class made of components, handle their events in Java and inject your Spring services into the class. There are no templates and no fragment endpoints to write, so the interactive UI for Spring Boot comes without JavaScript.

<!-- truncate -->

:::tip Try a working Spring Boot app
The [webforj-crud](https://github.com/webforj/built-with-webforj/tree/main/webforj-crud) app in built-with-webforj is a Spring Boot app with a table, search, a dialog and form validation, all written in Java. It runs with Java and Maven.
:::

## What htmx gives you {#what-htmx-gives}

htmx describes itself as "high power tools for HTML". It adds AJAX, CSS transitions, WebSockets and server-sent events to HTML through attributes. Its own front page shows a button with `hx-post` that sends a request to the server and uses `hx-swap` to replace itself with the HTML that comes back. The [htmx site](https://htmx.org/) gives its size as about 16k minified and gzipped, with no dependencies.

That is a real strength. You stay close to HTML, you add one script, and a server that returns markup is all the contract you need.

The attributes are small and readable. On the htmx documentation, `hx-trigger` chooses the event that fires a request, such as `keyup changed delay:500ms` for a search box, and `hx-target` names the element that receives the response. Each one is a decision made in markup, which is exactly where it also has to stay in sync with the server.

## The pattern it implies on Spring Boot {#the-pattern}

On Spring Boot, htmx usually sits next to a template engine. A page is a template. Each interaction points at a controller method that returns a piece of that template, a fragment, and the attribute decides where the fragment lands.

For one interaction, you maintain three things:

1. The markup with its `hx-` attributes.
2. A controller method that returns the fragment.
3. An id or target on the page that the swap points to.

The three must agree. Rename an id or reshape a fragment and the breakage shows up in the browser, not in the compiler. State is the other cost. The server answers and forgets, so selection, filters and open dialogs live in the page, the URL or a session you manage.

## An htmx alternative for Spring Boot {#alternative}

webforJ moves the whole screen into Java. A view is a class, a button is an object, and a click is a listener. Spring services arrive through the constructor, as the [Spring routing guide](/docs/integrations/spring/routing) shows:

```java title="HelloWorldView.java"
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private Button btn = new Button("Generate Random Number");

  public HelloWorldView(RandomNumberService service) {
    btn.setPrefixComponent(TablerIcon.create("dice"))
        .setTheme(ButtonTheme.GRAY)
        .addClickListener(e -> {
          Toast.show("The new random number is " + service.getRandomNumber(), Theme.SUCCESS);
        });

    self.add(btn);
  }
}
```

`RandomNumberService` is a normal Spring `@Service`. The route needs only `@Route`, with no `@Controller` and no template. The click runs in Java on the server, and the framework handles the browser side. Because the screen is one class, a rename or a changed signature is a compile error.

## What you build, side by side {#side-by-side}

| Need | htmx with templates | webforJ |
|------|---------------------|---------|
| A screen | A template file plus a controller method | One Java class with `@Route` |
| Reacting to a click | `hx-` attribute plus a fragment endpoint | `addClickListener` in Java |
| Updating part of the page | A fragment and a swap target id | Change the component, the framework syncs it |
| Calling a service | Controller method calls the service | Constructor injection into the view |
| Form validation | Server checks and a re-rendered form fragment | Data binding with Jakarta Validation |
| Tables with sorting and filtering | Fragment per sort order or filter, query params, header markup | A `Table` component with a repository |
| Dialogs | A fragment loaded into a container | A `Dialog` component opened from Java |
| Refactoring | Ids and endpoints checked in the browser | Checked by the Java compiler |

## What this looks like for real screens {#real-screens}

Three screens decide most of these comparisons.

**Forms.** In webforJ you bind fields to a bean and put Jakarta Validation annotations on it. See [Jakarta Validation](/docs/data-binding/validation/jakarta-validation). The constraints and their messages live on the bean, so they stay in one place instead of in each fragment.

**Tables.** The [Table component](/docs/components/table/overview) renders rows from a collection or a repository, and a column becomes sortable with `setSortable(true)`, and filtering is set on the repository. You do not write a fragment for each sort order.

**Dialogs.** A [Dialog](/docs/components/dialog) is a component you add to a view, then open or close from a listener.

All of these [events](/docs/building-ui/events) reach your Java code, and the component state is already on the server when they do.

## When htmx fits {#when-htmx-fits}

htmx is the right pick in several cases, and we would not argue with them:

- **Content sites.** Pages that are mostly text, rendered once, with a link here and there.
- **Small interactions on server-rendered pages.** A search box that filters a list, a button that loads more, an inline edit on one row.
- **Teams that already own the templates.** If the pages exist and work, a few attributes can add the missing behavior cheaply.
- **No need for client-side state.** If each response stands alone, the stateless model suits you.

The signal that you have outgrown it is the shape of the screen. When one table needs a third fragment endpoint, or you are carrying selection in hidden fields, you are building an app screen with a page toolkit. Our post on [simple Java web development](/blog/simple-java-web-development) explains why keeping the whole app in one language pays off at that point.

## Can both live in one app? {#coexist}

Yes. You do not need to rewrite content pages to adopt webforJ. When webforJ is mapped to the root context, the `webforj.exclude-urls` property lists URL patterns it should leave alone, and Spring MVC serves those instead. The [Spring Boot guide](/docs/integrations/spring/spring-boot) describes this and the `webforj.servlet-mapping` setting. An htmx page can stay where it is while the new, interactive screens are components.

If you would rather give webforJ its own corner of the URL space, remap its servlet in `application.properties`:

```ini
webforj.servlet-mapping=/ui/*
```

This moves `WebforjServlet` from `/*` to `/ui/*` and frees the rest of the namespace for your own servlets and controllers. Leave out quotes around the value, because they are read as part of the URL pattern. See [Deploying additional servlets](/docs/configuration/deploying-additional-servlets) for the details.

## Let an AI assistant port a screen {#ai-port}

Moving a template screen to a component is mostly mechanical, which suits an AI coding agent. The webforJ AI plugin keeps the result correct. It bundles the [MCP server](/docs/ai-tooling/mcp), so the assistant checks real webforJ APIs instead of guessing, with [Agent Skills](/docs/ai-tooling/agent-skills) that hold the framework's patterns. See the [AI tooling overview](/docs/ai-tooling) to install it.

## Next step {#next-step}

Generate a Spring Boot project with [startforJ](https://docs.webforj.com/startforj), pick Spring Boot under Runs on, and rebuild your most interactive screen as a view. Compare it with the template and fragment version. The [getting started guide](/docs/introduction/getting-started) covers the first run.

## Frequently asked questions {#faq}

### What is a good htmx alternative for Spring Boot? {#faq-alternative}

webforJ is a strong choice when your screens behave like an app. You write views as Java components, handle events in Java and inject Spring services, so there are no templates or fragment endpoints. For content pages, htmx with templates remains a good fit.

### How does htmx compare with Java components? {#faq-vs-components}

htmx adds attributes to HTML that request fragments from the server and swap them in. Java components keep the screen, its state and its event handling in one class on the server. The first stays close to markup, the second stays inside the compiler.

### Can I build server-side UI for Spring Boot without templates? {#faq-no-templates}

Yes. In webforJ a route class extends `Composite`, adds components in Java and receives Spring beans through its constructor. No template files are involved.

### Can I keep htmx pages while adopting webforJ? {#faq-together}

Yes. Use `webforj.exclude-urls` to leave URL patterns to Spring MVC, so existing template pages keep working while new screens are webforJ views.
