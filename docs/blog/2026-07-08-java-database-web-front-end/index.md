---
title: "Java web front end for a database: browse and edit"
description: "Build a Java web front end for a database you already have. Map tables with JPA, show them in a webforJ Table, edit with validation, and secure the screen."
slug: java-database-web-front-end
date: 2026-07-08
authors: Eric Handtke
tags: [tutorial, spring, table, security]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-07-08-java-database-web-front-end/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-07-08-java-database-web-front-end/cover.jpg)

You have a database that has been running for years. The tables are in good shape, a few Java services already read them, and now someone wants a screen where people can browse the rows, search them and fix a value without opening a SQL client.

A Java web front end for a database needs four things: an entity mapped to the table, a repository, a grid, and a form. With webforJ, the full-stack Java framework, you write all of it in Java. Spring Data JPA reads the table, the `SpringDataRepository` adapter hands it to a webforJ `Table`, data binding drives the edit form, and route annotations lock the screen down. No separate front-end project, no REST layer in between.

<!-- truncate -->

This post is about the case where the database already exists. You map what is there and leave the schema alone.

## What you need {#what-you-need}

- A relational database with a JDBC driver on the classpath
- A Spring Boot project with the Spring Data JPA starter and the webforJ starter
- One table you want to show first

Pick one table. Get it working end to end, then repeat the steps for the next one.

## Step 1: map the table with JPA {#step-1-map-the-table}

Map the existing table to an entity. This is plain JPA and has nothing to do with webforJ. Use `@Entity` and `@Table(name = "...")` for the table, `@Id` for the key, and `@Column(name = "...")` wherever a column name differs from your field name.

Three rules keep an existing schema safe:

1. Point Spring Boot at the real database in `application.properties`.
2. Do not let Hibernate generate or change the schema. Set `spring.jpa.hibernate.ddl-auto` to `none` or `validate`.
3. Map only the columns you need. Unmapped columns stay untouched.

The [Spring Boot reference](https://docs.spring.io/spring-boot/reference/data/sql.html) describes the data source and JPA properties, and the [Spring Data JPA reference](https://docs.spring.io/spring-data/jpa/reference/) covers entity mapping in detail. If the table has a composite key or a view behind it, those pages show the options.

## Step 2: create the repository {#step-2-create-the-repository}

Declare a Spring Data repository for the entity. For full compatibility with webforJ components, extend both `JpaRepository` and `JpaSpecificationExecutor`. This is the example from the [Spring Data JPA integration page](/docs/integrations/spring/spring-data-jpa):

```java title="PersonRepository.java"
@Repository
public interface PersonRepository
  extends JpaRepository<Person, Long>,
      JpaSpecificationExecutor<Person> {
  // Custom query methods can go here
}
```

Why both? The adapter in the next step checks which Spring Data interfaces the repository implements and switches features on to match. `JpaRepository` brings paging and sorting. `JpaSpecificationExecutor` brings dynamic filtering. Your existing repositories, if you have them, work as they are.

## Step 3: show the rows in a Table {#step-3-show-the-rows}

Wrap the repository in `SpringDataRepository` and give it to the [`Table`](/docs/components/table/overview). Then declare the columns:

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
    table.addColumn("name", Person::getFullName)
          .setPropertyName("firstName"); // Sort by actual JPA property
    table.addColumn("email", Person::getEmail);
    table.addColumn("city", Person::getCity);

    // Enable sorting
    table.getColumns().forEach(column -> column.setSortable(true));
  }
}
```

Spring injects the repository into the view constructor. The `SpringDataRepository` adapter checks which Spring Data interfaces your repository implements and turns on the matching features: paginated queries, sorting from the column headers, and filtering with JPA Specifications. The [Spring Data JPA page](/docs/integrations/spring/spring-data-jpa) lists what each interface enables.

## Step 4: sort, filter and page {#step-4-sort-filter-page}

Three features matter on a database screen, and each one maps to a database operation.

**Sorting.** `setSortable(true)` on a column lets users click the header. The adapter turns the click into an `ORDER BY`. For a column that shows a computed value, call `setPropertyName()` and name the real JPA property. Without it, sorting a computed column does not work. If users need to sort by more than one column, enable `setMultiSorting(true)`, as the [sorting docs](/docs/components/table/sorting) describe. Multi-column sorting is off by default.

**Filtering.** `SpringDataRepository` accepts a JPA `Specification`. Call `repository.setFilter(spec)` and the filter applies to both the row query and the count query. Pass `null` to clear it. A search field typically builds a `Specification` from its text and sets it on the repository. The docs page shows a single-condition filter and a combined one with `cb.and(...)`.

**Paging.** A repository that implements `PagingAndSortingRepository`, which `JpaRepository` does, gives the adapter paginated queries. The table requests a slice, the database returns that slice, and the count query reports the total. The [querying data guide](/docs/advanced/repository/querying-data) shows how offset and limit travel through the repository criteria if you want to see what the adapter sends.

A practical order of work:

1. Make the table load.
2. Mark the columns users will sort by.
3. Add one search field wired to `setFilter`.
4. Extend it to the one or two columns people actually search on.

Resist adding a filter for every column on day one. Check what users ask for first.

## Step 5: add the edit form {#step-5-add-the-edit-form}

Reading is half the job. For editing, build a form with one field per column the user may change, then bind the form to the entity.

The [data binding](/docs/data-binding/overview) feature connects form fields to bean properties. `BindingContext.of(this, Person.class, true)` matches the fields declared in your form to the entity properties by name. `context.read(bean)` fills the form from the selected row. `context.write(bean)` copies the form values back and returns a result you check with `isValid()`.

The third argument, `true`, turns on Jakarta Bean Validation. Put the constraints on the entity and the form reports them. This is the pattern from the [Jakarta validation page](/docs/data-binding/validation/jakarta-validation):

```java
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

