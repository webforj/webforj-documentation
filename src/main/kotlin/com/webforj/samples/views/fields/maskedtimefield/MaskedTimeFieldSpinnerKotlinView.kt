package com.webforj.samples.views.fields.maskedtimefield

import com.webforj.component.Composite
import com.webforj.component.field.MaskedTimeFieldSpinner
import com.webforj.component.field.MaskedTimeFieldSpinner.SpinField
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.field.maskedTimeFieldSpinner
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import java.time.LocalTime

@Route
@FrameTitle("Masked Time Field Spinner")
class MaskedTimeFieldSpinnerKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      alignment = FlexAlignment.CENTER
      spacing = "var(--dwc-space-m)"
      margin = "var(--dwc-space-m)";
      maskedTimeFieldSpinner("Available Time Slots") {
        spinField = SpinField.MINUTE
        mask = "%hz:%mz %p"
        value = LocalTime.of(9, 0)
        min = LocalTime.of(9, 0)
        max = LocalTime.of(17, 0)
        helperText = "<b>Min:</b> 09:00 AM, <b>Max:</b> 05:00 PM. Use the spinner to select a time."
        placeholder = "hh:mm AM/PM"
        isAllowCustomValue = false;
        whenAttached().thenAccept { picker.open() }
      }
    }
  }
}