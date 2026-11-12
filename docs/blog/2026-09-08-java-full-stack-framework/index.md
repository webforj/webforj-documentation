---
title: "Java full stack framework: UI to database in one codebase"
description: "webforJ is a Java full stack framework: UI, routing, forms, security and data in one Java codebase with Spring Boot, built and shipped as a single artifact."
slug: java-full-stack-framework
date: 2026-09-08
authors: Eric Handtke
tags: [insights, full-stack, spring, back end]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-09-08-java-full-stack-framework/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-09-08-java-full-stack-framework/cover.jpg)

Most Java teams know the split: the backend is Java, and the screens live in a second project with another language, another build, and an API between the two. A Java full stack framework removes that split. It's a framework where you build every layer of a web app in Java: the screens, the routing between them, the forms and their validation, the security rules and the data access, all in one project with one build.

webforJ is the full-stack Java framework for business web apps: you build the whole app, screens included, in Java. On Spring Boot it ships as a single executable JAR, and your views receive services and repositories through constructor injection like any other Spring bean. The separate frontend project, the API contract and the validation you write twice all go away.

This post lists what a full stack Java app contains in webforJ, then walks through a small slice from entity to table to form.

<!-- truncate -->

## What "full stack" covers in webforJ {#what-full-stack-covers}

A full stack framework should let one team own every layer of a feature without switching languages. Here is where each layer lives in a webforJ app:

| Layer | In webforJ |
| --- | --- |
| UI | Java components such as `Table`, `TextField` and `FlexLayout` |
| Routing | `@Route` on a view class gives it a URL |
| Forms and validation | `BindingContext` with Jakarta Bean Validation annotations on your model |
| Security | Route annotations such as `@RolesAllowed`, backed by Spring Security |
| Services | Spring beans injected into views through the constructor |
| Data | Spring Data repositories adapted to a `Table` with `SpringDataRepository` |
| Delivery | One Spring Boot JAR, or a WAR for a servlet container |

Everything in that table is Java, compiled by one toolchain, and tested with the tools you already use for the rest of your backend. A longer list of [what a complete platform includes](/blog/complete-java-web-platform) adds themes, translations, and installable apps.

## The full stack checklist {#full-stack-checklist}

Use this list to judge any Java full stack framework. Each row is a capability a business app needs, what webforJ gives you for it, and where to read more.

| Capability | What you get | Docs |
| --- | --- | --- |
| Routing | `@Route` maps a view class to a URL, with deep links and browser history | [Routing](/docs/routing/overview) |
| Layouts and components | Buttons, fields, dialogs and tables as Java classes, arranged with `AppLayout` and `FlexLayout` | AppLayout, FlexLayout |
| Forms with binding and validation | `BindingContext` keeps fields and a bean in sync and checks Jakarta Bean Validation rules | Data Binding |
| Tables backed by repositories | `Table` reads from a `Repository`, and `SpringDataRepository` brings paging, sorting and filtering from Spring Data | Repository, Spring Data JPA |
| Route security | `@AnonymousAccess`, `@PermitAll`, `@RolesAllowed` and `@DenyAll`, checked before a view renders | Security |
| Translation | Text from resource bundles through `t()`, with placeholders and browser locale detection | [Translation](/docs/advanced/i18n-localization) |
| Installable apps | `@AppProfile` generates a web app manifest so users can install the app on their device | [Installable Apps](/docs/configuration/installable-apps) |
| Testing | JUnit tests for app logic, and Selenium or Playwright end-to-end tests for full user journeys | [Testing](/docs/testing/overview) |

## What a Java backend plus JavaScript frontend costs {#cost-of-the-split}

The split architecture is common and it works. It is the right call when several clients consume the same API, for example a mobile app next to a partner integration. The cost is easy to underestimate when the only client is your own web UI.

- **Two builds.** The Java project and the frontend project each have their own dependencies, build tool, test setup and deployment step.
- **An API contract.** Every screen needs endpoints, request and response shapes, and error handling on both sides. A change to a Java model means a change to the client that reads it.
- **Duplicated validation.** The server must validate because it can't trust the client. The form validates too, so users get feedback before they submit. Those two rule sets drift apart.
- **Two skill sets.** Someone has to own the frontend toolchain and the component framework on top of it, and a Java developer can't pick up that work without learning it.

None of this is a flaw in the approach. It is overhead you only want to pay when a second consumer makes the contract worth having. A closer look at [React on a Java backend](/blog/react-with-java-backend) shows when the split pays off.

## Start from Spring Boot {#start-from-spring-boot}

