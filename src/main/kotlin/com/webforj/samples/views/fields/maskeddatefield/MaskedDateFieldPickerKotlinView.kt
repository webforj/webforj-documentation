package com.webforj.samples.views.fields.maskeddatefield

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.field.maskedDateField
import com.webforj.kotlin.dsl.component.field.picker
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import java.time.LocalDate

@Route
@FrameTitle("Masked Date Field with Picker")
class MaskedDateFieldPickerKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      alignment = FlexAlignment.CENTER
      margin = "var(--dwc-space-m)"
      maskedDateField {
        mask = "%Dz/%Mz/%Yl"
        value = LocalDate.now()
        helperText = "Click the icon to open the date picker."
        isAllowCustomValue = false
        picker {
          isIconVisible = true
          isAutoOpen = true
          isShowWeeks = true
        }
        whenAttached().thenAccept { picker.open() }
      }
    }
  }
}