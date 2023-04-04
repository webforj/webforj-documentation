---
sidebar_position: 60 
title: List Box
---
import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';

<div style={{width: "100%" , display: "flex", justifyContent: "flex-end", marginBottom: "-50px"}}>
<p style={{color: "gray"}} >API:&nbsp;</p>
<b><a href="https://javadoc.io/static/org.dwcj/dwcj-engine/0.15.0/org/dwcj/controls/listbox/ListBox.html" style={{justifySelf: "flex-end"}}> Java </a></b>
</div>

### Labeling

Labels can be easily added above the component without the need to create a separate Label component above the control. Use the `setAttribute()` function to create a label. The attribute to be changed is `label`, and the value should be the desired label text.

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.listboxdemos.ListboxLabel' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/listboxdemos/ListboxLabel.java'
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
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.listboxdemos.ListboxMultipleSelection' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/listboxdemos/ListboxMultipleSelection.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/listbox/MultipleSelection.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/listboxstyles/multiple_selection.css' 
javaHighlight='{44}'
height = '250px'
/>

<br/>

### Expanses

Below are the various expanses supported for the list box control: <br/>

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.listboxdemos.ListboxExpanses' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/listboxdemos/ListboxExpanses.java'
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