package com.webforj.samples.views.fields.numberfield

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.field.numberField
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Number Field Demo")
class NumberFieldKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      margin = "var(--dwc-space-m)"
      numberField("Quantity:", placeholder = "Enter a number...") {
        width = 200.px
      }
    }
  }
}
