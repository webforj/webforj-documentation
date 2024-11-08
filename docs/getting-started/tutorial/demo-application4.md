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

The first thing you need to facilitate databinding in your form 

### Validation

The validation process ensures that only complete and correct data can be submitted. Key steps are:

1. **Real-time Validation**: Validation is performed as data is entered into each field, checking for completeness and correctness.
2. **Submit Button Control**: The `Submit` button is off when any fields have invalid data, preventing submission until all fields are valid.

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

### Logic for adding entries

In add mode, the form initializes with an empty `Customer` instance. When `submitCustomer()` is called, it validates the data through `context.write(customer)`. If the data is valid, it calls `Service.getCurrent().addCustomer(customer)` to add the new customer to the repository.

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

### Logic for editing entries

When editing an entry, `FormView` retrieves the existing customer data using the `id` parameter in the route. The `onDidEnter` method handles this by calling `context.read(customer)`, which populates the form fields with the customer’s data, enabling seamless editing.

```java title="FormView.java"
@Override
public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("id").ifPresent(id -> {
        customer = Service.getCurrent().getCustomerByKey(UUID.fromString(id));
        customerId = id;
    });
    context.read(customer);
}
```

By using data binding, validation, and automated updates between the form and model, this version of `FormView` is more efficient and reduces the potential for manual errors.
