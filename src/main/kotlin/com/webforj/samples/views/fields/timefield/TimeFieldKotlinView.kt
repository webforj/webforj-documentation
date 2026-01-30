package com.webforj.samples.views.fields.timefield

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.field.timeField
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import java.time.LocalTime

@Route
@FrameTitle("Time Field Demo")
class TimeFieldKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      margin = "var(--dwc-space-m)"
      timeField("Set Reminder", LocalTime.now())
    }
  }
}