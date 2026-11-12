---
title: "JSF to Spring Boot migration: Faces views in webforJ"
description: "A JSF to Spring Boot migration with webforJ, a JSF alternative: see how Facelets, backing beans, EL and validators map over, with a before and after view."
slug: jsf-alternative
date: 2026-10-10
authors: Hyyan Abo Fakher
tags: [tutorial, modernization, web development, spring]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-10-10-jsf-alternative/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-10-10-jsf-alternative/cover.jpg)

If you maintain JSF or PrimeFaces screens and are planning a JSF to Spring Boot migration, webforJ is a direct fit, and a JSF alternative that stays in Java. It is a [full-stack Java framework](/blog/java-full-stack-framework): you build each screen as a Java component tree, route to it with an annotation, bind it to a bean, and validate with the same Jakarta constraints you already put on your model.

A migration is mostly a translation exercise. Most JSF concepts have a direct counterpart, and most of them get smaller. This post maps the concepts one by one and then converts a real form, so you can judge the effort on your own views before committing to anything.

<!-- truncate -->

## Why teams look for a Jakarta Faces alternative {#why-look-for-an-alternative}

[Jakarta Faces](https://jakarta.ee/specifications/faces/), formerly JavaServer Faces, is a component-based, server-side UI specification. A page is a Facelets XHTML file, state lives in a backing bean, and a request moves through the phases the specification defines, from restoring the view to rendering the response. It is a standard and it works.

The reasons teams still move are practical. The view is XHTML while the logic is Java, so every screen spans two files and a string-based bridge between them. Expressions such as `#{customerBean.customer.name}` are resolved at runtime, so a rename in Java is not caught by the compiler in the page. And the component set you get depends on which add-on library you chose.

webforJ keeps the server-side model and removes that split. The screen is a Java class, the compiler checks every reference, and the components ship with the framework. If you are weighing other options, the [best Java web framework in 2026](/blog/java-web-frameworks-2026) depends on the job.

## How each concept maps to webforJ {#concept-mapping}

| In JSF | In webforJ |
|---|---|
| Facelets XHTML view | A Java class extending `Composite` |
| `h:` and `f:` tags | Java components such as `TextField` and `Button` |
| Managed or backing bean (`@Named`) | The view class itself, plus services injected into its constructor |
| EL bindings (`#{bean.property}`) | `BindingContext` between fields and a bean |
| Navigation outcomes and rules | `@Route` and `Router.getCurrent().navigate(...)` |
| `f:validateLength`, `required`, Bean Validation | Jakarta Validation through `BindingContext`, plus `Validator` classes |
| `h:messages` | The default binding reporter, shown on each field |
| `f:viewParam` | Route patterns such as `customer/:id` |
| Facelets templates (`ui:insert`) | Route layouts and outlets |
| `h:dataTable` or a PrimeFaces table | `Table` |

The rest of the post takes the central rows in turn.

## Views: from Facelets XHTML to a Java class {#views}

In JSF the view declares components as tags and the container builds the component tree from them. In webforJ you build the same tree yourself in Java. A `Composite` wraps a root component, you add children to it, and the class is the view. There is no template to parse, so layout, conditional rendering, and loops are ordinary Java.

Shared page chrome, which Facelets handles with templates, becomes a [route layout](/docs/routing/route-hierarchy/overview): a parent component that renders child views into an outlet.

## Backing beans: the view is the bean {#backing-beans}

A JSF backing bean holds state and action methods, and the page reaches them through EL. In webforJ the view class holds its own state and event handlers, so most backing beans collapse into the view. Business logic stays in services. Where JSF injects them into the bean, webforJ with Spring Boot injects them into the view constructor, and each navigation creates a fresh view instance. See [Spring routing](/docs/integrations/spring/routing) for the details.

## Navigation: rules become routes {#navigation}

JSF navigation runs through a navigation handler. An action method returns an outcome and the navigation handler decides the next view. In webforJ the target is a class:

```java
Router.getCurrent().navigate(CustomerListView.class);
```

Each view declares its own URL with `@Route`, so you can rename a path in one annotation without touching the code that navigates to it. The [routing overview](/docs/routing/overview) covers parameters, layouts, and navigation observers.

## Validation: constraints stay on the bean {#validation}

JSF integrates with Bean Validation and also offers validator tags and `required` on inputs. If your model classes carry Jakarta constraints, they keep working. Pass `true` to `BindingContext` and it enforces them on the bound fields. For rules that are not annotations, implement the `Validator` interface. The [data binding docs](/docs/data-binding/overview) cover the full set.

## A JSF form, before and after {#before-and-after}

Start with a typical JSF customer form. The Facelets page:

```xml
<h:form>
  <h:outputLabel for="name" value="Name"/>
  <h:inputText id="name" value="#{customerBean.customer.name}" required="true">
    <f:validateLength minimum="3" maximum="40"/>
  </h:inputText>

  <h:outputLabel for="email" value="Email"/>
  <h:inputText id="email" value="#{customerBean.customer.email}"/>

  <h:commandButton value="Save" action="#{customerBean.save}"/>
  <h:messages/>
</h:form>
```

And its backing bean:

```java
@Named
@ViewScoped
public class CustomerBean implements Serializable {
  @Inject
  private CustomerService service;

  private Customer customer = new Customer();

  public String save() {
    service.save(customer);
    return "customers?faces-redirect=true";
  }

  public Customer getCustomer() {
    return customer;
  }
}
```

In webforJ the constraints move onto the bean, which is also where Jakarta Validation expects them:

```java
public class Customer {
  @NotBlank(message = "Name is required")
  @Size(min = 3, max = 40, message = "Name must be 3 to 40 characters")
  private String name;

  @Email(message = "Enter a valid email")
  private String email;

  // getters and setters
}
```

The page and the backing bean become one class:

```java
@Route("/customers/new")
@FrameTitle("New customer")
public class CustomerFormView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final TextField name = new TextField("Name");
  private final TextField email = new TextField("Email");
  private final Button save = new Button("Save", ButtonTheme.PRIMARY);
  private final BindingContext<Customer> context;
  private final Customer customer = new Customer();

  public CustomerFormView(CustomerService service) {
    self.setDirection(FlexDirection.COLUMN).setSpacing("1em").add(name, email, save);

    context = BindingContext.of(this, Customer.class, true);

    save.onClick(e -> {
      if (context.write(customer).isValid()) {
        service.save(customer);
        Router.getCurrent().navigate(CustomerListView.class);
      }
    });
  }
}
```

`BindingContext.of` pairs the `name` and `email` fields with the bean properties of the same name. `write` copies the field values into the bean and returns a `ValidationResult`. The default reporter shows messages for invalid fields as a popover or inline beneath the field, so there is no `h:messages` equivalent to place by hand.

## Custom validators and edit routes {#validators-and-routes}

A JSF custom validator is a class registered with the framework and referenced from a tag. The webforJ equivalent is a plain class implementing `Validator<T>`, attached to a single binding:

```java
public class NoTestAccountValidator implements Validator<String> {
  @Override
  public ValidationResult validate(String value) {
    if (value != null && value.endsWith("@example.test")) {
      return ValidationResult.invalid("Use a real email address");
    }
    return ValidationResult.valid();
  }
}

// Attach it to the email binding
context.bind(email, "email")
    .useValidator(new NoTestAccountValidator())
    .add();
```

The same applies to loading an existing record. Where a JSF page receives an `id` through a view parameter, a webforJ route declares the parameter in its pattern and reads it before the view appears:

```java
@Route("customer/:id<[0-9]+>")
public class CustomerEditView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService service;

  public CustomerEditView(CustomerService service) {
    this.service = service;
  }

  @Override
  public void onWillEnter(WillEnterEvent event, ParametersBag parameters) {
    int id = parameters.getInt("id").orElse(0);
    Customer customer = service.find(id);
    // read the customer into the bound fields
  }
}
```

The `<[0-9]+>` constraint in the pattern means non-numeric ids never reach the view.

## Replacing component libraries {#replacing-primefaces}

Component libraries for JSF exist because the base tag set is small. webforJ ships a larger built-in set, including `Table` for data grids, dialogs, drawers, tabbed panes, and an app layout. The [Table docs](/docs/components/table/overview) show columns, sorting, filtering, and renderers. For a widget the set lacks, `ElementComposite` wraps any web component in a typed Java class. If you are coming from PrimeFaces specifically, the [PrimeFaces alternative](/blog/primefaces-alternative) post covers the rich component set and data binding.

## Migration order from JSF to Spring Boot {#migration-order}

Start with leaf screens, such as a single form or report, and leave the screens with the heaviest Facelets templating for last. Port the model constraints and services unchanged, since they have no UI dependency. Build the layout route once, then move views behind it one at a time.

## Frequently asked questions {#faq}

### What is the best JSF alternative for Java teams? {#faq-best}

For teams that want to keep writing the UI in Java, webforJ is a strong choice. It keeps the server-side component model that JSF users know, but replaces XHTML views with Java classes and EL bindings with a typed `BindingContext`.

### Is the Faces standard deprecated? {#faq-deprecated}

No. Jakarta Faces is an active Jakarta EE specification, and the [specification page](https://jakarta.ee/specifications/faces/) lists version 4.1 as current for Jakarta EE 11. Teams move for developer experience, not because the standard is gone.

### Can I keep my model constraints? {#faq-validation}

Yes. Annotated model classes work as they are. Create the `BindingContext` with the Jakarta validator enabled and it applies the constraints to the bound fields.

### Does webforJ work with Spring Boot? {#faq-spring}

Yes. With Spring Boot on the classpath, services are injected into view constructors. See [Spring Boot integration](/docs/integrations/spring/spring-boot) for project setup.
