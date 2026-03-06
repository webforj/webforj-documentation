package com.webforj.samples.views.optiondialog.filesave;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.optiondialog.FileSaveDialog;
import com.webforj.component.optiondialog.MessageDialog;
import com.webforj.component.optiondialog.OptionDialog;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("File Save Basics")
public class FileSaveDialogBasicView extends Composite<Div> {
  // File save dialog
  private final FileSaveDialog dialog;

  public FileSaveDialogBasicView() {
    // Create file save dialog with default path and filename
    dialog = new FileSaveDialog("Save As", "/usr2/bbx/demos", "report.xls");
    dialog.setRestricted(true);

    show();
  }

  /**
   * Shows the file save dialog and displays the result.
   */
  private void show() {
    String path = dialog.show();

    if (path != null) {
      OptionDialog.showMessageDialog("Saved file to: " + path, "Path Selected");
    } else {
      OptionDialog.showMessageDialog(
          "No path is selected",
          "Path Selected",
          MessageDialog.MessageType.ERROR);
    }
  }
}