Hibernate Validator or another Bean Validation implementation must be on the classpath. Spring Boot projects usually add it through the validation starter.

Then wire the save button:

1. Read the selected row from the table and call `context.read(entity)`.
2. On save, call `context.write(entity)`.
3. If the result is valid, call `personRepository.save(entity)`. This is the normal Spring Data call.
4. Call `commit()` on the repository you gave the table. `commit()` is the call that notifies connected components, and the `Table` updates when it receives the commit event. The [editing and refreshing page](/docs/components/table/refreshing) shows the pattern.

Existing databases often carry rules the entity cannot see, such as a unique key, a foreign key, or a check constraint. Let the database enforce them, catch the exception from `save`, and show a message in the form. Annotations cover shape and range. The database covers integrity.

If you want a view of how a data-heavy screen formats its cells, see [table renderers for custom cells](/blog/table-renderers-custom-cells).

## Step 6: protect the screen {#step-6-protect-the-screen}

A screen that edits production data needs a login and a role check. webforJ route security works with Spring Security and uses annotations on the route, so the rule sits next to the view it protects. The [getting started guide](/docs/security/getting-started) shows the `SecurityConfig` class and the login view. After that, each route carries one annotation.

Put `@RolesAllowed("ADMIN")` next to `@Route` on the view class. Users without the role land on the access denied page. This is the pattern from the [security annotations](/docs/security/annotations) page.

The annotations are `@AnonymousAccess`, `@PermitAll`, `@RolesAllowed` and `@DenyAll`. For a database screen, a good split is `@PermitAll` for the read-only table and `@RolesAllowed` for the edit view. To make every route without an annotation require a login, set `webforj.security.secure-by-default=true`.

A hidden button is no access control. The route annotation is.

## Why this beats a low-code tool {#why-this-beats-a-low-code-tool}

A generated admin screen is quick to start. It gets hard to live with when the business asks for a rule that the tool does not offer.

| Need | In a generated tool | In webforJ |
| --- | --- | --- |
| A custom filter on two columns | Whatever the tool exposes | Any JPA `Specification` |
| A computed column | Often a workaround | A column backed by a Java method |
| Validation rules | Tool configuration | Jakarta annotations on the entity |
| Access rules | Tool roles | Spring Security with route annotations |
| Tests and code review | Hard to diff | Java source in your repository |
| Reusing existing services | Needs an API | Inject them in the view |

Your screens are Java classes. Your team already reads, reviews and tests Java. The same database access code serves the screen and any other consumer. Nothing about the front end lives in a vendor console.

## Common pitfalls {#common-pitfalls}

- **Hibernate rewrites the schema.** Check `ddl-auto` before the first run against a real database.
- **Sorting a computed column.** Set `setPropertyName()` to a real property.
- **Lazy associations.** A column that reads a lazy relation can trigger extra queries per row. Show a flat field or use a query that fetches what the grid needs.
- **Editing the entity directly.** If the form should be able to cancel without side effects, work on a copy.
- **Missing validation implementation.** Without it on the classpath, the annotations do nothing.

## Checklist {#checklist}

- [ ] Entity maps the existing table, schema generation is off
- [ ] Repository extends `JpaRepository` and `JpaSpecificationExecutor`
- [ ] `SpringDataRepository` is set on the `Table`
- [ ] Sortable columns name a real JPA property
- [ ] Search sets a `Specification` with `setFilter`
- [ ] Edit form uses `BindingContext` with Jakarta validation
- [ ] Save calls the Spring Data repository, then `commit()` on the table repository
- [ ] Routes carry a security annotation
- [ ] Secure-by-default is on

## Next step {#next-step}

Generate a project with [startforJ](https://docs.webforj.com/startforj), add your data source, and map your first table. The [Spring Data JPA integration page](/docs/integrations/spring/spring-data-jpa) has the full adapter reference.

## Frequently asked questions {#faq}

### How do I build a web UI for an existing database in Java? {#faq-web-ui-existing-database}

Map the table with a JPA entity, expose it through a Spring Data repository, and pass that repository to a webforJ `Table` through `SpringDataRepository`. Add a form with data binding for edits and route annotations for access. Everything is Java, and the schema stays as it is.

### How do sorting and filtering reach the database? {#faq-load-whole-table}

Through your Spring Data repository. The `SpringDataRepository` adapter hands sorting, filters and paging to it, and which of those are available depends on the interfaces the repository implements, such as `PagingAndSortingRepository` or `JpaSpecificationExecutor`.

### Can I browse and edit database tables in a Java web app without a REST API? {#faq-no-rest-api}

Yes. The view runs on the server in the same Java process as your repository, so it calls the repository directly. You do not write endpoints or a separate client.
