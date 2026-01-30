package com.webforj.samples.views.dialog

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.dialog.dialog
import com.webforj.kotlin.dsl.component.dialog.addToHeader
import com.webforj.kotlin.dsl.component.field.textField
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Dialog Auto-Focus")
class DialogAutoFocusKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
      self.apply {
        dialog {
          addToHeader { div("Auto Focus") }
          textField("This Box is Auto Focused")
          isAutoFocus = true
          open()
        }
      }
  }
}
