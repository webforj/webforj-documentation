---
sidebar_position: 150
title: Text Box
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';

<div style={{width: "100%" , display: "flex", justifyContent: "flex-end", marginBottom: "-50px"}}>
<p style={{color: "gray"}} >API:&nbsp;</p>
<b><a href="https://javadoc.io/static/org.dwcj/dwcj-engine/0.15.0/org/dwcj/controls/textbox/TextBox.html" style={{justifySelf: "flex-end"}}> Java </a></b>
</div>

### Labeling

The text box control can be easily labeled without the needing to create an extra label control using the `setAttribute()` method and passing the desired label as a string, as shown below: <br/>

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.textboxdemos.TextBoxLabel' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/textboxdemos/TextBoxLabel.java'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/textboxstyles/text_box_styles.css' 
javaHighlight='{16}'
height = '125px'
/>

<br/>

### Placeholder

It is also possible to set placeholder text within the control to better help users understand what type of input is expected. Similar to a label, this can be accomplished using the `setAttribute()` method: <br/>

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.textboxdemos.TextBoxPlaceholder' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/textboxdemos/TextBoxPlaceholder.java'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/textboxstyles/text_box_styles.css' 
javaHighlight='{16}'
height = '125px'
/>

<br/>

### Spellcheck

The text box can also be configured with spellchecking to help the user improve their input. Use the `setAttribute()` method to do this:

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.textboxdemos.TextBoxSpellcheck' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/textboxdemos/TextBoxSpellcheck.java'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/textboxstyles/text_box_styles.css' 
javaHighlight='{16}'
height = '125px'
/>

<br/>

### Expanses

DWCJ's text box comes with 5 expanses for quick styling without the use of CSS. Expanses are supported by use of a built-in enum class.
Below are the various expanses supported for the text box control: <br/>

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.textboxdemos.TextBoxExpanses' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/textboxdemos/TextBoxExpanses.java'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/textboxstyles/text_box_styles.css' 
javaHighlight='{16,19,22,25,28}'
height = '125px'
/>

|Text Box Expanses|
|-|
|<ul><li>```TextBox.Expanse.XSMALL```</li><li>```TextBox.Expanse.SMALL```</li><li>```TextBox.Expanse.MEDIUM```</li><li>```TextBox.Expanse.LARGE```</li><li>```TextBox.Expanse.XLARGE```</li></ul>|
