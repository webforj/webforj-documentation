package com.webforj.samples.views.fields.datetimefield

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.field.dateTimeField
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Date Time Field Demo")
class DateTimeFieldKotlinView: Composite<FlexLayout>() {

  init {
    boundComponent.apply {
      margin = "var(--dwc-space-m)"
      dateTimeField("Departure Date and Time:") {
        width = "200px"
      }
    }
  }
}