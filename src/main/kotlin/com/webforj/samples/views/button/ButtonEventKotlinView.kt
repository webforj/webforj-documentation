package com.webforj.samples.views.button

import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.vertical
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Button Event")
class ButtonEventKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent
  private var counter = 0

  init {
      self.apply {
        direction = FlexDirection.ROW
        spacing = "var(--dwc-space-l)"
        padding = "var(--dwc-space-l)"
        val button = button("Click Me!") {
          width = "150px"
        }
        flexLayout {
          vertical()
          spacing = "0px"
          val text = div("Current Counter: 0")
          val payload = div("Event Payload: null")
          button.onClick {
            text.text = "Current Counter: ${++counter}"
            payload.text = "Event Payload: ${it.data}"
          }
        }
      }
  }
}
