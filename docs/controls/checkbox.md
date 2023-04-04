---
sidebar_position: 10
title: Checkbox
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';

<div style={{width: "100%" , display: "flex", justifyContent: "flex-end", marginBottom: "-50px"}}>
<p style={{color: "gray"}} >API:&nbsp;</p>
<b><a href="https://javadoc.io/static/org.dwcj/dwcj-engine/0.15.0/org/dwcj/controls/checkbox/CheckBox.html" style={{justifySelf: "flex-end"}}> Java </a></b>
</div>

### Text

Check boxes can utilize the ```setText(String foo)``` method, which will positioned near the check box according to the built-in `HorizontalTextPosition` property which is detailed below. 

<br/>

### Horizontal Text Position

DWCJ checkboxes have built-in functionality to set text to be displayed either to the right or left of the box. By default, the text will be displayed to the right of the control. Positioning of the horizontal text is supported by use of a built-in enum class. Show below are the two settings: <br/>

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.checkboxdemos.CheckboxHorizontalText' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/checkboxdemos/CheckboxHorizontalText.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/checkbox/Expanse.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/checkboxstyles/text_styles.css' 
javaHighlight='{18}'
/>

<br/>

|Checkbox Horizontal Text Positions|
|-|
|<ul><li>```CheckBox.HorizontalTextPosition.LEFT```</li><li>```CheckBox.HorizontalTextPosition.RIGHT```</li></ul>|


<br/>

### Expanses
There are five checkbox expanses that are supported in the DWCJ which allow for quick styling without using CSS.
Expanses are supported by use of a built-in enum class. Below are the expanses supported for the checkbox control: <br/>

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.checkboxdemos.CheckboxExpanseDemo' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/checkboxdemos/CheckboxExpanseDemo.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/checkbox/Horizontal.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/checkboxstyles/expanse_styles.css' 
javaHighlight='{17,21,25,29,33}'
/>

<br/>

Listed below are the current supported expanse options for the checkbox control:

|Checkbox Expanses|
|-|
|<ul><li>```CheckBox.Expanse.XSMALL```</li><li>```CheckBox.Expanse.SMALL```</li><li>```CheckBox.Expanse.MEDIUM```</li><li>```CheckBox.Expanse.LARGE```</li><li>```CheckBox.Expanse.XLARGE```</li></ul>|
