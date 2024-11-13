# Fourth step

## Goals and resources

The final step of this tutorial will walk through the implementation of data binding and validation. 

<!-- TODO add list of resources -->

## `FormView` data binding

To start, enhance the `FormView` class with data binding, which simplifies the process of synchronizing the form fields with the `Customer` data model. This approach saves development time by reducing the need for repetitive getter and setter calls, while also improving the reliability of data handling. 

For more information on data binding reference [this article.](../../data-binding/overview)

:::info
For successful data binding, it’s important that the setter methods in the `Customer` model don't return `this`. Each setter should return `void`, as returning the object instance can interfere with binding logic and lead to unexpected results.
:::

### Binding the fields

The data binding setup begins with initializing a `BindingContext` for the `Customer` model. The `BindingContext` links the model properties to the form fields, enabling automatic data synchronization. This is set up in the `FormView` constructor.

```java title="FormView.java"
BindingContext<Customer> context;
context = BindingContext.of(this, Customer.class, true);
```

- `BindingContext.of(this, Customer.class, true)` initializes the binding context for the `Customer` class. The third parameter, `true`, enables immediate validation, meaning the data in each field is validated as soon as it changes.

:::info
This implementation uses autobinding as described in the data binding article. This works if the field in the datamodel `Customer` are named the same as the fields in the `FormView` which correspond to them.
:::
### Validation

Through annotations in the customer class you can give jakarta validation parameters to the field.

```java
  @NotEmpty(message = "Name cannot be empty")
  @Pattern(regexp = "[a-zA-Z]*", message = "Invalid characters")
  private String firstName = "";
```

The `onValidate` method is then added to control the `Submit` button's state based on the validity of the form fields. This ensures that only valid data can be submitted.

```java title="FormView.java"
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

- `e.isValid()` checks the validation status of each field. If all fields are valid, the `Submit` button is enabled; otherwise, it remains turned off, preventing submission until corrections are made.

#### Adding and editing entries with validation

The `submitCustomer()` method performs validation using the `BindingContext` before adding or editing entries.

- **Add Mode**: When adding a new customer, the `submitCustomer()` method writes validated data to the `Customer` model and adds it to the repository if valid.
- **Edit Mode**: When editing, the method retrieves the customer data using the `id` parameter, validates the inputs, and updates the entry.

```java title="FormView.java"
private void submitCustomer() {
    ValidationResult results = context.write(customer);
    if (results.isValid()) {
        if (customerId.isEmpty()) {
            Service.getCurrent().addCustomer(customer);
        }
        Router.getCurrent().navigate(DemoView.class);
    }
}
```
