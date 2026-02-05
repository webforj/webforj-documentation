---
title: Validating and Binding Data
sidebar_position: 5
pagination_next: null
description: Step 4 - Learn how to add validation checks and bind data.
---

The `FormView` created in [Scaling with Routing and Composites](/docs/introduction/tutorial/validating-and-binding-data) added a UI for updating a data model. This step uses [Data binding](/docs/data-binding/overview), which connects UI components directly to the data model for automatic value synchronization. This reduces boilerplate in your app and lets you add validation checks to the Spring entity `Customer`, making your users provide complete and accurate information when filling out forms. This step covers the following concepts:

- Jakarta validation
- Using the [`BindingContext`](https://javadoc.io/doc/com.webforj/webforj-data/latest/com/webforj/data/binding/BindingContext.html) class

Completing this step creates a version of [4-validating-and-binding-data](https://github.com/webforj/webforj-demo-application/tree/main/4-validating-and-binding-data).

<!-- <div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/validating-and-binding-data.mp4" type="video/mp4"/>
  </video>
</div> -->

## Binding the fields {#binding-the-fields}

Before data binding, each field in `FormView` required an event listener to sync with a Spring entity `Customer` manually. Creating a `BindingContext` in `FormView` binds and automatically syncs the `Customer` data model to the UI components.

Adding a third parameter (`true`) when creating the `BindingContext` enables Jakarta validation. Doing this also allows you to set a validation event that makes the submit button only clickable on valid forms. Validity can be defined using various Jakarta annotations on the `Customer` class directly.

You’ll add the validation definitions for `Customer` later in this step, at [Defining data validation](#defining-data-validation
).

```java title="FormView.java" {4-5}
public FormView(CustomerService customerService) {
  this.customerService = customerService;

  context = BindingContext.of(this, Customer.class, true);
  context.onValidate(e -> submit.setEnabled(e.isValid()));
  
  fillCountries();

  self.setMaxWidth("600px");
  self.setHeight("100dvh");
  self.addClassName("form");
  self.add(columnsLayout);
} 
```

With the data model and UI components bound, you can now remove the event listeners to each field:

**Before**
```java title="FormView.java"
// Without data binding
TextField firstName = new TextField("First Name", e -> customer.setFirstName(e.getValue()));
TextField lastName = new TextField("Last Name", e -> customer.setLastName(e.getValue()));
TextField company = new TextField("Company", e -> customer.setCompany(e.getValue()));
ChoiceBox country = new ChoiceBox("Country",
    e -> customer.setCountry(Country.valueOf(e.getSelectedItem().getText())));
```

**After**
```java title="FormView.java"
// With data binding
TextField firstName = new TextField("First Name");
TextField lastName = new TextField("Last Name");
TextField company = new TextField("Company");
ChoiceBox country = new ChoiceBox("Country");
```


### Binding by property names {#binding-by-property-names}

Since each component's name matched to the data model, webforJ applied [Automatic Binding](/docs/data-binding/automatic-binding). If the names didn't match, you could use the `@UseProperty` annotation to map them.

```java
@UseProperty("firstName")
TextField firstNameField = new TextField("First Name");
```

## Reading data on the `DidEnterObserver` {#reading-data-on-the-didenterobserver}

The `onDidEnter()` method uses the binding context to populate the form fields. Instead of setting each value manually, the context synchronizes the UI with the model inside `FormView`:

```java title="FormView.java" {7}
@Override
public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
  parameters.getInt("id").ifPresent(id -> {
    customerId = Long.valueOf(id);
    customer = customerService.getCustomerByKey(customerId);
    // Removed each setValue() method for the UI components
    context.read(customer);
  });
}
```

## Adding validation to `submitCustomer()` {#adding-validation-to-submitcustomer}

The last change to `FormView` for this step will be adding a safeguard to the `submitCustomer()` method. Writing the current data context to a `ValidationResult` allows the app to perform a final validation before committing those changes to the H2 database:

```java title="FormView.java" {2-3}
private void submitCustomer() {
  ValidationResult results = context.write(customer);
  if (results.isValid()) {
    if (customerService.doesCustomerExist(customerId)) {
      customerService.updateCustomer(customer);
    } else {
      customerService.createCustomer(customer);
    }
    navigateToHome();
  }
}
```

## Defining data validation {#defining-data-validation}

With a binding context, validation is handled in the `Customer` entity by adding Jakarta annotations to the customer properties. You can also specify the message that a user sees when they enter an invalid value:

```java title="Customer.java" {1-2,5-6,9}
@NotEmpty(message = "Customer first name is required")
@Pattern(regexp = "[a-zA-Z]*", message = "Invalid characters")
private String firstName = "";

@NotEmpty(message = "Customer last name is required")
@Pattern(regexp = "[a-zA-Z]*", message = "Invalid characters")
private String lastName = "";

@Pattern(regexp = "[a-zA-Z0-9 ]*", message = "Invalid characters")
private String company = "";
```

### Types of Jakarta validation {#types-of-jakarta-validation}

`@NotEmpty` and `@Pattern` are [Jakarta Validation](https://beanvalidation.org/) annotations. They declare validation rules directly on the model property:

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

See the [Jakarta Bean Validation constraints reference](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary.html) for a full list of validations, or read [Jakarta Validation](/docs/data-binding/validation/jakarta-validation) for more webforJ documentation.

With these changes, the app now supports data binding and validation using Spring Boot and webforJ. Form inputs are automatically synchronized with the model and checked against validation rules.

## Running the app {#running-the-app}

When you’ve finished this step, you can compare it to [4-validating-and-binding-data](https://github.com/webforj/webforj-demo-application/tree/main/4-validating-and-binding-data) on GitHub. To see the app in action:

1. Navigate to the top-level directory containing the `pom.xml` file, this is `4-validating-and-binding-data` if you're following along with the version on GitHub.

2. Use the following Maven command to run the Spring Boot app locally:
    ```bash
    mvn
    ```

3. Open your browser and go to http://localhost:8080 to view the app.

:::info Next steps
Looking for more ways to improve your app from this tutorial? You can try using the [`AppLayout`](/docs/components/app-layout) component as a wrapper to add your customer table and add more features.
:::