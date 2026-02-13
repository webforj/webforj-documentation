package com.webforj.samples.views.dialog

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.dialog.dialog
import com.webforj.kotlin.dsl.component.dialog.header
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.kotlin.extension.vw
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Dialog Close")
class DialogCloseKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
      self.apply {
        val d = dialog {
          header { div("Closing the Dialog") }
          button("Close Dialog") {
            onClick { this@dialog.close() }
          }
          isCancelOnEscKey = true
          open()
        }
        button("Show Dialog") {
          styles["margin-left"] = 48.vw
          styles["margin-top"] = 20.px
          onClick { d.open() }
        }
      }
  }
}
