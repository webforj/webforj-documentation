---
title: MaskedDateField
sidebar_position: 16
---

<DocChip chip='shadow' />

<DocChip chip='name' label="dwc-datefield" />

<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

The `MaskedDateField` component is a structured input control for date entry. It ensures user input is formatted according to a defined date mask and allows flexible parsing, validation, localization, and restoration of values. It's especially useful in forms for registrations, bookings, and scheduling where consistent and regionalized date formats are important.

## Basics

The `MaskedDateField` can be instantiated with or without parameters. You can define an initial value, a label, a placeholder, and an event listener for value changes.

<ComponentDemo path='/webforj/maskeddatefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java' height='200px'/>

## Mask rules

The `MaskedDateField` uses format indicators, prefixed with `%`, to control how date input is interpreted.

### Date format indicators

| Format  | Description |
|---------|-------------|
| `%Y`    | Year        |
| `%M`    | Month       |
| `%D`    | Day         |

### Modifiers

Modifiers allow more control over how components of the date are formatted:

| Modifier | Description                  |
|----------|------------------------------|
| `z`      | Zero-fill                    |
| `s`      | Short text representation    |
| `l`      | Long text representation     |
| `p`      | Packed number                |
| `d`      | Decimal (default format)     |

These can be combined to build a wide variety of date masks.

## Date format localization

The `MaskedDateField` supports localization, allowing the date format to match the user's region.

| Region        | Common Format | Example        |
|---------------|----------------|----------------|
| United States | MM/DD/YYYY     | `07/04/2023`   |
| Europe        | DD/MM/YYYY     | `04/07/2023`   |
| ISO Standard  | YYYY-MM-DD     | `2023-07-04`   |

```java
dateField.setLocale(Locale.FRANCE);
```

:::tip
The `setLocale()` method expects a [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html).
:::

## Date parsing logic

Date input is parsed based on the defined mask. The parser accepts both complete and abbreviated numeric values with or without delimiters.

### Example parsing scenarios

| Input       | Interpreted Date                        |
|-------------|------------------------------------------|
| `1`         | first day of current month & year          |
| `12`        | twelvth day of current month & year         |
| `112`       | January 12 of current year               |
| `1004`      | October 4 of current year                |
| `020304`    | March 4, 2002                            |
| `20230715`  | July 15, 2023                            |
| `12/4`      | April 12 of current year (with delimiter)|
| `01.01`     | January 1 of current year (dot-separated)|

This flexibility supports natural input while still ensuring correctness.

## Setting min/max date constraints

You can restrict input to a specific date range using `setMin()` and `setMax()`:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

:::tip
The values passed to `setMin()` and `setMax()` must be instances of [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html).
:::

<ComponentDemo path='/webforj/maskeddatefieldminmax?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldMinMaxView.java' height='100px'/>

## Restore method

The `MaskedDateField` supports a **restore feature** that resets the field’s value to a predefined or original state.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Ways to restore
- **Programmatically** via `restoreValue()`
- **Keyboard Shortcut**: Pressing <kbd>ESC</kbd>

:::info Default restore key
The restore key is <kbd>ESC</kbd> unless overridden by event listeners.
:::

<ComponentDemo path='/webforj/maskeddatefieldrestore?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java' height='150px'/>

## Custom patterns for validation

Use regular expressions with `setPattern()` to apply custom client-side validation:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

:::info Regular expression conventions
The pattern must follow JavaScript RegExp syntax as documented [here](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

## `MaskedDateFieldSpinner`

The `MaskedDateFieldSpinner` extends `MaskedDateField` by adding spinner controls to increment or decrement dates interactively.

<ComponentDemo path='/webforj/maskeddatefieldspinner?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java' height='150px'/>

### Spinner features
- **Step Control:** Adjust date by step (e.g. `setStep(Period.ofWeeks(1))`)
- **Min/Max Boundaries:** Enforce allowed range
- **Formatted Output:** Fully compatible with date masks

## Styling

### Shadow parts

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').DateField} table='parts' exclusions='' />

### CSS properties

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').DateField} exclusions='' table='properties' />

### Reflected attributes

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').DateField} table="reflects" />

