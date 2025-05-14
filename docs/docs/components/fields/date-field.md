---
sidebar_position: 10
title: DateField
slug: datefield
description: A component that provides a default browser-based date picker for selecting a date through an input field.
---

<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateField" top='true'/>

<ParentLink parent="Field" />

The `DateField` is a field component that allows users to input or select dates by the year, month, and day. It provides an intuitive and efficient way to handle date-related information in various apps, and offers the flexibility to validate a user's input.

<ComponentDemo 
path='/webforj/datefield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datefield/DateFieldView.java'
/>

## Field value (`LocalDate`)

The `DateField` component stores its value internally as a `LocalDate` object, representing a date without time or timezone information. This allows for accurate handling of calendar-based input across different systems.

While the **visual display adapts to the user's browser locale**, ensuring regionally familiar formatting (e.g., MM/DD/YYYY in the USA or DD.MM.YYYY in Europe), the internal parsing and programmatic interaction always rely on the fixed format: **`yyyy-MM-dd`**.

### Getting and setting the value

To retrieve the current value:

```java
LocalDate value = dateField.getValue();
```

To set the value programmatically:

```java
dateField.setValue(LocalDate.of(2024, 4, 27));
```

### Using `setText()`

You can also assign a value using a raw string, but it must follow the exact `yyyy-MM-dd` format:

```java
dateField.setText("2024-04-27"); // valid
```

:::tip
When using `setText(...)`, the input must strictly follow the `yyyy-MM-dd` format. If parsing fails, an `IllegalArgumentException` will be thrown.
:::


## Usages

The `DateField` is ideal for choosing and displaying dates in your app. Here are some examples of when to use the `DateField`:

1. **Event Scheduling and Calendars**: Date fields are essential in apps that involve scheduling events, booking appointments, or keeping track of important dates.

2. **Form Inputs**: Simplify the date selection process for a user filling out a form that requires a date, like a birthday.

3. **Booking and Reservation Systems**: Apps that involve booking and reservation systems often require users to input specific dates. A date field streamlines the process and ensures accurate date selection.

4. **Task Management and Deadlines**: Date fields are valuable in apps that involve task management or setting deadlines. Users can easily specify due dates, start dates, or other time-sensitive information.

## Min and max value

### `setMax`

If the value entered into the component is later than the specified maximum date, the component will fail constraint validation. When both the min and max values are set, the max value must be a date that's the same as or later than the min value.

### `setMin`

If the value entered into the component is earlier than the specified minimum date, the component will fail constraint validation. When both the min and max values are set, the min value must be a date that's the same as or earlier than the max value.


## Localized display

By default, the `DateField` displays information within the UI element based on the locale the browser is configured to. For example, users with United States configurations will see MM/dd/yyyy, whereas European users will see dd/MM/yyyy. Still, this doesn't stop you from manipulating the `LocalDate` object returned by the methods from the `DataField` class.

:::info Picker UI 
The appearance of the date picker input UI depends not only on the selected locale but also on the browser and operating system being used. This ensures automatic consistency with the interface users are already familiar with.
:::

## Static utilities

The `DateField` class also provides the following static utility methods:

- `fromDate(String dateAsString)`: Convert a date string in yyyy-MM-dd format to a `LocalDate` object which can then be utilized with this field, or elsewhere.

- `toDate(LocalDate date)`: Convert a `LocalDate` object to a date string in yyyy-MM-dd format.

- `isValidDate(String dateAsString)`: Checks to see if the given string is a valid yyyy-MM-dd date.

## Best practices

To ensure an optimal user experience when using the `DateField` component, consider the following best practices:

- **Accessibility**: Utilize appropriate labels to ensure users with assistive technologies can easily navigate to and use the date fields in your app.

- **Auto-Populate Current Date**: If appropriate for your app's use case, auto-populate with the date field with the current date.