---
title: MaskedDateField
sidebar_position: 5
---

<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

The `MaskedDateField` is a text input control designed for structured date entry. It lets users enter dates as **numbers** and automatically formats the input based on a defined mask when the field loses focus. The mask is a string that specifies the expected date format, guiding both input and display.

This component supports flexible parsing, validation, localization, and value restoration. It's especially useful in forms like registrations, bookings, and scheduling, where consistent and region-specific date formats are required.

:::tip Looking for time input?
The `MaskedDateField` is focused solely on **date** values. If you need a similar component for entering and formatting **time**, look into the [`MaskedTimeField`](./timefield) instead.
:::

## Basics {#basics}

The `MaskedDateField` can be instantiated with or without parameters. You can define an initial value, a label, a placeholder, and an event listener for value changes.

<ComponentDemo path='/webforj/maskeddatefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java' height='120px'/>

## Mask rules {#mask-rules}

The `MaskedDateField` supports multiple date formats used around the world, which vary by the order of day, month, and year. Common patterns include:

- **Day/Month/Year** (used across most of Europe)
- **Month/Day/Year** (used in the United States)
- **Year/Month/Day** (used in China, Japan, and Korea; also the ISO standard: `YYYY-MM-DD`)

Within these formats, local variations include the choice of separator (e.g., `-`, `/`, or `.`), whether years are two or four digits, and whether single-digit months or days are padded with leading zeros.

To handle this diversity, the `MaskedDateField` uses format indicators, each starting with `%`, followed by a letter that represents a specific part of the date. These indicators define how input is parsed and how the date is displayed.

### Date format indicators {#date-format-indicators}

| Format | Description |
| ------ | ----------- |
| `%Y`   | Year        |
| `%M`   | Month       |
| `%D`   | Day         |

### Modifiers {#modifiers}

Modifiers allow more control over how components of the date are formatted:

| Modifier | Description               |
| -------- | ------------------------- |
| `z`      | Zero-fill                 |
| `s`      | Short text representation |
| `l`      | Long text representation  |
| `p`      | Packed number             |
| `d`      | Decimal (default format)  |

These can be combined to build a wide variety of date masks.

## Date format localization {#date-format-localization}

The `MaskedDateField` adapts to regional date formats by setting the appropriate locale. This ensures that dates are displayed and parsed in a way that matches user expectations.

| Region        | Format     | Example      |
| ------------- | ---------- | ------------ |
| United States | MM/DD/YYYY | `07/04/2023` |
| Europe        | DD/MM/YYYY | `04/07/2023` |
| ISO Standard  | YYYY-MM-DD | `2023-07-04` |

To apply localization, use the `setLocale()` method. It accepts a [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) and automatically adjusts both formatting and parsing:

```java
dateField.setLocale(Locale.FRANCE);
```

## Parsing logic {#parsing-logic}

The `MaskedDateField` parses user input based on the defined date mask. It accepts both complete and abbreviated numeric inputs with or without delimiters, allowing flexible entry while ensuring valid dates.
Parsing behavior depends on the format order defined by the mask (e.g., `%Mz/%Dz/%Yz` for month/day/year). This format determines how numeric sequences are interpreted.

For example, assuming that today is `September 15, 2012`, this is how various inputs would be interpreted:

### Example parsing scenarios {#example-parsing-scenarios}

| Entry                                | YMD (ISO)                                                                                                                                                                                          | MDY (US)                                                                            | DMY (EU)                                                                                                                     |
| ------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>        | A single digit is always interpreted as a day number within the current month, so this would be September 1, 2012.                                                                                 | Same as YMD                                                                         | Same as YMD                                                                                                                  |
| <div align="center">`12`</div>       | Two digits are always interpreted as a day number within the current month, so this would be September 12, 2012.                                                                                   | Same as YMD                                                                         | Same as YMD                                                                                                                  |
| <div align="center">`112`</div>      | Three digits are interpreted as a 1-digit month number followed by a 2-digit day number, so this would be January 12, 2012.                                                                        | Same as YMD                                                                         | Three digits are interpreted as a 1-digit day number followed by a two-digit month number, so this would be December 1, 2012. |
| <div align="center">`1004`</div>     | Four digits are interpreted as MMDD, so this would be October 4, 2012.                                                                                                                             | Same as YMD                                                                         | Four digits are interpreted as DDMM, so this would be April 10, 2012.                                                         |
| <div align="center">`020304`</div>   | Six digits are interpreted as YYMMDD, so this would be March 4, 2002.                                                                                                                              | Six digits are interpreted as MMDDYY, so this would be February 3, 2004.            | Six digits are interpreted as DDMMYY, so this would be March 2, 2004.                                                         |
| <div align="center">`8 digits`</div> | Eight digits are interpreted as YYYYMMDD. For example, `20040612` is June 12, 2004.                                                                                                                | Eight digits are interpreted as MMDDYYYY. For example, `06122004` is June 12, 2004. | Eight digits are interpreted as DDMMYYYY. For example, `06122004` is December 6, 2004.                                        |
| <div align="center">`12/6`</div>     | Two numbers separated by any valid delimiter is interpreted as MM/DD, so this would be December 6, 2012. <br />Note: All characters except for letters and digits are considered valid delimiters. | Same as YMD                                                                         | Two numbers separated by any delimiter is interpreted as DD/MM, so this would be June 12, 2012.                               |
| <div align="center">`3/4/5`</div>    | April 5, 2012                                                                                                                                                                                      | March 4, 2005                                                                       | April 3, 2005                                                                                                                 |


