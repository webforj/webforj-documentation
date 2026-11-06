---
title: "An Apache Struts alternative on Spring Boot"
description: "Looking for an Apache Struts alternative? Replace action and JSP pairs with webforJ views that plug into Spring Boot and keep your Java services, one screen at a time."
slug: apache-struts-alternative
date: 2026-06-08
authors: webforJ
tags: [community, ai, modernization, spring]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-06-08-apache-struts-alternative/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-06-08-apache-struts-alternative/cover.jpg)

Your team maintains an app built around action classes, JSP views, and a mapping file. It works, the business rules inside it are valuable, and every new screen still means touching three or four files in different places. If you are weighing an Apache Struts alternative, the short answer is webforJ, the full-stack Java framework, with your Spring Boot services plugged in: each action and JSP pair becomes one Java view with a route, the markup and tag libraries become Java components, form beans become validated data binding, and your services carry over unchanged.

<!-- truncate -->

## The problem with action and JSP pairs {#the-problem}

A classic Struts screen is spread over several artifacts. An action class handles the request, a mapping in `struts.xml` or an annotation connects a URL to that action, a form bean carries the submitted values, and a JSP renders the page with tag libraries. Following one user flow means opening all of them.

That structure fits request and response page rendering. It gets expensive when the UI has to feel interactive. Validation rules live in one place and error display in another, and a table that sorts and filters without a page reload usually needs JavaScript on top. The people who know the business logic are Java developers, and the UI work lands on them anyway.

A migration therefore has two goals. Keep the Java code that encodes the business, and replace the layers around it with something a Java developer can read top to bottom.

## Views as the alternative {#apache-struts-alternative}

webforJ is a full-stack Java framework where the whole app, screens included, is written in Java and runs on the server. There is no template language and no separate front-end project. A screen is one class: it has a route, it builds its components, and it talks to your services.

Because webforJ integrates with [Spring Boot](/docs/integrations/spring/spring-boot), you get an executable JAR with an embedded server instead of a WAR in an external container. Your views can inject Spring beans, so your existing service layer plugs in without a rewrite. The [Spring integration](/docs/integrations/spring/overview) covers beans, repositories, and scopes.

## What replaces what {#what-replaces-what}

Follow one request through the old stack: the URL reaches an action, the action fills a form bean, runs validation, calls a service, and forwards to a JSP. In webforJ the same flow reads as one class. The `@Route` annotation takes the place of the URL mapping, components created in Java take the place of the tag library, a bound bean takes the place of the form bean, and the service call stays exactly where it was.

The last step matters most. Nothing in the first four touches your business logic.

## Routes replace actions {#routes-replace-actions}

In webforJ a URL is mapped with the `@Route` annotation on the view itself. There is no separate mapping file to keep in sync. A route can take parameters in its pattern, and `@RouteAlias` lets a second URL reach the same view, which helps when old bookmarks must keep working.

```java
@Route(value = "profile")
@RouteAlias("user/me")
public class UserProfileView extends Composite<Div> {
  // Component logic here
}
```

The [defining routes](/docs/routing/defining-routes) page covers patterns, aliases, and nested layouts. Where an action used to forward to a JSP, the view now builds its own components, and navigation between views is a method call rather than a result mapping. Our post on [routing in 10 minutes](/blog/master-webforj-routing-in-10-minutes) is a quick walk through the basics.

## Form beans become bound beans {#form-beans}

A form bean exists to move values between the request and your model. In webforJ the same job is done by [data binding](/docs/data-binding/overview). You declare the components as fields and create a `BindingContext` for your bean class.

Components are matched to bean properties by name. `context.read(bean)` fills the form from the bean, and `context.write(bean)` pushes the values back and returns a `ValidationResult` you check before saving. Passing `true` when you create the context turns on Jakarta validation, so the constraints on the bean are the rules.

The page on [Jakarta Validation](/docs/data-binding/validation/jakarta-validation) shows the annotations and their messages. Constraints from your current validation files translate to annotations on the model class.

## Services move unchanged {#services}

Business rules, calculations, integrations and persistence code are plain Java. They do not depend on how the page is drawn. Register them as Spring beans, inject them into a view through the constructor, and call them from a click handler where the action used to call them.

This is the part of the migration that carries the least risk, and it is the reason to choose a framework that stays in Java. You are not porting logic to another language. You are replacing the way results reach the screen.

## Migrate one screen at a time {#screen-by-screen}

A full rewrite is rarely the right plan. A safer order looks like this:

1. Create a webforJ project with Spring Boot using [startforJ](https://docs.webforj.com/startforj) and move your service classes into it.
2. Pick one screen with a clear owner, ideally a list with a form behind it.
3. Build the view, bind the bean, and wire the same service calls.
4. Route users to the new URL and retire the old action and JSP for that screen.
5. Repeat, starting with the screens people use most.

While both UIs exist, other systems may still call URLs that the old app served. If you expose those as Spring MVC controllers, the `webforj.exclude-urls` property tells webforJ to leave those paths alone when it is mapped to the root, so REST endpoints and your views live in the same app. The [Spring Boot configuration table](/docs/integrations/spring/spring-boot#configuration) describes the property.

Each migrated screen is a small, reviewable change, and your users see improvements as they land instead of waiting for a big release.

## Let an AI assistant do the port {#ai-port}

Mechanical translation suits an AI coding assistant. Give it one action, its mapping, its JSP and its form bean, and ask for the matching view. The webforJ AI plugin makes the result reliable. It connects the assistant to the [webforJ MCP server](/docs/ai-tooling/mcp), so class and method names come from the framework's documentation rather than from guesswork, and to [Agent Skills](/docs/ai-tooling/agent-skills) that describe how to build forms, secure routes and style apps the way webforJ expects. Setup is on the [AI plugin page](/docs/ai-tooling).

Review and test every generated screen like any other change.

## Your next step {#next-step}

Generate a Spring Boot project with [startforJ](https://docs.webforj.com/startforj), move one service into it, and port the simplest screen you have. The [getting started guide](/docs/introduction/getting-started) walks through the first run.

## Frequently asked questions {#faq}

### How do I move to webforJ? {#faq-migrate}

Move your service classes into a Spring Boot project, then replace each action and JSP pair with a webforJ view that has a route. Bind a validated bean in place of the form bean and call the same services from the view. Do it one screen at a time so the old and new UIs can coexist.

### Is this a Struts to Spring Boot path? {#faq-struts-to-spring-boot}

Yes. webforJ integrates with Spring Boot, so the migration also gives you an embedded server, dependency injection and Spring Data access. Your views inject Spring beans directly, which means the new screens use the same services as the rest of a modern Java back end.

### Do I need to learn JavaScript for the new UI? {#faq-javascript}

No. Screens are written in Java with components, routes and data binding, and the framework handles the browser side. Your team keeps working in the language and tools it already uses.
