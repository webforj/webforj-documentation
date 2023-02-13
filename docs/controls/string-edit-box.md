---
sidebar_position: 120 
---

# String Edit Box

### At a Glance


|Parent Class| Interfaces |
|------------|------------|
|[AbstractDwcControl](#)| <ul><li>[HasReadOnly](#)</li><li>[HasFocus](#)</li><li>[HasTabTraversal](#)</li><li>[HasTextAlignment](#)</li><li>[HasTextHighlight](#)</li></ul>|

| Methods |
|------------|
| <ul><li>[`StringEditBox(String text)`](#)</li><li>[`Integer getCaretPos()`](#)</li><li>[`Integer getError()`](#)</li><li>[`String getEditString()`](#)</li><li>[`Integer getLength()`](#)</li><li>[`Integer getMargin()`](#)</li><li>[`String getMask()`](#)</li><li>[`String getPadCharacter()`](#)</li><li>[`String getRestore()`](#)</li><li>[`Theme getTheme()`](#)</li><li>[`Boolean isHighlight()`](#)</li><li>[`Boolean isInsertMode()`](#)</li><li>[`Boolean isPassEnter()`](#)</li><li>[`Boolean isPassTab()`](#)</li><li>[`StringEditBox restore()`](#)</li><li>[`StringEditBox selectAll()`](#)</li><li>[`StringEditBox setCaretPos(Integer position)`](#)</li><li>[`StringEditBox setEditString(String edit)`](#)</li><li>[`StringEditBox setHighlight(Boolean highlight)`](#)</li><li>[`StringEditBox setInsertMode(Boolean insert)`](#)</li><li>[`StringEditBox setLength(Integer len)`](#)</li><li>[`StringEditBox setMargin(Integer marginWidth)`](#)</li><li>[`StringEditBox setMask(String mask)`](#)</li><li>[`StringEditBox setPadCharacter(String pad)`](#)</li><li>[`StringEditBox setPassEnter(Boolean pass)`](#)</li><li>[`StringEditBox setPassTab(Boolean pass)`](#)</li><li>[`StringEditBox setRestore(String restore)`](#)</li><li>[`StringEditBox setTheme(Theme theme)`](#)</li></ul>|


| Events |
|------------|
| <ul><li>[`StringEditBox onEditModify(Consumer<StringEditBoxEditModifyEvent> callback)`](#)</li></ul> |

<br/>

### Label


The string edit control can be easily labeled without the needing to create an extra label control using the `setAttribute()` method and passing the desired label as a string, as shown below: <br/>

<!-- <iframe 
loading="lazy"
src='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.stringeditdemos.StringEditLabelDemo' 
style={{"width": "100%", "height":"125px"}}></iframe> -->

![String edit box label](./_images/stringeditbox/stringedit_label.jpg)

<br/>

```java
import org.dwcj.controls.stringeditbox.StringEditBox;

StringEditBox exampleStringEditBox = new StringEditBox();      //Creates a new StringEditBox
exampleStringEditBox.setAttribute("label", "Example Label");     
```

<br/>

### Mask

<br/>

### Placeholder

It is also possible to set placeholder text within the control to better help users understand what type of input is expected. Similar to a label, this can be accomplished using the `setAttribute()` method: <br/>

<!-- <iframe 
loading="lazy"
src='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.stringeditdemos.StringEditPlaceholder' 
style={{"width": "100%", "height":"125px"}}></iframe> -->

![String edit box placeholder](./_images/stringeditbox/stringedit_placeholder.jpg)

<br/>

```java
import org.dwcj.controls.stringeditbox.StringEditBox;

StringEditBox exampleStringEditBox = new StringEditBox();      //Creates a new StringEditBox
exampleStringEditBox.setAttribute("placeholder", "Example Placeholder");
```

<br/>

### Expanses

Additionally, the string edit box come with 5 expanses for quick styling without the use of CSS.
Below are the various expanses supported for the string edit box control: <br/>

<!-- <iframe 
loading="lazy"
src='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.stringeditdemos.StringEditExpanse' 
style={{"width": "100%", "height":"125px"}}></iframe> -->

![String edit box expanses](./_images/stringeditbox/stringedit_expanses.jpg)

<br/>

|String Edit Box Expanses|
|-|
|<ul><li>```StringEditBox.Expanse.XSMALL```</li><li>```StringEditBox.Expanse.SMALL```</li><li>```StringEditBox.Expanse.MEDIUM```</li><li>```StringEditBox.Expanse.LARGE```</li><li>```StringEditBox.Expanse.XLARGE```</li></ul>|

<br/>Expanses are supported by use of a built-in enum class. To apply an expanse, execute code similar to the following: <br/><br />

```java
import org.dwcj.controls.stringeditbox.StringEditBox;

StringEditBox exampleStringEditBox = new StringEditBox();      //Creates a new StringEditBox
exampleStringEditBox.setExpanse(StringEditBox.Expanse.MEDIUM);     
```
