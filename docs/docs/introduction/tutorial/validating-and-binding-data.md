---
title: Validating and Binding Data
sidebar_position: 5
pagination_next: null
description: Step 4 - Learn how to add validation checks.
---

[Data binding](../../data-binding/overview.md) connects UI components directly with your data model, enabling automatic synchronization of values. This reduces boilerplate and improves reliability. Validation checks that form data follows rules such as being non-empty or matching a pattern. With webforJ and Spring Boot, you can use Jakarta validation annotations and webforJ’s binding system for a user-friendly experience.


```
webforj-demo-application
│   .gitignore
│   LICENSE
│   README.md
│
├───1-creating-a-basic-app  
├───2-working-with-data
├───3-scaling-with-routing-and-composites
// highlight-next-line
└───4-validating-and-binding-data
```

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/validating-and-binding-data.mp4" type="video/mp4"/>
  </video>
</div>


## Binding the fields

The data binding setup begins with initializing a `BindingContext` for the `Customer` model. The `BindingContext` links model properties to form fields, enabling automatic data sync. This is set up in the `FormView` constructor:

```java title="FormView.java"
context = BindingContext.of(this, Customer.class, true);
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

The third parameter (`true`) enables Jakarta validation.

:::info
This uses auto-binding as described in the [Data Binding Article](../../data-binding/automatic-binding). Field names in the data model and form must match, or you can use the `UseProperty` annotation to map them.
:::


## Data binding with `onDidEnter()`

The `onDidEnter` method uses the binding context to populate the form fields. Instead of setting each value manually, the context synchronizes the UI with the model:

```java title="FormView.java"
@Override
public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
  parameters.get("id").ifPresent(id -> {
    customer = customerService.getCustomerByKey(Long.parseLong(id));
    customerId = Long.parseLong(id);
  });
  context.read(customer);
}
```


## Validating data

Validation is handled by Jakarta annotations in the `Customer` entity:

```java title="Customer.java"
@NotEmpty(message = "Customer name is required")
@Pattern(regexp = "[a-zA-Z]*", message = "Invalid characters")
@Column(name = "first_name")
private String firstName = "";
```

**Annotation overview:**

- `@NotEmpty` and `@Pattern` are [Jakarta Validation](https://beanvalidation.org/) annotations. They declare validation rules directly on the model property:
  - `@NotEmpty` requires the value to be non-empty.
  - `@Pattern` restricts input to the specified regular expression (here, only letters).

**Other common Jakarta Validation annotations:**

- `@NotNull`: Value must not be null.
- `@NotBlank`: String must not be null and must contain at least one non-whitespace character.
- `@Size(min=, max=)`: String, collection, or array must have a length/size within the given bounds.
- `@Email`: Value must be a valid email address.
- `@Min` / `@Max`: Numeric value must be within the specified range.
- `@Positive` / `@Negative`: Value must be positive or negative.
- `@Past` / `@Future`: Date/time value must be in the past or future.
- `@Digits(integer=, fraction=)`: Number must have the specified number of integer and fraction digits.

See the [Jakarta Bean Validation constraints reference](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary.html) for a full list.

webforJ integrates Jakarta validation via the `BindingContext` (with validation enabled), so these constraints are automatically checked when binding data. For more, see [Jakarta Validation in webforJ](../../data-binding/validation/jakarta-validation.md).


The binding context disables the submit button if the form is invalid:

```java title="FormView.java"
context.onValidate(e -> submit.setEnabled(e.isValid()));
```


## Adding and editing entries with validation

The `submitCustomer()` method validates data using the binding context before add or edit operations. Only valid data is processed:

```java title="FormView.java"
private void submitCustomer() {
  ValidationResult results = context.write(customer);
  if (results.isValid()) {
    if (customerId.intValue() == 0) {
      customerService.createCustomer(customer);
    } else {
      customerService.updateCustomer(customer);
    }
    Router.getCurrent().navigate(DemoView.class);
  }
}
```

With these changes, the app now supports data binding and validation using Spring Boot and webforJ. Form inputs are synchronized with the model and checked against validation rules automatically.

## Running the app {#running-the-app}

When you’ve finished this step, you can compare it to [4-validating-and-binding-data](https://github.com/webforj/webforj-demo-application/tree/main/4-validating-and-binding-data) on GitHub. To see the app in action:

1. Navigate to the top level directory containing the `pom.xml` file, this is `4-validating-and-binding-data` if you're following along with the version on GitHub.

2. Use the following Maven command to run the Spring Boot app locally:
    ```bash
    mvn
    ```

3. Open your browser and go to http://localhost:8080 to view the app.


:::info Next steps
Looking for more ways to improve your app from this tutorial? You can try using the [`AppLayout`](/docs/components/app-layout) component as a wrapper to add your customer table and add more features.
:::