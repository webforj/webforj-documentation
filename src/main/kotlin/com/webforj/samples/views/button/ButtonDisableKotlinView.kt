package com.webforj.samples.views.button

import com.webforj.component.Composite
import com.webforj.component.button.Button
import com.webforj.component.button.ButtonTheme
import com.webforj.component.field.TextField
import com.webforj.component.field.TextField.Type
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.field.textField
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Button Disable")
class ButtonDisableKotlinView: Composite<FlexLayout>() {
  val submit: Button

  init {
      boundComponent.apply {
        alignment = FlexAlignment.END
        margin = "var(--dwc-space-l)"
        spacing = "var(--dwc-space-xl)"
        styles["flex-wrap"] = "wrap"
        width = "100%"
        textField("Enter an email", type = Type.EMAIL) {
          onModify {
            submit.isEnabled = "@" in it.text
          }
        }
        submit = button("Submit", ButtonTheme.PRIMARY) {
          isEnabled = false
        }
      }
  }
}
