package com.webforj.samples.views.optiondialog.filesave;

import java.util.List;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.optiondialog.FileChooserFilter;
import com.webforj.component.optiondialog.FileSaveDialog;
import com.webforj.component.optiondialog.MessageDialog;
import com.webforj.component.optiondialog.OptionDialog;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("File Save Filters")
public class FileSaveDialogFiltersView extends Composite<Div> {
  // File save dialog with filters
  private final FileSaveDialog dialog;

  public FileSaveDialogFiltersView() {
    // Create list of file filters
    List<FileChooserFilter> filters = List.of(
        new FileChooserFilter("All Files", "*.*"),
        new FileChooserFilter("Text Files", "*.txt"),
        new FileChooserFilter("CSV Files", "*.csv"),
        new FileChooserFilter("Excel Files", "*.xls;*.xlsx"));

    // Create file save dialog with filters
    dialog = new FileSaveDialog("Save As", "/usr2/bbx/demos", "export.txt", filters);
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
