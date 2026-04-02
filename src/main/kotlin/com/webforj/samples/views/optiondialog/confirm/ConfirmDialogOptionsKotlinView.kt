package com.webforj.samples.views.optiondialog.confirm

import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.html.elements.Div
import com.webforj.component.optiondialog.ConfirmDialog
import com.webforj.component.optiondialog.OptionDialog
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Confirm Dialog Options")
class ConfirmDialogOptionsKotlinView: Composite<Div>() {
  private val dialog: ConfirmDialog

  init {
    dialog = ConfirmDialog(
      "There are unsaved changes. Do you want to discard or save them?",
      "Unsaved changes",
      ConfirmDialog.OptionType.CUSTOM,
      // MessageType.WARNING
    ).apply {
      firstButtonText = "Discard"
      setFirstButtonTheme(ButtonTheme.WARNING)
      secondButtonText = "Save"
      setSecondButtonTheme(ButtonTheme.OUTLINED_GRAY)
    }
    show()
  }

  private fun show() {
    val result = dialog.show()

    val (message, title, buttonText) = if (result == ConfirmDialog.Result.FIRST_CUSTOM_BUTTON) {
      listOf("Changes discarded", "Discarded", "Got it")
    } else {
      listOf("Changes saved", "Saved", "Got it")
    }

    OptionDialog.showMessageDialog(message, title, buttonText)
    show()
  }

}
