package com.webforj.samples.views.optiondialog.filesave

import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.optiondialog.FileSaveDialog
import com.webforj.component.optiondialog.MessageDialog
import com.webforj.component.optiondialog.OptionDialog
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("File Save Basics")
class FileSaveDialogBasicKotlinView: Composite<Div>() {
  private val dialog: FileSaveDialog

  init {
    dialog = FileSaveDialog("Save As", "/usr2/bbx/demos", "reports.xls").apply {
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
