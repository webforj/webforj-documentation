package com.webforj.samples.views.optiondialog.confirm;

import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.optiondialog.ConfirmDialog;

@Route
@FrameTitle("Confirm Dialog Constructor")
public class ConfirmDialogConstructorView extends Composite<Div> {
  private ConfirmDialog dialog;

  public ConfirmDialogConstructorView() {
    dialog = new ConfirmDialog(
        "Are you sure you want to delete this file? This action cannot be reverted.", "Deletion",
        ConfirmDialog.OptionType.OK_CANCEL, ConfirmDialog.MessageType.QUESTION);
    dialog.setTheme(Theme.DANGER);
    dialog.setButtonTheme(ConfirmDialog.Button.FIRST, ButtonTheme.DANGER);
    dialog.setButtonTheme(ConfirmDialog.Button.SECOND, ButtonTheme.OUTLINED_GRAY);

    show();
  }

  private void show() {
    dialog.show();
    show();
  }
}
