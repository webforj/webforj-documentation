package com.webforj.samples.views.fields.maskedtimefield

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.field.maskedTimeField
import com.webforj.kotlin.dsl.component.field.picker
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import java.time.LocalTime

@Route
@FrameTitle("Masked Time Field")
class MaskedTimeFieldKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      alignment = FlexAlignment.CENTER
      margin = "var(--dwc-space-m)";
      maskedTimeField("Meeting Time") {
        mask = "%h:%mz %p"
        value = LocalTime.now()
        maxWidth = "300px"
        helperText = "Meeting time is formatted as %h:%mz %p."
        picker {
          isIconVisible = false;
        }
      }
    }
  }
}