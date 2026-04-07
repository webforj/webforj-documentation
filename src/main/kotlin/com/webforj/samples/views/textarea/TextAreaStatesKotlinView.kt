package com.webforj.samples.views.textarea

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.field.textArea
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("TextArea States")
class TextAreaStatesKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      justifyContent = FlexJustifyContent.CENTER
      alignment = FlexAlignment.CENTER
      spacing = "var(--dwc-space-m)"
      styles["padding"] = "var(--dwc-space-m)"
      margin = "50px auto"
      maxWidth = 600.px
      textArea("Read-Only", "Value") {
        isReadOnly = true
      }
      textArea("Disabled", "Value") {
        isEnabled = false
      }
    }
  }
}
