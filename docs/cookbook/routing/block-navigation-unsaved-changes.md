---
title: "Block navigation when a form has unsaved changes"
description: "Implement WillLeaveObserver to veto route changes and prompt the user with a confirm dialog before discarding edits."
tags: [routing, forms]
components: []
difficulty: intermediate
---

Mark the form dirty on any field change, then veto navigation inside `onWillLeave`. Because `OptionDialog.showConfirmDialog` blocks until the user responds, the veto decision is based on that result.

```java
@Route("edit-product")
public class EditProductView extends Composite<Div> implements WillLeaveObserver {

  private boolean dirty = false;

  public EditProductView() {
    TextField name = new TextField("Product name");
    name.onModify(e -> dirty = true);
    getBoundComponent().add(name);
  }

  @Override
  public void onWillLeave(WillLeaveEvent event, ParametersBag parameters) {
    if (!dirty) return;

    ConfirmDialog.Result answer = OptionDialog.showConfirmDialog(
        "You have unsaved changes. Leave without saving?",
        "Unsaved Changes",
        ConfirmDialog.OptionType.YES_NO,
        ConfirmDialog.MessageType.WARNING);

    event.veto(answer != ConfirmDialog.Result.YES);
  }
}
```
