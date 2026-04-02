package com.webforj.samples.views.optiondialog.confirm

import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.button.ButtonTheme
import com.webforj.component.html.elements.Div
import com.webforj.component.optiondialog.ConfirmDialog
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Confirm Dialog Constructor")
class ConfirmDialogConstructorKotlinView: Composite<Div>() {

  init {
    ConfirmDialog(
      "Are you sure you want to delete this file? This action cannot be reverted.",
      "Deletion",
      ConfirmDialog.OptionType.OK_CANCEL,
      // MessageType.QUESTION
    ).apply {
      theme = Theme.DANGER
      setButtonTheme(ConfirmDialog.Button.FIRST, ButtonTheme.DANGER)
      setButtonTheme(ConfirmDialog.Button.SECOND, ButtonTheme.OUTLINED_GRAY)
      show()
    }
  }
}
