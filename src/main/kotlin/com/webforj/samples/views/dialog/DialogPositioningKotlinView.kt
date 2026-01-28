package com.webforj.samples.views.dialog

import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.dialog.dialog
import com.webforj.kotlin.dsl.component.dialog.addToHeader
import com.webforj.kotlin.dsl.component.dialog.addToFooter
import com.webforj.kotlin.dsl.component.field.numberField
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Dialog Positioning")
class DialogPositioningKotlinView: Composite<FlexLayout>() {

  init {
      boundComponent.apply {
        dialog {
          addToHeader { div("Positioning") }
          val xPos = numberField("X Pixels:") {
            min = 0.0
          }
          val yPos = numberField("Y Pixels:") {
            min = 0.0
          }
          addToFooter {
            button("Set Dialog Position", ButtonTheme.PRIMARY) {
              minHeight = "60px"
              onClick {
                val xValue = xPos.value
                val yValue = yPos.value
                if (xValue != null && yValue != null && xValue >= 0 && yValue >= 0) {
                  this@dialog.posx = "${xValue}px"
                  this@dialog.posy = "${yValue}px"
                }
              }
            }
          }
          isAutoFocus = true
          open()
          setCloseable(false)
          maxWidth = "200px"
        }
      }
  }
}
