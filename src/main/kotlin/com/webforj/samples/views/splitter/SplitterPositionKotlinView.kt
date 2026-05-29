package com.webforj.samples.views.splitter

import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.kotlin.dsl.component.splitter.detailSlot
import com.webforj.kotlin.dsl.component.splitter.masterSlot
import com.webforj.kotlin.dsl.component.splitter.splitter
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import com.webforj.samples.components.SplitterBox

@Route
@FrameTitle("Splitter Position")
class SplitterPositionKotlinView: Composite<Div>() {
  private val self = boundComponent

  init {
    self.apply {
      splitter {
        positionRelative = 75.0
        masterSlot {
          splitterBox("Master", SplitterBox.Theme.INFO)
        }
        detailSlot {
          splitterBox("Detail", SplitterBox.Theme.SUCCESS)
        }
      }
    }
  }
}
