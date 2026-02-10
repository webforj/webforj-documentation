package com.webforj.samples.views.fields.maskedtextfield

import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.field.maskedTextField
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Masked Text Field with Restore Value")
class MaskedTextFieldRestoreKotlinView: Composite<FlexLayout>() {
  val self = boundComponent

  init {
    self.apply {
      alignment = FlexAlignment.CENTER
      justifyContent = FlexJustifyContent.CENTER
      margin = "var(--dwc-space-m) auto"
      val usernameField = maskedTextField("Postal Code", "85001 PHX", "ex: 85001") {
        width = 250.px
        restoreValue = "85001 PHX"
        helperText = "Enter ZIP Code in format: 85001 PHX"
        pattern = "[0-9]{5} [A-Z]{3}"
      }
      button("Restore", ButtonTheme.PRIMARY).onClick {
        usernameField.restoreValue()
      }
    }
  }
}