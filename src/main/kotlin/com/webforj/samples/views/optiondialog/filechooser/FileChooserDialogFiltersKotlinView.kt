package com.webforj.samples.views.optiondialog.filechooser

import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.optiondialog.FileChooserDialog
import com.webforj.component.optiondialog.FileChooserFilter
import com.webforj.component.optiondialog.OptionDialog
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("File Chooser Filters")
class FileChooserDialogFiltersKotlinView: Composite<Div>() {
  private val dialog: FileChooserDialog

  init {
      val filters = listOf(
        FileChooserFilter("All Files", "*.*"),
        FileChooserFilter("Text Files (*.txt)", "*.txt"),
        FileChooserFilter("Java Files (*.java)", "*.java"),
        FileChooserFilter("Image Files (*.png, *.jpg, *.jpeg)", "*.png;*.jpg;*.jpeg")
      )

    dialog = FileChooserDialog(
      "Choose a file to download",
      System.getProperty("filechooser-files.path"),
      filters
    ).apply {
      isRestricted = true
      isGridView = true
    }

    show()
  }

  private fun show() {
    val file = dialog.show()

    file?.let {
      OptionDialog.showMessageDialog(
        "File selected: $it", "File Selected"
      )
    } ?: OptionDialog.showMessageDialog(
      "No file selected",
      "File Selection Failed",
      // MessageType.ERROR
    )
  }
}
