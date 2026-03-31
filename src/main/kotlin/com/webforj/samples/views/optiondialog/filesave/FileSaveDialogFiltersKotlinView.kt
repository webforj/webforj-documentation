package com.webforj.samples.views.optiondialog.filesave

import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.optiondialog.FileChooserFilter
import com.webforj.component.optiondialog.FileSaveDialog
import com.webforj.component.optiondialog.MessageDialog
import com.webforj.component.optiondialog.OptionDialog
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("File Save Filters")
class FileSaveDialogFiltersKotlinView: Composite<Div>() {
  private val dialog: FileSaveDialog

  init {
    val filters = listOf(
      FileChooserFilter("All Files", "*.*"),
      FileChooserFilter("Text Files", "*.txt"),
      FileChooserFilter("CSV Files", "*.csv"),
      FileChooserFilter("Excel Files", "*.xls;*.xlsx")
    )
    dialog = FileSaveDialog("Save As", "/usr2/bbx/demos", "export.txt", filters).apply {
      isRestricted = true
    }

    show()
  }

  private fun show() {
    val path = dialog.show()

    path?.let {
      OptionDialog.showMessageDialog("Saved file to: $path", "Path Selected")
    } ?: OptionDialog.showMessageDialog(
      "No path is selected",
      "Path Selected",
//      MessageDialog.MessageType.ERROR
    )
  }
}
