---
sidebar_position: 5
title: Automatic Binding
---

webforJ offers several features that streamline the configuration and automatic binding process for developers. This section demonstrates how to use these features effectively.

## Using `BindingContext.of`

The `BindingContext.of` method automatically binds UI components to the properties of a specified bean class, simplifying the binding process and reducing manual setup. It aligns bindable components, declared as fields within a form or app, with bean properties based on their names.

```java
public class HeroRegistration extends App {
  // Bindable components
  TextField name = new TextField("Text Field");
  ComboBox power = new ComboBox("Power");

  // ...

  @Override
  public void run() throws WebforjException {
    BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
    // ...
  }
}
```

```java
public class Hero {
  private String name;
  private String power;

  // Setters and getters
}
```

### `UseProperty` annotation

Use the `UseProperty` annotation to specify the bean property name when the UI field name doesn't match the bean property name.

```java
public class HeroRegistration extends App {
  // Bindable components
  @UseProperty("name")
  TextField nameField = new TextField("Text Field");
  ComboBox power = new ComboBox("Power");

  // ...
}
```

In the example above, the UI field name is `nameField`, but the bean property is `name`. You can annotate the UI field with the bean property name to ensure proper binding.

### `BindingExclude` annotation

Use the `BindingExclude` annotation to exclude a component from automatic binding configurations when you prefer to bind it manually or exclude it altogether.

```java
public class HeroRegistration extends App {
  // Bindable components
  @UseProperty("name")
  TextField nameField = new TextField("Text Field");

  @BindingExclude
  ComboBox power = new ComboBox("Power");

  // ...
}
```

### `UseValidator` annotation

Use the `UseValidator` annotation to declare validators that enforce additional validation rules during binding. Validators apply in the order you specify them.

```java
public class UserRegistration extends App {

  @UseValidator(EmailValidator.class)
  TextField email = new TextField("Email Address");
}
```

### `UseTransformer` annotation

Use the `UseTransformer` annotation to declare a transformer class directly on a UI field. The `BindingContext` automatically applies the specified transformer.

```java
public class UserRegistration extends App {

  @UseProperty("date")
  @UseTransformer(DateTransformer.class)
  DateField dateField = new DateField("Date Field");
}
```

### `BindingReadOnly` annotation

Use the `BindingReadOnly` to [mark a binding as readonly](./bindings/#configuring-readonly-bindings).

```java
public class UserRegistration extends App {

  @BindingReadOnly
  TextField IDField = new TextField("User ID");
}
```

### `BindingRequired` annotation

Use the `BindingReadOnly` to mark a binding as required. See also [required binding detections](#required-binding-detections).


```java
public class UserRegistration extends App {

  @BindingRequired
  TextField emailField = new TextField("User Email");
}
```

## Writing data automatically

To enhance the responsiveness and dynamism of applications, you can use the `observe` method. This method ensures that changes in UI components immediately propagate to the data model. It's particularly useful when you need continuous synchronization between the data model and the UI.

The `observe` method registers a `ValueChangeEvent` listener on all bindings in the context to monitor changes made by the user then it instantly writes these changes to the bound properties of the model if they're valid. When you first invoke this method, it reflects the bean properties in the UI components.

Here is an example of how to use `observe`:

```java
Hero bean = new Hero("Superman", "Fly");
BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
context.observe(bean);
context.onValidate(e -> {
  submit.setEnabled(e.isValid());
});

submit.onClick(e -> {
  ValidationResult results = context.validate();
  if (results.isValid()) {
    // Take action with the bean.
  }
});
```

:::info Update Direction
This automatic binding is unidirectional; updates are reflected in the model when you update UI components, but changes in the model only reflect in the UI components once, when you first invoke the method.
:::

:::tip Considerations
While `observe` increases the interactivity of applications, it's important to use it judiciously:

- **Performance Impact**: Frequent updates might affect performance, especially with complex models or slow backend services.
- **User Experience**: Automatic updates shouldn't disrupt the user's ability to comfortably enter data.
:::


## Required binding detections

When you mark a binding as required, it marks the component as required, provided the component supports this state through the `RequiredAware` interface. The binding doesn't enforce this state by itself, but rather sets it on the component when applicable.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
context
  .bind(emailField, "email")
    .required()
    .add()
```

 When utilizing [Jakarta annotations](./validation/jakarta-validation.md), the binding can be automatically detect required state based on the presence of any of the following annotations on bean properties:

1. `@NotNull` 
2. `@NotEmpty` 
3. `@NotBlank`
4. `@Size`