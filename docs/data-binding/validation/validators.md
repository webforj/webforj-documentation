---
sidebar_position: 2
title: Validators
---

Validators checks data within your UI components against defined constraints before committing this data to the data model. You can apply validators to ensure that data meets certain criteria, such as being within a specified range, matching a pattern, or not being empty.

Validations are configured per binding, allowing specific rules to apply to each data point individually. This setup ensures that each piece of data undergoes validation according to its own requirements.

## Adding validators

Add validators to a binding using the `useValidator` method on the `BindingBuilder`.

```java
context.bind(nameTextField, "name")
    .useValidator(value -> !value.isEmpty(), "Name cannot be empty")
    .useValidator(value -> value.length() >= 3, "Name must be at least 3 characters long")
    .add();
```

In the example above, two validators verify that the name isn't empty and that it contains at least three characters.

:::tip Validators processing
There is no limit to the number of validators you can add per binding. The binding applies the validators by the order of insertion, and stops at the first violation.
:::

## Implementing validators

You can create custom reusable validators by implementing the `Validator<T>` interface, where `T` is the type of data you want to validate. This setup involves defining the validate method, which checks the data and returns a `ValidationResult`.

Here’s an example of a reusable validator that checks if an user's email is valid.

```java
import com.webforj.data.validation.server.ValidationResult;
import com.webforj.data.validation.server.validator.Validator;

public class EmailValidator implements Validator<String> {
  private static final String PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

  @Override
  public ValidationResult validate(String value) {
    if (value.matches(PATTERN)) {
        return ValidationResult.valid();
    } else {
        return ValidationResult.invalid("Invalid email address");
    }
  }
}
```

## Using validators in bindings

Once you have defined a validator, you can easily apply it to any relevant bindings within your app. This is particularly useful for components that require common validation rules across different parts of your app, such as user email addresses, or password strength.

To apply the `EmailValidator` to a binding:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(ageField, "age")
    .useValidator(new EmailValidator())
    .add();
```

## Overriding validator messages

You can customize the error messages of validators at the point of binding to a specific UI component. This allows you to provide more detailed or contextually relevant information to the user if the validation fails. Custom messages are particularly useful when the same validator applies to multiple components but requires different user guidance based on the context in which it is used.

Here's how to override the default message of a reusable validator in a binding:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
    .useValidator(
        Validator.from(new EmailValidator(), "Custom message for invalid email address"))
    .add();
```

In the example above, the code applies the `EmailValidator` to an email field with a custom error message specifically tailored for that field. This allows for a more directed and helpful user experience if the validation fails.

:::tip Understanding `Validator.from`
The `Validator.from` method wraps a passed validator with a new one, allowing you to specify a custom error message in case the validator doesn't support customized messages. This technique is particularly useful when you need to apply the same validation logic across multiple components but with distinct, context-specific error messages for each instance.
:::
