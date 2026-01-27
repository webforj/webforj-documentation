package com.webforj.samples.views.dialog

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.dialog.dialog
import com.webforj.kotlin.dsl.component.dialog.header
import com.webforj.kotlin.dsl.component.field.textField
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Dialog Auto-Focus")
class DialogAutoFocusKotlinView: Composite<FlexLayout>() {

  init {
      boundComponent.apply {
        dialog {
          header { div("Auto Focus") }
          textField("This Box is Auto Focused")
          isAutoFocus = true
          open()
        }
      }
  }
}
