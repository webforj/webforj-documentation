package com.webforj.samples.views.fields.maskedtimefield

import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.field.maskedTimeField
import com.webforj.kotlin.dsl.component.field.picker
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import java.time.LocalTime

@Route
@FrameTitle("Masked Time Field Restore")
class MaskedTimeFieldRestoreKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      alignment = FlexAlignment.CENTER
      justifyContent = FlexJustifyContent.CENTER
      margin = "var(--dwc-space-m) auto"
      val eventField = maskedTimeField("Meeting Time:") {
        mask = "%hz:%mz %p"
        value = LocalTime.of(15, 30) // 3:30 PM
        restoreValue = LocalTime.of(14, 0) // 2:00 PM
        helperText = "Press <kbd>ESC</kbd> to restore the value to 2:00 PM."
        maxWidth = 300.px
        picker {
          isIconVisible = false
        }
      }
      button("Reset Value", ButtonTheme.PRIMARY).onClick {
        eventField.restoreValue()
      }
    }
  }
}