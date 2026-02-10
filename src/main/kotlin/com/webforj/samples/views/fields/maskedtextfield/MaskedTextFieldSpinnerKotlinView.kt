package com.webforj.samples.views.fields.maskedtextfield

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.field.maskedTextFieldSpinner
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Masked Text Field Spinner")
class MaskedTextFieldSpinnerKotlinView: Composite<FlexLayout>() {
  val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      alignment = FlexAlignment.CENTER
      margin = "var(--dwc-space-m)"
      maskedTextFieldSpinner("Project Code:") {
        options = listOf(
          "PRJ001", "PRJ002", "PRJ003", "PRJ004"
        )
        mask = "AAA-000"
        value = "PRJ-002"
        helperText = "Select or spin through project codes"
      }
    }
  }
}