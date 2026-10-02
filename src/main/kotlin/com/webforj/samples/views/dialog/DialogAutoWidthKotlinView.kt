package com.webforj.samples.views.dialog

import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.dialog.dialog
import com.webforj.kotlin.dsl.component.dialog.footerSlot
import com.webforj.kotlin.dsl.component.dialog.headerSlot
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.html.elements.paragraph
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.kotlin.dsl.component.layout.flexlayout.vertical
import com.webforj.kotlin.dsl.component.optioninput.switch
import com.webforj.kotlin.extension.ch
import com.webforj.kotlin.extension.percent
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Dialog Auto Width")
class DialogAutoWidthKotlinView : Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      dialog {
        isCloseable = false
        isAutoWidth = true

        headerSlot { div("Save changes") }

        flexLayout {
          vertical()
          padding = 20.px

          paragraph("Toggle Auto width to resize this dialog to fit its content.") {
            styles["max-width"] = 26.ch
          }
        }

        footerSlot {
          flexLayout {
            horizontal()
            justifyContent = FlexJustifyContent.BETWEEN
            alignment = FlexAlignment.CENTER
            styles["width"] = 100.percent

            switch("Auto width", checked = true).apply {
              onToggle { this@dialog.isAutoWidth = it.isToggled }
            }

            button("Save", ButtonTheme.PRIMARY)
          }
        }

        open()
      }
    }
  }
}
