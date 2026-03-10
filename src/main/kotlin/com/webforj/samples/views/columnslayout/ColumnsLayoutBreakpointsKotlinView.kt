package com.webforj.samples.views.columnslayout

import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.layout.columnslayout.ColumnsLayout
import com.webforj.kotlin.dsl.component.field.passwordField
import com.webforj.kotlin.dsl.component.field.textField
import com.webforj.kotlin.dsl.component.layout.columnslayout.columnsLayout
import com.webforj.kotlin.extension.dvh
import com.webforj.kotlin.extension.em
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Columns Layout Breakpoints")
class ColumnsLayoutBreakpointsKotlinView: Composite<Div>() {
  private val self = boundComponent

  init {
    self.apply {
      maxWidth = 60.em
      styles["margin"] = "0 auto"
      styles["overflow"] = "auto"
      styles["height"] = 100.dvh
      columnsLayout(
        ColumnsLayout.Breakpoint("default", 0, 1),
        ColumnsLayout.Breakpoint("small", 20.em, 1),
        ColumnsLayout.Breakpoint("medium", 40.em, 2),
        ColumnsLayout.Breakpoint("large", 60.em, 3),
      ) {
        styles["padding"] = "var(--dwc-space-xl)"
        textField("First Name")
        textField("Last Name")
        textField("Email")
        passwordField("Password")
        passwordField("Confirm Password")
      }
    }
  }
}