package com.webforj.samples.views.fields.maskeddatefield

import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.field.maskedDateField
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import java.time.LocalDate

@Route
@FrameTitle("Masked Date Field with Restore")
class MaskedDateFieldRestoreKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      alignment = FlexAlignment.CENTER
      justifyContent = FlexJustifyContent.CENTER
      margin = "var(--dwc-space-m) auto"
      val eventField = maskedDateField("Meeting Date:") {
        mask = "%Yl-%Mz-%Dz"
        value = LocalDate.now()
        restoreValue = LocalDate.now().minusDays(1)
        helperText = "Press <kbd>ESC</kbd> to restore the value to yesterday."
        maxWidth = "300px"
        picker.isIconVisible = false
      }
      button("Reset value", ButtonTheme.PRIMARY).onClick {
        eventField.restoreValue()
      }
    }
  }
}