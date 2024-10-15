---
sidebar_position: 3
title: DateTimeField
slug: datetimefield
---

<DocChip tooltipText="This component will render with a shadow DOM, an API built into the browser that facilitates encapsulation." label="Shadow" component="a" href="../../glossary#shadow-dom" target="_blank" clickable={true} iconName="shadow" />

<DocChip tooltipText="The name of the web component that will render in the DOM." label="dwc-field" clickable={false} iconName='code'/>

<JavadocLink type="foundation" location="com/webforj/component/field/DateTimeField" top='true'/>

<ParentLink parent="Field" />

The `DateTimeField` class provides a user interface component that allows the user to enter both a date and a time, including the year, month, and day, as well as the time in hours and minutes. It offers the flexibility to validate the input or use a special date-time picker interface.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/datetimefielddemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldDemoView.java'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/fields/datefield/dateFieldDemo.css'
/>


### Usages

The `DateTimeField` is best used in scenarios where capturing or displaying both date **and** time is essential to the user interface or application functionality. Here are some examples of when to use the `DateTimeField`:

1. **Event Scheduling and Calendars**: If time of day is in important factor in applications that involve event scheduling, appointment booking, or managing calendars then a `DateTimeField` is the proper component to use.

2. **Form Inputs**: When designing forms that require date and time inputs, using a `DateTimeField` simplifies the selection process for users. This is particularly useful for applications that collect user data or require scheduling with specific times.

3. **Data Logging and Timestamps**: Applications that involve data logging or capturing timestamps require `DateTimeFields` to record the date and time when events occur or when data is submitted.

4. **Task Management and Deadlines**: `DateTimeFields` are valuable in applications that involve task management or setting deadlines where both the date and time are relevant for accurate scheduling.


### Maximum and Minimum

You can use the `setMax()` and `setMin()` methods to specify the acceptable range of dates and times. If the value entered into the component is outside of the specified timestamp, the component fails constraint validation. Also, if there is already a maximum or minimum set, the value given to the other method must be lower or higher respectively.

### Display

The `DateTimeField` will, by default, display its information within the UI element based on the locale the browser is configured to. For example, users with United States configurations will see the date displayed with the month preceding the day, whereas European users will see the day before the month. This does not inhibit manipulation of the `LocalDateTime` object returned by methods from the class, however.

:::info
When displaying time, the seconds will display by default. However, if the component is given a `LocaleDateTime` object with the seconds set to 0, the seconds are then hidden from the display.
:::

### Static Utilities 

The DateTimeField class also provides the following static utility methods:

- `fromDateTime(String dateTimeAsString)`: Convert a date and time string in yyyy-MM-ddTHH:mm:ss format to a LocalDateTime object which can then be utilized with this class, or elsewhere.

- `toDateTime(LocalDateTime dateTime)`: Convert a LocalDateTime object to a date and time string in yyyy-MM-ddTHH:mm:ss format.

- `isValidDateTime(String dateTimeAsString)`: Checks to see if the given string is a valid yyyy-MM-ddTHH:mm:ss date and time. This will return a boolean value true if so, false otherwise.

### Best Practices

To ensure an optimal user experience when using the `DateField` component, consider the following best practices:

1. **Consider Localized Date Display**: When displaying dates, consider localizing the date format and incorporating regional preferences. This enhances usability and ensures dates are presented in a familiar format to the user.

2. **Include Timezone Considerations**: If your application deals with time-sensitive information across different time zones, consider incorporating timezone selection alongside the date field to ensure accurate date-time representation.

3. **Consider Accessibility**: Utilize the `DateField` with accessibility in mind. Ensure that it meets accessibility standards, such as providing proper labels, and being compatible with assistive technologies.

4. **Auto-Populate Current Date**: Consider providing an option to auto-populate the current date as a default value in the date field, if appropriate for your application's use case.