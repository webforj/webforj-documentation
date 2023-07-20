---
sidebar_position: 60 
title: ListBox
---
import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="engine" location="org/dwcj/component/listbox/ListBox" top='true'/>

### Labeling

Labels can be easily added above the component without the need to create a separate Label component above the component. Use the `setAttribute()` function to create a label. The attribute to be changed is `label`, and the value should be the desired label text.

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=component_demos.listboxdemos.ListboxLabel' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/component_demos/listboxdemos/ListboxLabel.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/listbox/Label.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/listboxstyles/label_styles.css' 
javaHighlight='{32}'
height = '250px'
/>

<br/>

### Selection Options

By default, the list box is configured to allow selection of a single item at a time. However, this can be easily configured with a built-in method which allows users to select multiple
items using the `Shift` for contiguous entry selection and `Control` (Windows) or `Command` (Mac) for separate, multiple item selection. Use the `setMultipleSelection()` function to change this property. True will enable multiple selection, false disables it. 

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=component_demos.listboxdemos.ListboxMultipleSelection' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/component_demos/listboxdemos/ListboxMultipleSelection.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/listbox/MultipleSelection.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/listboxstyles/multiple_selection.css' 
javaHighlight='{44}'
height = '250px'
/>

<br/>

### Expanses

Below are the various expanses supported for the list box component: <br/>

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=component_demos.listboxdemos.ListboxExpanses' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/component_demos/listboxdemos/ListboxExpanses.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/listbox/Expanses.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/listboxstyles/expanse_styles.css' 
javaHighlight='{18-22}'
height = '300px'
/>

<br/>

There are five list box expanses that are supported in the DWCJ which allow for quick styling without using CSS. These enums are:

|List Box Expanses|
|-|
|<ul><li>```ListBox.Expanse.XSMALL```</li><li>```ListBox.Expanse.SMALL```</li><li>```ListBox.Expanse.MEDIUM```</li><li>```ListBox.Expanse.LARGE```</li><li>```ListBox.Expanse.XLARGE```</li></ul>|

## Parts and CSS Properties

<TableBuilder tag={require('@site/docs/components/_bbj_control_map.json').ListBox} />