package com.webforj.samples.views.columnslayout

import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.html.elements.Div
import com.webforj.component.layout.columnslayout.ColumnsLayout
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.field.dateField
import com.webforj.kotlin.dsl.component.field.textArea
import com.webforj.kotlin.dsl.component.field.textField
import com.webforj.kotlin.dsl.component.layout.columnslayout.columnsLayout
import com.webforj.kotlin.dsl.component.optioninput.checkBox
import com.webforj.kotlin.extension.dvh
import com.webforj.kotlin.extension.em
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Columns Layout Alignment")
class ColumnsLayoutAlignmentKotlinView: Composite<Div>() {
  private val self = boundComponent

  init {
    self.apply {
      maxWidth = 60.em
      styles["margin"] = "0 auto"
      styles["overflow"] = "auto"
      styles["height"] = 100.dvh
      columnsLayout {
        styles["padding"] = "var(--dwc-space-xl)"
        textField("First Name")
        textField("Last Name")
        textField("Email")
        dateField("Date of Birth")
        setSpan(textArea("Bio"), 2)
        setSpan(checkBox("I agree to the terms and conditions"), 2)
        val submit = button("Submit", ButtonTheme.PRIMARY)
        setColumn(submit, 2)
        setHorizontalAlignment(submit, ColumnsLayout.Alignment.END)
      }
    }
  }
}
