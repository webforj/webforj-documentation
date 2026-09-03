package com.webforj.samples.views.tabbedpane

import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.vertical
import com.webforj.kotlin.dsl.component.tabbedpane.prefixSlot
import com.webforj.kotlin.dsl.component.tabbedpane.tab
import com.webforj.kotlin.dsl.component.tabbedpane.tabbedPane
import com.webforj.kotlin.extension.percent
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.kotlin.extension.vh
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Tabbed Pane Segment")
class TabbedPaneSegmentKotlinView : Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      flexLayout {
        vertical()
        justifyContent = FlexJustifyContent.CENTER
        alignment = FlexAlignment.CENTER
        styles["width"] = 100.percent
        styles["min-height"] = 100.vh

        tabbedPane {
          theme = Theme.PRIMARY
          maxWidth = "max-content"
          isSegment = true

          tab("Dashboard") {
            prefixSlot { tablerIcon("dashboard") }
          }
          tab("Orders") {
            prefixSlot { tablerIcon("shopping-cart") }
          }
          tab("Customers") {
            prefixSlot { tablerIcon("users") }
          }
        }
      }
    }
  }
}
