package com.webforj.samples.views.dialog

import com.webforj.component.Composite
import com.webforj.component.dialog.Dialog
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.dialog.dialog
import com.webforj.kotlin.dsl.component.dialog.header
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.list.choiceBox
import com.webforj.kotlin.dsl.component.list.items
import com.webforj.kotlin.dsl.component.list.listItem
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Dialog Alignments")
class DialogAlignmentsKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
      self.apply {
        dialog {
          styles["display"] = "flex"
          styles["justify-content"] = "center"
          setCloseable(false)
          header { div("Alignments") }
          choiceBox("Select Alignment") {
            items(
              Dialog.Alignment.TOP to "Top",
              Dialog.Alignment.CENTER to "Center",
              Dialog.Alignment.BOTTOM to "Bottom"
            )
            selectIndex(1)
            onSelect {
              this@dialog.alignment = it.selectedItem.key as Dialog.Alignment
            }
          }
          open()
        }
      }
  }
}
