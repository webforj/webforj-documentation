---
sidebar_position: 20 
title: ChoiceBox
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="engine" location="org/dwcj/component/choicebox/ChoiceBox"/>

### Labeling

Labels can be easily added above the component without the need to create a separate Label component above the component. Use the `setAttribute()` function to create a label. The attribute to be changed is `label`, and the value should be the desired label text.


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

DWCJ Choice Box components come with 14 themes built in for quick styling without the use of CSS. Theming is supported by use of a built-in enum class.
Shown below are example boxes with each of the supported Themes applied: <br/>

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.comboboxdemos.ComboboxThemeDemo' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/comboboxdemos/ComboboxThemeDemo.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/combobox/Theme.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/comboboxstyles/theme_styles.css' 
height="170px"
/>

<br/>

Listed below are the current supported theme options for the combo box component:

|Choice Box Themes|
|-|
|<ul><li>```ChoiceBox.Theme.DEFAULT```</li><li>```ChoiceBox.Theme.DANGER```</li><li>```ChoiceBox.Theme.GRAY```</li><li>```ChoiceBox.Theme.INFO```</li><li>```ChoiceBox.Theme.PRIMARY```</li><li>```ChoiceBox.Theme.SUCCESS```</li><li>```ChoiceBox.Theme.WARNING```</li><li>```ChoiceBox.Theme.OUTLINED_DEFAULT```</li><li>```ChoiceBox.Theme.OUTLINED_DANGER```</li><li>```ChoiceBox.Theme.OUTLINED_GRAY```</li><li>```ChoiceBox.Theme.OUTLINED_INFO```</li><li>```ChoiceBox.Theme.OUTLINED_PRIMARY```</li><li>```ChoiceBox.Theme.OUTLINED_SUCCESS```</li><li>```ChoiceBox.Theme.OUTLINED_WARNING```</li></ul>|

<br />

### Expanses
There are five Choice Box expanses that are supported in the DWCJ which allow for quick styling without using CSS. Expanses are supported by use of a built-in enum class.
Below are the various expanses supported this component: <br/>

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.comboboxdemos.ComboboxExpanseDemo' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/comboboxdemos/ComboboxExpanseDemo.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/combobox/Expanse.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/comboboxstyles/expanse_styles.css' 
javaHighlight='{21,25,29,33,37}'
height="150px"
/>

<br/>
Listed below are the current supported expanse options for the choice box component:<br/><br/>

|ChoiceBox Expanses|
|-|
|<ul><li>```ChoiceBox.Expanse.XSMALL```</li><li>```ChoiceBox.Expanse.SMALL```</li><li>```ChoiceBox.Expanse.MEDIUM```</li><li>```ChoiceBox.Expanse.LARGE```</li><li>```ChoiceBox.Expanse.XLARGE```</li></ul>|

## Parts and CSS Properties

<TableBuilder tag={require('@site/docs/components/_bbj_control_map.json').ChoiceBox} />