[startforJ](https://docs.webforj.com/startforj) generates a project with **Runs on** set to Spring Boot. The same project can come from the Maven archetype with `-Dflavor=webforj-spring`. You get a Spring Boot parent POM, the webforJ starter, and an app class annotated with `@SpringBootApplication` and `@Routify`.

Running `mvn` with no arguments compiles the app, starts the frontend watch, and runs the embedded Tomcat server. See the [Spring Boot guide](/docs/integrations/spring/spring-boot) for the full setup.

## A slice from database to screen {#end-to-end-slice}

The rest of this post builds one feature: a customer list with a form to add customers. It uses an entity, a repository, a service, and a view.

### The entity {#the-entity}

Validation lives on the model, once:

```java
@Entity
public class Customer {
  @Id @GeneratedValue
  private Long id;

  @NotBlank(message = "Name is required")
  private String name;

  @Email(message = "Enter a valid email")
  private String email;

  // getters and setters
}
```

The `@NotBlank` and `@Email` annotations are plain Jakarta Bean Validation. webforJ reads them through `JakartaValidator`, so the form enforces the same rules your service layer would. See the [Jakarta Validation guide](/docs/data-binding/validation/jakarta-validation) for the extra dependencies this needs.

### The repository and service {#the-repository-and-service}

These are ordinary Spring classes:

```java
public interface CustomerRepository
    extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
}

@Service
public class CustomerService {
  private final CustomerRepository repository;

  public CustomerService(CustomerRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public Customer save(Customer customer) {
    return repository.save(customer);
  }

  public CustomerRepository repository() {
    return repository;
  }
}
```

Extend both `JpaRepository` and `JpaSpecificationExecutor`. `SpringDataRepository` detects those interfaces and turns on paging, sorting, and filtering to match, as the [Spring Data JPA guide](/docs/integrations/spring/spring-data-jpa) shows.

### The view {#the-view}

```java
@Route("customers")
@RolesAllowed("ADMIN")
public class CustomersView extends Composite<FlexLayout> {
  private final Table<Customer> table = new Table<>();
  private final TextField name = new TextField("Name");
  private final TextField email = new TextField("Email");
  private final Button save = new Button("Save", ButtonTheme.PRIMARY);

  public CustomersView(CustomerService service) {
    SpringDataRepository<Customer, Long> data =
        new SpringDataRepository<>(service.repository());
    table.setRepository(data);
    table.addColumn("name", Customer::getName);
    table.addColumn("email", Customer::getEmail);

    BindingContext<Customer> form = BindingContext.of(this, Customer.class, true);
    save.onClick(e -> {
      Customer customer = new Customer();
      if (form.write(customer).isValid()) {
        service.save(customer);
        data.commit();
      }
    });

    getBoundComponent().setDirection(FlexDirection.COLUMN)
        .add(name, email, save, table);
  }
}
```

Each part does one job:

- `@Route("customers")` makes the class a page at `/customers`.
- The constructor receives `CustomerService` from Spring. The view class needs no Spring stereotype, since `@Route` alone is enough for [constructor injection](/docs/integrations/spring/routing).
- `SpringDataRepository` feeds the `Table`. The table pages and sorts through the repository instead of loading every row.
- `BindingContext.of(this, Customer.class, true)` matches the `name` and `email` fields to the bean properties of the same name and switches on Jakarta validation. `form.write(customer)` copies the field values into the bean and returns a result you can check.
- `data.commit()` notifies the repository that its data changed, so the table refreshes.

There is no controller, no data transfer object and no client-side fetch. The click handler calls the service in the same JVM.

## Security at the route {#security}

`@RolesAllowed("ADMIN")` on the view is the access rule. Route security in webforJ is enforced before any component is rendered. With Spring Boot you add the Spring Security starter and register `WebforjSecurityConfigurer` in a `SecurityFilterChain`, and the same annotations (`@AnonymousAccess`, `@PermitAll`, `@RolesAllowed`, `@DenyAll`) work across the app. The [security overview](/docs/security/overview) explains the model, and the [getting started guide](/docs/security/getting-started) shows the configuration.

Because the UI and the services run in one process, the rule you put on the route is the rule that applies. There is no second set of endpoints to remember to protect.

## One build, one deployable {#one-deployable}

A Spring Boot webforJ app runs as an executable JAR with an embedded Tomcat server. That JAR contains the UI, the services and the data access, and it is the only thing you deploy. Without Spring, a webforJ app packages as a WAR for Jetty, Tomcat, or another servlet container.

If you later need a REST endpoint for another client, it can sit beside the UI in the same app. The `webforj.exclude-urls` property lets Spring MVC controllers handle specific paths when webforJ is mapped to the root. You add the API when a consumer exists, not before.

## When a split architecture is still the better choice {#when-to-split}

Keep a separate frontend if several independent clients need the same API from day one, or if your frontend team already ships a JavaScript app and you want to keep it. webforJ also does not remove the need for web skills entirely. Your designer still works in CSS, and you can wrap third-party web components with `ElementComposite` when you need one. What changes is that the default path for a business app is Java from the table down to the button.

## Try it {#try-it}

Generate a Spring Boot project with [startforJ](https://docs.webforj.com/startforj), then add the entity, repository, and view from this post. The [getting started guide](/docs/introduction/getting-started) covers prerequisites and the first run.

To check any framework against the same requirements, use the list in [Java framework for web applications](/blog/java-framework-for-web-applications).

## Frequently asked questions {#faq}

### What is a Java full stack framework? {#faq-what-is-it}

It's a framework that covers the UI, the business logic and the data layer in Java. In webforJ that means components, routing, form binding, route security and Spring Data access in one project that you build and deploy as one artifact.

### Can Java do frontend and backend? {#faq-frontend-and-backend}

Yes. With a full-stack Java framework the screens are Java classes too. In webforJ a view is a Java class built from components and event listeners, it calls your services in the same JVM, and the browser shows the result as a web page. There is no separate JavaScript project for the frontend.

### Is a full stack Java framework a good fit for business apps? {#faq-business-apps}

Yes, when the app is mostly forms, tables, dashboards, and workflows. Those screens need binding, validation, data grids and access rules, and a full stack Java framework gives you all of them in the language your backend already uses. If several independent clients need one public API from the start, a split architecture can still make sense.

### Do I need to know JavaScript to use webforJ? {#faq-javascript}

No. You build views from Java components and style them with CSS. When you want a third-party web component or an npm package, webforJ provides ways to wrap it behind a Java API, so most of the team can stay in Java.

### Does webforJ work with Spring Boot? {#faq-spring-boot}

Yes. Views and routes are created through Spring, so a constructor can take services, repositories, and configuration values. The app runs as an executable JAR with embedded Tomcat, and `SpringDataRepository` connects Spring Data repositories to tables.

### Can I still expose a REST API? {#faq-rest-api}

Yes. Spring MVC controllers can run in the same app. Set `webforj.exclude-urls` so webforJ ignores the paths your controllers handle.
