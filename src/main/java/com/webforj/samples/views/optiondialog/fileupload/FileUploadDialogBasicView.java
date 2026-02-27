package com.webforj.samples.views.optiondialog.fileupload;

import com.webforj.UploadedFile;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.optiondialog.FileUploadDialog;
import com.webforj.component.optiondialog.MessageDialog;
import com.webforj.component.optiondialog.OptionDialog;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("File Upload Basics")
public class FileUploadDialogBasicView extends Composite<Div> {
  // File upload dialog
  private final FileUploadDialog dialog;

  public FileUploadDialogBasicView() {
    // Create file upload dialog
    dialog = new FileUploadDialog("Upload a file")
            .setFileSystemAccess(false);

    show();
  }

  /**
   * Shows the file upload dialog and handles the result.
   */
  private void show() {
    UploadedFile file = dialog.show();

    if (file != null) {
      // Process the uploaded file (delete for demo purposes)
      file.delete();
      OptionDialog.showMessageDialog("File uploaded successfully", "File Uploaded");
    } else {
      OptionDialog.showMessageDialog(
              "No file selected",
              "File Selection Failed",
              MessageDialog.MessageType.ERROR);
    }
  }
}
