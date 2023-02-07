---
sidebar_position: 20 
---

# Combo Box

### At a Glance

|Parent Class| Interfaces |
|------------|------------|
|[AbstractDwclistControl](#)| <ul><li>[HasReadOnly](#)</li><li>[HasFocus](#)</li><li>[HasTabTraversal](#)</li><li>[HasTextAlignment](#)</li></ul>|

| Methods |
|------------|
| <ul><li>[`ComboBox addItem(Object key, String item)`](#)</li><li>[`ComboBox addItems(Map<Object, String> items)`](#)</li><li>[`ComboBox close()`](#)</li><li>[`ComboBox deselect()`](#)</li><li>[`Map<Object, String> getAllItems()`](#)</li><li>[`String getItem(Object key)`](#)</li><li>[`String getItemAt(Integer idx)`](#)</li><li>[`Integer getItemCount()`](#)</li><li>[`Integer getSelectedIndex()`](#)</li><li>[`SimpleEntry<Object, String> getSelectedItem()`](#)</li><li>[`ComboBox insertItemAt(Object key, String item, Integer index)`](#)</li><li>[`ComboBox insertItemsAt(Map<Object, String> items, Integer index)`](#)</li><li>[`ComboBox open()`](#)</li><li>[`ComboBox removeAllItems()`](#)</li><li>[`ComboBox removeItemAt(Integer index)`](#)</li><li>[`ComboBox selectIndex(Integer index)`](#)</li><li>[`ComboBox setItems(Map<Object, String> values)`](#)</li><li>[`ComboBox setMaximumRowCount(Integer max)`](#)</li><li>[`ComboBox setTextAt(Integer idx, String text)`](#)</li></ul>|


| Events |
|------------|
| <ul><li>[`ComboBox onClose(Consumer<ComboBoxCloseEvent> callback)`](#)</li><li>[`ComboBox onOpen(Consumer<ComboBoxOpenEvent> callback)`](#)</li><li>[`ComboBox onSelect(Consumer<ComboBoxSelectEvent> callback)`](#)</li></ul> |


### Labeling

Labels can be easily added above the component without the need to create a separate Label component above the control. 

<iframe 
loading="lazy"
src='http://localhost:8888/webapp/dwcj_control_demos?class=org.dwcj.control_demos.comboboxdemos.ComboboxLabelDemo' 
style={{"width": "100%", "height":"100px"}}></iframe><br/><br />

Use the `setAttribute()` function to create a label. The attribute to be changed is `label`, and the value should be the desired label text.  <br/><br />

```java
import org.dwcj.controls.combobox.ComboBox;

ComboBox exampleComboBox = new ComboBox();      //Creates a new ComboBox
exampleComboBox.setAttribute("label", "Example combo box label!");     //Gives the combo box a label with the provided text
```

### Themes

DWCJ Combo Box components come with 14 themes built in for quick styling without the use of CSS.
Shown below are example boxes with each of the supported Themes applied: <br/>
<!-- ![various button expanses](./_images/button_themes.jpg) -->
<iframe 
loading="lazy"
src='http://localhost:8888/webapp/dwcj_control_demos?class=org.dwcj.control_demos.comboboxdemos.ComboboxThemeDemo' 
style={{"width": "100%", "height" : "170px"}}></iframe><br/>
Listed below are the current supported theme options for the button control:<br/><br/>

|Combo Box Themes|
|-|
|<ul><li>```ComboBox.Theme.DEFAULT```</li><li>```ComboBox.Theme.DANGER```</li><li>```ComboBox.Theme.GRAY```</li><li>```ComboBox.Theme.INFO```</li><li>```ComboBox.Theme.PRIMARY```</li><li>```ComboBox.Theme.SUCCESS```</li><li>```ComboBox.Theme.WARNING```</li><li>```ComboBox.Theme.OUTLINED_DEFAULT```</li><li>```ComboBox.Theme.OUTLINED_DANGER```</li><li>```ComboBox.Theme.OUTLINED_GRAY```</li><li>```ComboBox.Theme.OUTLINED_INFO```</li><li>```ComboBox.Theme.OUTLINED_PRIMARY```</li><li>```ComboBox.Theme.OUTLINED_SUCCESS```</li><li>```ComboBox.Theme.OUTLINED_WARNING```</li></ul>|

<br/>Theming is supported by use of a built-in enum class. To apply a theme, execute code similar to the following: <br/><br />

```java
import org.dwcj.controls.combobox.ComboBox;

ComboBox exampleComboBox = new ComboBox();      //Creates a new Combo Box with the text "example"
exampleComboBox.setTheme(ComboBox.Theme.DEFAULT);      //Sets the box's theme to be the default theme.
```


### Expanses
There are five Combo Box expanses that are supported in the DWCJ which allow for quick styling without using CSS.
Below are the various expanses supported this control: <br/>
<!-- ![various button expanses](./_images/button_expanses.jpg) -->
<iframe 
loading="lazy"
src='http://localhost:8888/webapp/dwcj_control_demos?class=org.dwcj.control_demos.comboboxdemos.ComboboxExpanseDemo' 
style={{"width": "100%", "height" : "120px"}}></iframe><br/>
Listed below are the current supported expanse options for the button control:<br/><br/>

|ComboBox Expanses|
|-|
|<ul><li>```ComboBox.Expanse.XSMALL```</li><li>```ComboBox.Expanse.SMALL```</li><li>```ComboBox.Expanse.MEDIUM```</li><li>```ComboBox.Expanse.LARGE```</li><li>```ComboBox.Expanse.XLARGE```</li></ul>|

<br/>Expanses are supported by use of a built-in enum class. To apply an expanse, execute code similar to the following: <br/><br />

```java
import org.dwcj.controls.combobox.ComboBox;

Button exampleButton = new Button("Example");      //Creates a new button with the text "example"
exampleButton.setExpanse(Button.Expanse.MEDIUM);     //Sets the button's expanse to the medium size.
```