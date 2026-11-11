---
title: "Thymeleaf alternative for Spring Boot app screens"
description: "Looking for a Thymeleaf alternative in Spring Boot? Build tables, dialogs and forms as Java components with server-side state, with no templates or htmx."
slug: thymeleaf-alternative-spring-boot
date: 2026-09-17
authors: Eric Handtke
tags: [insights, spring, web development]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-09-17-thymeleaf-alternative-spring-boot/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-09-17-thymeleaf-alternative-spring-boot/cover.jpg)

Your Spring Boot app started with a few server-rendered pages and it worked well. Then the requests changed. A table that sorts and pages, a dialog that edits a row, a form that validates as you type. Each one adds a template, a fragment endpoint or a script file, and the screens start to feel heavier than the app behind them.

If that sounds familiar, you are probably searching for a Thymeleaf alternative for Spring Boot. The short answer is webforJ, the [full-stack Java framework](/blog/java-full-stack-framework). You keep Spring Boot, your beans and your repositories, and you build the screens as Java components that hold their state on the server. There are no template files, no fragment endpoints and no hand-written JavaScript for interactivity.

<!-- truncate -->

## Why look past templates? {#why-look-past}

A server-side template engine takes a model from your controller and turns it into HTML. For pages that are mostly content, that model is hard to beat.

The friction appears when a page becomes an app screen. Every interaction needs a round trip that your code has to describe in three places:

1. A template that renders the markup.
2. A controller method that returns the fragment or the new page.
3. Some script or attribute wiring that connects a click to that controller method.

None of this is wrong. It is a lot of surface area for a table with a sort header, and it grows with every screen. State is the harder part. The server renders and forgets, so the selected row, the open dialog and the current filter live in the browser, in the URL, or in a session you manage by hand.

## Is htmx enough for Spring Boot? {#htmx}

A common next step is adding htmx, a small library that lets HTML attributes issue requests and swap the response into the page. For a Spring Boot team, that means returning small HTML fragments from controller methods.

That reduces the script you write. It does not remove the model. You still design fragments, name endpoints and decide which part of the page each response replaces. A screen with a table, a filter bar and an edit dialog turns into a set of small endpoints that must agree with each other on markup and ids.

If you searched for htmx with Java and Spring Boot because you want interactivity without a front end build, you can reach the same goal another way. Interactive UI in Spring Boot without JavaScript means the components and their state live in Java, and the framework handles the browser side for you. To compare more options, see the [best Java web framework in 2026](/blog/java-web-frameworks-2026) by job.

## What changes with webforJ? {#what-changes}

In webforJ, a screen is a Java class. Components such as `Table`, `Dialog`, `Button` and the form fields are objects. You create them, configure them and listen to their events in the same language as the rest of your app. When a user clicks, sorts or types, the event reaches your Java code on the server, and the component state is already there.

Here is the practical difference for the three requests from the opening:

- **Sorting and paging.** The `Table` component supports [per-column sorting](/docs/components/table/sorting) and works with a repository, so sorting and paging are requested from your data layer instead of being coded per screen.
- **Dialogs.** A [`Dialog`](/docs/components/dialog) is a component you add, open and close from Java. There is no second page or fragment to maintain.
- **Validation.** Forms bind to a bean, and [Jakarta Validation](/docs/data-binding/validation/jakarta-validation) annotations on that bean drive the checks through `JakartaValidator`.

## One real screen from a Spring app {#real-screen}

An inventory screen is a good example of a screen that would need many fragments in a template app. It is a Spring-managed class, so services arrive through the constructor like any other bean. This is the start of `InventoryView`:

```java
@Route(value = "/", outlet = MainLayout.class)
@FrameTitle("Inventory")
@StyleSheet("ws://inventory.css")
public class InventoryView extends Composite<FlexLayout> {

  private final BookService bookService;
  private final GenreService genreService;
  private final AuthorService authorService;
  private final PublisherService publisherService;

  public InventoryView(BookService bookService, GenreService genreService,
      AuthorService authorService,
      PublisherService publisherService) {
    this.bookService = bookService;
    this.genreService = genreService;
    this.authorService = authorService;
    this.publisherService = publisherService;
  }
}
```

