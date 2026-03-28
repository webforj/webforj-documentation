package com.webforj.samples.views.splitter

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.splitter.detailSlot
import com.webforj.kotlin.dsl.component.splitter.masterSlot
import com.webforj.kotlin.dsl.component.splitter.splitter
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import com.webforj.samples.components.SplitterBox

@Route
@FrameTitle("Splitter Basics")
class SplitterBasicKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      splitter {
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
