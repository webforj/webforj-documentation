package com.webforj.samples.views.optiondialog.confirm;

import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.component.Composite;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.optiondialog.ConfirmDialog;
import com.webforj.component.optiondialog.OptionDialog;

@Route
@FrameTitle("Confirm Dialog Options")
public class ConfirmDialogOptionsView extends Composite<Div> {
  private ConfirmDialog dialog;

  public ConfirmDialogOptionsView() {
    dialog = new ConfirmDialog(
        "There are unsaved changes. Do you want to discard or save them?", "Unsaved changes",
        ConfirmDialog.OptionType.CUSTOM, ConfirmDialog.MessageType.WARNING);
    dialog.setFirstButtonText("Discard");
    dialog.setFirstButtonTheme(ButtonTheme.WARNING);
    dialog.setSecondButtonText("Save");
    dialog.setSecondButtonTheme(ButtonTheme.OUTLINED_GRAY);

    show();
  }

  private void show() {
    ConfirmDialog.Result result = dialog.show();

    if (result == ConfirmDialog.Result.FIRST_CUSTOM_BUTTON) {
      OptionDialog.showMessageDialog("Changes discarded", "Discarded", "Got it");
    } else {
      OptionDialog.showMessageDialog(
          "Changes saved", "Saved", "Got it");
    }

    show();
  }
}
