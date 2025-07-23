package com.webforj.samples.views.optiondialog.filesave;

import java.util.ArrayList;
import java.util.List;

import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.optiondialog.FileChooserFilter;
import com.webforj.component.optiondialog.FileSaveDialog;
import com.webforj.component.optiondialog.OptionDialog;
import com.webforj.component.optiondialog.MessageDialog;

@Route
@FrameTitle("File Save Filters")
public class FileSaveDialogFiltersView extends Composite<Div> {
  private FileSaveDialog dialog;

  public FileSaveDialogFiltersView() {
    List<FileChooserFilter> filters = new ArrayList<>();
    filters.add(new FileChooserFilter("All Files", "*.*"));
    filters.add(new FileChooserFilter("Text Files", "*.txt"));
    filters.add(new FileChooserFilter("CSV Files", "*.csv"));
    filters.add(new FileChooserFilter("Excel Files", "*.xls;*.xlsx"));

    dialog = new FileSaveDialog("Save As", "/usr2/bbx/demos", "export.txt", filters);
    dialog.setRestricted(true);
    show();
  }

  private void show() {
    String path = dialog.show();
    if (path != null) {
      OptionDialog.showMessageDialog("Saved file to: " + path, "Path Selected");

    } else {
      OptionDialog.showMessageDialog("No path is selected", "Path Selected",
          MessageDialog.MessageType.ERROR);
    }

    show();
  }
}
