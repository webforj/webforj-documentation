---
sidebar_position: 40
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
path='/webforj/timefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java'
/>

## Usages

The `TimeField` is ideal for choosing and displaying times in your app. Here are some examples of when to use the `TimeField`:

1. **Event Scheduling**: Time fields are essential in apps that involve setting times for events, appointments, or meetings.

2. **Time Tracking and Logging**: Apps that track time, such as timesheets, need time fields for accurate entries.

3. **Reminders and Alarms**: Using a time field simplifies the input process for users setting reminders or alarms in your app.

## Min and max value

With the `setMin()` and `setMax()` methods, you can specify a range of acceptable times.

- **For `setMin()`**: If the value entered into the component is earlier than the specified minimum time, the component will fail constraint validation. When both the min and max values are set, the min value must be a time that's the same as or earlier than the max value.

- **For `setMax()`**: If the value entered into the component is later than the specified maximum time, the component will fail constraint validation. When both the min and max values are set, the max value must be a time that's the same as or later than the min value. 

## Localized display

The `TimeField` will, by default, display its information within the UI element based on the locale the browser is configured to. For example, users with United States configurations will see the time displayed with A.M. and P.M. labels, whereas other locales would see the time in 24-hour format. Still, this doesn't stop you from manipulating the `LocalTime` object returned by the methods from the `TimeField` class.

The value of the time field is always in 24-hour format that includes leading zeros: HH:mm, regardless of the UI field format, which is likely to be selected based on the user's locale (or by the user agent). If the time includes seconds, the format is always HH:mm:ss

:::tip
To hide seconds from the display, give the `TimeField` a `LocaleTime` object with the seconds set to 0.
:::

:::info Picker UI 
The appearance of the time picker input UI depends not only on the selected locale but also on the browser and operating system being used. This ensures automatic consistency with the interface users are already familiar with.
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