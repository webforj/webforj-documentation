---
sidebar_position: 160
---

# Text Combo Box


### At a Glance

|Parent Class| Interfaces |
|------------|------------|
|[AbstractDwclistControl](#)| <ul><li>[HasReadOnly](#)</li><li>[Focusable](#)</li><li>[HasMouseWheelCondition](#)</li><li>[TabTraversable](#)</li><li>[TextAlignable](#)</li></ul>|

| Methods |
|------------|
| <ul><li>[`TextComboBox addItem(Object key, String item)`](#)</li><li>[`TextComboBox addItems(Map<Object, String> items)`](#)</li><li>[`TextComboBox closeList()`](#)</li><li>[`TextComboBox deselect()`](#)</li><li>[`Map<Object, String> getAllItems()`](#)</li><li>[`String getEditText()`](#)</li><li>[`String getItemAt(Object key)`](#)</li><li>[`Integer getItemCount()`](#)</li><li>[`Integer getSelectedIndex()`](#)</li><li>[`SimpleEntry<Object, String> getSelectedItem()`](#)</li><li>[`TextComboBox insertItemAt(Object key, String item, Integer index)`](#)</li><li>[`TextComboBox insertItemsAt(Map<Object, String> items, Integer index)`](#)</li><li>[`TextComboBox openList()`](#)</li><li>[`TextComboBox removeAllItems()`](#)</li><li>[`TextComboBox select(Integer indexStart, Integer indexEnd)`](#)</li><li>[`TextComboBox selectIndex(Integer index)`](#)</li><li>[`TextComboBox setEditText(String text)`](#)</li><li>[`TextComboBox setExpanse(Expanse expanse)`](#)</li><li>[`TextComboBox setItems(Map<Object, String> values)`](#)</li><li>[`TextComboBox setMaximumRowCount(Integer max)`](#)</li><li>[`TextComboBox setTextAt(Integer idx, String text)`](#)</li></ul>|


| Events |
|------------|
| <ul><li>[`onChange(Consumer<TextComboBoxChangeEvent> callback)`](#)</li><li>[`TextComboBox onClose(Consumer<TextComboBoxCloseEvent> callback)`](#)</li><li>[`TextComboBox onEditModify(Consumer<TextComboBoxEditModifyEvent> callback)`](#)</li><li>[`TextComboBox onOpen(Consumer<TextComboBoxOpenEvent> callback)`](#)</li><li>[`TextComboBox onSelect(Consumer<TextComboBoxSelectEvent> callback)`](#)</li></ul> |

<br/>

### Menu Placement

The text combo box can be configured to open the menu in various positions relative to the position of the control itself.

<!-- <iframe 
loading="lazy"
src='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.textcomboboxdemos.TextComboBoxPlacement' 
style={{"width": "100%", "height":"350px"}}></iframe> -->

![Text combo box menu placement](./_images/textcombobox/textcombobox_placement.jpg)

<br/>

```java
import org.dwcj.controls.textcombobox.TextComboBox;

TextComboBox exampleTextComboBox = new TextComboBox();      //Creates a new TextComboBox
exampleTextComboBox.setAttribute("placement", "left");  
```

<br/>

### Label

The text combo box control can be easily labeled without the needing to create an extra label control using the `setAttribute()` method and passing the desired label as a string, as shown below: <br/>

<!-- <iframe 
loading="lazy"
src='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.textcomboboxdemos.TextComboBoxLabel' 
style={{"width": "100%", "height":"200px"}}></iframe> -->

![Text combo box label](./_images/textcombobox/textcombobox_label.jpg)

<br/>

```java
import org.dwcj.controls.textcombobox.TextComboBox;

TextComboBox exampleTextComboBox = new TextComboBox();      //Creates a new TextComboBox
exampleTextComboBox.setAttribute("label", "Example Label");     
```

<br/>

### Placeholder

It is also possible to set placeholder text within the control to better help users understand what type of input is expected. Similar to a label, this can be accomplished using the `setAttribute()` method: <br/>

<!-- <iframe 
loading="lazy"
src='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.textcomboboxdemos.TextComboBoxPlaceholder' 
style={{"width": "100%", "height":"200px"}}></iframe> -->

![Text combo box placeholder](./_images/textcombobox/textcombobox_placeholder.jpg)

<br/>

```java
import org.dwcj.controls.textcombobox.TextComboBox;

TextComboBox exampleTextComboBox = new TextComboBox();      //Creates a new TextComboBox
exampleTextComboBox.setAttribute("placeholder", "Example Placeholder");  
```

<br/>

### Expanses

DWCJ's text combo box comes with 5 expanses for quick styling without the use of CSS.
Below are the various expanses supported for the text box control: <br/>

<!-- <iframe 
loading="lazy"
src='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.textcomboboxdemos.TextComboBoxExpanses' 
style={{"width": "100%", "height":"400px"}}></iframe> -->

![Text combo box expanses](./_images/textcombobox/textcombobox_expanses.jpg)

<br/>

|Text Box Expanses|
|-|
|<ul><li>```TextBomboBox.Expanse.XSMALL```</li><li>```TextBomboBox.Expanse.SMALL```</li><li>```TextBomboBox.Expanse.MEDIUM```</li><li>```TextBomboBox.Expanse.LARGE```</li><li>```TextBomboBox.Expanse.XLARGE```</li></ul>|

<br/>Expanses are supported by use of a built-in enum class. To apply an expanse, execute code similar to the following: <br/><br />

```java
import org.dwcj.controls.textcombobox.TextComboBox;

TextComboBox exampleTextComboBox = new TextComboBox();      //Creates a new Text Box
exampleTextComboBox.setExpanse(TextComboBox.Expanse.MEDIUM);    
```