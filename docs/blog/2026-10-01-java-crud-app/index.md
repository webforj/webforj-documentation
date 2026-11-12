---
title: "Java CRUD web app with Spring Boot and webforJ, step by step"
description: "Build a Java CRUD web app with Spring Boot and webforJ: a JPA entity, a sortable and filterable Table, a validated form, and a confirm dialog, all in Java."
slug: java-crud-app
date: 2026-10-01
authors: Hyyan Abo Fakher
tags: [tutorial, spring, table]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-10-01-java-crud-app/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-10-01-java-crud-app/cover.jpg)

A Java CRUD web app in webforJ is one Spring Data repository and one view class. A `Table` backed by `SpringDataRepository` lists and sorts the entities, a `BindingContext` with Jakarta validation drives the create and edit form, and a `ConfirmDialog` guards the delete. The whole screen is Java, so there is no REST layer to write, no JavaScript to wire up, and no template language to learn.

This tutorial builds that screen for a `Customer` entity. By the end you have a list with sorting, search, and paging, a form that validates before it saves, and a delete that asks first. It's the shape of a Spring Boot CRUD UI you can reuse for any entity.

<!-- truncate -->

## What a Spring Boot CRUD UI needs {#what-a-spring-boot-crud-ui-needs}

Every CRUD screen has the same five parts. webforJ gives each one a direct answer in Java:

| Part | webforJ answer |
| --- | --- |
| Persistence | A Spring Data JPA repository |
| List with paging, sorting, filtering | `Table` with `SpringDataRepository` |
| Create and edit form | `BindingContext` on a bean |
| Validation | Jakarta Bean Validation annotations on the entity |
| Delete with confirmation | `ConfirmDialog` through `OptionDialog` |

