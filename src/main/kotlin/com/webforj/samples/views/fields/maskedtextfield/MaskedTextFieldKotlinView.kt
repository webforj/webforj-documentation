package com.webforj.samples.views.fields.maskedtextfield

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.field.maskedTextField
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Masked Text Field")
class MaskedTextFieldKotlinView: Composite<FlexLayout>() {
  val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      justifyContent = FlexJustifyContent.START
      alignment = FlexAlignment.CENTER
      spacing = "var(--dwc-space-m)"
      margin = "var(--dwc-space-m)"
      maskedTextField("Record Code", placeholder = "NE-24-0934") {
        mask = "AA-00-0000"
        value = "NE240934"
        helperText = "Mask: AA-00-0000 - for example: NE-24-0934"
        width = 300.px
      }
      maskedTextField("Coupon Code", placeholder = "ZZZZ-0000") {
        mask = "ZZZZ-0000"
        value = "SAVE2025"
        helperText = "Mask: ZZZZ-0000 - for example: SAVE-2025"
        width = 300.px
      }
    }
  }
}