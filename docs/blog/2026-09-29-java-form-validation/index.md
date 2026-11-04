---
title: "Java Form Validation in webforJ: A BindingContext Guide"
description: "Build a validated Java form in webforJ with BindingContext. Cover automatic binding, Jakarta Validation, custom validators, transformers, and save and reset."
slug: java-form-validation
date: 2026-09-29
authors: Matthew Hawkins
tags: [tutorial]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-09-29-java-form-validation/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-09-29-java-form-validation/cover.jpg)

Java form validation in webforJ comes down to one object: the `BindingContext`. You annotate a plain Java bean with Jakarta Validation constraints, bind your form fields to it by name, and the context validates every field as the user types, shows the error under the field, and writes to the bean only when everything passes.

This guide builds a complete workspace signup form that way. You get automatic form data binding in Java, a custom validator, a type transformer, control over when errors appear, and working save and reset buttons. No JavaScript is involved.

<!-- truncate -->

## How Java form validation works in webforJ {#how-it-works}

A form in webforJ has a bean on one side and UI fields on the other. The `BindingContext` sits between them and owns the rules. Each piece has one job:

| Piece | Job |
| --- | --- |
| `BindingContext` | Holds all bindings for one form, validates them together, reads and writes the bean |
| Jakarta Validation annotations | Declare rules on the bean, such as `@NotBlank` or `@Min` |
| `Validator` | Adds a rule that annotations can't express |
| `Transformer` | Converts between the field's type and the bean's type |
| Reporter | Shows the validation result next to the field |

The built-in reporter handles display for the core field components, so you don't write any error-rendering code to get a working form. The [data binding docs](/docs/data-binding/overview) cover each part in depth.

If your beans contain nested objects, read [Nested data binding in webforJ](/blog/nested-data-binding-webforj) after this guide. For layout and field design, [Design effective forms using the TextField](/blog/2025/08/07/using-textfield-in-forms) covers the UI side.

## Put the rules on the bean {#bean-constraints}

The example is a signup form for a team workspace. The bean declares what a valid signup looks like, and the form never repeats those rules.

```java
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class Signup {
  @NotBlank(message = "Full name is required")
  @Size(min = 3, max = 60, message = "Use between 3 and 60 characters")
  private String fullName;

  @NotBlank(message = "Work email is required")
  @Email(message = "Enter a valid email address")
  private String email;

  @NotBlank(message = "Choose a plan")
  private String plan;

  @NotNull(message = "Seat count is required")
  @Min(value = 1, message = "At least one seat is needed")
  @Max(value = 50, message = "Contact sales for more than 50 seats")
  private Integer seats;

  private String promoCode;
  private LocalDate startDate;

  // getters and setters
}
```

webforJ validates these constraints through its `JakartaValidator` adapter. You need a Bean Validation implementation such as Hibernate Validator on the classpath, and the [Jakarta Validation page](/docs/data-binding/validation/jakarta-validation) lists the Maven dependencies. The adapter reads constraints placed on properties, so annotate fields or getters rather than class-level rules.

A side effect worth knowing: constraints such as `@NotBlank` and `@NotNull` also mark the bound field as required automatically.

## Bind the form automatically {#automatic-binding}

Declare the fields in your view with names that match the bean properties, then call `BindingContext.of`. The third argument turns on Jakarta validation for the whole context.

```java
public class SignupForm extends Composite<Div> {
  TextField fullName = new TextField("Full name");
  TextField email = new TextField("Work email");
  ComboBox plan = new ComboBox("Plan");

  @UseTransformer(SeatsTransformer.class)
  NumberField seats = new NumberField("Seats");

  @UseValidator(PromoCodeValidator.class)
  TextField promoCode = new TextField("Promo code");

  @BindingExclude DateField startDate = new DateField("Start date");

  Button save = new Button("Create workspace", ButtonTheme.PRIMARY);
  Button reset = new Button("Reset");

  private final BindingContext<Signup> context;
  private Signup signup = new Signup();

  public SignupForm() {
    plan.add("Starter");
    plan.add("Team");
    plan.add("Business");

    context = BindingContext.of(this, Signup.class, true);
    // ... covered in the next sections

    getBoundComponent().add(fullName, email, plan, seats, promoCode, startDate, save, reset);
  }
}
```

`of` matches each bindable field to the bean property with the same name. If a field name differs from the property, `@UseProperty("fullName")` maps it. `@BindingExclude` keeps a field out of automatic binding so you can configure it by hand, which the `startDate` field does later. The [automatic binding page](/docs/data-binding/automatic-binding) lists every annotation.

Without any further code, an empty full name or a malformed email now produces the message from the bean, shown next to the field.

## Add a custom validator {#custom-validators}

Annotations cover most rules. Business rules that depend on live data need a `Validator`. Here a promo code must be one of the active campaign codes.

