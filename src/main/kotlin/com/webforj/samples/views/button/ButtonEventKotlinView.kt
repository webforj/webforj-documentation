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
  private var counter = 0
  private lateinit var text: Div
  private lateinit var payload: Div

  init {
      boundComponent.apply {
        direction = FlexDirection.ROW
        spacing = "var(--dwc-space-l)"
        padding = "var(--dwc-space-l)"
        button("Click Me!") {
          width = "150px"
          onClick {
            this@ButtonEventKotlinView.text.text = "Current Counter: ${++counter}"
            payload.text = "Event payload: ${it.data}"
          }
        }
        flexLayout {
          vertical()
          spacing = "0px"
          this@ButtonEventKotlinView.text = div("Current Counter: 0")
          payload = div("Event Payload: null")
        }
      }
  }
}
