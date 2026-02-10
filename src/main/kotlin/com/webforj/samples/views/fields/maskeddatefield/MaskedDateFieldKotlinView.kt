package com.webforj.samples.views.fields.maskeddatefield

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.field.maskedDateField
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import java.time.LocalDate

@Route
@FrameTitle("Masked Date Field")
class MaskedDateFieldKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      alignment = FlexAlignment.CENTER
      margin = "var(--dwc-space-m)"
      maskedDateField("Meeting Date") {
        mask = "%Mz/%Dz/%Yz"
        value = LocalDate.now()
        maxWidth = "300px"
        helperText = "Meeting Date is formatted as %Mz/%Dz/%Yz."
        picker.apply {
          isIconVisible = false
        }
      }
    }
  }
}