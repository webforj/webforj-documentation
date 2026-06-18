package com.webforj.samples.views.button

import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.field.TextField.Type
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.field.textField
import com.webforj.kotlin.extension.percent
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Button Disable")
class ButtonDisableKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
      self.apply {
        alignment = FlexAlignment.END
        margin = "var(--dwc-space-l)"
        spacing = "var(--dwc-space-xl)"
        styles["flex-wrap"] = "wrap"
        width = 100.percent

        val email = textField("Enter an email", type = Type.EMAIL)
        button("Submit", ButtonTheme.PRIMARY) {
          isEnabled = false
          email.onModify {
            isEnabled = "@" in it.text
          }
        }
      }
  }
}
