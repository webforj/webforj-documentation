---
sidebar_position: 150
---

# Text Box

### At a Glance

|Parent Class| Interfaces |
|------------|------------|
|[AbstractDwcControl](#)| <ul><li>[HasReadOnly](#)</li><li>[HasFocus](#)</li><li>[HasTabTraversal](#)</li><li>[TextAlignable](#)</li><li>[TextHighlightable](#)</li></ul>|

| Methods |
|------------|
| <ul><li>[`TextBox(String text)`](#)</li><li>[`String getEditType()`](#)</li><li>[`Integer getMaxLength()`](#)</li><li>[`String getSelectedText()`](#)</li><li>[`String getSelection()`](#)</li><li>[`Boolean isPassHomeDelete()`](#)</li><li>[`boolean isPasswordVisible()`](#)</li><li>[`TextBox select(Integer offset1, Integer offset2)`](#)</li><li>[`TextBox setExpanse(Expanse expanse)`](#)</li><li>[`TextBox setMaxLength(Integer length)`](#)</li><li>[`TextBox setPassHomeDelete(Boolean pass)`](#)</li><li>[`TextBox setPasswordVisible(Boolean visible)`](#)</li></ul>|

| Events |
|------------|
| <ul><li>[`TextBox onEditModify(Consumer<TextBoxEditModifyEvent> callback)`](#)</li></ul> |

<br/>

### Label


The text box control can be easily labeled without the needing to create an extra label control using the `setAttribute()` method and passing the desired label as a string, as shown below: <br/>

<!-- <iframe 
loading="lazy"
src='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.textboxdemos.TextBoxLabel' 
style={{"width": "100%", "height":"125px"}}></iframe> -->

![Text box label](./_images/textbox/textbox_label.jpg)

<br/>

```java
import org.dwcj.controls.textbox.TextBox;

TextBox exampleTextBox = new TextBox();      //Creates a new TextBox
exampleTextBox.setAttribute("label", "Example Label");     
```

<br/>

### Placeholder

It is also possible to set placeholder text within the control to better help users understand what type of input is expected. Similar to a label, this can be accomplished using the `setAttribute()` method: <br/>

<!-- <iframe 
loading="lazy"
src='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.textboxdemos.TextBoxPlaceholder' 
style={{"width": "100%", "height":"125px"}}></iframe> -->

![Text box placeholder](./_images/textbox/textbox_placeholder.jpg)

<br/>

```java
import org.dwcj.controls.textbox.TextBox;

TextBox exampleTextBox = new TextBox();      //Creates a new TextBox
exampleTextBox.setAttribute("placeholder", "Example Placeholder");  
```
<br/>

### Spellcheck

The text box can also be configured with spellchecking to help the user improve their input. Use the `setAttribute()` method to do this:

<!-- <iframe 
loading="lazy"
src='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.textboxdemos.TextBoxSpellcheck' 
style={{"width": "100%", "height":"125px"}}></iframe> -->

![Text box spellcheck](./_images/textbox/textbox_spellcheck.jpg)

<br/>

```java
import org.dwcj.controls.textbox.TextBox;

TextBox exampleTextBox = new TextBox();      //Creates a new TextBox
exampleTextBox.setAttribute("spellcheck", "true");  
```

<br/>

### Expanses

DWCJ's text box comes with 5 expanses for quick styling without the use of CSS.
Below are the various expanses supported for the text box control: <br/>

<!-- <iframe 
loading="lazy"
src='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.textboxdemos.TextBoxExpanses' 
style={{"width": "100%", "height":"125px"}}></iframe> -->

![Text box expanses](./_images/textbox/textbox_expanses.jpg)

<br/>

|Text Box Expanses|
|-|
|<ul><li>```TextBox.Expanse.XSMALL```</li><li>```TextBox.Expanse.SMALL```</li><li>```TextBox.Expanse.MEDIUM```</li><li>```TextBox.Expanse.LARGE```</li><li>```TextBox.Expanse.XLARGE```</li></ul>|

<br/>Expanses are supported by use of a built-in enum class. To apply an expanse, execute code similar to the following: <br/><br />

```java
import org.dwcj.controls.textbox.TextBox;

TextBox exampleTextBox = new TextBox();      //Creates a new Text Box
exampleTextBox.setExpanse(TextBox.Expanse.MEDIUM);    
```