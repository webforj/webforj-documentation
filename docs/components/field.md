---
sidebar_position: 27
title: Field
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';

<div style={{width: "100%" , display: "flex", justifyContent: "flex-end", marginBottom: "-50px"}}>
<p style={{color: "gray"}} >API:&nbsp;</p>
<b><a href="https://javadoc.io/static/org.dwcj/dwcj-engine/0.15.0/org/dwcj/controls/textbox/TextBox.html" style={{justifySelf: "flex-end"}}> Java </a></b>
</div>

### Labeling

The Field component can be easily labeled without the needing to create an extra label component using the `setAttribute()` method and passing the desired label as a string, as shown below: <br/>

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.textboxdemos.TextBoxLabel' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/textboxdemos/TextBoxLabel.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/textbox/Label.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/textboxstyles/text_box_styles.css' 
javaHighlight='{16}'
height = '125px'
/>

<br/>

### Placeholder

It is also possible to set placeholder text within the component to better help users understand what type of input is expected. Similar to a label, this can be accomplished using the `setAttribute()` method: <br/>

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.textboxdemos.TextBoxPlaceholder' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/textboxdemos/TextBoxPlaceholder.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/textbox/Placeholder.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/textboxstyles/text_box_styles.css' 
javaHighlight='{16}'
height = '125px'
/>

<br/>

### Spellcheck

The Field can also be configured with spell checking to help the user improve their input. Use the `setAttribute()` method to do this:

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.textboxdemos.TextBoxSpellcheck' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/textboxdemos/TextBoxSpellcheck.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/textbox/Spellcheck.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/textboxstyles/text_box_styles.css' 
javaHighlight='{16}'
height = '125px'
/>

<br/>

### Expanses

DWCJ's Field comes with 5 expanses for quick styling without the use of CSS. Expanses are supported by use of a built-in enum class.
Below are the various expanses supported for the Field component: <br/>

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.textboxdemos.TextBoxExpanses' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/textboxdemos/TextBoxExpanses.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/textbox/Expanses.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/textboxstyles/text_box_styles.css' 
javaHighlight='{16,19,22,25,28}'
height = '160px'
/>

|Field Expanses|
|-|
|<ul><li>```Field.Expanse.XSMALL```</li><li>```Field.Expanse.SMALL```</li><li>```Field.Expanse.MEDIUM```</li><li>```Field.Expanse.LARGE```</li><li>```Field.Expanse.XLARGE```</li></ul>|
