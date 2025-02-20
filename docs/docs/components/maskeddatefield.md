---
title: MaskedDateField
---

The `MaskedDateField` component is a specialized input field that ensures structured **date entry**. It automatically formats user input according to a specified **date mask**, ensuring consistency across different locales and formats. This is particularly useful for forms requiring standardized date input, such as registrations, scheduling, and booking systems.

## Basics

The `MaskedDateField` can be instantiated with or without parameters. You can define an initial value, label, a placeholder text, and an event listener for value changes.

## Supported format indicators

The `MaskedDateField` uses **format indicators**, prefixed with `%`, to define how dates are displayed and interpreted.

| Format  | Description |
|---------|------------|
| `%Y`    | Year       |
| `%M`    | Month      |
| `%D`    | Day        |

### Modifiers for date formatting

Modifiers allow for finer control of the displayed format.

| Modifier | Description                     |
|----------|---------------------------------|
| `z`      | Zero-fill                      |
| `s`      | Short text representation      |
| `l`      | Long text representation       |
| `p`      | Packed number                  |
| `d`      | Decimal (default format)       |

### Example masks

| Mask         | Input        | Display Output  |
|-------------|-------------|----------------|
| `%M/%D/%Y`  | `070423`    | `07/04/2023`  |
| `%Y-%M-%D`  | `20230715`  | `2023-07-15`  |
| `%D %M %Y`  | `15 July 23` | `15 July 2023` |

## Date format localization

Different regions use different date formats. `MaskedDateField` allows **customizing date formats** to match locale preferences.

| Region        | Common Format      | Example |
|--------------|-------------------|---------|
| United States | MM/DD/YYYY        | `07/04/2023` |
| Europe       | DD/MM/YYYY        | `04/07/2023` |
| ISO Standard | YYYY-MM-DD        | `2023-07-04` |

You can **set the locale** for regional formatting:

```java
dateField.setLocale(Locale.FRANCE); 
```

## Date parsing logic

The date parser interprets input based on the **defined mask**. It accepts various formats, including numeric inputs with or without delimiters.

### Example parsing scenarios

Assuming the mask is **`%Mz/%Dz/%Yz`** (US format), the following inputs are interpreted as:

| Input  | Interpreted Date |
|--------|----------------|
| `1`    | Current month, **first day** of the current year |
| `12`   | Current month, **12 th day** of the current year |
| `112`  | **January 12**, current year |
| `1004` | **October 4**, current year |
| `020304` | **March 4, 2002** |
| `8 digits (20230715)` | **July 15, 2023** |

This flexibility allows users to input dates naturally while ensuring correct interpretation.

## Setting min/max date constraints

You can restrict input values to a specific date range using `setMin()` and `setMax()`:

```java
dateField.setMin(LocalDate.of(2020, 1, 1)); 
dateField.setMax(LocalDate.of(2030, 12, 31)); 
```

## Custom patterns for validation

Use `setPattern()` to apply **regular expressions** for additional validation.

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

:::info Regular expression conventions
The pattern must be a valid JavaScript regular expression, as used by the RegExp type and as documented in the [regular expressions guide](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions). For more information about patterns see [this guide](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## Restoring values

Users can **restore** the field’s value to its original state using:

- **Programmatically:** `restoreValue()`
- **Keyboard Shortcut:** Pressing `ESC` resets to the original value.

```java
dateField.restoreValue(); 
```

You can also define a **custom restore value**:

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1)); 
```

:::info Default restore key
The restore key is `"ESC"` by default unless overridden with event listeners reacting to keystrokes.
:::

## `MaskedDateFieldSpinner`

The `MaskedDateFieldSpinner` extends `MaskedDateField` by introducing **spinner controls**, allowing users to **increment** or **decrement** the date.

### Example: Date selection with spinner

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner("Event Date", LocalDate.of(2024, 6, 15));

spinner.setMask("%Y-%M-%D"); 

spinner.setStep(Period.ofDays(1));

container.add(spinner);
```

### Spinner features
- **Step Control:** Adjust date by increments (`setStep(Period.ofWeeks(1))` for weekly changes).
- **Min/Max Boundaries:** Restrict selectable date ranges.
- **Formatted Output:** Uses `MaskedDateField`'s masking rules.

The `MaskedDateField` is an essential component for **structured date input**, ensuring correctness while simplifying user interactions. 
