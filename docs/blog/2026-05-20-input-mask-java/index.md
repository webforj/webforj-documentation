---
title: "Input masks in Java: phone, currency, and date fields"
description: "Add input masks in Java for phone, currency, and date fields with webforJ. Working code for MaskedTextField, MaskedNumberField, and MaskedDateField."
slug: input-mask-java
date: 2026-05-20
authors: Eric Handtke
tags: [tutorial]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-05-20-input-mask-java/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-05-20-input-mask-java/cover.jpg)

Phone numbers arrive as `5551234567`, `555-123-4567` and `(555) 123 4567`. Prices come in with stray letters, and dates show up in three different orders. Every form that accepts free text ends up cleaning it afterwards.

An input mask in Java stops that at the source. The field only accepts characters that fit the pattern and formats the value as the user types. In webforJ the masks are built in, so a clean phone, price or date field is a few lines of Java.

<!-- truncate -->

:::tip Try it live
The [masked text field](/docs/components/fields/masked/textfield), [masked number field](/docs/components/fields/masked/numberfield) and [masked date field](/docs/components/fields/masked/datefield) documentation has live, interactive samples you can click through in the browser, with the full Java source next to each one.
:::

This tutorial builds each field in order, then shows how to apply the same masks to values that are only displayed.

## Step 1: Mask a phone number field {#phone-field}

1. Create a `MaskedTextField` with a label.
2. Call `setMask()` with the layout you want. A `0` accepts one digit. Other characters, such as the parentheses and the hyphen, are literals.
3. Add helper text so the user knows the format.
4. Add the field to the layout.

```java
MaskedTextField phone = new MaskedTextField("Phone");
phone.setMask("(000) 000-0000")
    .setHelperText("Ten digits, area code first");
```

Invalid characters are ignored. Short input is padded with spaces, and long input is truncated to fit the mask.

If the mask allows more than you want, add a rule with `setPattern()`. For example, `setPattern("[A-Za-z0-9]{10}")` requires a ten-character alphanumeric code. The pattern must be a JavaScript regular expression.

A mask controls the shape of the input. It doesn't check that a phone number is in service, so add that check in your own logic if the form needs it.

## Step 2: Format a currency input {#currency-field}

`MaskedNumberField` works with a `Double`. The `$` in a mask always produces a dollar sign, and the `,` produces a grouping separator.

1. Create a `MaskedNumberField` with a label.
2. Set a mask with a `$` and grouping commas.
3. Set `setMin(0.0)` to flag amounts below zero as invalid, and call `setNegateable(false)` to stop negative values.
4. Add the field to the layout.

```java
MaskedNumberField price = new MaskedNumberField("Price");
price.setMask("$###,##0.00")
    .setMin(0.0)
    .setNegateable(false)
    .setRestoreValue(0.0);
```

Read the amount with `price.getValue()`, which returns a `Double`. Choose `$###,##0.00` when you show dollars and cents. The mask doesn't round. A value of `12.34567` in a field masked with `###0.00` shows `12.34`, so round the value yourself if your business rules need a specific precision.

Call `setRestoreValue()` to define the value that `restoreValue()` sets.

## Step 3: Accept dates in a range {#date-field}

`MaskedDateField` takes a `LocalDate`. It formats the input when the field loses focus.

1. Create a `MaskedDateField` with a label.
2. Call `setLocale()` so the field uses the right date order and separators for that region.
3. Call `setMin()` and `setMax()` to limit the dates the user can pick.
4. Add the field to the layout.

```java
MaskedDateField start = new MaskedDateField("Start date");
start.setLocale(Locale.FRANCE);
start.setMin(LocalDate.of(2020, 1, 1));
start.setMax(LocalDate.of(2030, 12, 31));
```

The locale changes both how the date is shown and how typed input is parsed, so test with the locale your users type in. If the form should open with a known date, call `setRestoreValue()` with that `LocalDate`, and `restoreValue()` will set it again later.

## Step 4: Format values for display {#mask-decorator}

Masked fields handle input. For labels, table cells, and reports, use `MaskDecorator`. It applies the same mask syntax to strings, numbers, dates, and times.

```java
MaskDecorator.forString("1234567890", "(000) 000-0000");  // → "(123) 456-7890"
MaskDecorator.forNumber(42.0, "$###,##0.00");             // → "     $42.00"
```

Every `for*()` and `parse*()` method returns `null` when the input is invalid or can't be parsed. Check the result before you show it or use it in logic. Use the `parseDate()` and `parseTime()` methods to turn a stored string back into a `LocalDate` or `LocalTime`.

## Checklist {#checklist}

- Pick the field that matches the data: `MaskedTextField` for codes and phone numbers, `MaskedNumberField` for amounts, `MaskedDateField` for dates.
- Set a mask on every masked field with `setMask()`.
- Set `setMin()` or `setMax()` where the range matters.
- Use `setNegateable(false)` for amounts that can't be negative.
- Add helper text that shows the expected format.
- Check every `MaskDecorator` result for `null`.

## Next step {#next-step}

Create a project with [startforJ](https://docs.webforj.com/startforj), add the classes from the preceding steps to a view, and run it. The [masked fields overview](/docs/components/fields/masked/overview) lists every mask character, and each field page covers its full set of options.

## Frequently asked questions {#faq}

### How do you create a masked text field in Java? {#masked-text-field}

Create a `MaskedTextField` and call `setMask()` with a mask string such as `"ZZZZ-0000"`. The field applies the mask to the text the user enters. See the [MaskedTextField reference](/docs/components/fields/masked/textfield) for every mask character.

### Can a currency input field reject negative values? {#negative-currency}

Yes. Call `setNegateable(false)` on a `MaskedNumberField` to stop negative amounts. Use `setMin(0.0)` to flag any value below zero as invalid. Both calls return the field, so you can chain them with `setMask()`.

### What happens to input that doesn't match the mask? {#invalid-input}

Invalid characters are ignored. Short input is padded with spaces, and long input is truncated to the mask length. Add `setPattern()` when you need to reject a value instead of reshaping it. Test a few bad entries, such as letters in a digits-only mask, before you ship the form. Run the same entries on a phone field and a code field to see how each mask reacts, and write down any case where a stricter pattern would help your users.

### Why does MaskDecorator return null? {#decorator-null}

The `for*()` and `parse*()` methods return `null` when the input is invalid or can't be parsed. Check the result with an `if` statement before you display it or return it from a table renderer.

### Which mask characters work in a number field? {#number-mask-characters}

The number mask supports `0`, `#`, `,`, `-`, `+`, `$`, `(`, `)`, `CR`, `DR`, `*`, `.`, and `B`. Use `0` for a required digit, `#` to hide leading zeros, and `$` for a fixed dollar sign. The [MaskedNumberField reference](/docs/components/fields/masked/numberfield) explains how each one behaves.
