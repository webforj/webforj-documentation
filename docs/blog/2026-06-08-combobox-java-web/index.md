---
title: "ComboBox in Java: preset items and custom values"
description: "Build a Java combo box with preset items, typed custom values, a placeholder and select and close events using the webforJ ComboBox component."
slug: combobox-java-web
date: 2026-06-08
authors: Matthew Hawkins
tags: [tutorial, web development]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-06-08-combobox-java-web/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-06-08-combobox-java-web/cover.jpg)

A combo box in Java lets users pick an option from a list or type a value of their own. In webforJ, the `ComboBox` component handles both in one control, and you build it entirely in Java. This guide builds a country picker step by step. You will add preset items, set a placeholder, cap the dropdown height, turn custom input off for a field that needs a fixed answer, and read what the user chose.

<!-- truncate -->

:::tip Try it live
The [ComboBox documentation](/docs/components/lists/combobox) has live, interactive samples you can click through in the browser, with the full Java source next to each one.
:::

## What a combo box does {#what-a-combo-box-does}

A `ComboBox` is a text field with a dropdown attached. The user can open the list and click an option, or start typing into the field. Use it when you have a set of suggested answers but still want to accept something outside that set.

If the user must pick from the list and nothing else, a `ChoiceBox` is the better fit. The two components share the same item API, so what you learn here applies to both.

## Add items to the component {#add-items}

The examples use the Composite pattern. A `Composite` wraps a layout, and `getBoundComponent()` returns that layout so you can add children to it. The class below creates a `ComboBox` labeled Country and inserts five countries into it. `insert` takes a list of values and returns the component, so you can chain the next setting onto the same line.

```java
public class CountryPicker extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final ComboBox country = new ComboBox("Country");

  public CountryPicker() {
    country.insert("Canada", "Germany", "Japan", "Mexico", "Brazil");
    self.setMargin("20px").add(country);
  }
}
```

At this point the user can open the list and pick a country, or type one in. The next step shapes how the field looks and behaves.

When your items come from a collection, pass the whole list to `insert` in one call instead of calling `add` once per entry. The bulk form is the better choice for [lists](/docs/components/lists/overview) because each single `add` sends its own message to the browser. The varargs form in the snippet is fine for a short, fixed set of values.

## Placeholder and row limit {#placeholder-and-row-limit}

A placeholder shows inside the empty text field and gives the user a hint about what to enter. `setMaxRowCount` controls how many rows the dropdown displays. By default, the dropdown grows to fit its content. Passing a value of 0 or less removes the limit.

The snippet below sets a placeholder and caps the dropdown at three rows. Add it inside the constructor, after the `add` call from the previous step.

```java
country.setPlaceholder("Pick or type a country")
    .setMaxRowCount(3);
```

Row count and open height both shape the dropdown, but they answer different questions. `setMaxRowCount` limits how many rows appear. `setOpenHeight`, covered in the next step, sets the maximum height of the dropdown itself. Use row count when you want a fixed number of visible options. Use open height when you care about the pixel size of the list.

## Allow or block custom values {#custom-values}

Custom values are on by default. The user can change the text in the field to anything. When you set `setAllowCustomValue(false)`, the field stops accepting typed input, so the only answers are the items you inserted.

Turn custom input off for fields with a fixed meaning, such as a department. Keep it on for free text, such as a city. In that case, validate what the user types, because the typed value doesn't come from your list. The snippet below creates a locked Department field and sizes its dropdown with `setOpenWidth` and `setOpenHeight`. A string such as `"240px"` works for width, and an `int` sets pixels.

By the end of this step the form has three working pieces: an open country field with a placeholder and a row cap, a locked department field, and a list that accepts typed text only when you allow it. The events come next.

```java
ComboBox department = new ComboBox("Department");
department.insert("Sales", "Support", "Engineering")
    .setAllowCustomValue(false)
    .setOpenWidth("240px")
    .setOpenHeight(200);
self.add(department);
```

## Read the selection and close events {#selection-and-close-events}

Two listeners cover most needs. `onSelect` fires when the user picks an item from the list, and its event carries the chosen `ListItem`. `onClose` fires when the list closes.

Read the value from the event instead of querying the component inside the handler. The [client-server interaction guide](/docs/architecture/client-server) explains why. Calling `getSelectedIndex()` on the component during a handler sends a round trip to the browser. The event already holds the selected item, so reading it from the event avoids that trip.

```java
Label status = new Label("No country selected");
country.onSelect(e -> status.setText("Selected: " + e.getSelectedItem().getText()));
country.onClose(e -> status.setText("Country list closed"));
self.add(status);
```

The `Label` here comes from `com.webforj.component.text`. Each handler updates the label, so the user can read the current choice on screen.

Notice that the handlers never ask the component for its value. Keep that habit as the form grows. Every value you need is already in the event, or in your own fields.

## Choosing a list component {#choosing-a-list-component}

Pick the list component that matches the input you need.

| Component | Typed values | Selection |
| --- | --- | --- |
| [ComboBox](/docs/components/lists/combobox) | Allowed by default, can be turned off | One item from the list, or typed text |
| [ChoiceBox](/docs/components/lists/choicebox) | Not allowed | One item from a fixed list |
| [ListBox](/docs/components/lists/listbox) | Not allowed | All items visible at once, with multiple selection available |

Use a ComboBox when the answer is usually one of your suggestions but the user may need something else. Use a ChoiceBox when the answer must come from your list. Use a ListBox when the user should see every option and may [pick several](/blog/multi-select-java-ui).

## Next steps {#next-steps}

Create a project with [startforJ](https://docs.webforj.com/startforj), or follow [getting started](/docs/introduction/getting-started) if you already have a Maven project. Paste the country picker into a view and run it. Then open the [ComboBox reference](/docs/components/lists/combobox) to explore the prefix and suffix slots, the dropdown type for styling, and the open and close methods.
