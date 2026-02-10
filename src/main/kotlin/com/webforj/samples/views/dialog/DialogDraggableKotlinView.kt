package com.webforj.samples.views.dialog

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.dialog.dialog
import com.webforj.kotlin.dsl.component.dialog.header
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Dialog Dragging")
class DialogDraggableKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
      self.apply {
        dialog {
          header { div("Snapping") }
          div("This dialog will snap then dragged within 100px of the edge of the display")
          open()
          isSnapToEdge = true
          snapThreshold = 100
        }
      }
  }
}
