package com.webforj.samples.views.optiondialog.input

import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.html.elements.Div
import com.webforj.component.optiondialog.InputDialog
import com.webforj.component.optiondialog.MessageDialog
import com.webforj.component.optiondialog.OptionDialog
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Input Dialog Basics")
class InputDialogBasicKotlinView : Composite<Div?>() {
  init {
    val message = """
        <dwc-alert theme='warning'>
          <b style='color: inherit'>Unexpected bad things will happen if you don't read this!</b>
        </dwc-alert>
        <br/>
        This will permanently delete the repository, wiki, issues,
        comments, packages, secrets, workflow runs, and remove all collaborator associations.
        <br/><br/>
        To confirm, type <b>$CONFIRMATION_CODE</b> in the box below
        """.trimIndent()

    val dialog: InputDialog = InputDialog(message, "Delete Repository", /*InputDialog.MessageType.ERROR*/).apply {
      messageType = InputDialog.MessageType.PLAIN
      firstButtonText = "Delete Repository"
      setSecondButtonTheme(ButtonTheme.OUTLINED_GRAY)
    }

    var input: String?
    do {
      input = dialog.show()

      when (input) {
          null -> {
            // User cancelled
            OptionDialog.showMessageDialog(
              "Aborted repository deletion.",
              "Repository Deletion Aborted",
              MessageDialog.MessageType.ERROR
            )
          }
          CONFIRMATION_CODE -> {
            // Correct code entered
            OptionDialog.showMessageDialog(
              "Repository was deleted successfully",
              "Repository Deleted",
              MessageDialog.MessageType.INFO
            )
          }
          else -> {
            // Wrong code entered
            OptionDialog.showMessageDialog(
              "Failed to delete the repository. Code entered is incorrect",
              "Repository Deletion Failed",
              MessageDialog.MessageType.ERROR
            )
          }
      }
    } while (input == null || input != CONFIRMATION_CODE)
  }

  companion object {
    // Confirmation code required for deletion
    private const val CONFIRMATION_CODE = "7ANfB"
  }
}
