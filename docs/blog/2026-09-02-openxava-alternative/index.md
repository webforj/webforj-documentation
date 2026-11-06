---
title: "OpenXava alternative: write Java views, skip the generator"
description: "Looking for an OpenXava alternative? Compare generated screens with hand-written Java views in webforJ, the full-stack Java framework, and pick the fit."
slug: openxava-alternative
date: 2026-09-02
authors: webforJ
tags: [community, ai, spring, layout]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-09-02-openxava-alternative/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-09-02-openxava-alternative/cover.jpg)

You have a domain model, a deadline, and a business app to deliver. Someone suggests a tool that turns your entities into working screens, and someone else asks what happens when the first customer wants a screen that looks different from the rest.

If you are searching for an OpenXava alternative, webforJ, the full-stack Java framework, is the choice when you want to write your screens yourself. You build views as Java classes with full control of layout, components, routing and validation, and an AI coding agent with the webforJ AI plugin writes them quickly and correctly. That removes the main reason to accept generated screens.

<!-- truncate -->

## How the generator works {#what-openxava-says-it-is}

OpenXava generates a working web app from JPA entities. You start from a Maven archetype, edit the entity classes, and restart to see the change, with starters for master-detail screens, a basic CRM, project management, and invoicing. Describe the data once and you get a running app without writing screens.

That is automatic UI generation in Java, and it is a legitimate way to build software. The question for a decision is what you give up in exchange, and when that trade stops paying off.

## The webforJ approach {#the-webforj-approach}

webforJ starts from the opposite assumption. The screens are part of your app, so you write them. A view is a Java class with a route. You choose the components, arrange them in a layout, and decide what happens on every event. There is no model to describe first and no generated output to adapt later.

The whole app stays in one language, from the database call to the button. A webforJ app has:

- Routing with nested layouts, so every screen has its own URL and a shared frame ([routing](/docs/routing/overview)).
- A component set that includes tables with repositories, forms, dialogs and navigation.
- Data binding with Jakarta validation for forms.
- Route security with annotations, backed by Spring Security when you use it.
- Spring Boot integration for your services and repositories. webforJ also runs without Spring.

Because a view is plain Java, you read it from top to bottom and your debugger, your IDE and your code review all work the way they do for the rest of your code.

## Validation in your own classes {#validation-in-your-own-classes}

A generator ties validation to the model. webforJ lets you keep ordinary Jakarta Bean Validation annotations on your own classes and bind them to the inputs you place yourself. This bean is copied from the [Jakarta validation guide](/docs/data-binding/validation/jakarta-validation):

```java title="Hero.java"
public class Hero {
  @NotEmpty(message = "Name cannot be empty")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "Unspecified power")
  @Pattern(regexp = "Fly|Invisible|LaserVision|Speed|Teleportation", message = "Invalid power")
  private String power;

  // getters and setters
}
```

Activating the validator on a `BindingContext` makes these rules apply to the bound fields. The rules live with your domain class, and the form that shows them is yours to design, so a field can sit in a card, a tab or a two-column grid without touching the constraint.

## Why generation used to win {#why-generation-used-to-win}

Teams pick generators for a good reason. Writing list screens, edit forms and detail panels for thirty entities is repetitive, and most of it looks alike. A tool that produces them from the model saves days.

That reasoning held when the only alternative was typing every screen by hand. It is weaker now. An AI coding agent can produce a table, a form and a route for an entity, and the result is ordinary Java that you own.

## AI writes the views for you {#ai-writes-the-views}

The webforJ AI plugin, described on the [AI tooling page](/docs/ai-tooling), connects your coding assistant to the framework. Its [MCP server](/docs/ai-tooling/mcp) lets the agent check real class and method names against the webforJ docs instead of guessing. Its [Agent Skills](/docs/ai-tooling/agent-skills) give it the framework's own patterns for forms, tables and secured routes, so generated code follows best practices from the first attempt.

