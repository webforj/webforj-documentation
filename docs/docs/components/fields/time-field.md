---
sidebar_position: 40
title: TimeField
slug: timefield
description: A component that provides a default browser-based time picker for selecting a time value through an input field.
---

<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

`TimeField` is a user interface component that allows users to input or select times with hour-and-minute precision. If seconds are supplied, the component discards them.

<!-- INTRO_END -->

## Using the `TimeField` {#using-timefield}

<ParentLink parent="Field" />

`TimeField` extends the shared `Field` class, which provides common features across all field components. The following example creates a reminder `TimeField` initialized to the current time.

<ComponentDemo
path='/webforj/timefield'
files={['src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java']}
/>

## Usages {#usages}

The `TimeField` is ideal for choosing and displaying times in your app. Here are some examples of when to use the `TimeField`:

1. **Event Scheduling**: Time fields are essential in apps that involve setting times for events, appointments, or meetings.

2. **Time Tracking and Logging**: Apps that track time, such as timesheets, need time fields for accurate entries.

3. **Reminders and Alarms**: Using a time field simplifies the input process for users setting reminders or alarms in your app.

## Min and max value {#min-and-max-value}

With the `setMin()` and `setMax()` methods, you can specify a range of acceptable times.

- **For `setMin()`**: If the value entered into the component is earlier than the specified minimum time, the component will fail constraint validation. When both the min and max values are set, the min value must be a time that's the same as or earlier than the max value.

- **For `setMax()`**: If the value entered into the component is later than the specified maximum time, the component will fail constraint validation. When both the min and max values are set, the max value must be a time that's the same as or later than the min value.

## Value handling and localization {#value-handling-and-localization}

Internally, the `TimeField` component represents its value using a `LocalTime` object from the `java.time` package. This allows developers to interact with precise time values regardless of how they're visually rendered.

The browser determines how the picker displays the time for the user's locale. The field's text value uses 24-hour `HH:mm` format, and its `LocalTime` value is truncated to minutes.

If setting a raw string value, use the `setText()` method carefully:

```java
timeField.setText("09:15");    // valid
timeField.setText("09:15:30"); // also valid; seconds are discarded, leaving 09:15
```

:::warning
When using `setText()`, an `IllegalArgumentException` is thrown if the input can't be parsed as a valid time. Both `HH:mm` and `HH:mm:ss` inputs are accepted, but seconds are discarded.
:::


:::info Picker UI
The appearance of the time picker input UI depends on the selected locale, the browser, and the operating system. This creates automatic consistency with the interface users are already familiar with.
:::

## Static utilities {#static-utilities}

The `TimeField` class also provides the following static utility methods:

- `fromTime(String timeAsString)`: Parse a time string, with or without seconds, into a `LocalTime` truncated to minutes.

- `toTime(LocalTime time)`: Convert a `LocalTime` to a string in `HH:mm` format, discarding seconds.

- `isValidTime(String timeAsString)`: Check whether a time string is valid, including `HH:mm` and `HH:mm:ss` inputs. Returns `true` if valid and `false` otherwise.

## Best practices {#best-practices}

- **Provide Clear Time Format Examples**: Clearly show users the expected time format near the `TimeField`. Use examples or placeholders to help them enter the time correctly. If possible, display the time format based on the user's location.

- **Accessibility**: Use the `TimeField` component with accessibility in mind, meeting accessibility standards such as proper labels, sufficient color contrast, and compatibility with assistive technologies.

- **Reset Option**: Provide a way for users to easily clear the `TimeField` to an empty or default state.
