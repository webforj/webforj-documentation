---
title: "Jmix alternative: plain Java and Spring Boot with webforJ"
description: "Looking for a Jmix alternative or a CUBA platform alternative? See how webforJ gives you Java views that plug into your own Spring Boot app, data layer and security."
slug: jmix-alternative
date: 2026-07-15
authors: webforJ
tags: [community, ai, spring, security]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-07-15-jmix-alternative/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-07-15-jmix-alternative/cover.jpg)

You are evaluating a business app platform for your next internal tool, or you run on a platform such as CUBA and are planning your next move. Either way the same question shows up in the planning meeting: do we adopt a platform with its own model for entities, screens and tooling, or do we write a normal Spring Boot app and add a UI to it?

If you want a Jmix alternative that stays plain Java, webforJ, the full-stack Java framework, is that choice. You build views in Java that plug into your own Spring Boot app, use your own Spring Data repositories, pick your own security setup, and keep a project layout you could explain to any Spring developer on day one.

<!-- truncate -->

:::tip See a table with a dialog form
The [webforj-crud](https://github.com/webforj/built-with-webforj/tree/main/webforj-crud) app in built-with-webforj shows a music artist table with search, a dialog that adds, edits and deletes entries, data binding and validation on Spring Boot. It runs with Java and Maven.
:::

## What a platform gives you {#what-a-platform-gives-you}

Business app platforms such as Jmix and CUBA are built around a clear idea. You describe an entity model, and the platform helps you produce the screens, the data access and the administration features that every line-of-business app needs. Visual tooling guides you through the process, and the platform defines where things live and how they fit together.

That idea has real value. It also has a cost that only shows up later: the platform's conventions become the architecture of your app. Screens follow the platform's structure. The data model follows the platform's entity model. Upgrades follow the platform's schedule. Teams looking for a Java business app platform alternative are usually reacting to that last part, not to a missing feature.

## What webforJ is instead {#what-webforj-is-instead}

webforJ is a full-stack Java framework that works with the Spring Boot app you already have. There is no entity designer to adopt and no generated layer between your code and your database. You write views as Java classes, compose components with Java, and let Spring Boot do what it already does.

In practice that means:

- Your app is a standard [Spring Boot app](/docs/integrations/spring/spring-boot). You can start it from startforJ or add webforJ to an existing project.
- Views are `@Route` classes. Spring beans such as services and repositories reach them through constructor injection.
- The data model is whatever you already have. JPA entities, a REST client or a custom service all work.
- Security is your decision, backed by Spring Security or a custom implementation.

There is nothing to generate and nothing to regenerate after a model change. A new field is a new property on your class and a new column or input in your view.

## Your data layer stays yours {#your-data-layer-stays-yours}

Teams that compare platforms usually start with the question of where the entity model lives. With webforJ it lives in your Spring app, written the way you would write it without any UI framework at all.

The `SpringDataRepository` adapter connects an existing Spring Data JPA repository to a webforJ `Table`. It detects which Spring Data interfaces your repository implements, so paging, sorting and specification-based filtering work without changes to the repository itself. This is the shape of a view built on it, trimmed from the [Spring Data JPA guide](/docs/integrations/spring/spring-data-jpa):

```java title="TableView.java"
@Route
public class TableView extends Composite<Div> {
  private SpringDataRepository<Person, Long> repository;
  private Table<Person> table = new Table<>();

  public TableView(PersonRepository personRepository) {
    // Wrap Spring Data repository for webforJ
    repository = new SpringDataRepository<>(personRepository);

    // Connect to table
    table.setRepository(repository);

    // Define columns
    table.addColumn("email", Person::getEmail);
    table.addColumn("city", Person::getCity);
    table.addColumn("profession", Person::getProfession);
  }
}
```

`PersonRepository` is an ordinary Spring Data interface. If you later change your persistence approach, the view code does not carry any platform assumptions that you would have to unpick. For forms, the [data binding](/docs/data-binding/overview) feature binds your own bean classes to input components and validates them, again without a generated screen in between.

## Security you choose yourself {#security-you-choose-yourself}

Access control is the second place where platform conventions tend to spread. In a webforJ app you protect routes with annotations on the view, and the framework enforces the rules before any component is rendered. When Spring Boot is on the classpath you can use Spring Security for users, passwords and sessions, as described in the [security getting started guide](/docs/security/getting-started). If you are not on Spring, the same annotations work with a custom implementation of the security interfaces.

This is a route protected for one role, copied from the [security annotations guide](/docs/security/annotations):

```java title="TrashView.java"
@Route(value = "/trash", outlet = MainLayout.class)
@RolesAllowed("ADMIN")
public class TrashView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public TrashView() {
    self.setHeight("100%");
    self.add(new Explore("Trash"));
  }
}
```

Users without the `ADMIN` role are redirected to the access denied page. For rules that depend on the data, such as ownership checks, [custom evaluators](/docs/security/custom-evaluators) let you write the logic in plain Java. Your authentication source is configured through Spring Security, not through a platform setting.

## When a platform fits {#when-a-platform-fits}

A fair comparison includes the cases where a platform is the right call:

- **The app is mostly CRUD over a large entity model.** If screens are close to uniform and you want them produced from the model with little hand work, generation saves real time.
- **You want one opinionated way to do everything.** Some organizations value a single set of conventions across many small apps, and are happy to trade flexibility for it.
- **Your team already works with the platform's tooling.** Familiarity matters. A team that is productive in a studio environment should not switch because of a blog post.
- **The scope is administrative.** Back-office tools with standard lists, forms and roles can be a good match for a platform.

If that describes your project, evaluate the platform on its own terms. If it does not, keep reading.

## When plain Java wins {#when-plain-java-wins}

The picture changes when your app has its own character. Custom layouts, unusual workflows, a domain where every screen is different, or a requirement to embed the UI in a larger Spring system all push against a generator's assumptions. In those projects you end up writing code around the platform's structure instead of in it.

The webforJ side of that decision looks like this:

| Question | Platform approach | webforJ approach |
| --- | --- | --- |
| Where does the data model live? | In the platform's entity model | In your Spring Data repositories and classes |
| How are screens created? | Generated, then customized | Written as Java `@Route` classes |
| How is access controlled? | Through the platform's security model | Route annotations with Spring Security or custom rules |
| What does a new developer need to know? | The platform and its tooling | Java and Spring Boot |
| What happens when you leave? | Screens and model follow platform structure | Your Spring app and domain code remain |

The last row matters most for teams who come to this question from a CUBA platform project. A migration path is easier to plan when the target is a framework that does not ask you to adopt a new app structure to use it. You can move one view at a time, because each view is a Java class that talks to the same Spring services as before.

## Generated code, written by AI {#what-you-write}

In webforJ the table columns, the form layout, and the binding are plain Java code. A generator hands you those on day one, and today an AI coding agent writes them just as fast. webforJ makes that output reliable: the [Agent Skills](/docs/ai-tooling/agent-skills) give the agent webforJ's own patterns to follow, and the [MCP server](/docs/ai-tooling/mcp) checks every API and design token against the real webforJ docs, so the agent corrects its own mistakes in a validation loop before you review the code. What you get back is that the code is the app. There is no second description of your screens to keep in sync, and a developer who finds a view can read it from top to bottom.

The [Table component](/docs/components/table/overview) covers sorting, selection and custom cell renderers, so the hand-written part is mostly declarative: which columns, which properties, which actions. The work you do not repeat is the plumbing, because Spring Boot already handles the app lifecycle, configuration, dependency injection, and data access.

## Let an AI assistant do the port {#ai-port}

Moving one view at a time is repetitive work, which suits an AI coding assistant. Give it a single screen and the entity or service behind it, and ask for the matching webforJ view. The webforJ AI plugin keeps the result grounded: its [MCP server](/docs/ai-tooling/mcp) lets the assistant look up real class and method names, and its [Agent Skills](/docs/ai-tooling/agent-skills) guide forms and secured routes. Setup is on the [AI plugin page](/docs/ai-tooling). Review each screen as you would any other change.

## A first step {#a-first-step}

Generate a Spring Boot project on [startforJ](https://docs.webforj.com/startforj), add one of your existing repositories, and build a single list view with `SpringDataRepository` and `Table`. That one view tells you more about fit than any feature checklist. The [getting started guide](/docs/introduction/getting-started) walks through the first run.

## Frequently asked questions {#faq}

### What is a good Jmix alternative for Java teams? {#faq-good-alternative}

If you want to stay on plain Java and Spring Boot, webforJ is a strong option. You write views as Java classes alongside your own Spring app, keep your own data layer, and choose your own security setup, with no platform conventions to adopt.

### Is there a CUBA platform alternative that does not need a rewrite of the data layer? {#faq-cuba-alternative}

Yes, if your data access is already built on Spring and JPA, you can connect existing Spring Data repositories to webforJ tables with `SpringDataRepository`. Because each view is a separate Java class, you can move screens over one at a time.

### Jmix vs webforJ: which should I pick? {#faq-jmix-vs}

Pick a platform when the app is largely standard CRUD and you value generated screens and one set of conventions. Pick webforJ when you want to write your views in Java with a normal Spring Boot app and keep full control over structure, data access, and security.
