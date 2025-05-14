---
sidebar_position: 15
title: DateTimeField
slug: datetimefield
description: A component that provides a default browser-based date and time picker for selecting both date and time through a single input field.
---

<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateTimeField" top='true'/>

<ParentLink parent="Field" />

The `DateTimeField` component is designed to allow users to input both a date and a time. This includes specifying the year, month, and day, along with the time in hours and minutes. It provides users with the option to validate their input for accuracy or utilize a dedicated date-time picker interface to streamline the selection process.

<ComponentDemo 
path='/webforj/datetimefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java'
/>

## Usages

The `DateTimeField` is best used in scenarios where capturing or displaying both date **and** time is essential to your app. Here are some examples of when to use the `DateTimeField`:

1. **Event Scheduling and Calendars**: Let users efficiently schedule events, book appointments, and manage their calendars by giving them a single component that allows them to choose the date and time.
<!-- vale off -->
2. **Check-in and check-out**: Facilitate user selection of check-in and check-out times when the period can span multiple days.
<!-- vale on -->
3. **Data Logging and Timestamps**: Utilize `DateTimeFields` for apps that involve recording the date and time of when events occur or when a user submits data.

4. **Task Management and Deadlines**: `DateTimeFields` are valuable in applications that involve task management or setting deadlines where both the date and time are relevant for accurate scheduling.

## Min and max value

### `setMax`

If the value entered into the component is later than the specified maximum timestamp, the component will fail constraint validation. When both the min and max values are set, the max value must be a timestamp that's the same as or later than the min value.

### `setMin`

If the value entered into the component is earlier than the specified minimum timestamp, the component will fail constraint validation. When both the min and max values are set, the min value must be a timestamp that's the same as or earlier than the max value.

## Field value (`LocalDateTime`)

Internally, the `DateTimeField` component represents its value using a `LocalDateTime` object from the `java.time` package. This provides precise control over both the date and time components of the input.

While the **client-side input is rendered based on the user's browser locale** (e.g., date and time formats that match local conventions), the internal parsing and formatting follow a strict and predictable structure: **`yyyy-MM-ddTHH:mm:ss`**.

### Getting and setting the value

To retrieve the current value:

```java
LocalDateTime value = dateTimeField.getValue();
```

To programmatically set the value:

```java
dateTimeField.setValue(LocalDateTime.of(2024, 4, 27, 14, 30, 0));
```

### Using `setText()`

If you prefer to set the value via a raw string, it must follow the full format `yyyy-MM-ddTHH:mm:ss`:

```java
dateTimeField.setText("2024-04-27T14:30:00"); // valid
```

:::tip
When using `setText(...)`, the component will attempt to parse the input string in the `yyyy-MM-ddTHH:mm:ss` format. If the input doesn't match this format, an `IllegalArgumentException` will be thrown.
:::

## Static utilities 

The DateTimeField class also provides the following static utility methods:

- `fromDateTime(String dateTimeAsString)`: Convert a date and time string in yyyy-MM-ddTHH:mm:ss format to a LocalDateTime object which can then be utilized with this class, or elsewhere.

- `toDateTime(LocalDateTime dateTime)`: Convert a LocalDateTime object to a date and time string in yyyy-MM-ddTHH:mm:ss format.

- `isValidDateTime(String dateTimeAsString)`: Checks to see if the given string is a valid yyyy-MM-ddTHH:mm:ss date and time. This will return a boolean value true if so, false otherwise.

## Best practices

To ensure an optimal user experience when using the `DateTimeField` component, consider the following best practices:

- **Localized Date Display**: Localizing the date format and incorporating regional preferences ensures dates are presented in a familiar format to the user.

- **Include Timezones**: If your app deals with time-sensitive information across different time zones, consider incorporating timezone selection alongside the date field to ensure accurate date-time representation.

- **Accessibility**: Utilize the `DateTimeField` with accessibility in mind. Ensure that it meets accessibility standards, such as providing proper labels and being compatible with assistive technologies.

- **Auto-Populate Current Date**: Consider providing an option to auto-populate the current date and time as a default value in the date time field, if appropriate for your app's use case.