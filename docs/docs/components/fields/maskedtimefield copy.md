---
title: MaskedTimeField
sidebar_position: 19
---

<DocChip chip='shadow' />

<DocChip chip='name' label="dwc-timefield" />

<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

The `MaskedTimeField` component provides a structured and mask-driven way to input and validate time values. It is ideal for applications involving scheduling, booking systems, logging interfaces, or time-sensitive data entry. When the field loses focus, it formats the input based on the specified time mask, enhancing both consistency and readability.

---

## Basics

The `MaskedTimeField` can be instantiated with or without parameters. You can define an initial value, a label, a placeholder, and an event listener for value changes.

<ComponentDemo
path='/webforj/maskedtimefield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java'
height='250px'
/>

---

## Mask rules

The `MaskedTimeField` uses time format indicators, all of which begin with `%`, to define the input structure.

### Time format indicators

| Format  | Description               |
|---------|---------------------------|
| `%H`    | Hour (24-hour clock)      |
| `%h`    | Hour (12-hour clock)      |
| `%m`    | Minute                    |
| `%s`    | Second                    |
| `%p`    | AM/PM indicator           |

### Modifiers

Format indicators can be extended using modifiers to customize formatting:

| Modifier | Description                     |
|----------|---------------------------------|
| `z`      | Zero-fill                       |
| `s`      | Short text representation       |
| `l`      | Long text representation        |
| `p`      | Packed number                   |
| `d`      | Decimal (default format)        |

### Examples

| Mask         | Input   | Display Output |
|--------------|---------|----------------|
| `%H:%mz`     | `1430`  | `14:30`        |
| `%h:%mz %p`  | `230`   | `2:30 AM`      |
| `%H:%mz:%sz` | `91545` | `09:15:45`     |

---

## Locale support

You can localize time display and parsing by specifying a locale:

```java
timeField.setLocale(Locale.GERMANY);
```

---

## Time constraints

You can enforce valid input ranges by setting a minimum and maximum allowed time:

```java
timeField.setMin(LocalTime.of(8, 0));  
timeField.setMax(LocalTime.of(18, 0));
```

This ensures users can't enter values outside the defined window.

<ComponentDemo
path='/webforj/maskedtimefieldminmax?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldMinMaxView.java'
height='200px'
/>

---

## Restore method

The `MaskedTimeField` provides a restore mechanism to reset the field’s value to a known initial state. This is useful for reverting accidental edits.

You can define a restore target using `setRestoreValue()` and revert using either a method call or a keyboard key.

```java
timeField.setRestoreValue(LocalTime.of(10, 0));
timeField.restoreValue(); // Restores the value to 10:00
```

### Triggering restore

- **Programmatically**: Using `restoreValue()`
- **Keyboard**: Press <kbd>ESC</kbd> (default restore key)

:::info Default restore key
The restore key is <kbd>ESC</kbd> unless overridden by a key listener.
:::

---

## `MaskedTimeFieldSpinner`

The `MaskedTimeFieldSpinner` extends the `MaskedTimeField` by adding spinner controls, allowing users to increment or decrement time values interactively.

### Features

- Inherits all mask and validation logic from `MaskedTimeField`
- Supports programmatic `spinUp()` / `spinDown()`
- Honors min/max constraints and step durations

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner("Check-In");
spinner.setMask("%Hz:%mz");
spinner.setStep(Duration.ofMinutes(15));
spinner.setMin(LocalTime.of(7, 0));
spinner.setMax(LocalTime.of(10, 0));
```

<ComponentDemo
path='/webforj/maskedtimefieldspinner?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java'
height='120px'
/>

---

## Styling

### Shadow parts

These are the customizable shadow DOM parts of the component.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').TimeField} table='parts' exclusions=''/>

### CSS properties

These CSS properties can be used to customize the component's appearance.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').TimeField} exclusions='' table='properties'/>

### Reflected attributes

These attributes are reflected in the DOM and can be used for styling via CSS selectors.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').TimeField} table="reflects" />

