package com.webforj.samples.views.optiondialog.filechooser;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.optiondialog.FileChooserDialog;
import com.webforj.component.optiondialog.MessageDialog;
import com.webforj.component.optiondialog.OptionDialog;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("File Chooser Basics")
public class FileChooserDialogBasicView extends Composite<Div> {
  // File chooser dialog for directory selection
  private final FileChooserDialog dialog;

  public FileChooserDialogBasicView() {
    // Create directory chooser dialog
    dialog = new FileChooserDialog(
            "Choose Directory to save",
            System.getProperty("filechooser-files.path"),
            FileChooserDialog.SelectionMode.DIRECTORIES)
            .setRestricted(true);

    show();
  }

  /**
   * Shows the directory chooser and displays the result.
   */
  private void show() {
    String directory = dialog.show();

    if (directory != null) {
      OptionDialog.showMessageDialog(
              "Directory selected: " + directory,
              "Directory Selected");
    } else {
      OptionDialog.showMessageDialog(
              "No directory selected",
              "Directory Selection Failed",
              MessageDialog.MessageType.ERROR);
    }
  }
}
