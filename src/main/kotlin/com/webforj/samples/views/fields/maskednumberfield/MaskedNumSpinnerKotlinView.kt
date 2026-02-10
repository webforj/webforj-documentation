package com.webforj.samples.views.fields.maskednumberfield

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.field.maskedNumberField
import com.webforj.kotlin.dsl.component.field.maskedNumberFieldSpinner
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Masked Number Field Spinner")
class MaskedNumSpinnerKotlinView: Composite<FlexLayout>() {
  val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      alignment = FlexAlignment.CENTER
      spacing = "var(--dwc-space-m)"
      margin = "var(--dwc-space-m)"
      maskedNumberFieldSpinner("Tip Percentage (%)") {
        step = 5.0
        value = 15.0
        width = 250.px
        min = 0.0
        max = 100.0
        mask = "###%"
        helperText = "<b>Min:</b> 0% <b>Max:</b> 100%"
        maxWidth = 300.px
      }
    }
  }
}