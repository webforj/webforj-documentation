---
sidebar_position: 2
title: ComboBox
slug: combobox
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';
import DocChip from '@site/src/components/DocsTools/DocChip';

<DocChip tooltipText="This component will render with a shadow DOM, an API built into the browser that facilitates encapsulation." label="Shadow" component="a" href="../../glossary#shadow-dom" target="_blank" clickable={true} iconName="shadow" />

<DocChip tooltipText="The name of the web component that will render in the DOM." label="dwc-combobox" clickable={false} iconName='code'/>

<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

The `ComboBox` component is a user interface element designed to present users with a list of options or choices, as well as a field for entering their own custom values. Users can select a single option from this list, typically by clicking the `ComboBox`, which triggers the display of a dropdown list containing available choices, or type in a custom value. Users can also interact with the `ComboBox` with the arrow keys. When a user makes a selection, the chosen option is then displayed in the `ComboBox`. 

<!-- <ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.comboboxdemos.ComboBoxDemo' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/comboboxdemos/ComboBoxDemo.java'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/textcomboboxstyles/demo_styles.css'
height = '200px'
/> -->

## Usages

The ComboBox component is a versatile input element that combines the features of both a dropdown list and a text input field. It allows users to select items from a predefined list or enter custom values as needed. This section explores common usages of the ComboBox component in various scenarios:

1. **Product Search and Entry**: Use a ComboBox to implement a product search and entry feature. Users can either select a product from a predefined list or type in a custom product name. This is helpful for applications like e-commerce sites where products are vast and diverse.

2. **Tag Selection and Entry**: In applications involving content tagging, a ComboBox can serve as an excellent choice. Users can select from a list of existing tags or add custom tags by typing them. This is useful for organizing and categorizing content. Examples of such tags include:
    >- Project Labels: In a project management tool, users can select labels or tags (e.g., "Urgent," "In Progress," "Completed") to categorize tasks or projects, and they can create custom labels as needed.
    >- Recipe Ingredients:  In a cooking or recipe app, users can select ingredients from a list (e.g., "Tomatoes," "Onions," "Chicken") or add their own ingredients for custom recipes.
    >- Location Tags:  In a mapping or geotagging application, users can choose predefined location tags (e.g., "Beach," "City," "Park") or create custom tags to mark specific places on a map.

3. **Data Entry with Suggested Values**: In data entry forms, a ComboBox can be used to speed up input by providing a list of suggested values based on user input. This helps users enter data accurately and efficiently.

    :::tip
    The `ComboBox` should be used when users are allowed to enter custom values. If only preset values are desired, use a [`ChoiceBox`](./choice-box.md) instead.
    :::

## Constructors

1. <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#<init>()'>ComboBox()</JavadocLink>: Constructs a new `ComboBox` without a label.
2. <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#<init>(java.lang.String)'>ComboBox(String label)</JavadocLink>: Constructs a new `ComboBox` with the specified label.
3. <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#<init>(java.lang.String,org.dwcj.component.event.ComponentEventListener)'>ComboBox(String label, ComponentEventListener< ListSelectEvent> selectListener)</JavadocLink>: Constructs a new `ComboBox` with the given label and a listener to handle item selection events.


## Custom Value

Changing the custom value property allows control over whether or not a user is able to change the value in the `ComboBox` component's input field. If `true`, which is the default, then a user can change the value. If set to `false`, the user won't be able to change the value. This can be set using the <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink> method.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.comboboxdemos.ComboBoxCustomValue' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/comboboxdemos/ComboBoxCustomValue.java'
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/combobox/CustomValue.txt'
height = '200px'
/>

## Placeholder

A placeholder can be set for a `ComboBox` which will display in the text field of the component when it is empty to prompt users for the desired entry in the field. This can be done using the <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink> method.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.comboboxdemos.ComboBoxPlaceholder' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/comboboxdemos/ComboBoxPlaceholder.java'
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/combobox/Placeholder.txt'
height = '200px'
/>

## Dropdown Type

Using the <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> method will assign a value to the `type` attribute of a `ComboBox`, and a corresponding value for the `data-dropdown-for` attribute in the dropdown of the `ComboBox`. This is helpful for styling, as the dropdown is taken out of its current position in the DOM and relocated to the end of the page body when opened.

This detachment creates a situation where directly targeting the
dropdown using CSS or shadow part selectors from the parent component becomes challenging, unless you make use of the dropdown type attribute.

In the demo below, the Dropdown type is set and used in the CSS file to select the dropdown and change the background color.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.comboboxdemos.ComboBoxDropdownType' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/comboboxdemos/ComboBoxDropdownType.java'
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/combobox/DropdownType.txt'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/textcomboboxstyles/dropdown_styles.css'
height='250px'
/>

