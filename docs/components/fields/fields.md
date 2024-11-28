---
sidebar_position: 0
title: Fields
---

<JavadocLink type="foundation" location="com/webforj/component/field/AbstractField"/>

The webforJ framework supports seven different types of field components, each with different behaviors and implementations that suit various needs for inputs.
While each of these components has variations in their implementations, this article describes the shared properties among all of the field classes.

:::info
This section describes common features of various field components in webforJ, and isn't itself a class that can be instantiated and used.
:::

## Shared field properties

### Label

A field label is a descriptive text or title that's associated with the field that can be defined in the constructor or by using the `setLabel()` method. Labels provide a brief explanation or prompt to help users understand the purpose or expected input for that particular field. Field labels are important for usability and play a crucial role in accessibility, as they allow screen readers and assistive technologies to provide accurate information and facilitate keyboard navigation.

### Helper text

Each field can display helper text beneath the input using the `setHelperText()` method. This helper text offers additional context or explanations about the available inputs, ensuring users have the necessary information to make informed selections.

### Required

You can call the `setRequired(true)` method to require users to provide a value before submitting a form. This property works in tandem with the field label, providing a visual indication that a field is necessary. This visual cue helps individuals complete forms accurately.

:::info
Field components contain built-in visual validation to notify users when a required field is empty or if a user removed a value.
:::

### Spellcheck

By using `setSpellCheck(true)`, you can allow the browser or user agent to verify the spelling of the text entered by the user and identify any errors.

### Slots

Slots provide flexible options for improving the capability of field components. You can have icons, labels, loading spinners, clear/reset capability, avatar/profile pictures, and other beneficial components nested within a field to further clarify intended meaning to users.
Fields have two slots: the `prefix` and `suffix` slots. Use the `setPrefixComponent()` and `setSuffixComponent()` methods to insert various components before and after the displayed option within a field. Here's an example using the `TextField` field:

```java
TextField textField = new TextField();
textField.setPrefixComponent(TablerIcon.create("box"));
textField.setSuffixComponent(TablerIcon.create("box"));
```

## Styling

:::info
Because all field components are built from a singular web component, they all share the
following Shadow Parts and CSS Property values
:::

### Shadow parts

These are the various parts of the shadow DOM for the component, which are required when styling via CSS.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Field}  table='parts'/>

### CSS properties

These are the various CSS properties that are used in the component, with a short description of their use.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Field}  table='properties'/>

### Reflected attributes

The reflected attributes of a component are shown as attributes in the rendered HTML element for the component in the DOM. This means that styling can be applied using these attributes.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Field} table="reflects"/>

### Dependencies

This component relies on the following components - see the related article for more detailed styling information:

<TableBuilder tag='dwc-field' table="dependencies"/>

## Topics

<DocCardList className="topics-section" />