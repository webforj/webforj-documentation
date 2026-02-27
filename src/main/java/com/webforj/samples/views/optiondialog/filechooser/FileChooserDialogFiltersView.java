package com.webforj.samples.views.optiondialog.filechooser;

import java.util.List;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.optiondialog.FileChooserDialog;
import com.webforj.component.optiondialog.FileChooserFilter;
import com.webforj.component.optiondialog.MessageDialog;
import com.webforj.component.optiondialog.OptionDialog;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("File Chooser Filters")
public class FileChooserDialogFiltersView extends Composite<Div> {
  // File chooser dialog with filters
  private final FileChooserDialog dialog;

  public FileChooserDialogFiltersView() {
    // Create list of file filters
    List<FileChooserFilter> filters = List.of(
            new FileChooserFilter("All Files", "*.*"),
            new FileChooserFilter("Text Files (*.txt)", "*.txt"),
            new FileChooserFilter("Java Files (*.java)", "*.java"),
            new FileChooserFilter("Image Files (*.png, *.jpg, *.jpeg)", "*.png;*.jpg;*.jpeg"));

    // Create file chooser dialog with filters
    dialog = new FileChooserDialog(
            "Choose a file to download",
            System.getProperty("filechooser-files.path"),
            filters)
            .setRestricted(true)
            .setGridView(true);

    show();
  }

  /**
   * Shows the file chooser and displays the selected file.
   */
  private void show() {
    String file = dialog.show();

    if (file != null) {
      OptionDialog.showMessageDialog("File selected: " + file, "File Selected");
    } else {
      OptionDialog.showMessageDialog(
              "No file selected",
              "File Selection Failed",
              MessageDialog.MessageType.ERROR);
    }
  }
}
