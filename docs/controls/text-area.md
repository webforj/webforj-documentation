---
sidebar_position: 140
title: Text Area
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';

<div style={{width: "100%" , display: "flex", justifyContent: "flex-end", marginBottom: "-50px"}}>
<p style={{color: "gray"}} >API:&nbsp;</p>
<b><a href="https://javadoc.io/static/org.dwcj/dwcj-engine/0.15.0/org/dwcj/controls/textarea/TextArea.html" style={{justifySelf: "flex-end"}}> Java </a></b>
</div>

### Labeling

The text area control can be easily labeled without the needing to create an extra label control using the `setAttribute()` method and passing the desired label as a string, as shown below: <br/>

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.textareademos.TextAreaLabel' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/textareademos/TextAreaLabel.java'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/textareastyles/text_area_styles.css' 
javaHighlight='{15}'
height = '125px'
/>

<br/>

### Placeholder

It is also possible to set placeholder text within the control to better help users understand what type of input is expected. Similar to a label, this can be accomplished using the `setAttribute()` method: <br/>

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.textareademos.TextAreaPlaceholder' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/textareademos/TextAreaPlaceholder.java'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/textareastyles/text_area_styles.css' 
javaHighlight='{16}'
height = '125px'
/>

<br/>

### Spellcheck

The text area can also be configured with spellchecking to help the user improve their input. Use the `setAttribute()` method to do this:

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.textareademos.TextAreaSpellcheck' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/textareademos/TextAreaSpellcheck.java'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/textareastyles/text_area_styles.css' 
javaHighlight='{16}'
height = '125px'
/>

<br/>

### Expanses

DWCJ's text area comes with 5 expanses for quick styling without the use of CSS. Expanses are supported by use of a built-in enum class.
Below are the various expanses supported for the text area control: <br/>

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.textareademos.TextAreaExpanse' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/textareademos/TextAreaExpanse.java'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/textareastyles/text_area_styles.css' 
javaHighlight='{19,23,27,31,35}'
height = '200px'
/>

|Text Area Expanses|
|-|
|<ul><li>```TextArea.Expanse.XSMALL```</li><li>```TextArea.Expanse.SMALL```</li><li>```TextArea.Expanse.MEDIUM```</li><li>```TextArea.Expanse.LARGE```</li><li>```TextArea.Expanse.XLARGE```</li></ul>|