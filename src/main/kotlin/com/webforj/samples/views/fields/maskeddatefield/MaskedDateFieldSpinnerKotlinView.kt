package com.webforj.samples.views.fields.maskeddatefield

import com.webforj.component.Composite
import com.webforj.component.field.MaskedDateFieldSpinner
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.field.maskedDateFieldSpinner
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import java.time.LocalDate

@Route
@FrameTitle("Masked Date Field Spinner")
class MaskedDateFieldSpinnerKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      alignment = FlexAlignment.CENTER
      spacing = "var(--dwc-space-m)"
      margin = "var(--dwc-space-m)"
      maskedDateFieldSpinner("Available Appointments") {
        spinField = MaskedDateFieldSpinner.SpinField.DAY
        mask = "%Dz/%Mz%Yl"
        value = LocalDate.now()
        min = LocalDate.now()
        max = LocalDate.now().plusMonths(6)
        helperText = "<b>Min:</b> today, <b>Max:</b> 6 months from now. Use the spinner to select a date."
        placeholder = "DD/MM/YYYY"
        isAllowCustomValue = false
        whenAttached().thenAccept { picker.open() }
      }
    }
  }
}