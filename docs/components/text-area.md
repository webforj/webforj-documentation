---
sidebar_position: 140
title: TextArea
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="engine" location="org/dwcj/component/textarea/TextArea" top='true'/>

### Labeling

The text area component can be easily labeled without the needing to create an extra label component using the `setAttribute()` method and passing the desired label as a string, as shown below: <br/>

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=component_demos.textareademos.TextAreaLabel' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/component_demos/textareademos/TextAreaLabel.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/textarea/Label.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/textareastyles/text_area_styles.css' 
javaHighlight='{15}'
height = '125px'
/>

<br/>

### Placeholder

It is also possible to set placeholder text within the component to better help users understand what type of input is expected. Similar to a label, this can be accomplished using the `setAttribute()` method: <br/>

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=component_demos.textareademos.TextAreaPlaceholder' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/component_demos/textareademos/TextAreaPlaceholder.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/textarea/Placeholder.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/textareastyles/text_area_styles.css' 
javaHighlight='{16}'
height = '125px'
/>

<br/>

### Spellcheck

The text area can also be configured with spellchecking to help the user improve their input. Use the `setAttribute()` method to do this:

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=component_demos.textareademos.TextAreaSpellcheck' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/component_demos/textareademos/TextAreaSpellcheck.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/textarea/Spellcheck.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/textareastyles/text_area_styles.css' 
javaHighlight='{16}'
height = '125px'
/>

<br/>

### Expanses

DWCJ's text area comes with 5 expanses for quick styling without the use of CSS. Expanses are supported by use of a built-in enum class.
Below are the various expanses supported for the text area component: <br/>

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=component_demos.textareademos.TextAreaExpanse' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/component_demos/textareademos/TextAreaExpanse.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/textarea/Expanse.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/textareastyles/text_area_styles.css' 
javaHighlight='{19,23,27,31,35}'
height = '200px'
/>

|Text Area Expanses|
|-|
|<ul><li>```TextArea.Expanse.XSMALL```</li><li>```TextArea.Expanse.SMALL```</li><li>```TextArea.Expanse.MEDIUM```</li><li>```TextArea.Expanse.LARGE```</li><li>```TextArea.Expanse.XLARGE```</li></ul>|

## Parts and CSS Properties

<TableBuilder tag={require('@site/docs/components/_bbj_control_map.json').TextArea} />