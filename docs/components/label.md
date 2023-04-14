---
sidebar_position: 50 
title: Label
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';

<div style={{width: "100%" , display: "flex", justifyContent: "flex-end", marginBottom: "-50px"}}>
<p style={{color: "gray"}} >API:&nbsp;</p>
<b><a href="https://javadoc.io/static/org.dwcj/dwcj-engine/0.15.0/org/dwcj/controls/label/Label.html" style={{justifySelf: "flex-end"}}> Java </a></b>
</div>

### Text

The label's text can be set either at creation using the parameterized constructor, or by utilizing the inherited `setText()` method. 

In addition to using the label as static text, it can also be used as an HTML tag within your code. Simply set the label's text to the desired HTML tag with the various attributes, class names, etc, and
the label will be replaced with the desired HTML element.

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.labeldemos.LabelDemo' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/labeldemos/LabelDemo.java'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/labelstyles/text_styles.css' 
javaHighlight='{16-18}'
height="250px"
/>

<br />

### Text Alignment

Label text can also be aligned using the inherited alignment functionality provided with the [TextAlignable](#) interface.

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.labeldemos.LabelAlignment' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/labeldemos/LabelAlignment.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/label/Alignment.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/labelstyles/alignment_styles.css' 
javaHighlight='{43-45}'
/>

<br/>