package com.webforj.samples.views.optiondialog.fileupload

import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.optiondialog.FileUploadDialog
import com.webforj.component.optiondialog.MessageDialog
import com.webforj.component.optiondialog.OptionDialog
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("File Upload Basics")
class FileUploadDialogBasicKotlinView : Composite<Div?>() {
  private val dialog: FileUploadDialog

  init {
    dialog = FileUploadDialog("Upload a file").apply {
      isFileSystemAccess = false
    }

    show()
  }

  /**
   * Shows the file upload dialog and handles the result.
   */
  private fun show() {
    val file = dialog.show()

    if (file != null) {
      file.delete()
      OptionDialog.showMessageDialog("File uploaded successfully", "File Uploaded")
    } else {
      OptionDialog.showMessageDialog(
        "No file selected",
        "File Selection Failed",
        MessageDialog.MessageType.ERROR
      )
    }
  }
}

