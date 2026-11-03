---
title: "Java full stack framework: UI to database in one codebase"
description: "webforJ is a Java full stack framework: UI, routing, forms, security and data in one Java codebase with Spring Boot, built and shipped as a single artifact."
slug: java-full-stack-framework
date: 2026-09-08
authors: Eric Handtke
tags: [community, full-stack, spring, back end]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-09-08-java-full-stack-framework/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-09-08-java-full-stack-framework/cover.jpg)

webforJ is a Java full stack framework. You write the UI, routing, forms, validation, security and data access in Java, inside one project, with one build. On Spring Boot it ships as a single executable JAR, and your views receive services and repositories through constructor injection like any other Spring bean.

That removes the second half of the usual "Java backend plus JavaScript frontend" setup: the separate frontend project, the API contract between the two, and the validation you write twice. This post lists what a full stack Java app contains in webforJ, then walks through a small slice from entity to table to form.

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

Everything in that table is Java, compiled by one toolchain, and tested with the tools you already use for the rest of your backend.

## What a Java backend plus JavaScript frontend costs {#cost-of-the-split}

The split architecture is common and it works. It is the right call when several clients consume the same API, for example a mobile app next to a partner integration. The cost is easy to underestimate when the only client is your own web UI.

- **Two builds.** The Java project and the frontend project each have their own dependencies, build tool, test setup and deployment step.
- **An API contract.** Every screen needs endpoints, request and response shapes, and error handling on both sides. A change to a Java model means a change to the client that reads it.
- **Duplicated validation.** The server must validate because it can't trust the client. The form validates too, so users get feedback before they submit. Those two rule sets drift apart.
- **Two skill sets.** Someone has to own the frontend toolchain and the component framework on top of it, and a Java developer can't pick up that work without learning it.

None of this is a flaw in the approach. It is overhead you only want to pay when a second consumer makes the contract worth having.

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

## Frequently asked questions {#faq}

### What is a Java full stack framework? {#faq-what-is-it}

It's a framework that covers the UI, the business logic and the data layer in Java. In webforJ that means components, routing, form binding, route security and Spring Data access in one project that you build and deploy as one artifact.

### Do I need to know JavaScript to use webforJ? {#faq-javascript}

No. You build views from Java components and style them with CSS. When you want a third-party web component or an npm package, webforJ provides ways to wrap it behind a Java API, so most of the team can stay in Java.

### Does webforJ work with Spring Boot? {#faq-spring-boot}

Yes. Views and routes are created through Spring, so a constructor can take services, repositories, and configuration values. The app runs as an executable JAR with embedded Tomcat, and `SpringDataRepository` connects Spring Data repositories to tables.

### Can I still expose a REST API? {#faq-rest-api}

Yes. Spring MVC controllers can run in the same app. Set `webforj.exclude-urls` so webforJ ignores the paths your controllers handle.
