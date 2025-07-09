---
sidebar_position: 4
title: Context Results
---

When you write data from the UI to the model, the `write` method of the `BindingContext` triggers the validations. The validation results determine whether the data is acceptable.

## Processing validation results

You can process validation results to provide feedback to the user. If a validation fails, you can prevent the data update in the model and display error messages associated with each failed validation.

```java
ValidationResult result = context.write(hero);
if (!result.isValid()) {
    displayErrors(result.getMessages());
} else {
    proceedWithUpdate();
}
```

<!-- vale off -->
## Context Validation State
<!-- vale on -->

Whenever the context validates the components, it fires a `BindingContextValidateEvent`. This event delivers the `ValidationResult` for all bindings that have changed simultaneously. You can use these results to trigger actions and respond appropriately, such as enabling or disabling the submit button based on the overall form validity.

```java
BindingContext<User> context = new BindingContext<>(User.class);

// Listen to the BindingContextValidateEvent which is fired on each user interaction.
context.addValidateListener(event -> {
    submit.setEnabled(event.isValid());
});
```

## Auto focus violation

When dealing with forms that require validation across multiple fields, automatically focusing the first field with an error can significantly improve user experience. This feature helps users immediately identify and correct errors, streamlining the form completion process.

The `BindingContext` simplifies the process of setting up auto-focus on the first component with a validation error. By using the `setAutoFocusFirstViolation` method, you can enable this feature with minimal code, ensuring that the user interface becomes more intuitive and responsive to input errors.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.setAutoFocusFirstViolation(true);
```

:::info Focus Aware
This feature works only for the components the implements the `FocusAcceptorAware` concern.
:::