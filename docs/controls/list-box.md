---
sidebar_position: 60 
---

# ListBox

### At a Glance

|Parent Class| Interfaces |
|------------|------------|
|[AbstractDwclistControl](#)| <ul><li>[HasScroll](#)</li><li>[HasReadOnly](#)</li><li>[HasMouseWheelCondition](#)</li><li>[HasFocus](#)</li><li>[HasTabTraversal](#)</li><li>[HasTextAlignment](#)</li></ul>|

| Methods |
|------------|
| <ul><li>[`ListBox addItem(Object key, String item)`](#)</li><li>[`ListBox addItems(Map<Object, String> items)`](#)</li><li>[`ListBox deselectAll()`](#)</li><li>[`ListBox deselectIndex(int index)`](#)</li><li>[`Map<Object, String> getAllItems()`](#)</li><li>[`SimpleEntry<Object, String> getEntryByValue(String value)`](#)</li><li>[`String getItem(Object key)`](#)</li><li>[`String getItemAt(Integer idx)`](#)</li><li>[`SimpleEntry<Object, String> getSelectedItem()`](#)</li><li>[`Map<Object, String> getSelectedItems()`](#)</li><li>[`Boolean isMultipleSelection()`](#)</li><li>[`ListBox insertItemAt(Object key, String item, Integer index)`](#)</li><li>[`ListBox insertItemsAt(Map<Object, String> items, Integer index)`](#)</li><li>[`ListBox removeAllItems()`](#)</li><li>[`ListBox removeItemAt(Integer idx)`](#)</li><li>[`ListBox selectIndex(Integer idx)`](#)</li><li>[`ListBox setExpanse(Expanse expanse)`](#)</li><li>[`ListBox setItems(Map<Object, String> values)`](#)</li><li>[`ListBox setMultipleSelection(Boolean multipleSelection)`](#)</li><li>[`ListBox setTextAt(Integer idx, String text)`](#)</li></ul>|


| Events |
|------------|
| <ul><li>[`ListBox onSelect(Consumer<ListBoxSelectEvent> callback)`](#)</li><li>[`ListBox onDoubleClick(Consumer<ListBoxDoubleClickEvent> callback)`](#)</li></ul> |

### Labeling

Labels can be easily added above the component without the need to create a separate Label component above the control. 

<iframe 
loading="lazy"
src='http://localhost:8888/webapp/dwcj_control_demos?class=org.dwcj.control_demos.listboxdemos.ListboxLabel' 
style={{"width": "100%", "height":"170px"}}></iframe><br/><br />

Use the `setAttribute()` function to create a label. The attribute to be changed is `label`, and the value should be the desired label text.  <br/><br />

```java
import org.dwcj.controls.listbox.ListBox;

ListBox exampleListBox = new ListBox();      //Creates a new ListBox
exampleListBox.setAttribute("label", "Example list box label!");     //Gives the list box a label with the provided text
```

### Selection Options

By default, the list box is configured to allow selection of a single item at a time. However, this can be easily configured with a built-in method which allows users to select multiple
items using the `Shift` for contiguous entry selection and `Control` (Windows) or `Command` (Mac) for separate, multiple item selection. 

<iframe 
loading="lazy"
src='http://localhost:8888/webapp/dwcj_control_demos?class=org.dwcj.control_demos.listboxdemos.ListboxMultipleSelection' 
style={{"width": "100%", "height":"170px"}}></iframe><br/><br/>


Use the `setMultipleSelection()` function to change this property. True will enable multiple selection, false disables it. <br/><br />

```java
import org.dwcj.controls.listbox.ListBox;

ListBox exampleListBox = new ListBox();      //Creates a new ListBox
exampleListBox.setMultipleSelection(true);     //Sets the multiple selection property
```


### Expanses

There are five list box expanses that are supported in the DWCJ which allow for quick styling without using CSS.
Below are the various expanses supported for the list box control: <br/>

<iframe 
loading="lazy"
src='http://localhost:8888/webapp/dwcj_control_demos?class=org.dwcj.control_demos.listboxdemos.ListboxExpanses' 
style={{"width": "100%", "height":"250px"}}></iframe><br/>

|List Box Expanses|
|-|
|<ul><li>```ListBox.Expanse.XSMALL```</li><li>```ListBox.Expanse.SMALL```</li><li>```ListBox.Expanse.MEDIUM```</li><li>```ListBox.Expanse.LARGE```</li><li>```ListBox.Expanse.XLARGE```</li></ul>|

<br/>Expanses are supported by use of a built-in enum class. To apply an expanse, execute code similar to the following: <br/><br />

```java
import org.dwcj.controls.listbox.ListBox;

ListBox exampleListBox = new ListBox();      //Creates a new ListBox
exampleListBox.setExpanse(ListBox.Expanse.MEDIUM);     //Sets the list box's expanse to the medium size.
```