## Textual date parsing {#textual-date-parsing}

By default, the `MaskedDateField` only accepts numeric input for dates. However, you can enable **textual date parsing** to allow users to enter month and day names in their input. This feature is particularly useful for creating more natural date entry experiences.

To enable textual parsing, use the `setTextualDateParsing()` method:

```java
dateField.setTextualDateParsing(true);
```

### Month name substitution {#month-name-substitution}

When textual parsing is enabled, you can use special modifiers in your mask to accept month names instead of numeric values:

- **`%Ms`** - Accepts short month names (Jan, Feb, Mar, etc.)
- **`%Ml`** - Accepts long month names (January, February, March, etc.)

Month names can appear in any position within the mask, and the field will still accept numeric input as a fallback.

#### Examples

| Mask | Input | Result |
| ---- | ----- | ------ |
| `%Ms/%Dz/%Yz` | `Sep/01/25` | ✅ Valid - Parses as September 1, 2025 |
| `%Ml/%Dz/%Yz` | `September/01/25` | ✅ Valid - Parses as September 1, 2025 |
| `%Dz/%Ml/%Yz` | `01/September/25` | ✅ Valid - Parses as September 1, 2025 |
| `%Ms/%Dz/%Yz` | `09/01/25` | ✅ Valid - Numeric fallback still works |

All 12 months are supported in both short (Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec) and long (January, February, etc.) forms.

### Day name decoration {#day-name-decoration}

Day-of-week names can be included in the input for better readability, but they are **decorative only** and are stripped during parsing. They don't affect the actual date value.

- **`%Ds`** - Accepts short day names (Mon, Tue, Wed, etc.)
- **`%Dl`** - Accepts long day names (Monday, Tuesday, Wednesday, etc.)

:::warning Day Names Require Numeric Day
When using day-of-week names (`%Ds` or `%Dl`), your mask **must also include** `%Dz` or `%Dd` to specify the actual day number. Without a numeric day component, the input will be invalid.
:::

#### Examples

| Mask | Input | Result |
| ---- | ----- | ------ |
| `%Ds %Mz/%Dz/%Yz` | `Mon 09/01/25` | ✅ Valid - Day name is decorative |
| `%Dl %Mz/%Dz/%Yz` | `Monday 09/01/25` | ✅ Valid - Day name is decorative |
| `%Mz/%Dz/%Yz %Ds` | `09/01/25 Tue` | ✅ Valid - Day name at the end |
| `%Dl/%Mz/%Yz` | `Monday/09/25` | ❌ Invalid - Missing `%Dz` |
| `%Mz/%Dl/%Yz` | `09/Monday/25` | ❌ Invalid - Missing `%Dz` |

All 7 weekdays are supported in both short (Mon, Tue, Wed, Thu, Fri, Sat, Sun) and long (Monday, Tuesday, etc.) forms.

### Additional parsing rules {#additional-parsing-rules}

Textual date parsing includes several helpful features:

- **Case-insensitive:** Input like `MONDAY 09/01/25`, `monday 09/01/25`, or `Monday 09/01/25` all work the same way.
- **Locale-aware:** Month and day names are recognized based on the field's locale setting. For example:
  - French locale: `septembre/01/25` is recognized as September
  - German locale: `Montag 09/01/25` is recognized with Monday as the day name

### When disabled (default)

