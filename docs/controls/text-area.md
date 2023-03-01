---
sidebar_position: 140
---

# Text Area

### Label


The text area control can be easily labeled without the needing to create an extra label control using the `setAttribute()` method and passing the desired label as a string, as shown below: <br/>

<!-- <iframe 
loading="lazy"
src='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.textareademos.TextAreaLabel' 
style={{"width": "100%", "height":"125px"}}></iframe> -->

![Text area label](./_images/textarea/textarea_label.jpg)

<br/>

```java
import org.dwcj.controls.textarea.TextArea;

TextArea exampleTextArea = new TextArea();      //Creates a new TextArea
exampleTextArea.setAttribute("label", "Example Label");     
```

<br/>

### Placeholder

It is also possible to set placeholder text within the control to better help users understand what type of input is expected. Similar to a label, this can be accomplished using the `setAttribute()` method: <br/>

<!-- <iframe 
loading="lazy"
src='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.textareademos.TextAreaPlaceholder' 
style={{"width": "100%", "height":"125px"}}></iframe> -->

![Text area placeholder](./_images/textarea/textarea_placeholder.jpg)

<br/>

```java
import org.dwcj.controls.textarea.TextArea;

TextArea exampleTextArea = new TextArea();      //Creates a new TextArea
exampleTextArea.setAttribute("placeholder", "Example Placeholder");  
```

<br/>

### Spellcheck

The text area can also be configured with spellchecking to help the user improve their input. Use the `setAttribute()` method to do this:

<!-- <iframe 
loading="lazy"
src='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.textareademos.TextAreaSpellcheck' 
style={{"width": "100%", "height":"125px"}}></iframe> -->

![Text area spellcheck](./_images/textarea/textarea_spellcheck.jpg)

<br/>

```java
import org.dwcj.controls.textarea.TextArea;

TextArea exampleTextArea = new TextArea();      //Creates a new TextArea
exampleTextArea.setAttribute("spellcheck", "true");  
```

<br/>

### Expanses

DWCJ's text area comes with 5 expanses for quick styling without the use of CSS.
Below are the various expanses supported for the text area control: <br/>

<!-- <iframe 
loading="lazy"
src='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.textareademos.TextAreaExpanse' 
style={{"width": "100%", "height":"125px"}}></iframe> -->

![Text area expanses](./_images/textarea/textarea_expanses.jpg)

<br/>

|Text Area Expanses|
|-|
|<ul><li>```TextArea.Expanse.XSMALL```</li><li>```TextArea.Expanse.SMALL```</li><li>```TextArea.Expanse.MEDIUM```</li><li>```TextArea.Expanse.LARGE```</li><li>```TextArea.Expanse.XLARGE```</li></ul>|

<br/>Expanses are supported by use of a built-in enum class. To apply an expanse, execute code similar to the following: <br/><br />

```java
import org.dwcj.controls.textarea.TextArea;

TextArea exampleTextArea = new TextArea();      //Creates a new TextArea
exampleTextArea.setExpanse(TextArea.Expanse.MEDIUM);    
```