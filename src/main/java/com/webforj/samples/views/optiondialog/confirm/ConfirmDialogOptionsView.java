package com.webforj.samples.views.optiondialog.confirm;

import com.webforj.component.Composite;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.optiondialog.ConfirmDialog;
import com.webforj.component.optiondialog.OptionDialog;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Confirm Dialog Options")
public class ConfirmDialogOptionsView extends Composite<Div> {
  // Confirm dialog with custom buttons
  private final ConfirmDialog dialog;

  public ConfirmDialogOptionsView() {
    // Create confirm dialog with custom options
    dialog = new ConfirmDialog(
            "There are unsaved changes. Do you want to discard or save them?",
            "Unsaved changes",
            ConfirmDialog.OptionType.CUSTOM,
            ConfirmDialog.MessageType.WARNING)
            .setFirstButtonText("Discard")
            .setFirstButtonTheme(ButtonTheme.WARNING)
            .setSecondButtonText("Save")
            .setSecondButtonTheme(ButtonTheme.OUTLINED_GRAY);
    show();
  }

  /**
   * Shows the confirm dialog and handles the result.
   */
  private void show() {
    ConfirmDialog.Result result = dialog.show();

    // Show appropriate message based on button clicked
    if (result == ConfirmDialog.Result.FIRST_CUSTOM_BUTTON) {
      OptionDialog.showMessageDialog("Changes discarded", "Discarded", "Got it");
    } else {
      OptionDialog.showMessageDialog("Changes saved", "Saved", "Got it");
    }
  }
}
