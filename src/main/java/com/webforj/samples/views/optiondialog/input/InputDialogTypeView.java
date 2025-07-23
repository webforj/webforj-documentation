package com.webforj.samples.views.optiondialog.input;

import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.component.Composite;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.optiondialog.InputDialog;
import com.webforj.component.optiondialog.OptionDialog;
import com.webforj.component.optiondialog.MessageDialog;

@Route
@FrameTitle("Input Dialog Type")
public class InputDialogTypeView extends Composite<Div> {
  InputDialog dialog;

  public InputDialogTypeView() {
    dialog = new InputDialog(
        "This page is restricted. Please enter your password to continue.",
        "Restricted access.",
        InputDialog.InputType.PASSWORD);

    dialog.setFirstButtonText("Continue");
    dialog.setSecondButtonText("Cancel");
    dialog.setFirstButtonTheme(ButtonTheme.PRIMARY);

    tryLogin();
  }

  public void tryLogin() {
    String result = dialog.show();

    if (result != null && !result.isEmpty()) {
      OptionDialog.showMessageDialog("Access granted", "Welcome", "Got it", MessageDialog.MessageType.INFO);
    } else {
      OptionDialog.showMessageDialog("Access denied", "Access denied", "Try again", MessageDialog.MessageType.ERROR);
      tryLogin();
    }
  }
}
