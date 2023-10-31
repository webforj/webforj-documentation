---
sidebar_position: 0
title: Lists
draft: true
---

import componentData from '@site/static/field_data.js'
import ComponentViewer from '@site/src/components/PageTools/ComponentViewer'

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="engine" location="org/dwcj/component/list/DwcList"/>

The DWCJ contains three types of lists for use within your applications: [`ListBox`](docs/components/list-components/listbox), [`ChoiceBox`](docs/components/list-components/choicebox) and [`ComboBox`](docs/components/list-components/combobox). These three components all display a list of key value items, and support methods to add, remove, select and manage the items within the list. 

This section will list the commonalities within each of the aforementioned components, with the specific behaviors belonging to each outlined in their own sections.

:::info
This section describes common functionality amongst all of the list components, and is not itself a class that can be instantiated and used.
:::

## Using `ListItem`

The `ListItem` class represents individual items within a list. Each ListItem is associated with a key and a display text. Here are the key points regarding the ListItem class:

1. **Item Representation**

  >- A `ListItem` encapsulates a unique key `Object`, and a text `String` for display within the component. List components are composed of `ListItem` objects.

  >- You can construct a `ListItem` by providing a key and text, or simply by specifying the text, where a random UUID is used as the key.

2. **Property Change**

  >- `ListItem` supports property change events. When the text of a ListItem is modified, it notifies listeners of the change.

  >- You can add property change listeners to monitor text changes using the `addPropertyChangeListener()` method.

```java
listItem.addPropertyChangeListener(e -> {
  //Executed when the event fires
});

//OR

listItem.addPropertyChangeListener(new ComponentEventListener<PropertyChangeEvent>() {
  @Override
  public void onComponentEvent(PropertyChangeEvent e){
    //Executed when the event fires
  }
});

//OR

listItem.addPropertyChangeListener(this::propertyChangeMethod); // Consumer method
```


## Shared Field Properties 

### Label

The various List components can be assigned a label, which is a descriptive text or title that is associated with the component. It provides a brief explanation or prompt to help users understand the purpose or expected selection for that particular list. List labels are not only important for usability but also play a crucial role in accessibility, as they enable screen readers and assistive technologies to provide accurate information and facilitate keyboard navigation.

<!-- ADD DEMO WITH ALL THREE AND A LABEL -->

## Shared Events

:::caution Notice
All List components share the following methods to add and remove event listeners for the following events
:::

| Events | Description |
|:-:|-|
|[`BlurEvent`](../events/BlurEvent)|An event that is triggered when a component loses focus.|
|[`FocusEvent`](../events/FocusEvent)|An event that is triggered when a component gains focus, opposite of a blur event. |
|[`MouseEnterEvent`](../events/MouseEnterEvent)|An event that is triggered when the mouse cursor enters the boundaries of a component. |
|[`MouseExitEvent`](../events/MouseExitEvent)|An event that is triggered when the mouse cursor exits the boundaries of a component. |
|[`RightMouseDownEvent`](../events/RightMouseDownEvent)|An event that is triggered when the user presses the right mouse button while the cursor is over a component.|