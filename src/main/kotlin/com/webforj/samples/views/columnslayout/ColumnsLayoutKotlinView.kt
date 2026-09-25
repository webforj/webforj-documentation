package com.webforj.samples.views.columnslayout

import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.html.elements.Div
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.field.passwordField
import com.webforj.kotlin.dsl.component.field.textField
import com.webforj.kotlin.dsl.component.layout.columnslayout.columnsLayout
import com.webforj.kotlin.extension.dvh
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Columns Layout")
class ColumnsLayoutKotlinView: Composite<Div>() {
  private val self = boundComponent

  init {
    self.apply {
      maxWidth = 600.px
      styles["margin"] = "0 auto"
      styles["overflow"] = "auto"
      styles["height"] = 100.dvh
      columnsLayout {
        styles["padding"] = "var(--dwc-space-xl)"
        textField("First Name")
        textField("Last Name")
        setSpan(textField("Email"), 2)
        passwordField("Password")
        passwordField("Confirm Password")
        val button = button("Submit", ButtonTheme.PRIMARY) {
          styles["margin-top"] = "var(--dwc-space-l)"
        }
        setSpan(button, 2)
      }
    }
  }
}