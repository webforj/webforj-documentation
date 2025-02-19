---
title: MaskedTimeField
---

The `MaskedTimeField` component is a text input field specifically designed for **time entry**. It ensures correct formatting by applying a **time mask**, allowing users to enter times in a structured way. When the user leaves the field, the input is automatically formatted according to the mask, making it highly useful for scheduling, time-tracking, and time-based form entries.

## Basics

The `MaskedTimeField` can be instantiated with or without parameters. You can define an initial value, label, a placeholder text, and an event listener for value changes.

## Supported format indicators

The `MaskedTimeField` uses **format indicators**, prefixed with `%`, to define how time is displayed and interpreted.

| Format  | Description               |
|---------|---------------------------|
| `%H`    | Hour (24-hour clock)      |
| `%h`    | Hour (12-hour clock)      |
| `%m`    | Minute                    |
| `%s`    | Second                    |
| `%p`    | AM/PM indicator           |

### Modifiers for time formatting

Format indicators can be customized using **modifiers**:

| Modifier | Description                     |
|----------|---------------------------------|
| `z`      | Zero-fill                      |
| `s`      | Short text representation      |
| `l`      | Long text representation       |
| `p`      | Packed number                  |
| `d`      | Decimal (default format)       |

### Example masks

| Mask       | Input   | Display Output |
|------------|--------|---------------|
| `%H:%m`    | `1430` | `14:30`       |
| `%h:%m %p` | `230`  | `2:30 AM`     |
| `%H:%m:%s` | `91545`| `09:15:45`    |

## Locale support

You can set the locale for **localized time formatting**.

```java
timeField.setLocale(Locale.GERMANY);
```

## Setting min/max time constraints

Define valid time ranges using `setMin()` and `setMax()`:

```java
timeField.setMin(LocalTime.of(8, 0));  
timeField.setMax(LocalTime.of(18, 0)); 
```

## `MaskedTimeFieldSpinner`

The `MaskedTimeFieldSpinner` extends `MaskedTimeField` by introducing **spinner controls**, allowing users to **increment** or **decrement** the time.

### Example: time selection with spinner
```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner("Meeting Time", LocalTime.of(10, 0));

spinner.setMask("%h:%m %p"); 

spinner.setStep(Duration.ofMinutes(15)); 

container.add(spinner);
```

### Spinner features
- **Step Control:** Adjust time by increments (`setStep(Duration.ofMinutes(30))`).
- **Min/Max Boundaries:** Restrict selectable time ranges.
- **Formatted Output:** Uses `MaskedTimeField`'s masking rules.


The `MaskedTimeField` is an essential tool for structured **time input**, ensuring correctness while enhancing usability. 🚀
