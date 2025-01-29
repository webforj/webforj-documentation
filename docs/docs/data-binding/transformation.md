---
sidebar_position: 4
title: Transformation
---

Data transformations are a pivotal feature, facilitating seamless conversion between the data types used in UI components and those in your data model. This capability ensures that data types are compatible and appropriately formatted when moving data between the frontend and backend of your applications.

:::tip
The transformer setting is best used when the data type of the bean property doesn't match the data type handled by the UI components. If you simply need to transform data of the same type, configuring [the bindings' getters and setters](bindings#binding-getters-and-setters) is the preferred approach.
:::

## Configuring transformers

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

## Implementing a transformer

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

This transformer facilitates the handling of date fields, ensuring that dates are correctly formatted when displayed in the UI and correctly parsed back into the model.

## Using transformers in bindings

Once you have defined a transformer, you can apply it across multiple bindings within your app. This approach is particularly useful for standard data formats that need consistent handling across different parts of your app.

```java
BindingContext<Employee> context = new BindingContext<>(Employee.class);
context.bind(startDateField, "startDate", String.class)
    .useTransformer(new DateTransformer())
    .add();
```

:::info Specifying the Bean Property Type

In the `bind` method, specifying the type of the bean property as the third parameter is essential when there is a discrepancy between the data type displayed by the UI component and the data type used in the model. For instance, if the component handles `startDateField` as a Java `LocalDate` within the component but stored as a `String` in the model, explicitly defining the type as `String.class` ensures that the binding mechanism accurately processes and converts the data between the two different types utilized by the component and the bean using the provided transformer and validators.
:::

## Simplifying transforms with `Transformer.of`

It's possible to simplify the implementation of such transformations using the `Transformer.of` method provided by the `Transformer`. This method is syntactic sugar, and allows you to write a method that handles transformations inline, rather than passing a class implementing the `Transformer` interface. 

In the following example , the code handles a checkbox interaction within a travel app where users can opt for additional services like car rental. The checkbox state `boolean` needs to be transformed into a string representation `"yes"` or `"no"` that the backend model uses.

```java
CheckBox carRental = new CheckBox("Car Rental");
BindingContext<Trip> context = new BindingContext<>(Trip.class, true);
context.bind(carRental, "carRental", String.class)
  .useTransformer(
      Transformer.of(
        // convert component value to modal value
        bool -> Boolean.TRUE.equals(bool) ? "yes" : "no",
        // convert modal value to component value
        str -> str.equals("yes")
      ), 

      // in case transformation fails, show the following
      // message
      "Checkbox must be checked"
  )
  .add();
```
