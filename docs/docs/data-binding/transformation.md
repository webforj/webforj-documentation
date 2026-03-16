---
sidebar_position: 4
title: Transformation
sidebar_class_name: updated-content
---

Data transformations convert between the data types used in UI components and those in your data model. This keeps data types compatible and appropriately formatted when moving data between the frontend and backend of your applications.

:::tip
The transformer setting is best used when the data type of the bean property doesn't match the data type handled by the UI components. If you simply need to transform data of the same type, configuring [the bindings' getters and setters](bindings#binding-getters-and-setters) is the preferred approach.
:::

## Configuring transformers {#configuring-transformers}

You configure data transformations directly within your bindings, enabling you to define how data should be transformed during the data binding process.

You can add transformers to a binding using the `useTransformer` method on the `BindingBuilder`. Transformers must implement the `Transformer` interface, which requires defining methods for both directions of data flow: from model to UI and from UI to model.

```java
context.bind(salaryField, "salary")
    .useTransformer(new CurrencyTransformer())
    .add();
```

In the example above, the code configures a `CurrencyTransformer` to handle conversions between the model data type (for example, BigDecimal) and the UI representation (for example, a formatted string).

:::info
Each binding is associated with a single transformer. If transforming a value requires multiple steps, it's recommend to implement your own transformer for these steps.
:::

## Implementing a transformer {#implementing-a-transformer}

Here’s an example of implementing a simple transformer that converts between a `LocalDate` model and a `String` UI representation:

```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.webforj.data.transformation.TransformationException;
import com.webforj.data.transformation.transformer.Transformer;

public class DateTransformer implements Transformer<LocalDate, String> {
  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @Override
  public LocalDate transformToComponent(String modelValue) {
    try {
      return LocalDate.parse(modelValue, formatter);
    } catch (Exception e) {
      throw new TransformationException("Invalid date format");
    }
  }

  @Override
  public String transformToModel(LocalDate componentValue) {
    try {
      return componentValue.format(formatter);
    } catch (Exception e) {
      throw new TransformationException("Invalid date format");
    }
  }
}
```

This transformer handles date fields, formatting dates when displayed in the UI and parsing them back into the model.

### Using transformers in bindings {#using-transformers-in-bindings}

Once you have defined a transformer, you can apply it across multiple bindings within your app. This approach is particularly useful for standard data formats that need consistent handling across different parts of your app.

```java
BindingContext<Employee> context = new BindingContext<>(Employee.class);
context.bind(startDateField, "startDate", String.class)
    .useTransformer(new DateTransformer())
    .add();
```

:::info Specifying the Bean Property Type

In the `bind` method, specifying the type of the bean property as the third parameter is essential when there is a discrepancy between the data type displayed by the UI component and the data type used in the model. For instance, if the component handles `startDateField` as a Java `LocalDate` within the component but stored as a `String` in the model, explicitly defining the type as `String.class` tells the binding mechanism to accurately process and convert the data between the two different types used by the component and the bean using the provided transformer and validators.
:::

### Simplifying transforms with `Transformer.of` {#simplifying-transforms-with-transformerof}

It's possible to simplify the implementation of such transformations using the `Transformer.of` method provided by the `Transformer`. This method is syntactic sugar, and allows you to write a method that handles transformations inline, rather than passing a class implementing the `Transformer` interface. 

In the following example, the code handles a checkbox interaction within a travel app where users can opt for additional services like car rental. The checkbox state `boolean` needs to be transformed into a string representation `"yes"` or `"no"` that the backend model uses.

```java
CheckBox carRental = new CheckBox("Car Rental");
BindingContext<Trip> context = new BindingContext<>(Trip.class, true);
context.bind(carRental, "carRental", String.class)
  .useTransformer(
      Transformer.of(
        // convert component value to model value
        bool -> Boolean.TRUE.equals(bool) ? "yes" : "no",
        // convert model value to component value
        str -> str.equals("yes")
      ),

      // in case transformation fails, show the following
      // message
      "Checkbox must be checked"
  )
  .add();
```

### Dynamic transformer error messages <DocChip chip='since' label='25.12' /> {#dynamic-transformer-error-messages}

By default, the error message shown when a transformation fails is a static string. In apps that support multiple languages, you can pass a `Supplier<String>` instead so the message is resolved each time the transformation fails:

```java {7}
context.bind(quantityField, "quantity", Integer.class)
    .useTransformer(
        Transformer.of(
            str -> Integer.parseInt(str),
            val -> String.valueOf(val)
        ),
        () -> t("validation.quantity.invalid")
    )
    .add();
```

The supplier is invoked only when the transformation throws a `TransformationException`. This means the message always reflects the current locale at the time of failure.

#### Locale-aware transformers {#locale-aware-transformers}

For reusable transformers that need access to the current locale internally (for example, to format numbers or dates according to regional conventions), implement the `LocaleAware` interface. When the locale changes via `BindingContext.setLocale()`, the context automatically propagates the new locale to transformers that implement this interface.
