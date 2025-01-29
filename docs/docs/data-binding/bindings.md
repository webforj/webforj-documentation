---
sidebar_position: 2
title: Bindings
---

A binding in webforJ links a specific property of a Java Bean to a UI component. This linkage enables automatic updates between the UI and the backend model. Each binding can handle data synchronization, validation, transformation, and event management.

You can initiate bindings only through the `BindingContext`. It manages a collection of binding instances, each linking a UI component to
a property of a bean. It facilitates group operations on bindings, such as validation and
synchronization between the UI components and the bean's properties. It acts as an aggregator,
allowing for collective actions on multiple bindings, thereby streamlining the management of data
flow within applications.

:::tip Automatic Binding
This section introduces the basics of manually configuring bindings. Additionally, you can automatically create bindings based on the UI components in your form. Once you grasp the fundamentals, learn more by reading the [Automatic Binding](./automatic-binding) section.
:::

## Configure bindings

Start by creating a new instance of `BindingContext` which manages all bindings for a particular model.
This context ensures that all bindings can be validated and updated collectively.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
```

:::info
Each form should have only one `BindingContext` instance, and you should use this instance for all the components in the form.
:::

### The bound property

A binding property is a specific field or attribute of a Java Bean that can be linked to a UI component in your app. 
This linkage allows changes in the UI to directly affect the corresponding property of the data model, and vice versa, 
facilitating a reactive user experience.

When setting up a binding, you should provide the property name as a string. This name must match the field name in the Java Bean class. Here's a simple example:

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class, true);
context
    .bind(textField, "power")
    .add()
```

```java
public class Hero  {
  private String name;
  private String power;

  // setters and getters
}
```

the `bind` methods returns a `BindingBuilder` which creates the `Binding` object and you can use to configure the binding several settings, the `add` method which
is what actually gets the binding added to the context.

### The bound component

The other side of the binding is the bound component, which refers to the UI component that interacts with the property of the Java Bean. 
The bound component can be any UI component that supports user interaction and display, such as text fields, combo boxes, checkboxes, or 
any custom component that implements the `ValueAware` interface.

The bound component serves as the user's point of interaction with the underlying data model. 
It displays data to the user and also captures user inputs which are then propagated back to the model.

```java
TextField nameTextField = new TextField("Name");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## Reading and writing data

### Reading data

Reading data involves populating UI components with values from the data model. 
This is typically done when a form is initially displayed, or when you need to reload the data due to changes in the underlying model. 
The `read` method provided by `BindingContext` makes this process straightforward.

```java
// Assume Hero object has been instantiated and initialized
Hero hero = new Hero("Clark Kent", "Flying");

// BindingContext is already configured with bindings
context.read(hero);
```

In this example, the `read` method takes an instance of `Hero` and updates all bound UI components to reflect the hero's properties. 
If the hero's name or power changes, the corresponding UI components (like a `TextField` for name and a `ComboBox` for powers) 
display these new values.

### Writing data

Writing data involves collecting values from the UI components and updating the data model. 
This typically occurs when a user submits a form. The `write` method handles validation and model updating in one step.

```java
// This could be triggered by a form submission event
submit.onClick(event -> {
  ValidationResult results = context.write(hero);
  if (results.isValid()) {
    // Data is valid, and hero object has been updated
    // repository.save(hero); 
  } else {
    // Handle validation errors
    // results.getMessages();
  }
});
```

In the code above, when the user clicks the submit button, the `write` method is called. 
It performs all configured validations and, if the data passes all checks, updates the `Hero` object 
with new values from the bound components. 
If the data is valid, you might save to a database or processed further. If there are validation errors, 
you should handle appropriately, typically by displaying error messages to the user.


:::tip Validation Errors Reporting
All core components of webforJ have default configurations to automatically report validation errors, either inline or through a popover. You can customize this behavior using [Reporters](./validation/reporters.md).
:::

<!-- vale off -->
## ReadOnly data
<!-- vale on -->

In certain scenarios, you may want your app to display data without allowing the end-user to modify it directly through the UI. 
This is where read-only data bindings become crucial. webforJ supports the configuration of bindings to be read-only, ensuring that 
you can display data, but not edit it through bound UI components.

### Configuring readonly bindings

To set up a read-only binding, you can configure the binding to turn off or ignore UI component input. 
This ensures that the data remains unchanged from the UI perspective, while still updating programmatically if needed.

```java
// Configuring a text field to be read-only in the binding context
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
    .readOnly()
    .add();
```

In this configuration, `readOnly` ensures that the `nameTextField` doesn't accept user input, effectively making the text field display 
the data without allowing modifications.

:::info
The binding can mark the component as read-only only if the UI components implements the `ReadOnlyAware` interface.
:::

:::tip Component ReadOnly vs Binding ReadOnly
It's important to differentiate between bindings you configure as read-only and UI components you set to display as read-only. 
When you mark a binding as read-only, it impacts how the binding manages data during the write process, not just the UI behavior.

When you mark a binding as read-only, the system skips data updates. Any changes to the UI component won't transmit back to the data model. 
This ensures that even if the UI component somehow receives user input, it won't update the underlying data model. 
Maintaining this separation is crucial for preserving data integrity in scenarios where user actions shouldn't alter the data.

In contrast, setting a UI component as read-only, without configuring the binding itself as read-only, simply stops the user from making changes 
to the UI component but doesn't stop the binding from updating the data model if changes occur programmatically or through other means.
:::

## Binding getters and setters

Setters and getters are methods in Java that set and get the values of properties, respectively.
In the context of data binding, they are used to define how properties are updated and retrieved within the binding framework.

### Customizing setters and getters

Although webforJ can automatically use standard JavaBean naming conventions
(for example, `getName()`, `setName()` for a property `name`), you might need to define custom behavior.
This is necessary when the property doesn't follow the conventional naming or when the data handling requires additional logic.

### Using custom getters

Custom getters are used when the value retrieval process involves more than just returning a property.
For example, you might want to format the string, compute a value, or log certain actions when a property is accessed.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
    .bind(textField, "power")
    .useGetter(hero -> {
        String name = hero.getName();
        return name.toUpperCase(); // Custom logic: convert name to uppercase
    });
```

### Using custom setters

Custom setters come into play when setting a property involves additional operations such as validation, transformation, or side effects
like logging or notifying other parts of your app.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
    .bind(textField, "power")
    .useSetter((hero, name) -> {
        System.out.println("Updating name from " + hero.getName() + " to " + name);
        hero.setName(name); // Additional operation: logging
    });
```
