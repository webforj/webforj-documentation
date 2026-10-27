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

The `DateTimeField` component lets users input both a date and a time in a single field, covering year, month, day, hours, and minutes. It validates input for accuracy and can present a date-time picker to make selection easier.

<!-- INTRO_END -->

## Using `DateTimeField` {#using-datetimefield}

<ParentLink parent="Field" />

`DateTimeField` extends the shared `Field` class, which provides common features across all field components. The following example creates a labeled `DateTimeField` for selecting a departure date and time.

<ComponentDemo
path='/webforj/datetimefield'
files={['src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java']}
/>

## Usages {#usages}

The `DateTimeField` is best used in scenarios where capturing or displaying both date **and** time is essential to your app. Here are some examples of when to use the `DateTimeField`:

1. **Event Scheduling and Calendars**: Let users efficiently schedule events, book appointments, and manage their calendars by giving them a single component that allows them to choose the date and time.
<!-- vale off -->
2. **Check-in and check-out**: Facilitate user selection of check-in and check-out times when the period can span multiple days.
<!-- vale on -->
3. **Data Logging and Timestamps**: Utilize `DateTimeFields` for apps that involve recording the date and time of when events occur or when a user submits data.

4. **Task Management and Deadlines**: `DateTimeFields` are valuable in applications that involve task management or setting deadlines where both the date and time are relevant for accurate scheduling.

## Field value (`LocalDateTime`) {#field-value-localdatetime}

Internally, the `DateTimeField` component represents its value using a `LocalDateTime` object from the `java.time` package. Values are truncated to minutes, so seconds and fractional seconds are discarded.

The browser displays the date and time according to the user's locale, while the field's text value uses the format `yyyy-MM-ddTHH:mm`. `LocalDateTime` doesn't include a time zone, and input with a time zone suffix such as `Z` or `+02:00` isn't accepted.

### Getting and setting the value {#getting-and-setting-the-value}

To retrieve the current value, use the `getValue()` method:

```java
LocalDateTime value = dateTimeField.getValue();
```

To programmatically set the value, use the `setValue()` method:

```java
dateTimeField.setValue(LocalDateTime.of(2024, 4, 27, 14, 30));
```

### Using `setText()` {#using-settext}

To set the value from a raw string, use `setText()` with a local date-time, such as `yyyy-MM-ddTHH:mm`. Input can also include seconds or fractional seconds, which are discarded.

```java
dateTimeField.setText("2024-04-27T14:30");       // valid
dateTimeField.setText("2024-04-27T14:30:45");    // valid; seconds are discarded

dateTimeField.setText("24-04-27T14:30");        // invalid; use a four-digit year
dateTimeField.setText("2024-04-27T14:30Z");      // invalid; time zone suffixes aren't accepted
```

:::warning
When using `setText()`, an `IllegalArgumentException` is thrown if the input can't be parsed as a valid local date-time. Both minute-precision input and input with seconds are accepted, but time zone suffixes aren't.
:::

## Static utilities {#static-utilities}

The DateTimeField class also provides the following static utility methods:

- `fromDateTime(String dateTimeAsString)`: parse a local date-time string, with or without seconds, into a `LocalDateTime` truncated to minutes.

- `toDateTime(LocalDateTime dateTime)`: convert a `LocalDateTime` to a string in `yyyy-MM-ddTHH:mm` format. Seconds and fractional seconds are discarded.

- `isValidDateTime(String dateTimeAsString)`: check whether a string is a valid local date-time. Input with seconds or fractional seconds is accepted. Returns `false` for invalid input or time zone suffixes.

## Min and max value {#min-and-max-value}

### The min value {#the-min-value}

If the value entered into the component is earlier than the specified minimum timestamp, the component will fail constraint validation. When both the min and max values are set, the min value must be a timestamp that's the same as or earlier than the max value.

```java
// Set minimum allowed timestamp: January 1, 2023 at 08:00
dateTimeField.setMin(LocalDateTime.of(2023, 1, 1, 8, 0));
```

### The max value {#the-max-value}

If the value entered into the component is later than the specified maximum timestamp, the component will fail constraint validation. When both the min and max values are set, the max value must be a timestamp that's the same as or later than the min value.

```java
// Set maximum allowed timestamp: December 31, 2023 at 18:00
dateTimeField.setMax(LocalDateTime.of(2023, 12, 31, 18, 0));
```

## Best practices {#best-practices}

To ensure an optimal user experience when using the `DateTimeField` component, consider the following best practices:

- **Localized Date Display**: Localizing the date format and incorporating regional preferences ensures dates are presented in a familiar format to the user.

- **Handle Time Zones Separately**: if your app works across time zones, provide a separate time zone selector and handle conversions in your app. The `DateTimeField` stores only the local date and time.

- **Accessibility**: Utilize the `DateTimeField` with accessibility in mind. Ensure that it meets accessibility standards, such as providing proper labels and being compatible with assistive technologies.

- **Auto-Populate Current Date**: consider providing an option to auto-populate the current date and time as a default value in the date time field, if appropriate for your app's use case.
