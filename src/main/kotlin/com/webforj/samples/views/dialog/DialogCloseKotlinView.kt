package com.webforj.samples.views.dialog

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.dialog.dialog
import com.webforj.kotlin.dsl.component.dialog.addToHeader
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Dialog Close")
class DialogCloseKotlinView: Composite<FlexLayout>() {

  init {
      boundComponent.apply {
        val d = dialog {
          addToHeader { div("Closing the Dialog") }
          button("Close Dialog") {
            onClick { this@dialog.close() }
          }
          isCancelOnEscKey = true
          open()
        }
        button("Show Dialog") {
          styles["margin-left"] = "48vw"
          styles["margin-top"] = "20px"
          onClick { d.open() }
        }
      }
  }
}