If textual date parsing is **not** enabled (the default behavior), text names will not be recognized:

| Mask | Input | Result |
| ---- | ----- | ------ |
| `%Ms/%Dz/%Yz` | `Sep/01/25` | ❌ Invalid - Names not recognized |
| `%Ms/%Dz/%Yz` | `09/01/25` | ✅ Valid - Numeric input works |


## Setting min/max constraints {#setting-minmax-constraints}

You can restrict the allowed date range in a `MaskedDateField` using the `setMin()` and `setMax()` methods:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Both methods accept values of type [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). Input outside the defined range will be considered invalid.

## Restoring the value {#restoring-the-value}

The `MaskedDateField` includes a restore feature that resets the field’s value to a predefined or original state. This is useful for reverting user input or resetting to a default date.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Ways to restore the value {#ways-to-restore-the-value}

- **Programmatically**, by calling `restoreValue()`
- **Via keyboard**, by pressing <kbd>ESC</kbd> (this is the default restore key unless overridden by an event listener)

You can set the value to restore with `setRestoreValue()`, passing a [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) instance.

<ComponentDemo 
path='/webforj/maskeddatefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java' 
height='120px'/>

## Validation patterns {#validation-patterns}

You can apply client-side validation rules using regular expressions with the `setPattern()` method:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

This pattern ensures that only values matching the `MM/DD/YYYY` format (two digits, slash, two digits, slash, four digits) are considered valid.

:::tip Regular Expression Format
The pattern must follow JavaScript RegExp syntax as documented [here](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Notes on Input Handling
The field attempts to parse and format numeric date inputs based on the current mask. However, users can still manually enter values that don't match the expected format. If the input is syntactically valid but semantically incorrect or unparsable (e.g. `99/99/9999`), it may pass pattern checks but fail logical validation.
You should always validate the input value in your app logic, even if a regular expression pattern is set, to ensure the date is both correctly formatted and meaningful.
::::

## Date picker {#date-picker}

The `MaskedDateField` includes a built-in calendar picker that lets users select a date visually, rather than typing it. This enhances usability for less technical users or when precise input is required.

<ComponentDemo 
path='/webforj/maskeddatefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java' 
height='450px'/>

### Accessing the picker {#accessing-the-picker}

You can access the date picker using `getPicker()`:

```java
DatePicker picker = dateField.getPicker();
```

### Show/hide the picker icon {#showhide-the-picker-icon}

Use `setIconVisible()` to show or hide the calendar icon next to the field:

```java
picker.setIconVisible(true); // shows the icon
```

### Auto-open behavior {#auto-open-behavior}

You can configure the picker to open automatically when the user interacts with the field (e.g., clicks, presses Enter or arrow keys):

```java
picker.setAutoOpen(true);
```

:::tip Enforce Selection Through the Picker
To ensure users can only select a date using the calendar picker (and not manually type one), combine the following two settings:

```java
dateField.getPicker().setAutoOpen(true); // Opens the picker on user interaction
dateField.setAllowCustomValue(false);    // Disables manual text input
```

This setup guarantees that all date input comes through the picker UI, which is useful when you want strict format control and eliminate parsing issues from typed input.
:::

### Manually open the calendar {#manually-open-the-calendar}

To open the calendar programmatically:

```java
picker.open();
```

Or use the alias:

```java
picker.show(); // same as open()
```

### Show weeks in the calendar {#show-weeks-in-the-calendar}

The picker can optionally display week numbers in the calendar view:

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

The `MaskedDateFieldSpinner` extends [`MaskedDateField`](#basics) by adding spinner controls that let users increment or decrement the date using arrow keys or UI buttons. It provides a more guided interaction style, especially useful in desktop-style applications.

<ComponentDemo 
path='/webforj/maskeddatefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java' 
height='450px'/>

### Key features {#key-features}

- **Interactive Date Stepping:**  
  Use arrow keys or spin buttons to increment or decrement the date value.

- **Customizable Step Unit:**  
  Choose which part of the date to modify using `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Options include `DAY`, `WEEK`, `MONTH`, and `YEAR`.

- **Min/Max Boundaries:**  
  Inherits support for minimum and maximum allowed dates using `setMin()` and `setMax()`.

- **Formatted Output:**  
  Fully compatible with masks and localization settings from `MaskedDateField`.

### Example: Configure weekly stepping {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

This makes each spin step advance or rewind the date by one week.

## Styling {#styling}

<TableBuilder name="MaskedDateField" />