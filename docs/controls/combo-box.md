---
sidebar_position: 20 
title: Combo Box
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';

<div style={{width: "100%" , display: "flex", justifyContent: "flex-end", marginBottom: "-50px"}}>
<p style={{color: "gray"}} >API:&nbsp;</p>
<b><a href="https://javadoc.io/static/org.dwcj/dwcj-engine/0.15.0/org/dwcj/controls/combobox/ComboBox.html" style={{justifySelf: "flex-end"}}> Java </a></b>
</div>

### Labeling

Labels can be easily added above the component without the need to create a separate Label component above the control. Use the `setAttribute()` function to create a label. The attribute to be changed is `label`, and the value should be the desired label text.


<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.comboboxdemos.ComboboxLabelDemo' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/comboboxdemos/ComboboxLabelDemo.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/combobox/Label.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/comboboxstyles/label_styles.css' 
javaHighlight='{33}'
height="170px"
/>

<br/>

### Themes

DWCJ Combo Box components come with 14 themes built in for quick styling without the use of CSS. Theming is supported by use of a built-in enum class.
Shown below are example boxes with each of the supported Themes applied: <br/>

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.comboboxdemos.ComboboxThemeDemo' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/comboboxdemos/ComboboxThemeDemo.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/combobox/Theme.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/comboboxstyles/theme_styles.css' 
height="170px"
/>

<br/>

Listed below are the current supported theme options for the combo box control:

|Combo Box Themes|
|-|
|<ul><li>```ComboBox.Theme.DEFAULT```</li><li>```ComboBox.Theme.DANGER```</li><li>```ComboBox.Theme.GRAY```</li><li>```ComboBox.Theme.INFO```</li><li>```ComboBox.Theme.PRIMARY```</li><li>```ComboBox.Theme.SUCCESS```</li><li>```ComboBox.Theme.WARNING```</li><li>```ComboBox.Theme.OUTLINED_DEFAULT```</li><li>```ComboBox.Theme.OUTLINED_DANGER```</li><li>```ComboBox.Theme.OUTLINED_GRAY```</li><li>```ComboBox.Theme.OUTLINED_INFO```</li><li>```ComboBox.Theme.OUTLINED_PRIMARY```</li><li>```ComboBox.Theme.OUTLINED_SUCCESS```</li><li>```ComboBox.Theme.OUTLINED_WARNING```</li></ul>|

<br />

### Expanses
There are five Combo Box expanses that are supported in the DWCJ which allow for quick styling without using CSS. Expanses are supported by use of a built-in enum class.
Below are the various expanses supported this control: <br/>

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.comboboxdemos.ComboboxExpanseDemo' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/comboboxdemos/ComboboxExpanseDemo.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/combobox/Expanse.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/comboboxstyles/expanse_styles.css' 
javaHighlight='{21,25,29,33,37}'
height="120px"
/>

<br/>
Listed below are the current supported expanse options for the combo box control:<br/><br/>

|ComboBox Expanses|
|-|
|<ul><li>```ComboBox.Expanse.XSMALL```</li><li>```ComboBox.Expanse.SMALL```</li><li>```ComboBox.Expanse.MEDIUM```</li><li>```ComboBox.Expanse.LARGE```</li><li>```ComboBox.Expanse.XLARGE```</li></ul>|