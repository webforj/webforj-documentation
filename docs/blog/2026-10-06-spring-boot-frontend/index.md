---
title: "Spring Boot frontend: Thymeleaf, React or webforJ?"
description: "Choosing a Spring Boot frontend? Compare Thymeleaf, React or Angular with REST, and webforJ, which builds the UI in Java inside the same Spring Boot app."
slug: spring-boot-frontend
date: 2026-10-06
authors: webforJ
tags: [integrations, spring, full-stack, web development]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-10-06-spring-boot-frontend/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-10-06-spring-boot-frontend/cover.jpg)

For most Java teams the best Spring Boot frontend is webforJ, the full-stack Java framework. You write the whole app, screens included, in Java, and it runs inside the same Spring Boot app as your services and gets its beans through constructor injection. There is no second language, no second build, and no HTTP contract between your screens and your data.

The alternatives are real and each fits some teams. Thymeleaf suits server-rendered pages with little interactivity, and a React or Angular single-page app suits teams that already have frontend specialists. This post compares them on build, skills, security, data access, and the cost of one feature, so you can choose a frontend framework for Spring Boot on evidence.

<!-- truncate -->

## The three realistic Spring Boot UI options {#the-options}

Every Spring Boot UI falls into one of three shapes.

**Server-side templates.** [Thymeleaf](https://www.thymeleaf.org/) is a server-side Java template engine, and [Spring Boot auto-configures it](https://docs.spring.io/spring-boot/reference/web/servlet.html) so templates under `src/main/resources/templates` work immediately. A controller returns a model, the template renders HTML, and the browser gets a finished page.

**A JavaScript SPA plus REST.** You build the UI with [React](https://react.dev/learn/build-a-react-app-from-scratch) or [Angular](https://angular.dev/overview) as a separate project. Spring Boot exposes JSON endpoints and the SPA calls them. React's own guidance for building from scratch is to start with a build tool such as Vite, then add routing and data fetching yourself or adopt a framework that provides them.

**A full-stack Java framework.** With webforJ the screens are Java classes. A route class builds components and calls your services directly. The browser receives the rendered interface and sends events back, so the UI logic stays on the server in the same JVM as the rest of your app.

## How the options compare {#comparison}

| | Thymeleaf | React or Angular SPA | webforJ |
| --- | --- | --- | --- |
| UI language | HTML templates with Java expressions | JavaScript or TypeScript | Java |
| Projects to build | One | Two (frontend and backend) | One |
| Backend contract | Controllers return models | REST or GraphQL endpoints | Direct service calls |
| Rich components | Add your own JavaScript | Large ecosystem | Built-in table, dialogs, layouts, forms |
| Security model | Spring Security | Spring Security plus token or cookie handling in the client | Spring Security with route annotations |
| Best fit | Content pages, simple forms | Teams with dedicated frontend developers | Java teams building business apps |

The next sections go through the rows that decide projects.

## Build and deployment {#build}

A Thymeleaf app is a single Maven or Gradle project. That is its strength.

An SPA adds a second project with its own package manager, bundler, test runner, and deployment target. You either serve the compiled assets from Spring Boot or deploy them separately, and in the second case you also configure cross-origin access between the two. Every release has two artifacts to keep compatible.

webforJ stays at one project. The Spring Boot starter and the build plugin are the only additions, and `mvn` on a generated project compiles and runs the app. You can still bring in JavaScript when you need a specific library, but it is optional, not the foundation.

## Team skills {#team-skills}

This is usually where the decision is made. A Java team that picks an SPA takes on a second discipline. Someone has to own the component model, state management, build tooling, and the frontend upgrade cycle. If you already have those people, the SPA is a fair choice.

If you don't, the cost is hiring or retraining. Thymeleaf keeps the team in Java but limits you to what server-rendered pages do well. webforJ lets the same developers who write the services also write the screens, with components such as `Table`, `FlexLayout`, and dialogs available from the start.

## Security {#security}

Spring Security protects all three, but the wiring differs.

With Thymeleaf, you secure URLs and the pages follow. With an SPA, you also decide how the browser authenticates against the API and where credentials live. That is a well-understood problem, but it is a second place to get wrong.

webforJ integrates with Spring Security directly. You add `spring-boot-starter-security`, declare a `SecurityFilterChain` with `WebforjSecurityConfigurer.webforj()`, and protect routes with annotations such as `@RolesAllowed("ADMIN")`. The security layer checks the annotation before it renders the view. The [security getting started guide](/docs/security/getting-started) shows the full configuration, and [route access control with Spring Security](/blog/spring-security-route-access-control) goes deeper on the annotations.

```java title="SecurityConfig.java"
@Bean
SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
  return http
      .with(WebforjSecurityConfigurer.webforj(), configurer -> configurer
          .loginPage(LoginView.class)
          .accessDeniedPage(AccessDenyView.class)
          .logout())
      .build();
}
```

## Data access {#data-access}

With Thymeleaf, controllers call services and hand models to templates, so data access is direct. With an SPA it passes through serialization on both sides, which a [post about the REST layer](/blog/java-ui-without-rest-api) covers in detail.

In webforJ a route class receives Spring beans through its constructor. Spring's container creates the instance, and webforJ makes a fresh one per navigation. Spring Data repositories plug into components too. `SpringDataRepository` adapts a `JpaRepository` so a `Table` can page and sort against the database. See [Spring Data JPA integration](/docs/integrations/spring/spring-data-jpa) for filtering with specifications.

## The webforJ setup on Spring Boot {#webforj-setup}

Generate a project from [startforJ](https://docs.webforj.com/startforj) and choose Spring Boot, or use the archetype with `-Dflavor=webforj-spring`. The [Spring Boot guide](/docs/integrations/spring/spring-boot) lists both options. The starter dependency is:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-spring-boot-starter</artifactId>
</dependency>
```

A route class needs no Spring annotation. `@Route` alone enables discovery and injection, as described in [Spring routing](/docs/integrations/spring/routing).

```java title="OrdersView.java"
package com.example.views;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.table.Table;
import com.webforj.data.repository.CollectionRepository;
import com.webforj.router.annotation.Route;

@Route("/orders")
public class OrdersView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Table<Order> table = new Table<>();
  private final Button ship = new Button("Ship selected");

  public OrdersView(OrderService orders) {
    self.setDirection(FlexDirection.COLUMN).setSpacing("1em");

    table.addColumn("Order", Order::getNumber);
    table.addColumn("Customer", Order::getCustomer);
    table.addColumn("Status", Order::getStatus);
    table.setRepository(new CollectionRepository<>(orders.findOpenOrders()));

    ship.onClick(e -> {
      table.getSelectedItems().forEach(orders::ship);
      table.setRepository(new CollectionRepository<>(orders.findOpenOrders()));
    });

    self.add(table, ship);
  }
}
```

`OrderService` is an ordinary `@Service` bean. The view shows open orders and ships the selected ones. The [Table component](/docs/components/table/overview) supplies the columns, sorting, and selection.

## What one feature costs in each option {#cost-of-a-feature}

Take a feature a product owner might ask for: a screen that lists open orders and lets staff ship selected ones.

- **Thymeleaf:** a controller, a template, a form post for the action, and a redirect. Selecting rows and updating the list without a page reload needs JavaScript you write or add.
- **SPA plus REST:** an endpoint to list, an endpoint to ship, DTOs, a client module, a component, state handling, loading and error states, and a build that deploys both sides.
- **webforJ:** the preceding view. It calls `OrderService` directly.

None of these is impossible. The difference is how many layers a single change crosses, and every layer you add is one more thing to test and keep in sync.

## When another option is the better choice {#when-others-win}

Pick Thymeleaf when the app is mostly content pages and forms and you have no need for live updates. Pick an SPA when you have dedicated frontend developers, when the same API must serve a mobile app or partners, or when you need a specific JavaScript ecosystem library as the core of the product. In that case the REST contract earns its cost.

For an internal tool, an admin console, a dashboard, or any business app built by Java developers, webforJ removes the most work. Start from the [Spring Boot guide](/docs/integrations/spring/spring-boot).

## Frequently asked questions {#faq}

### What is the best frontend framework for Spring Boot? {#faq-best}

It depends on your team. If your developers write Java and you are building a business app, webforJ keeps the UI in the same Spring Boot project. If you have dedicated frontend developers or need one API for several clients, React or Angular with REST fits better.

### Can I build a Spring Boot UI without JavaScript? {#faq-no-javascript}

Yes. Thymeleaf renders HTML on the server, and webforJ builds the whole interface from Java components. With webforJ you can still add JavaScript for a specific library, but you don't need it to ship screens.

### Does webforJ work with Spring Security and Spring Data? {#faq-spring-integration}

Yes. The webforJ Spring Boot starter integrates with Spring Security through route annotations, and `SpringDataRepository` connects Spring Data JPA repositories to components such as `Table`. Beans are injected into route constructors.

### Is Thymeleaf enough for a Spring Boot web UI? {#faq-thymeleaf}

For content pages and simple forms, yes. Once users expect live tables, dialogs, and updates without a reload, you start writing JavaScript on top, and at that point a component framework is less work.
