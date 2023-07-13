---
sidebar_position: 7
title: TimeField
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="engine" location="org/dwcj/component/field/TimeField"/>

:::success **Important**
The `TimeField` class is a Field component, and as such shares all of the commonalities belonging to a Field. Please refer to the **[Field documentation page](/docs/components/fields)** for an overview of Field properties, events, and other important information.
:::

The `TimeField` class provides a user interface component that allows the user to enter both a time; hours, minutes, and optionally seconds. The value of the time field is always in 24-hour format that includes leading zeros: HH:mm, regardless of the field format, which is likely to be selected based on the user's locale (or by the user agent). If the time includes seconds, the format is always HH:mm:ss

### Constructors

The `TimeField` class has four constructors:

1. `TimeField(String label, LocalTime time)`: Creates a `TimeField` with a given label and time.
2. `TimeField(String label)`: Creates a `TimeField` with a given label but with no pre-populated time.
3. `TimeField(LocalTime time)`: Creates a `TimeField` with a given time, but without a label.
4. `TimeField()`: Creates a `TimeField` without any provided information.

### Maximum and Minimum

You can use the `setMax()` and `setMin()` methods to specify the acceptable time range. If the value entered into the component is outside of the specified value, the component fails constraint validation. Also, if there is already a maximum or minimum set, the value given to the other method must be lower or higher respectively.

### Static Utilities 

The `TimeField` class also provides the following static utility methods:

`fromTime(String timeAsString)`: Convert a time string in HH:mm:ss format to a LocalTime object which can then be utilized with this class, or elsewhere.

`toTime(LocalTime time)`: Convert a LocalTime to a time string in HH:mm:ss format.

`isValidTime(String timeAsString)`: Check if the given string is a valid HH:mm:ss time. This will return a boolean value true if so, false otherwise.

### Usages

The `TimeField` is best used in scenarios where capturing or manipulating time values is crucial to the user interface or application functionality. Here are some examples of when to use the `TimeField`:

1. **Event Scheduling and Calendars**: Time fields are essential in applications that involve event scheduling, appointment booking, or managing calendars where precise time selection is required.

2. **Time Tracking and Logging**: Applications that involve time tracking, such as timesheet management or logging work hours, require Time fields to capture accurate time entries.

3. **Reminders and Alarms**: If your application involves setting reminders or alarms that require specific times, using a `TimeField` simplifies the input process for users.

4. **Meeting or Event Invitations**: When designing applications that involve meeting or event invitations, a `TimeField` enables users to specify event start times or deadlines accurately.

### Best Practices

1. **Provide Clear Time Format Examples**: Clearly indicate the expected time format to users within or near the `TimeField`. Use examples or placeholders to guide users in entering time correctly. Consider displaying the time format dynamically based on the user's locale if applicable.

1. **Consider Accessibility**: Utilize the `TimeField` component with accessibility in mind, ensuring it meets accessibility standards such as providing proper labels, sufficient color contrast, and compatibility with assistive technologies.

1. **Include Clear Time Clearing Option**: Enable users to clear the selected time easily if needed, providing a way to reset the `TimeField` to an empty state.


### Parts and CSS Properties

<TableBuilder tag={require('@site/docs/components/_bbj_control_map.json').Field} />