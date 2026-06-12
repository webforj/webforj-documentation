---
title: "Block navigation when a form has unsaved changes"
description: "Implement WillLeaveObserver to prompt the user with a confirm dialog before discarding edits."
tags: [routing, forms]
components: []
difficulty: intermediate
---

Mark the form dirty on any field change, then decide whether to accept or reject navigation inside `onWillLeave`. Because `OptionDialog.showConfirmDialog` blocks until the user responds, the navigation decision is based on that result.

```java
import com.webforj.component.Composite;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.optiondialog.ConfirmDialog;
import com.webforj.component.optiondialog.OptionDialog;
import com.webforj.router.annotation.Route;
import com.webforj.router.event.WillLeaveEvent;
import com.webforj.router.history.ParametersBag;
import com.webforj.router.observer.WillLeaveObserver;

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
    if (!dirty) {
      event.accept();
      return;
    }

    ConfirmDialog.Result answer = OptionDialog.showConfirmDialog(
        "You have unsaved changes. Leave without saving?",
        "Unsaved Changes",
        ConfirmDialog.OptionType.YES_NO,
        ConfirmDialog.MessageType.WARNING);

    if (answer == ConfirmDialog.Result.YES) {
      event.accept();
    } else {
      event.reject();
    }
  }
}
```
