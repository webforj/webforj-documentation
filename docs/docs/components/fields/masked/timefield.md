---
title: MaskedTimeField
sidebar_position: 20
---

<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

The `MaskedTimeField` is a text input control designed for precise, structured time entry. It lets users enter times as **numbers** and automatically formats the input based on a defined mask when the field loses focus. The mask is a string that specifies the expected time format, guiding both input and display.

This component supports flexible parsing, validation, localization, and value restoration. It's especially useful in time-sensitive forms like schedules, timesheets, and reservations.

:::tip Looking for date input?
The `MaskedTimeField` is built for **time-only** input. If you’re looking for a component to handle **dates** with similar mask-based formatting, take a look at the [`MaskedDateField`](./datefield.md).
:::

## Basics {#basics}

The `MaskedTimeField` can be instantiated with or without parameters. You can define an initial value, a label, a placeholder, and an event listener for value changes.

<ComponentDemo path='/webforj/maskedtimefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java' height='120px'/>

## Mask rules {#mask-rules}

The `MaskedTimeField` uses format indicators to define how time is parsed and displayed. Each format indicator begins with a `%` followed by a letter that represents a time component.

### Time format indicators {#time-format-indicators}

| Format | Description         |
|--------|---------------------|
| `%H`   | Hour (24-hour)      |
| `%h`   | Hour (12-hour)      |
| `%m`   | Minute              |
| `%s`   | Second              |
| `%p`   | AM/PM               |

### Modifiers {#modifiers}

Modifiers refine the display of time components:

| Modifier | Description               |
|----------|---------------------------|
| `z`      | Zero-fill                 |
| `s`      | Short text representation |
| `l`      | Long text representation  |
| `p`      | Packed number             |
| `d`      | Decimal (default format)  |

These allow for flexible and locale-friendly time formatting.

## Time format localization {#time-format-localization}

The `MaskedTimeField` supports localization by setting the appropriate locale. This ensures that time input and output match regional conventions.

```java
field.setLocale(Locale.GERMANY);
```

This affects how AM/PM indicators are displayed, how separators are handled, and how values are parsed.

## Parsing logic {#parsing-logic}

The `MaskedTimeField` parses user input based on the defined time mask. It accepts both complete and abbreviated numeric inputs with or without delimiters, allowing flexible entry while ensuring valid times.
Parsing behavior depends on the format order defined by the mask (e.g., `%Hz:%mz` for hour/minute). This format determines how numeric sequences are interpreted.

### Example parsing scenarios {#example-parsing-scenarios}

| Entry  | Mask          | Interpreted As|
|--------|---------------|---------------|
| `900`  | `%Hz:%mz`     | `09:00`       |
| `1345` | `%Hz:%mz`     | `13:45`       |
| `0230` | `%hz:%mz %p`  | `02:30 AM`    |
| `1830` | `%hz:%mz %p`  | `06:30 PM`    |

## Setting min/max constraints {#setting-minmax-constraints}

You can restrict the allowed time range in a `MaskedTimeField` using the `setMin()` and `setMax()` methods:

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Both methods accept values of type [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html). Inputs outside the defined range are considered invalid.

## Restoring the value {#restoring-the-value}

The `MaskedTimeField` includes a restore feature that resets the field’s value to a predefined or original state. This can be useful for undoing changes or returning to a default time.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Ways to restore the value {#ways-to-restore-the-value}

- **Programmatically**, by calling `restoreValue()`
- **Via keyboard**, by pressing <kbd>ESC</kbd> (this is the default restore key unless overridden by an event listener)

<ComponentDemo 
path='/webforj/maskedtimefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java' 
height='120px'/>

## Validation patterns {#validation-patterns}

You can apply client-side validation rules using regular expressions with the `setPattern()` method:

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

This pattern ensures that only values matching the `HH:mm` format (two digits, colon, two digits) are considered valid.

:::tip Regular Expression Format
The pattern must follow JavaScript RegExp syntax as documented [here](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Notes on Input Handling
The field attempts to parse and format numeric time inputs based on the current mask. However, users can still manually enter values that don't match the expected format. If the input is syntactically valid but semantically incorrect or unparseable (e.g. `99:99`), it may pass pattern checks but fail logical validation.
You should always validate the input value in your app logic, even if a regular expression pattern is set, to ensure the time is both correctly formatted and meaningful.
:::

## Time picker {#time-picker}

The `MaskedTimeField` includes a built-in time picker that lets users select a time visually, rather than typing it. This enhances usability for less technical users or when precise input is required.

<ComponentDemo 
path='/webforj/maskedtimefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java' 
height='450px'/>

### Accessing the picker {#accessing-the-picker}

You can access the time picker using `getPicker()`:

```java
TimePicker picker = field.getPicker();
```

### Show/hide the picker icon {#showhide-the-picker-icon}

Use `setIconVisible()` to show or hide the clock icon next to the field:

```java
picker.setIconVisible(true); // shows the icon
```

### Auto-open behavior {#auto-open-behavior}

You can configure the picker to open automatically when the user interacts with the field (e.g. clicks, presses Enter or arrow keys):

```java
picker.setAutoOpen(true);
```

:::tip Enforce Selection Through the Picker
To ensure users can only select a time using the picker (and not manually type one), combine the following two settings:

```java
field.getPicker().setAutoOpen(true); // Opens the picker on user interaction
field.setAllowCustomValue(false);    // Disables manual text input
```

This setup guarantees that all time input comes through the picker UI, which is useful when you want strict format control and eliminate parsing issues from typed input.
:::

### Manually open the picker {#manually-open-the-picker}

To open the time picker programmatically:

```java
picker.open();
```

Or use the alias:

```java
picker.show(); // same as open()
```

### Setting the picker step {#setting-the-picker-step}

You can define the interval between selectable times in the picker using `setStep()`. This allows you to control how granular the time options are—ideal for scenarios like scheduling in 15-minute blocks.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Step Constraint
The step must evenly divide an hour or a full day. Otherwise, an exception will be thrown.
:::

This ensures the dropdown list contains predictable, evenly spaced values like `09:00`, `09:15`, `09:30`, etc.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

The `MaskedTimeFieldSpinner` extends [`MaskedTimeField`](#basics) by adding spinner controls that let users increment or decrement the time using arrow keys or UI buttons. It provides a more guided interaction style, especially useful in desktop-style applications.

<ComponentDemo 
path='/webforj/maskedtimefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java' 
height='450px'/>

### Key features {#key-features}

- **Interactive Time Stepping:**  
  Use arrow keys or spin buttons to increment or decrement the time value.

- **Customizable Spin Unit:**  
  Choose which part of the time to modify using `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Options include `HOUR`, `MINUTE`, `SECOND`, and `MILLISECOND`.

- **Min/Max Boundaries:**  
  Inherits support for minimum and maximum allowed times using `setMin()` and `setMax()`.

- **Formatted Output:**  
  Fully compatible with masks and localization settings from `MaskedTimeField`.

### Example: Configure stepping by hour {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Styling {#styling}

<TableBuilder name="MaskedTimeField" />