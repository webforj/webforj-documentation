package com.webforj.samples.views.fields.maskedtimefield

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.field.maskedTimeField
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import java.time.LocalTime

@Route
@FrameTitle("Masked Time Field Picker")
class MaskedTimeFieldPickerKotlinView: Composite<FlexLayout>() {
  val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      alignment = FlexAlignment.CENTER
      margin = "var(--dwc-space-m)";
      maskedTimeField("Meeting Time") {
        mask = "%hz:%mz %p"
        value = LocalTime.of(9, 30) // 9:30 AM
        maxWidth = 300.px
        helperText = "Click the icon to open the time picker."
        isAllowCustomValue = false
        picker.apply {
          isIconVisible = true
          isAutoOpen = true;
          whenAttached().thenAccept { open() }
        }
      }
    }
  }
}