```java
public class PromoCodeValidator implements Validator<String> {
  private static final Set<String> ACTIVE_CODES = Set.of("SPRING25", "TEAMUP");

  @Override
  public ValidationResult validate(String value) {
    if (value == null || value.isBlank()) {
      return ValidationResult.valid();
    }

    return ACTIVE_CODES.contains(value.trim().toUpperCase())
        ? ValidationResult.valid()
        : ValidationResult.invalid("This promo code is not active");
  }
}
```

The `@UseValidator(PromoCodeValidator.class)` annotation on the field attaches it. Because the promo code is optional, the validator treats an empty value as valid. The same class works in manual bindings through `useValidator(new PromoCodeValidator())`, and you can override its message per field with `Validator.from`. See [Validators](/docs/data-binding/validation/validators) for both.

## Convert types with a transformer {#transformers}

A `NumberField` holds a `Double`, but the bean stores seats as an `Integer`. A `Transformer` bridges the two so the `@Min` and `@Max` constraints run against the real model type.

```java
public class SeatsTransformer implements Transformer<Double, Integer> {
  @Override
  public Integer transformToModel(Double componentValue) {
    return componentValue == null ? null : (int) Math.round(componentValue);
  }

  @Override
  public Double transformToComponent(Integer modelValue) {
    return modelValue == null ? null : modelValue.doubleValue();
  }
}
```

For one-off conversions, `Transformer.of` takes two lambdas instead of a class. If a failed conversion should show a specific message, pass it as the second argument to `useTransformer`. The [Transformation page](/docs/data-binding/transformation) shows both forms.

## Control how and when errors appear {#showing-errors}

By default a binding revalidates whenever the field's value changes. That can feel aggressive on a text field that validates on every keystroke. Switch the email field to validate when it loses focus, and send focus to the first invalid field when the user submits.

```java
context.setAutoFocusFirstViolation(true);
email.setValueChangeMode(ValueChangeMode.ON_BLUR);
```

Some fields need a manual binding. The start date rule is a plain predicate, so it doesn't need a class. This binding also shows `useDefaultReporter`, which lets you decide how multiple messages join into the single string displayed under the field.

```java
context
    .bind(startDate, "startDate")
    .useValidator(
        date -> date == null || !date.isBefore(LocalDate.now()),
        "Start date can't be in the past")
    .useDefaultReporter(messages -> String.join(" ", messages))
    .add();
```

Validators on a binding run in the order you add them and stop at the first failure. When you need a fully custom display, such as a summary banner, `useReporter` accepts your own `BindingReporter`. The [Reporters](/docs/data-binding/validation/reporters) and [Triggers](/docs/data-binding/validation/triggers) pages cover those options.

To react to the state of the whole form, listen on the context. This enables the save button only when every binding is valid:

```java
context.onValidate(e -> save.setEnabled(e.isValid()));
context.read(signup);
```

## Save and reset {#save-and-reset}

`write` is the method that matters on submit. It validates every binding, and only if the whole form is valid does it copy the field values into the bean. It returns a `ValidationResult` you can inspect.

```java
save.onClick(e -> {
  ValidationResult result = context.write(signup);
  if (result.isValid()) {
    Toast.show("Workspace created for " + signup.getEmail());
  }
});
```

Reset works in the opposite direction. `read` pushes a bean's values into the fields, so reading a fresh bean clears the form:

```java
reset.onClick(e -> {
  signup = new Signup();
  context.read(signup);
});
```

Edit mode uses the same call. Load an existing record from your service, pass it to `read`, and the same form validates and saves changes with no extra code. If you prefer live updates, `observe(bean)` writes each valid change to the bean as the user types. The [Bindings page](/docs/data-binding/bindings) describes `read`, `write`, and `observe` in detail.

## Frequently asked questions {#faq}

### Does webforJ work with Jakarta validation? {#does-webforj-work-with-jakarta-validation}

Yes. Create the context with `new BindingContext<>(Signup.class, true)` or `BindingContext.of(this, Signup.class, true)` and the `JakartaValidator` applies constraints from the bean's properties. You need Hibernate Validator or another implementation on the classpath.

### How do I write a custom validator in webforJ? {#how-do-i-write-a-custom-validator-in-webforj}

Implement `Validator<T>` and return `ValidationResult.valid()` or `ValidationResult.invalid(message)`. Attach it with `@UseValidator` on a field or `useValidator` on a manual binding. For simple rules, pass a predicate and a message directly to `useValidator`.

### How do I bind a form to a Java bean? {#how-do-i-bind-a-form-to-a-java-bean}

Create a `BindingContext` for the bean class, then bind each field with `bind(field, "property").add()` or let `BindingContext.of` match fields to properties by name. Use `read(bean)` to fill the form and `write(bean)` to validate and save it.

### How do I validate a field only when the user leaves it? {#how-do-i-validate-a-field-only-when-the-user-leaves-it}

Call `setValueChangeMode(ValueChangeMode.ON_BLUR)` on the field. Validation then runs once on blur instead of on every keystroke. You can also turn off automatic validation for a binding with `autoValidate(false)` and trigger it yourself.
