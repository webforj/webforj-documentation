package com.webforj.samples.views.optiondialog.input;

import com.webforj.component.Composite;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.optiondialog.InputDialog;
import com.webforj.component.optiondialog.MessageDialog;
import com.webforj.component.optiondialog.OptionDialog;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Input Dialog Type")
public class InputDialogTypeView extends Composite<Div> {
  // Input dialog for password entry
  private final InputDialog dialog;

  public InputDialogTypeView() {
    // Create password input dialog
    dialog = new InputDialog(
            "This page is restricted. Please enter your password to continue.",
            "Restricted access.",
            InputDialog.InputType.PASSWORD)
            .setFirstButtonText("Continue")
            .setSecondButtonText("Cancel")
            .setFirstButtonTheme(ButtonTheme.PRIMARY);

    tryLogin();
  }

  /**
   * Attempts login by showing the password dialog.
   */
  public void tryLogin() {
    String result = dialog.show();

    if (result != null && !result.isEmpty()) {
      // Access granted
      OptionDialog.showMessageDialog(
              "Access granted",
              "Welcome",
              "Got it",
              MessageDialog.MessageType.INFO);
    } else {
      // Access denied - retry
      OptionDialog.showMessageDialog(
              "Access denied",
              "Access denied",
              "Try again",
              MessageDialog.MessageType.ERROR);
    }
  }
}
