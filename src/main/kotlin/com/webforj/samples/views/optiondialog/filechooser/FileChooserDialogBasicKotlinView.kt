package com.webforj.samples.views.optiondialog.filechooser

import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.optiondialog.FileChooserDialog
import com.webforj.component.optiondialog.OptionDialog
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("File Chooser Basics")
class FileChooserDialogBasicKotlinView: Composite<Div>() {
  private val dialog: FileChooserDialog

  init {
    dialog = FileChooserDialog(
      "Choose Directory to save",
      System.getProperty("filechooser-files.path"),
      FileChooserDialog.SelectionMode.DIRECTORIES
    ).apply {
      isRestricted = true
    }

    show()
  }

  private fun show() {
    val directory = dialog.show()

    directory?.let {
      OptionDialog.showMessageDialog(
        "Directory selected: $it",
        "Directory Selected"
      )
    } ?: OptionDialog.showMessageDialog(
      "No directory selected",
      "Directory Selection Failed",
      // MessageType.ERROR
    )

    show()
  }
}