The same view builds its table by adding columns, and sorting is a flag on each one:

```java
bookTable.addColumn("Title", Book::getTitle).setMinWidth(100.0f).setSortable(true);
bookTable.addColumn("Author", Book::getAuthor).setMinWidth(100.0f).setSortable(true);
bookTable.addColumn("Publisher", Book::getPublisher).setMinWidth(100.0f).setSortable(true);
```

Clicking a row opens a drawer to edit that book, and all of it is handled by Java listeners on the server. Data access stays on Spring Data through the `SpringDataRepository` adapter, which the [Spring Data JPA guide](/docs/integrations/spring/spring-data-jpa) covers.

## Can both live in one Spring Boot app? {#coexist}

Yes, and for many teams this is the realistic plan. You do not have to rewrite the content pages to adopt webforJ.

When webforJ is mapped to the root context, the `webforj.exclude-urls` property lists URL patterns that webforJ should not handle. Spring MVC controllers can serve those URLs instead. The [Spring Boot guide](/docs/integrations/spring/spring-boot) describes the property and the related `webforj.servlet-mapping` setting. A sensible split looks like this:

| Part of the app | Good fit |
|-----------------|----------|
| Marketing pages, documentation, email-like content | Templates through Spring MVC |
| REST endpoints and webhooks | Spring MVC controllers |
| Tables, dialogs, forms and admin screens | webforJ components |

## When should you keep templates? {#keep-templates}

Templates are a great fit when the page is content first. A page that renders mostly text, loads once and has a few links does not need components with server-side state. Anything your team already ships well with templates can stay that way.

The signal to switch is the shape of the screen. If you are adding the third fragment endpoint to make one table behave, or you are keeping selection state in hidden fields, the screen is an app screen. That is where webforJ saves the most code, because the table, the dialog and the form are already components with state you can read from Java.

## Moving one screen at a time {#move-gradually}

Start with one new screen in webforJ, leave everything else alone, and move more screens over when it makes sense. Your services and repositories do not change, so each ported screen only swaps the template and its fragment endpoints for a view class.

## Let an AI assistant port the screen {#ai-port}

Porting a template screen is mostly mechanical, which suits an AI coding assistant. The webforJ AI plugin keeps the output reliable. It bundles the [MCP server](/docs/ai-tooling/mcp), so the assistant can look up real webforJ APIs, with [Agent Skills](/docs/ai-tooling/agent-skills) that guide the workflow. See the [AI plugin overview](/docs/ai-tooling) for installation, and review the generated view before you ship it.

The assistant can also check its own work. Ask it for a test with every screen it ports, so each one comes back with a pass or a fail before you review it. That write, check, review cycle is [the AI verification loop for Java](/blog/ai-verification-loop-java), and it fits a migration well, because every screen you move is a small, testable unit.

## Next step {#next-step}

Generate a Spring Boot project with [startforJ](https://docs.webforj.com/startforj) and pick **Spring Boot** under **Runs on**. Then rebuild your most interactive screen as a view and compare it with the template version. The [getting started guide](/docs/introduction/getting-started) walks through the first run.

Templates are one of five ways to build the UI. The others are in the comparison of [Java frontend frameworks](/blog/java-frontend-frameworks).

## Frequently asked questions {#faq}

### What is a good Thymeleaf alternative for Spring Boot? {#faq-alternative}

webforJ is a strong choice when your screens behave like an app. It keeps Spring Boot, Spring beans and Spring Data, and replaces templates with Java components that hold their state on the server. For content-only pages, templates remain a good fit.

### Can I use webforJ and Thymeleaf in the same Spring Boot app? {#faq-together}

Yes. With webforJ mapped to the root, the `webforj.exclude-urls` property lets Spring MVC keep the URL patterns you list. Content pages and REST endpoints can stay on Spring MVC while new app screens use webforJ.

### How do I build interactive Spring Boot UI without JavaScript? {#faq-no-js}

Build the screen from webforJ components in a Java class and handle events in Java. Sorting, dialogs and form validation run through server-side component state, so you do not write fragment endpoints or script files for them.
