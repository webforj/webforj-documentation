---
sidebar_position: 140
---

# Text Area

### At a Glance

|Parent Class| Interfaces |
|------------|------------|
|[AbstractDwcControl](#)| <ul><li>[HasReadOnly](#)</li><li>[TextHighlightable](#)</li><li>[Focusable](#)</li><li>[HasMouseWheelCondition](#)</li><li>[Scrollable](#)</li><li>[TabTraversable](#)</li></ul>|

| Methods |
|------------|
| <ul><li>[`TextArea addParagraph(Integer index, String paragraph)`](#)</li><li>[`TextArea appendToParagraph(Integer parNum, String text)`](#)</li><li>[`List<String> getAllParagraphs()`](#)</li><li>[`Integer getCurrentParagraphIndex()`](#)</li><li>[`Expanse getExpanse()`](#)</li><li>[`Integer getLineCountLimit()`](#)</li><li>[`Integer getMaxParagraphSize()`](#)</li><li>[`Integer getMaxLength()`](#)</li><li>[`Integer getNumberOfParagraphs()`](#)</li><li>[`String getParagraph(Integer parNum)`](#)</li><li>[`List<String> getSelection()`](#)</li><li>[`Integer getTabSize()`](#)</li><li>[`Theme getTheme()`](#)</li><li>[`void highlight(Integer parIndex1, Integer off1, Integer parIndex2, Integer off2)`](#)</li><li>[`Boolean isHorizontalScrollable()`](#)</li><li>[`Boolean isIgnoreEnters()`](#)</li><li>[`Boolean isIgnoreTabs()`](#)</li><li>[`Boolean isLimitToOneParagraph()`](#)</li><li>[`Boolean isLimitToOneParagraph()`](#)</li><li>[`Boolean isLineWrap()`](#)</li><li>[`Boolean isOvertypeMode()`](#)</li><li>[`Boolean isVerticalScrollable()`](#)</li><li>[`Boolean isWrapStyleWord()`](#)</li><li>[`void removeAll()`](#)</li><li>[`TextArea removeParagraph(Integer parIndex)`](#)</li><li>[`TextArea setExpanse(Expanse expanse)`](#)</li><li>[`TextArea setHorizontalScrollable(Boolean scroll)`](#)</li><li>[`TextArea setIgnoreEnters(Boolean ignore)`](#)</li><li>[`TextArea setIgnoreTabs(Boolean ignore)`](#)</li><li>[`TextArea setLimitToOneParagraph(Boolean limit)`](#)</li><li>[`TextArea setLineCountLimit(Integer limit)`](#)</li><li>[`TextArea setLineWrap(Boolean wrap)`](#)</li><li>[`TextArea setMaxParagraphSize(Integer size)`](#)</li><li>[`TextArea setMaxLength(Integer length)`](#)</li><li>[`TextArea setOvertypeMode(Boolean overtype)`](#)</li><li>[`TextArea setTabSize(Integer size)`](#)</li><li>[`TextArea setTheme(Theme theme)`](#)</li><li>[`TextArea setVerticalScrollable(Boolean scroll)`](#)</li><li>[`TextArea setWrapStyleWord(Boolean word)`](#)</li></ul>|


| Events |
|------------|
| <ul><li>[`TextArea onEditModify(Consumer<TextAreaOnEditModifyEvent> callback)`](#)</li></ul> |

### Label


The text area control can be easily labeled without the needing to create an extra label control using the `setAttribute()` method and passing the desired label as a string, as shown below: <br/>

<iframe 
loading="lazy"
src='http://localhost:8888/webapp/controlsamples?class=control_demos.textareademos.TextAreaLabel' 
style={{"width": "100%", "height":"125px"}}></iframe><br/>

```java
import org.dwcj.controls.textarea.TextArea;

TextArea exampleTextArea = new TextArea();      //Creates a new TextArea
exampleTextArea.setAttribute("label", "Example Label");     
```



### Placeholder

It is also possible to set placeholder text within the control to better help users understand what type of input is expected. Similar to a label, this can be accomplished using the `setAttribute()` method: <br/>

<iframe 
loading="lazy"
src='http://localhost:8888/webapp/controlsamples?class=control_demos.textareademos.TextAreaPlaceholder' 
style={{"width": "100%", "height":"125px"}}></iframe><br/>

```java
import org.dwcj.controls.textarea.TextArea;

TextArea exampleTextArea = new TextArea();      //Creates a new TextArea
exampleTextArea.setAttribute("placeholder", "Example Placeholder");  
```


### Spellcheck

The text area can also be configured with spellchecking to help the user improve their input. Use the `setAttribute()` method to do this:

<iframe 
loading="lazy"
src='http://localhost:8888/webapp/controlsamples?class=control_demos.textareademos.TextAreaSpellcheck' 
style={{"width": "100%", "height":"125px"}}></iframe><br/>

```java
import org.dwcj.controls.textarea.TextArea;

TextArea exampleTextArea = new TextArea();      //Creates a new TextArea
exampleTextArea.setAttribute("spellcheck", "true");  
```

### Expanses

DWCJ's text area comes with 5 expanses for quick styling without the use of CSS.
Below are the various expanses supported for the text area control: <br/>

<iframe 
loading="lazy"
src='http://localhost:8888/webapp/controlsamples?class=control_demos.textareademos.TextAreaExpanse' 
style={{"width": "100%", "height":"125px"}}></iframe><br/>

|Text Area Expanses|
|-|
|<ul><li>```TextArea.Expanse.XSMALL```</li><li>```TextArea.Expanse.SMALL```</li><li>```TextArea.Expanse.MEDIUM```</li><li>```TextArea.Expanse.LARGE```</li><li>```TextArea.Expanse.XLARGE```</li></ul>|

<br/>Expanses are supported by use of a built-in enum class. To apply an expanse, execute code similar to the following: <br/><br />

```java
import org.dwcj.controls.textarea.TextArea;

TextArea exampleTextArea = new TextArea();      //Creates a new TextArea
exampleTextArea.setExpanse(TextArea.Expanse.MEDIUM);    
```