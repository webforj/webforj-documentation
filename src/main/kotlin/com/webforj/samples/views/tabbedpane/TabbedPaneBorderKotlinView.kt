package com.webforj.samples.views.tabbedpane

import com.webforj.annotation.InlineStyleSheet
import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.kotlin.dsl.component.optioninput.switch
import com.webforj.kotlin.dsl.component.tabbedpane.prefixSlot
import com.webforj.kotlin.dsl.component.tabbedpane.tab
import com.webforj.kotlin.dsl.component.tabbedpane.tabbedPane
import com.webforj.kotlin.extension.classNames
import com.webforj.kotlin.extension.plus
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@InlineStyleSheet(
  """
  .window {
    display: flex;
    flex-direction: column;
    gap: 50px;
    margin: 20px;
  }

"""
)
@Route
@FrameTitle("Tabbed Pane Border")
class TabbedPaneBorderKotlinView : Composite<Div>() {
  private val self = boundComponent

  init {
    self.apply {
      classNames + "window"
      val border = switch("Hide Border")
      val active = switch("Hide Active Indicator")
      tabbedPane {
        tab("Dashboard") {
          prefixSlot { tablerIcon("dashboard") }
        }
        tab("Orders") {
          prefixSlot { tablerIcon("shopping-cart") }
        }
        tab("Customers") {
          prefixSlot { tablerIcon("users") }
        }
        tab("Products") {
          prefixSlot { tablerIcon("box") }
        }
        tab("Documents") {
          prefixSlot { tablerIcon("files") }
        }
        border.onToggle { isBorderless = !isBorderless }
        active.onToggle { isHideActiveIndicator = !isHideActiveIndicator }
      }
    }
  }
}
