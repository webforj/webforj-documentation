---
sidebar_position: 0
title: Fields
---

import componentData from '@site/static/field_data.js'
import ComponentViewer from '@site/src/components/PageTools/ComponentViewer'

<JavadocLink type="foundation" location="com/webforj/component/field/AbstractField"/>

WebforJ supports seven different types of field components, each with different behaviors and implementations that suit various
needs. While each of these components have variations in their implementations, this article describes commonalities among all of the 
field classes.

:::info
This section describes common features of various field components in webforJ, and isn't itself a class that can be instantiated and used.
:::

<!-- <ComponentViewer componentData={componentData} /> -->

## Shared field properties 

<!-- ### Autocomplete

Specifies the browser's behavior regarding automatic form filling and completion. The available options include OFF, ON, and specific values for different types of autocomplete data such as names, addresses, and email addresses. The complete list of Enum values can be found in the Javadoc. -->

### Label

A field label is a descriptive text or title that is associated with the field. It provides a brief explanation or prompt to help users understand the purpose or expected input for that particular field. Field labels are not only important for usability but also play a crucial role in accessibility, as they enable screen readers and assistive technologies to provide accurate information and facilitate keyboard navigation.

### Helper text

Each field can display helper text beneath the input using the `setHelperText()` method. This helper text offers additional context or explanations about the available inputs, ensuring users have the necessary information to make informed selections.

### Required

A field is required when the user must provide a value before submitting a form. This is mainly used in conjunction with `setLabel(String)` to provide a visual indication to users that the field is required. 

:::info
Field components contain built-in visual validation which notifies users if a required field is empty, or has had values removed.
:::

### Spellcheck

By calling the `setSpellCheck(true)` method, you can enable the spellcheck feature for a field. This means that when a user enters text into the field, the browser or user agent may check the spelling of the entered text for errors.

### Slots

Slots provide flexible options for improving the capability of field components. You can have icons, labels, loading spinners, clear/reset capability, avatar/profile pictures, and other beneficial components nested within a field to further clarify intended meaning to users.
Fields have two slots: the `prefix` and `suffix` slots. Use the `setPrefixComponent()` and `setSuffixComponent()` methods to insert various components before and after the displayed option within a field. Here's an example using the `TextField` field:

```java
TextField textField = new TextField();
textField.setPrefixComponent(TablerIcon.create("box"));
textField.setSuffixComponent(TablerIcon.create("box"));
```


## Parts and CSS properties

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