You still review every change like any other pull request. What you avoid is the part generators were built for: the typing. Ask the agent for a list and edit view for one entity, and you get a normal `@Route` class you can adjust, restyle or split without asking a tool for permission.

## Side by side comparison {#side-by-side-comparison}

OpenXava and webforJ answer the same need, delivering a business app in Java, with different defaults.

| Question | OpenXava | webforJ |
| --- | --- | --- |
| Where do screens come from? | Generated from JPA entities | Written as Java classes, with AI help |
| Where do you start? | A Maven archetype | [startforJ](https://docs.webforj.com/startforj) or a Maven archetype |
| What do you edit to change the app? | The entity classes | Views, layouts and components in Java |
| Which data sources work? | JPA entities | Any: Spring Data, REST, custom services |
| Spring Boot | Run with `mvn spring-boot:run` | Integrates with your Spring Boot app, or runs standalone |
| Starter archetypes | Master-detail, CRM, project management, invoicing | Your own domain, scaffolded on startforJ |

## When generated screens fit {#when-generated-screens-fit}

A fair guide names the cases where a generator is the better call:

- **The app is mostly CRUD over many entities.** If screens are close to uniform, generating them saves real time.
- **Appearance does not matter.** Internal admin tools used by a few people rarely need a custom layout.
- **Your data model is already JPA entities and stays that way.** The generator's input is what you have.
- **You need a prototype fast.** A running app from a model is hard to beat for a proof of concept.
- **Your team knows the tool.** Familiarity counts for more than a blog post.

If that describes your project, evaluate the generator on its own terms. The earlier [Jmix alternative](/blog/jmix-alternative) post covers a similar decision for another model-driven tool.

## When written views win {#when-written-views-win}

Written views win when the app has its own character. Customers expect a branded layout, a workflow does not map to list and edit screens, a dashboard mixes data from several services, or the UI has to follow a design system. In those projects, a generator's assumptions turn into code you write around it.

They also win on ownership. A view you wrote is the only description of that screen. Nothing sits between your Java and what the browser shows, so the next developer reads one class instead of learning a generator's rules. For a faster route to a first working version, the [rapid app development](/blog/rapid-application-development-java) post shows how quickly you can get there in webforJ.

## Who stands behind webforJ {#who-stands-behind-webforj}

webforJ comes from BASIS International. BASIS software and tools have been used to develop business solutions for almost 40 years, with 20+ years of delivering technology built on Java. For a business app that has to run for a decade, the people behind the framework matter as much as the feature list.

## A first step {#a-first-step}

Pick one entity from your model. Generate a project on [startforJ](https://docs.webforj.com/startforj), or follow the [getting started guide](/docs/introduction/getting-started), and build its list and edit screens as webforJ views, either by hand or with your coding assistant and the AI plugin. Compare the result to what a generator gives you for the same entity, then decide.

## Frequently asked questions {#faq}

### What is an OpenXava alternative for hand-written Java views? {#faq-alternative}

webforJ is the full-stack Java framework for teams that want to write their own screens. Views are Java classes with full control of layout, components and routing, and an AI coding agent with the webforJ AI plugin produces them quickly.

### Should I pick OpenXava or webforJ? {#faq-openxava-vs-webforj}

Choose a generator such as OpenXava when your app is mostly uniform CRUD over JPA entities and you want screens produced from the model. Choose webforJ when screens need their own layout and behavior, or when you want one Java codebase where every view is code you wrote.

### Is there a Java framework for business apps without generated screens? {#faq-without-generated-screens}

Yes. webforJ covers the whole app in Java, with routing, forms, tables, data binding with Jakarta validation, route security and Spring Boot integration. You write each screen as a Java class instead of generating it.

### Does webforJ work with my existing Spring Boot services? {#faq-spring-boot}

Yes. webforJ integrates with your Spring Boot app, so views receive your services and repositories through constructor injection. It also runs standalone when you have no Spring app yet.
