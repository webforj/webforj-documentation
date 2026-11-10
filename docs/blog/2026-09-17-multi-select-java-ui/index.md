---
title: "Multi-select in Java UI: ListBox and ChoiceBox with webforJ"
description: "Build a multi-select Java UI with webforJ's ListBox, read the selected keys in Java, and use a ChoiceBox when users pick a single value from a list."
slug: multi-select-java-ui
date: 2026-09-17
authors: Matthew Hawkins
tags: [tutorial, web development]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-09-17-multi-select-java-ui/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-09-17-multi-select-java-ui/cover.jpg)

A multi-select Java UI in webforJ is a `ListBox` with its selection mode set to `MULTIPLE`. The user holds Shift, Control or Command to pick several items, and your Java code reads the chosen values with `getSelectedKeys()`. When the user should pick exactly one value from a fixed set, a `ChoiceBox` is the better fit. In this guide you build both, read and set the selection from Java, and learn when each component is the right one.

<!-- truncate -->

:::tip Try it live
The [ListBox](/docs/components/lists/listbox) and [ChoiceBox](/docs/components/lists/choicebox) documentation has live, interactive samples you can click through in the browser, with the full Java source next to each one.
:::

## What is a multi-select list? {#what-is-a-multi-select-list}

A multi-select list is a control where the user can hold more than one value at the same time. You see it in permission pickers, filter panels and assignment screens, anywhere the answer is a set rather than a single choice.

In webforJ, the `ListBox` class handles this. It starts in single selection mode, so you have to turn multiple selection on. Each item in the list has two parts. The key is the value your code works with, and the text is what the user reads. You add items with `add(key, text)`, which keeps the two separate. That split matters later, because the selection methods return keys, not labels.

## How do you allow multiple selection? {#how-do-you-allow-multiple-selection}

Start with a plain list so you can see the default. A list that never calls `setSelectionMode` stays in single mode, so the user picks one item at a time. The label is set in the constructor, and each call to `add` puts one item in the list:

```java
ListBox department = new ListBox("Department");
department.add("marketing", "Marketing");
department.add("it", "IT Support");
department.add("finance", "Finance");
department.add("operations", "Operations");
```

Now create a second list for roles and switch it to multiple selection. The `setSelectionMode` method takes `SelectionMode.MULTIPLE`, and `selectKey` preselects two items when the view loads. The value change listener runs whenever the selection changes, and it reads the current keys:

```java
ListBox roles = new ListBox("Roles");
roles.add("admin", "Admin");
roles.add("editor", "Editor");
roles.add("viewer", "Viewer");
roles.add("auditor", "Auditor");
roles.setSelectionMode(SelectionMode.MULTIPLE);
roles.selectKey("editor", "viewer");

roles.addValueChangeListener(e -> {
  List<Object> selected = roles.getSelectedKeys();
  System.out.println("Selected roles: " + selected);
});
```

The component handles these interactions, so you write no key handling yourself. With multiple selection on, a user can:

- Hold Shift to select a contiguous range.
- Hold Control on Windows or Command on Mac to add separate items.
- Use the arrow keys to move, and type a letter to jump to the next item that starts with it.

On tablets and phones, the user selects several items with plain taps, with no Shift key needed.

Recap: `setSelectionMode(SelectionMode.MULTIPLE)` is the switch, and the keys you pass to `add` are the values you read back.

## How do you read selected values? {#how-do-you-read-selected-values}

`getSelectedKeys()` returns a `List<Object>` with the keys of every selected item. Because each item has its own key, you can compare against plain strings like `"admin"` and skip parsing the display text. The preceding listener is where you would react to a change, for example by enabling a Save button when the list is not empty.

Keep the reading logic in the listener or in a method the listener calls. Reading the keys inside the listener keeps the value in step with each change.

## How do you set a selection in code? {#how-do-you-set-a-selection-in-code}

Loading a saved user profile is a common case. Clear the current selection, then select the keys from the saved record. The `selectKey` method accepts several keys at once:

```java
roles.deselectAll();
roles.selectKey("admin", "auditor");
boolean isAdmin = roles.getSelectedKeys().contains("admin");
```

The `deselectAll` call comes first, so old choices are cleared. The `contains` check on the returned keys gives you a simple way to decide what the rest of the view should show, such as an admin panel.

Selection methods take keys, not labels. If your saved data stores the label, store the key beside it. Then a change to the display text does not break the saved selection.

## When should you use a ChoiceBox? {#when-should-you-use-a-choicebox}

A `ChoiceBox` is a dropdown that holds one selected value. Use it when the list is short, the choices are fixed, and the user picks only one. It suits input that must be one choice from predefined options.

The dropdown grows to fit its items by default. If you have a long list, cap the height with `setMaxRowCount`:

```java
ChoiceBox country = new ChoiceBox("Country");
country.add("de", "Germany");
country.add("fr", "France");
country.add("es", "Spain");
country.add("it", "Italy");
country.add("pt", "Portugal");
country.add("pl", "Poland");
country.setMaxRowCount(4);
country.selectKey("es");
```

The dropdown shows four rows, and the user scrolls for the rest. The `selectKey` call preselects Spain, which saves the user one click when the most common answer is known.

Recap: a `ListBox` shows its items all the time and can hold many selections. A `ChoiceBox` hides its items in a dropdown and holds one value.

## Which list component to pick? {#which-list-component-to-pick}

| Need | Component |
| --- | --- |
| The user picks several values | `ListBox` with `SelectionMode.MULTIPLE` |
| The user picks one value from a dropdown | `ChoiceBox` |
| The user types a value that may not be in the list | `ComboBox` |

Choose the `ListBox` when all options should stay visible on the page, such as a role assignment screen. Choose the `ChoiceBox` for a compact single-value field in a form. If you are unsure, count the answers the user can give. One answer points to a `ChoiceBox`, and several answers point to a `ListBox`.

Ready to try this? Start with the [getting started guide](/docs/introduction/getting-started), then read the [ListBox docs](/docs/components/lists/listbox) for the full set of selection methods and the [ChoiceBox docs](/docs/components/lists/choicebox) for dropdown settings.

## Frequently asked questions {#faq}

### Can a ChoiceBox select multiple values? {#can-a-choicebox-select-multiple-values}

No. A `ChoiceBox` holds one selected value. For several values, use a `ListBox` with `SelectionMode.MULTIPLE`, which shows every option and lets the user hold Shift or Control to pick more than one.

### Does multiple selection work on tablets and phones? {#does-multiple-selection-work-on-tablets-and-phones}

Yes. On tablets and phones, users select several items of a `ListBox` with plain taps once multiple selection is enabled, so they do not need a Shift key. Your Java code reads the same keys in both cases.
