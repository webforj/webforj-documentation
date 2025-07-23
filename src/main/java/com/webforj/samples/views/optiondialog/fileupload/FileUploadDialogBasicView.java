package com.webforj.samples.views.optiondialog.fileupload;

import com.webforj.UploadedFile;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.optiondialog.FileUploadDialog;
import com.webforj.component.optiondialog.OptionDialog;
import com.webforj.component.optiondialog.MessageDialog;

@Route
@FrameTitle("File Upload Basics")
public class FileUploadDialogBasicView extends Composite<Div> {
  private FileUploadDialog dialog;

  public FileUploadDialogBasicView() {
    dialog = new FileUploadDialog("Upload a file");
    dialog.setFileSystemAccess(false);

    show();
  }

  private void show() {
    UploadedFile file = dialog.show();
    if (file != null) {
      // Do something with the uploaded file
      file.delete();
      OptionDialog.showMessageDialog("File uploaded successfully", "File Uploaded");
    } else {
      OptionDialog.showMessageDialog("No file selected", "File Selection Failed", MessageDialog.MessageType.ERROR);
    }

    show();
  }
}
