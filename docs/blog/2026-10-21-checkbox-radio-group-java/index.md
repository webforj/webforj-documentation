---
title: "Checkbox group in Java: choose the right input in webforJ"
description: "Build a checkbox group, a radio button group and a toggle switch in Java with webforJ. Pick the right input and read each value in a listener."
slug: checkbox-radio-group-java
date: 2026-10-21
authors: Eric Handtke
tags: [tutorial, components, front end]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-10-21-checkbox-radio-group-java/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-10-21-checkbox-radio-group-java/cover.jpg)

Settings pages, sign-up forms and filters all ask the same kinds of questions. Which of these do you want? Pick exactly one. Turn this on or off. Each question has a control that fits it, and picking the wrong one makes a form harder to use than it needs to be.

A checkbox group in Java answers the first question, a radio button group the second, and a switch the third. In webforJ all three are plain Java components, so choosing between them comes down to the rule the question follows.

<!-- truncate -->

:::tip Try it live
The [CheckBox](/docs/components/checkbox), [RadioButton](/docs/components/radiobutton) and [RadioButtonGroup](/docs/components/radiobuttongroup) documentation has live, interactive samples you can click through in the browser, with the full Java source next to each one.
:::

This guide builds each control with code from the docs and ends with a quick way to choose between them.

## The problem with loose inputs {#the-problem}

A common first attempt is a few `CheckBox` or `RadioButton` components with a separate listener on each one. The rule for the set ends up spread across the code. Radio buttons only enforce a single choice when they belong to a `RadioButtonGroup`. A radio button placed on its own does not give you that behavior.

Before you pick a control, ask how many options the user may select: none or one, one only, or any number. The answer decides the control.

Match the control to the rule:

1. Use checkboxes when any number of options can be on.
2. Use a radio button group when exactly one option can be on.
3. Use a switch when a single setting turns on or off.

Validation rules for these fields are a separate concern. See [Java form validation in webforJ](/blog/java-form-validation) for that part.

## Build a checkbox group {#checkbox-group}

A `CheckBox` takes its text and, optionally, its starting state. Each box keeps its own checked state, so a group is simply several checkboxes in one layout. This example from the docs stacks five of them, with the first one checked:

```java
FlexLayout rightAligned =
    FlexLayout.create(
            new CheckBox("Daily", true),
            new CheckBox("Weekly"),
            new CheckBox("Bi-Weekly"),
            new CheckBox("Monthly"),
            new CheckBox("Annually"))
        .vertical()
        .build()
        .setWidth("100px");
```

When you need the user's choices, call `isChecked()` on each box. The checkbox holds its own state, so you don't keep a second list of booleans.

For a parent checkbox over a set of child options, call `setIndeterminate(true)` when only some children are checked. The [checkbox docs](/docs/components/checkbox) cover the pattern.

## Build a radio button group {#radio-button-group}

A `RadioButtonGroup` enforces one selection. Pass a name and the buttons to its constructor, and checking one button unchecks the rest. This is the example from the docs, with the group placed in a `Fieldset`:

```java
RadioButton agree = new RadioButton("Agree");
RadioButton neutral = new RadioButton("Neutral");
RadioButton disagree = new RadioButton("Disagree");

RadioButtonGroup group = new RadioButtonGroup("choices", agree, neutral, disagree);

Fieldset fieldset = new Fieldset("Options");
fieldset.add(group);
```

Attach one listener to the group instead of one to each button. The docs' drawer sample reads the new choice from the event and checks it for `null` first:

```java
group.onChange(e -> {
  RadioButton selected = e.getChecked();
  if (selected != null) {
    // react to the new choice
  }
});
```

The group name is a Java convenience. It doesn't appear in the DOM, so pick a name that describes the question. The [radio button group page](/docs/components/radiobuttongroup#naming) covers naming in more detail.

## Add a toggle switch {#toggle-switch}

A `RadioButton` can render as a switch. The docs sample puts a normal radio button next to one created with the `RadioButton.Switch` factory method:

```java
RadioButton normalButton = new RadioButton("Normal RadioButton").setExpanse(Expanse.XLARGE);
RadioButton switchButton = RadioButton.Switch("Switch RadioButton").setExpanse(Expanse.XLARGE);
```

To turn an existing radio button into a switch, call `setSwitch(true)` on it.

The switch keeps radio button behavior. Inside a `RadioButtonGroup`, only one switch can be on. Use a standalone switch for one setting, and use checkboxes for a list of settings. The [radio button page](/docs/components/radiobutton#switches) covers the switch styling options.

## Tune the look and the keyboard {#tune-look}

Each of these controls takes its styling from the same small set of options, so you do not write CSS for the common cases.

1. Move the label with `setTextPosition()`. A `CheckBox` shows its text to the right of the box by default and can show it on the left.
2. Resize any of the three with `setExpanse()` and the `Expanse` enum. The `RadioButton` values are `XSMALL`, `SMALL`, `MEDIUM`, `LARGE`, `XLARGE` and `NONE`.
3. Choose how a radio button reacts to focus. The default is `Activation.MANUAL`, so keyboard users can move across the options without changing the selection. Call `setActivation(Activation.AUTO)` to check a button as soon as it gains focus.

Pick manual activation for forms where a stray tab press must not change an answer. Pick auto activation for short lists where moving focus and choosing are the same act.

## Choose the control quickly {#choose-control}

If the user can pick zero, one or many items from a list, use `CheckBox`. If the user must pick one item from a short list, use `RadioButtonGroup`. If the user flips a single setting, use `RadioButton.Switch`.

Labels matter more than the control. Write each label so the user can tell the options apart without reading the surrounding text. Group related checkboxes together, and give a sensible default when one choice is far more common than the rest. For a parent box that controls a set of children, the indeterminate state tells the user that only some children are checked.

Every control reads and writes its state in plain Java. You call `isChecked()` or `setChecked()`, and the component keeps the browser in sync. There is no template to edit and no second model to maintain.

## Checklist {#checklist}

- Use `CheckBox` when the options are independent.
- Use `RadioButtonGroup` when exactly one option applies.
- Attach one listener to a radio button group, not one to each button.
- Null-check `getChecked()` in group listeners.
- Use `RadioButton.Switch` for one on or off setting.

## Next step {#next-step}

Create a project with [startforJ](https://docs.webforj.com/startforj), or follow the [getting started guide](/docs/introduction/getting-started). Paste the three views into your app, run it, and change each input to watch the summary update.
