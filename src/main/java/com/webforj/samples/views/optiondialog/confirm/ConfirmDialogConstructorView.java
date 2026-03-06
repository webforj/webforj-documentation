package com.webforj.samples.views.optiondialog.confirm;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.optiondialog.ConfirmDialog;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Confirm Dialog Constructor")
public class ConfirmDialogConstructorView extends Composite<Div> {
  // Confirm dialog with OK/CANCEL options
  private final ConfirmDialog dialog;

  public ConfirmDialogConstructorView() {
    // Create confirm dialog with OK/CANCEL and question message type
    dialog = new ConfirmDialog(
            "Are you sure you want to delete this file? This action cannot be reverted.",
            "Deletion",
            ConfirmDialog.OptionType.OK_CANCEL,
            ConfirmDialog.MessageType.QUESTION)
            .setTheme(Theme.DANGER)
            .setButtonTheme(ConfirmDialog.Button.FIRST, ButtonTheme.DANGER)
            .setButtonTheme(ConfirmDialog.Button.SECOND, ButtonTheme.OUTLINED_GRAY);
    dialog.show();
  }
}
