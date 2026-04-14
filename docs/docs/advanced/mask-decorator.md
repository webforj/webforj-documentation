---
sidebar_position: 16
title: MaskDecorator
sidebar_class_name: new-content
---

<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/MaskDecorator" top='true'/>

`MaskDecorator` is a static utility class for applying masks to strings, numbers, dates, and times outside of an input field. It uses the same mask syntax as webforJ's [masked field components](/docs/components/fields/masked/overview), making it straightforward to format and parse values consistently—whether in a display label, a [`Table`](/docs/components/table/overview) renderer, or any other location in your app.

Use `MaskDecorator` when you need to format values programmatically for display rather than for interactive input, such as in table cell renderers, read-only labels, exported reports, or any context where a form field isn't appropriate. For interactive formatting as a user types, use a masked field component instead.

## Masking strings {#masking-strings}

Use `forString()` to apply a character mask to a plain string value:

```java
String result = MaskDecorator.forString("abc123", "AAA-000");
// → "ABC-123"
```

The mask controls which characters are accepted at each position.

### Mask characters {#string-mask-characters}

| Character | Description |
|-----------|-------------|
| `X`       | Any printable character |
| `a`       | Any alphabetic character |
| `A`       | Any alphabetic character; lowercase letters are converted to uppercase |
| `0`       | Any digit (0–9) |
| `z`       | Any digit or letter |
| `Z`       | Any digit or letter; lowercase letters are converted to uppercase |

Any other character in the mask is treated as a literal and is inserted as-is into the output. Invalid characters in the input are silently ignored, short inputs are padded with spaces, and long inputs are truncated to fit the mask.

### Examples {#string-examples}

```java
MaskDecorator.forString("1234567890", "(000) 000-0000");  // → "(123) 456-7890"
MaskDecorator.forString("a1b2c3",     "A0A 0A0");         // → "A1B 2C3"
MaskDecorator.forString("1234",       "ZZZZ-0000");        // → "1234-    " (padded)
```

## Masking numbers {#masking-numbers}

Use `forNumber()` to format a numeric value using a number mask:

```java
String result = MaskDecorator.forNumber(1234567.89, "#,###,##0.00");
// → "1,234,567.89"
```

### Mask characters {#number-mask-characters}

| Character | Description |
|-----------|-------------|
| `0`       | Always replaced by a digit (0–9) |
| `#`       | Suppresses leading zeros. Replaced by the fill character to the left of the decimal point. For trailing digits to the right, replaced by a space or zero. Otherwise, replaced by a digit |
| `,`       | Used as a grouping separator. Replaced by the fill character if no digits have yet been placed; otherwise shown as a comma |
| `-`       | Displays `-` if the number is negative; replaced by the fill character if positive |
| `+`       | Displays `+` if positive, or `-` if negative |
| `$`       | Always results in a dollar sign |
| `(`       | Inserts `(` if the number is negative; replaced by the fill character if positive |
| `)`       | Inserts `)` if the number is negative; replaced by the fill character if positive |
| `CR`      | Inserts `CR` for negative numbers; two spaces for positive numbers |
| `DR`      | Inserts `CR` for negative numbers; `DR` for positive numbers |
| `*`       | Always inserts an asterisk |
| `.`       | Marks the decimal point. Replaced by the fill character if no digits appear in the output. After the decimal, the fill character becomes a space |
| `B`       | Always becomes a space; any other literal character is copied as-is |

The characters `-`, `+`, `$`, and `(` can float: the first occurrence is moved to the last position where a `#` or `,` was replaced by the fill character.

:::info Rounding behavior
`forNumber()` rounds values to match the decimal precision in the mask. For example, `MaskDecorator.forNumber(12.34567, "###0.00")` produces `"  12.35"`.
:::

### Examples {#number-examples}

```java
MaskDecorator.forNumber(1234.5,    "###,##0.00");  // → "  1,234.50"
MaskDecorator.forNumber(-9876.0,   "###,##0.00-"); // → "  9,876.00-"
MaskDecorator.forNumber(42.0,      "$###,##0.00"); // → "     $42.00"
MaskDecorator.forNumber(0.5,       "#0.000");      // → " 0.500"
```

## Masking dates {#masking-dates}

Use `forDate()` to format a `LocalDate` value with a date mask:

```java
LocalDate date = LocalDate.of(2025, 7, 4);
String result = MaskDecorator.forDate(date, "%Mz/%Dz/%Yl");
// → "07/04/2025"
```

Use `parseDate()` to parse a masked date string back to a `LocalDate`:

