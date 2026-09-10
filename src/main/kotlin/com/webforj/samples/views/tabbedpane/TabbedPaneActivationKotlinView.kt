package com.webforj.samples.views.tabbedpane

import com.webforj.annotation.InlineStyleSheet
import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.tabbedpane.TabbedPane
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
@FrameTitle("Tabbed Pane Activation")
class TabbedPaneActivationKotlinView: Composite<Div>() {
  private val self = boundComponent

  init {
    self.apply {
      classNames + "window"
      val activationSwitch = switch("Manual")
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
        activationSwitch.onCheck {
          activationSwitch.text = "Automatic"
          activation = TabbedPane.Activation.AUTO
        }
        activationSwitch.onUncheck {
          activationSwitch.text = "Manual"
          activation = TabbedPane.Activation.MANUAL
        }
      }
    }
  }
}
