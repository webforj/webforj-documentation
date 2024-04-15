---
sidebar_position: 23
title: ComboBox
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="com/webforj/component/combobox/ComboBox" top='true'/>

### Menu Placement

The combo box can be configured to open the menu in various positions relative to the position of the component itself.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.textcomboboxdemos.TextComboBoxPlacement' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/textcomboboxdemos/TextComboBoxPlacement.java'
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/textcombobox/Placement.txt'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/textcomboboxstyles/placement_styles.css' 
javaHighlight='{24,29,33,38}'
height = '350px'
/>

<br/>

### Label

The combo box component can be easily labeled without the needing to create an extra label component using the `setAttribute()` method and passing the desired label as a string, as shown below: <br/>

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.textcomboboxdemos.TextComboBoxLabel' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/textcomboboxdemos/TextComboBoxLabel.java'
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/textcombobox/Label.txt'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/textcomboboxstyles/text_combo_styles.css' 
javaHighlight='{24}'
height = '200px'
/>

<br/>

### Placeholder

It is also possible to set placeholder text within the component to better help users understand what type of input is expected. Similar to a label, this can be accomplished using the `setAttribute()` method: <br/>

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.textcomboboxdemos.TextComboBoxPlaceholder' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/textcomboboxdemos/TextComboBoxPlaceholder.java'
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/textcombobox/Placeholder.txt'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/textcomboboxstyles/text_combo_styles.css' 
javaHighlight='{24}'
height = '200px'
/>

<br/>

### Expanses

webforJ's combo box comes with 5 expanses for quick styling without the use of CSS. Expanses are supported by use of a built-in enum class.
Below are the various expanses supported for the text box component: <br/>

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.textcomboboxdemos.TextComboBoxExpanses' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/textcomboboxdemos/TextComboBoxExpanses.java'
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/textcombobox/Expanses.txt'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/textcomboboxstyles/expanse_styles.css' 
javaHighlight='{24,27,30,33,36}'
height = '350px'
/>

|Combo Box Expanses|
|-|
|<ul><li>```ComboBox.Expanse.XSMALL```</li><li>```ComboBox.Expanse.SMALL```</li><li>```ComboBox.Expanse.MEDIUM```</li><li>```ComboBox.Expanse.LARGE```</li><li>```ComboBox.Expanse.XLARGE```</li></ul>|

## Parts and CSS Properties

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').ComboBox} />