```java
LocalDate date = MaskDecorator.parseDate("07/04/2025", "%Mz/%Dz/%Yl");
// → LocalDate.of(2025, 7, 4)
```

A locale-aware overload is available when parsing strings that contain week number references:

```java
LocalDate date = MaskDecorator.parseDate("07/04/2025", "%Mz/%Dz/%Yl", Locale.US);
```

### Date format indicators {#date-format-indicators}

| Format | Description |
|--------|-------------|
| `%Y`   | Year        |
| `%M`   | Month       |
| `%D`   | Day         |

### Modifiers {#date-modifiers}

An optional modifier immediately follows the format indicator:

| Modifier | Description               |
|----------|---------------------------|
| `z`      | Zero-fill                 |
| `s`      | Short text representation |
| `l`      | Long text representation  |
| `p`      | Packed number             |
| `d`      | Decimal (default format)  |

### Examples {#date-examples}

```java
LocalDate d = LocalDate.of(2025, 3, 5);

MaskDecorator.forDate(d, "%Mz/%Dz/%Yl");  // → "03/05/2025"
MaskDecorator.forDate(d, "%Dz.%Mz.%Yz");  // → "05.03.25"
MaskDecorator.forDate(d, "%Dl, %Ml %Dz");  // → "Wednesday, March 05"
MaskDecorator.forDate(d, "%Yl-%Mz-%Dz");  // → "2025-03-05"
```

## Masking times {#masking-times}

Use `forTime()` to format a `LocalTime` value with a time mask:

```java
LocalTime time = LocalTime.of(14, 30, 0);
String result = MaskDecorator.forTime(time, "%Hz:%mz");
// → "14:30"
```

Use `parseTime()` to parse a masked time string back to a `LocalTime`:

```java
LocalTime time = MaskDecorator.parseTime("14:30", "%Hz:%mz");
// → LocalTime.of(14, 30)
```

A locale-aware overload is available when parsing strings that contain localized AM/PM values:

```java
LocalTime time = MaskDecorator.parseTime("02:30 pm", "%hz:%mz %p", Locale.US);
```

### Time format indicators {#time-format-indicators}

| Format | Description          |
|--------|----------------------|
| `%H`   | Hour (24-hour clock) |
| `%h`   | Hour (12-hour clock) |
| `%m`   | Minute               |
| `%s`   | Second               |
| `%p`   | am/pm                |

### Modifiers {#time-modifiers}

Time masks use the same modifiers as date masks. See [Date modifiers](#date-modifiers).

### Examples {#time-examples}

```java
LocalTime t = LocalTime.of(9, 5, 30);

MaskDecorator.forTime(t, "%Hz:%mz:%sz");  // → "09:05:30"
MaskDecorator.forTime(t, "%hz:%mz %p");   // → "09:05 am"
MaskDecorator.forTime(t, "%Hz%mz");       // → "0905"
```

## Masking date and time {#masking-datetime}

Use `forDateTime()` to format a `LocalDateTime` value using a combined date and time mask:

```java
LocalDateTime dt = LocalDateTime.of(2025, 7, 4, 14, 30, 0);
String result = MaskDecorator.forDateTime(dt, "%Mz/%Dz/%Yl %Hz:%mz");
// → "07/04/2025 14:30"
```

### Format indicators {#datetime-format-indicators}

`forDateTime()` supports all date and time format indicators in any combination. See [Date format indicators](#date-format-indicators) and [Time format indicators](#time-format-indicators) for the full list.

### Modifiers {#datetime-modifiers}

All modifiers described in [Date modifiers](#date-modifiers) apply to both the date and time portions of a combined mask.

### Examples {#datetime-examples}

```java
LocalDateTime dt = LocalDateTime.of(2025, 7, 4, 14, 30, 0);

MaskDecorator.forDateTime(dt, "%Mz/%Dz/%Yl %Hz:%mz");      // → "07/04/2025 14:30"
MaskDecorator.forDateTime(dt, "%Mz/%Dz/%Yl %Hz:%mz:%sz");  // → "07/04/2025 14:30:00"
MaskDecorator.forDateTime(dt, "%Dz.%Mz.%Yz %hz:%mz %p");  // → "04.07.25 02:30 pm"
```

## Handling null results {#handling-null-results}

:::warning
All `for*()` and `parse*()` methods return `null` if the input is invalid or can't be parsed. Always verify the result is non-null before using it in your app logic.
:::

```java
String formatted = MaskDecorator.forDate(date, "%Mz/%Dz/%Yl");
if (formatted != null) {
  label.setText(formatted);
}
```
