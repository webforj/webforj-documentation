package com.webforj.samples.views.fields.maskednumberfield

import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.field.maskedNumberField
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Masked Number Field with Restore Value")
class MaskedNumRestoreKotlinView: Composite<FlexLayout>() {
  val self = boundComponent

  init {
    self.apply {
      alignment = FlexAlignment.CENTER
      justifyContent = FlexJustifyContent.CENTER
      margin = "var(--dwc-space-m) auto"
      val field = maskedNumberField("Project Budget:") {
        mask = "$###,###,##0.00"
        value = 1234567.0
        restoreValue = 1234567.0
        helperText = "Press <kbd>ESC</kbd> to restore the value to default."
        maxWidth = 300.px
      }
      button("Reset value", ButtonTheme.PRIMARY).onClick {
        field.restoreValue()
      }
    }
  }
}