## Max Row Count

By default, the number of rows displayed in the dropdown of a `ComboBox` will be increased to fit the content. However, using the <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> method allows for control over how many items are displayed.

:::caution
Using a number that is less than or equal to 0 will result in unsetting this property.
:::

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.comboboxdemos.ComboBoxMaxRowDemo' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/comboboxdemos/ComboBoxMaxRowDemo.java'
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/combobox/MaxRow.txt'
height='250px'
/>

## Opening Dimensions

The `ComboBox` component has methods that allow manipulation of the dropdown dimensions. The **maximum height** and **minimum width** of the dropdown can be set using the <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> and <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> methods, respectively. 

:::tip
Passing a `String` value to either of these methods will allow for [any valid CSS unit](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units) to be applied, such as pixels, viewport dimensions, or other valid rules. Passing an `int` will set the value passed in pixels.
:::

## Highlighting

When working with a `ComboBox`, you can customize when the text is highlighted based on how the component gains focus. This feature can reduce input errors when users are completing forms and can improve the overall navigation experience. Change the highlighting behavior using the `setHighlightOnFocus()` method with on of the built-in `HasHighlightOnFocus.Behavior` enums:

- `ALL`
Contents of the component are always automatically highlighted when the component gets focus.
- `FOCUS`
Contents of the component are automatically highlighted when the component gets focus under program control.
- `FOCUS_OR_KEY`
Contents of the component are automatically highlighted when the component gets focus under program control or by tabbing into it.
- `FOCUS_OR_MOUSE`
Contents of the component are automatically highlighted when the component gets focus under program control or by clicking into it with the mouse.
- `KEY`
Contents of the component are automatically highlighted when the component gets focus by tabbing into it.
- `KEY_MOUSE`
Contents of the component are automatically highlighted when the component gets focus by tabbing into it or clicking into it with the mouse.
- `MOUSE`
Contents of the component are automatically highlighted when the component gets focus by clicking into it with the mouse.
- `NONE`
Contents of the component are never automatically highlighted when the component gets focus.

:::note
If content was highlighted upon losing focus, it will be highlighted again upon regaining focus, regardless of the set behavior.
:::

## Slots

Slots provide flexible options for improving the capability of a `ComboBox`. You can have icons, labels, loading spinners, clear/reset capability, avatar/profile pictures, and other beneficial components nested within a `ComboBox` to further clarify intended meaning to users.
The `ComboBox` has two slots: the `prefix` and `suffix` slots. Use the `setPrefixComponent()` and `setSuffixComponent()` methods to insert various components before and after the options within a `ComboBox`.

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Styling

<!-- ### Expanses -->

<!-- The `ComboBox` component comes with 5 expanses for quick styling without the use of CSS. Expanses are supported by use of a built-in enum class.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.comboboxdemos.TextComboBoxExpanses' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/comboboxdemos/TextComboBoxExpanses.java'
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/textcombobox/Expanses.txt'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/textcomboboxstyles/expanse_styles.css' 
javaHighlight='{24,27,30,33,36}'
height = '350px'
/> -->

### Shadow Parts

These are the various parts of the [shadow DOM](../../glossary#shadow-dom) for the `ComboBox` component, which will be required when styling via CSS is desired.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').ComboBox} table='parts' exclusions='' />

### Reflected Attributes

The reflected attributes of a component will be shown as attributes in the rendered HTML element for the component in the DOM. This means that styling can be applied using these attributes.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').ComboBox} table="reflects" exclusions=''/>

### Dependencies

This component relies on the following components - see the related article for more detailed styling information:

<TableBuilder tag='dwc-list-edit' table="dependencies"/>

## Best Practices 

To ensure an optimal user experience when using the `ComboBox` component, consider the following best practices:

1. **Preload Common Values**: If there are common or frequently used items, preload them in the `ComboBox` list. This speeds up selection for commonly chosen items and encourages consistency.

2. **User-Friendly Labels**: Ensure that the displayed labels for each option are user-friendly and self-explanatory. Make sure users can easily understand the purpose of each choice.

3. **Validation**: Implement input validation to handle custom entries. Check for data accuracy and consistency. You may want to suggest corrections or confirmations for ambiguous entries.

4. **Default Selection**: Set a default selection, especially if there is a common or recommended choice. This enhances the user experience by reducing the need for extra clicks.

5. **ComboBox vs. Other List Components**: A `ComboBox` is the best choice if you need a single input from the user and you wish to provide them with predetermined choices and the ability to customize their input. Another list component may be better if you need the following behaviors:
    - Multiple Selection and display all items at once: [ListBox](./list-box.md)
    - Prevent custom input: [ChoiceBox](./choice-box.md)
