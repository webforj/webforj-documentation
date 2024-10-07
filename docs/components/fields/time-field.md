---
sidebar_position: 7
title: TimeField
# sidebar_class_name: sidebar--item__hidden
slug: timefield
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';
import DocChip from '@site/src/components/DocsTools/DocChip';

<DocChip tooltipText="This component will render with a shadow DOM, an API built into the browser that facilitates encapsulation." label="Shadow" component="a" href="../../glossary#shadow-dom" target="_blank" clickable={true} iconName="shadow" />

<DocChip tooltipText="The name of the web component that will render in the DOM." label="dwc-field" clickable={false} iconName='code'/>

<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

:::success **Important**
The `TimeField` class is a Field component, and as such shares all of the commonalities belonging to a Field. Please refer to the **[Field documentation page](/docs/components/fields)** for an overview of Field properties, events, and other important information.
:::

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/timefielddemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldDemoView.java'
height='300px'
/>

### Usages

The `TimeField` is best used in scenarios where capturing or manipulating time values is crucial to the user interface or application functionality. Here are some examples of when to use the `TimeField`:

1. **Event Scheduling and Calendars**: Time fields are essential in applications that involve event scheduling, appointment booking, or managing calendars where precise time selection is required.

2. **Time Tracking and Logging**: Applications that involve time tracking, such as timesheet management or logging work hours, require Time fields to capture accurate time entries.

3. **Reminders and Alarms**: If your application involves setting reminders or alarms that require specific times, using a `TimeField` simplifies the input process for users.

4. **Meeting or Event Invitations**: When designing applications that involve meeting or event invitations, a `TimeField` enables users to specify event start times or deadlines accurately.

The `TimeField` class provides a user interface component that allows the user to enter both a time; hours, minutes, and optionally seconds. The value of the time field is always in 24-hour format that includes leading zeros: HH:mm, regardless of the UI field format, which is likely to be selected based on the user's locale (or by the user agent). If the time includes seconds, the format is always HH:mm:ss

### Maximum and Minimum

You can use the `setMax()` and `setMin()` methods to specify the acceptable time range. If the value entered into the component is outside of the specified value, the component fails constraint validation. Also, if there is already a maximum or minimum set, the value given to the other method must be lower or higher respectively.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.fielddemos.TimeFieldMinMax' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/fielddemos/TimeFieldMinMax.java'
height='300px'
/>

### Display

The `TimeField` will, by default, display its information within the UI element based on the locale the browser is configured to. For example, users with United States configurations will see the time displayed with A.M. and P.M. labels, whereas other locales would see the time in 24 hour format. This does not inhibit manipulation of the `LocalTime` object returned by methods from the class, however.

:::info
When displaying time, the seconds will display by default. However, if the component is given a `LocaleTime` object with the seconds set to 0, the seconds are then hidden from the display.
:::

### Static Utilities 

The `TimeField` class also provides the following static utility methods:

- `fromTime(String timeAsString)`: Convert a time string in HH:mm:ss format to a LocalTime object which can then be utilized with this class, or elsewhere.

- `toTime(LocalTime time)`: Convert a LocalTime to a time string in HH:mm:ss format.

- `isValidTime(String timeAsString)`: Check if the given string is a valid HH:mm:ss time. This will return a boolean value true if so, false otherwise.

### Best Practices

1. **Provide Clear Time Format Examples**: Clearly indicate the expected time format to users within or near the `TimeField`. Use examples or placeholders to guide users in entering time correctly. Consider displaying the time format dynamically based on the user's locale if applicable.

2. **Consider Accessibility**: Utilize the `TimeField` component with accessibility in mind, ensuring it meets accessibility standards such as providing proper labels, sufficient color contrast, and compatibility with assistive technologies.

3. **Include Clear Time Clearing Option**: Enable users to clear the selected time easily if needed, providing a way to reset the `TimeField` to an empty state.