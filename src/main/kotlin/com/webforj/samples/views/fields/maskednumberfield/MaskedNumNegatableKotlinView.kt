package com.webforj.samples.views.fields.maskednumberfield

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.field.maskedNumberField
import com.webforj.kotlin.dsl.component.optioninput.switch
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Masked Number Field with Negateable Option")
class MaskedNumNegatableKotlinView: Composite<FlexLayout>() {
  val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      justifyContent = FlexJustifyContent.START
      maxWidth = 300.px
      margin = "var(--dwc-space-m) auto"
      val field = maskedNumberField("Credits") {
        mask = "-$###,###,##0.00"
        isNegateable = true
        value = 123.0
      }
      switch("Negateable", true).onToggle {
        field.isNegateable = it.isToggled
      }
    }
  }
}