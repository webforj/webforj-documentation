---
sidebar_position: 8
title: TimeField
slug: timefield
description: A component that provides a default browser-based time picker for selecting a time value through an input field.
---

<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

<ParentLink parent="Field" />

`TimeField` is a user interface component that allows users to input or select times in hours, minutes, and optionally seconds. It provides an intuitive and efficient way to handle time-related information in various applications.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/timefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java'
/>

## Usages

The `TimeField` is ideal for choosing and displaying times in your app. Here are some examples of when to use the `TimeField`:

1. **Event Scheduling**: Time fields are essential in apps that involve setting times for events, appointments, or meetings.

2. **Time Tracking and Logging**: Apps that track time, such as timesheets, need time fields for accurate entries.

3. **Reminders and Alarms**: Using a time field simplifies the input process for users setting reminders or alarms in your app.

## Maximum and minimum

With the `setMax()` and `setMin()` methods, you can specify a range of acceptable times. If a user manually enters a value into the time field outside the specified range, the component will let the user know what's acceptable. Also, if a maximum or minimum is already set, the value for the other method must be lower or higher respectively.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/timefieldminmax'
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/fielddemos/TimeFieldMinMax.java'
/>

## Localized display

The `TimeField` will, by default, display its information within the UI element based on the locale the browser is configured to. For example, users with United States configurations will see the time displayed with A.M. and P.M. labels, whereas other locales would see the time in 24-hour format. Still, this doesn't stop you from manipulating the `LocalTime` object returned by the methods from the `TimeField` class.

The value of the time field is always in 24-hour format that includes leading zeros: HH:mm, regardless of the UI field format, which is likely to be selected based on the user's locale (or by the user agent). If the time includes seconds, the format is always HH:mm:ss

:::tip
To hide seconds from the display, give the `TimeField` a `LocaleTime` object with the seconds set to 0.
:::

## Static utilities

The `TimeField` class also provides the following static utility methods:

- `fromTime(String timeAsString)`: Convert a time string in HH:mm:ss format to a LocalTime object which can then be utilized with this class, or elsewhere.

- `toTime(LocalTime time)`: Convert a LocalTime to a time string in HH:mm:ss format.

- `isValidTime(String timeAsString)`: Check if the given string is a valid HH:mm:ss time. This will return a boolean value true if so, false otherwise.

## Best practices

- **Provide Clear Time Format Examples**: Clearly show users the expected time format near the `TimeField`. Use examples or placeholders to help them enter the time correctly. If possible, display the time format based on the user's location.

- **Accessibility**: Utilize the `TimeField` component with accessibility in mind, ensuring it meets accessibility standards such as providing proper labels, sufficient color contrast, and compatibility with assistive technologies.

- **Reset Option**: Provide a way for users to easily clear the `TimeField` to an empty or default state.