The view is a Spring bean, so the repository arrives through the constructor like any other dependency. Start from a project generated with [startforJ](https://docs.webforj.com/startforj) using the Spring flavor, and add Spring Data JPA, a database driver, and a Jakarta Bean Validation implementation such as Hibernate Validator.

## Define the entity {#define-the-entity}

The entity holds the data and the rules. Validation lives here, on the properties, so the form never repeats it:

```java
@Entity
public class Customer {
  @Id
  @GeneratedValue
  private Long id;

  @NotBlank(message = "Name is required")
  @Size(max = 60, message = "Name is too long")
  private String name;

  @NotBlank(message = "Email is required")
  @Email(message = "Not a valid email address")
  private String email;

  private String city;

  // getters and setters
}
```

The messages you write here are what the user sees next to a field that fails. You can change them without touching the view. The [Jakarta validation page](/docs/data-binding/validation/jakarta-validation) lists the constraints webforJ recognizes on bean properties. If the table already exists, you can [map it and leave the schema alone](/blog/java-database-web-front-end).

## Create the repository {#create-the-repository}

For a webforJ table, extend both `JpaRepository` and `JpaSpecificationExecutor`:

```java
public interface CustomerRepository
    extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {}
```

`SpringDataRepository` looks at which Spring Data interfaces the repository implements and uses them. Paging and sorting come from `PagingAndSortingRepository`, which `JpaRepository` includes. Filtering comes from `JpaSpecificationExecutor`. Details are in the [Spring Data JPA guide](/docs/integrations/spring/spring-data-jpa).

## Show the data in a Table {#table-paging-sorting-filtering}

Wrap the repository in a `SpringDataRepository` and hand it to the `Table`. Because the repository implements the Spring Data paging interfaces, the adapter runs paginated queries against the database. The `Table` also supports selection, renderers and lazy loading, which a guide to the [Java data grid](/blog/java-data-grid) covers.

Three settings matter for CRUD:

- **Sorting.** Mark each column sortable. When a column shows a computed value, call `setPropertyName` so the adapter knows which JPA property goes into the `ORDER BY`.
- **Filtering.** `setFilter` takes a JPA `Specification`. The repository applies it to both the row query and the count. Call `commit()` afterwards so the table reloads.
- **Row actions.** An `IconButtonRenderer` column puts an edit and a delete button on every row, and `e.getItem()` returns that row's customer. The key provider gives every row a stable identity.

The following view puts the list, the search field, the New button and the row actions together:

```java
@Route("/")
public class CustomerView extends Composite<FlexLayout> {
  private final CustomerRepository customers;
  private final SpringDataRepository<Customer, Long> repository;
  private final Table<Customer> table = new Table<>();
  private final Dialog dialog = new Dialog();
  private final Span dialogTitle = new Span();
  private final CustomerForm form = new CustomerForm(this::save);

  public CustomerView(CustomerRepository customers) {
    this.customers = customers;

    repository = new SpringDataRepository<>(customers);
    repository.setKeyProvider(Customer::getId);
    table.setRepository(repository);
    table.addColumn("name", Customer::getName).setLabel("Name").setSortable(true);
    table.addColumn("email", Customer::getEmail).setLabel("Email").setSortable(true);
    table.addColumn("city", Customer::getCity).setLabel("City").setSortable(true);

    // Every row gets its own edit and delete button. e.getItem() is the customer of that row.
    IconButtonRenderer<Customer> edit = new IconButtonRenderer<>(TablerIcon.create("pencil"));
    edit.addClickListener(e -> open(e.getItem(), "Edit customer"));
    table.addColumn("edit", c -> "").setRenderer(edit).setWidth(56);

    IconButtonRenderer<Customer> delete =
        new IconButtonRenderer<>(TablerIcon.create("trash").setTheme(Theme.DANGER));
    delete.addClickListener(e -> delete(e.getItem()));
    table.addColumn("delete", c -> "").setRenderer(delete).setWidth(56);

    TextField search = new TextField("Search");
    search.onModify(e -> {
      String text = "%" + search.getValue().toLowerCase() + "%";
      repository.setFilter((root, query, cb) -> cb.like(cb.lower(root.get("name")), text));
      repository.commit();
    });

    Button add = new Button("New", ButtonTheme.PRIMARY, e -> open(new Customer(), "New customer"));

    dialog.addToHeader(dialogTitle);
    dialog.addToContent(form);

    getBoundComponent().setDirection(FlexDirection.COLUMN);
    getBoundComponent().add(search, add, table, dialog);
  }

  private void open(Customer customer, String title) {
    dialogTitle.setText(title);
    form.edit(customer);
    dialog.open();
  }

  private void save(Customer customer) {
    customers.save(customer);
    dialog.close();
    repository.commit();
  }

  private void delete(Customer customer) {
    ConfirmDialog.Result answer = OptionDialog.showConfirmDialog(
        "Delete " + customer.getName() + "?", "Delete customer",
        ConfirmDialog.OptionType.OK_CANCEL);
    if (answer == ConfirmDialog.Result.OK) {
      customers.delete(customer);
      repository.commit();
    }
  }
}
```

Typing in the search field narrows the list as the user types. To clear it, call `repository.setFilter(null)` and then `commit()`.

## Build the edit form {#create-and-edit-form}

One form serves both create and edit. A `BindingContext<Customer>` links each field to a property of the bean. Passing `true` as the second constructor argument turns on the Jakarta validator, so the constraints on the entity are enforced with no extra code:

```java
public class CustomerForm extends Composite<FlexLayout> {
  private final TextField name = new TextField("Name");
  private final TextField email = new TextField("Email");
  private final TextField city = new TextField("City");
  private final BindingContext<Customer> context = new BindingContext<>(Customer.class, true);
  private Customer customer;

  public CustomerForm(Consumer<Customer> onSave) {
    context.bind(name, "name").add();
    context.bind(email, "email").add();
    context.bind(city, "city").add();

    Button save = new Button("Save", ButtonTheme.PRIMARY, e -> {
      if (context.write(customer).isValid()) {
        onSave.accept(customer);
      }
    });

    getBoundComponent().setDirection(FlexDirection.COLUMN);
    getBoundComponent().add(name, email, city, save);
  }

  public void edit(Customer customer) {
    this.customer = customer;
    context.read(customer);
  }
}
```

The two calls that matter are `read` and `write`:

- `read(customer)` fills the fields from the bean. For a new customer it shows empty fields, for an existing one it shows the saved values.
- `write(customer)` validates the fields and, only if they pass, copies the values into the bean. It returns a `ValidationResult`. When a field fails, it shows the message from your annotation, and the form stays open.

The form takes a callback instead of a repository. That keeps it free of persistence code, so the same class works for any caller that wants a validated `Customer`. More on binding is in the [data binding overview](/docs/data-binding/overview), and the post on [Java form validation](/blog/java-form-validation) goes deeper on validators and error display.

## Confirm before delete {#delete-with-a-confirm-dialog}

`OptionDialog.showConfirmDialog` shows a modal dialog and blocks the calling code until the user answers. That lets the delete read like plain sequential Java: ask, check the result, delete. The `OK_CANCEL` option type gives the user an `OK` and a `Cancel` button, and the result comes back as a `ConfirmDialog.Result`. Other option types such as `YES_NO` and a `WARNING` message type are covered on the [Confirm dialog page](/docs/components/option-dialogs/confirm).

## Refresh the table after save {#refresh-after-save}

After `customers.save` or `customers.delete`, the database has changed but the table does not know yet. Calling `repository.commit()` tells every component connected to the repository to reload. The table reloads its rows. For a single changed row, `commit(entity)` updates just that row, which matters on large tables. The [Repository overview](/docs/advanced/repository/overview) explains both forms.

## Common pitfalls {#common-pitfalls}

- **Sorting a computed column does nothing.** A column built from a method such as `getFullName()` has no matching JPA property, so the database has nothing to sort by. Call `setPropertyName("firstName")` on the column.
- **Search does not filter.** `setFilter` only stores the filter. Call `repository.commit()` after it so the table runs the query again.
- **The form saves invalid data.** Always branch on `context.write(bean).isValid()` before you call the repository.
- **The table shows stale rows.** Call `commit()` after every write to the repository.
- **Validation messages never appear.** Pass `true` to the `BindingContext` constructor to activate the Jakarta validator, and keep a Bean Validation implementation on the classpath.

## Next step {#next-step}

Generate a Spring Boot project with [startforJ](https://docs.webforj.com/startforj), add the entity, repository, view, and form from this tutorial for an entity of your own, and open the app.

## Frequently asked questions {#faq}

### Do I need JavaScript or a REST API? {#faq-no-javascript}

Yes. In webforJ the view runs on the server, and the `Table`, the form, and the dialogs are Java objects. The view calls the Spring Data repository directly, so there is no controller layer and no client code to keep in sync.

### How does the Table handle large data? {#faq-large-data}

`SpringDataRepository` detects the paging and sorting interfaces of your Spring Data repository and uses them, and it applies your `Specification` filter to the queries. The database does the sorting and filtering work.

### Is Jakarta validation required? {#faq-jakarta-validation}

No. You can attach validators to a binding in code instead. Putting the constraints on the entity as Jakarta annotations keeps the rules in one place, and `new BindingContext<>(Customer.class, true)` enforces them in the form.

### Can the same form handle create and edit? {#faq-create-and-edit}

Yes. Call `form.edit(new Customer())` for a create and `form.edit(e.getItem())` from the row's edit button. Both paths run through `read` and `write`, so validation behaves the same.
