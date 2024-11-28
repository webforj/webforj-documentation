---
sidebar_position: 10
title: DateTimeField
slug: datetimefield
description: A component that provides a default browser-based date and time picker for selecting both date and time through a single input field.
---

<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<JavadocLink type="foundation" location="com/webforj/component/field/DateTimeField" top='true'/>

<ParentLink parent="Field" />

The `DateTimeField` component is designed to allow users to input both a date and a time. This includes specifying the year, month, and day, along with the time in hours and minutes. It provides users with the option to validate their input for accuracy or utilize a dedicated date-time picker interface to streamline the selection process.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/datetimefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java'
/>

## Usages

The `DateTimeField` is best used in scenarios where capturing or displaying both date **and** time is essential to your app. Here are some examples of when to use the `DateTimeField`:

1. **Event Scheduling and Calendars**: Let users efficiently schedule events, book appointments, and manage their calendars by giving them a single component that allows them to choose the date and time.

2. **Check-in and check-out**: Facilitate user selection of check-in and check-out times when the period can span multiple days.

3. **Data Logging and Timestamps**: Utilize `DateTimeFields` for apps that involve recording the date and time of when events occur or when a user submits data.

4. **Task Management and Deadlines**: `DateTimeFields` are valuable in applications that involve task management or setting deadlines where both the date and time are relevant for accurate scheduling.

## Maximum and minimum

You can use the `setMax()` and `setMin()` methods to specify the acceptable range of dates and times. If the value entered into the component is outside of the specified timestamp, the component fails constraint validation. Also, if there is already a maximum or minimum set, the value given to the other method must be lower or higher respectively.

## Localized display

By default, the `DateTimeField` displays its information within the UI element based on the locale the browser is configured to. For example, users with United States configurations will see the date displayed with the month preceding the day, whereas European users will see the day before the month. Still, this doesn't stop you from manipulating the `LocalDateTime` object returned by the methods from the `DataTimeField` class.

:::tip
To hide seconds from the display, give the `DateTimeField` a `LocaleDateTime` object with the seconds set to 0.
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