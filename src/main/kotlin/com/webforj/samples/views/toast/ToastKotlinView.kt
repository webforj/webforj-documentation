package com.webforj.samples.views.toast

import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.button.ButtonTheme
import com.webforj.component.html.elements.Div
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.toast.Toast
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.html.elements.paragraph
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.spinner.spinner
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Toast Basics")
class ToastKotlinView : Composite<Div>() {
  private val toast = Toast("", -1, Theme.GRAY)

  init {
    toast.apply {
      flexLayout {
        direction = FlexDirection.ROW
        alignment = FlexAlignment.CENTER
        spacing = "var(--dwc-space-m)"

        spinner()
        paragraph("System update failed. Restoring to the previous state.")
        button("Stop", ButtonTheme.DANGER) {
          onClick { close() }
        }
      }
      open()
    }
  }
}
