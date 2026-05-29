package com.webforj.samples.views.tabbedpane

import com.webforj.annotation.InlineStyleSheet
import com.webforj.component.Composite
import com.webforj.component.Expanse
import com.webforj.component.Theme
import com.webforj.component.html.elements.Div
import com.webforj.component.tabbedpane.TabbedPane
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.list.choiceBox
import com.webforj.kotlin.dsl.component.list.items
import com.webforj.kotlin.dsl.component.tabbedpane.prefixSlot
import com.webforj.kotlin.dsl.component.tabbedpane.tab
import com.webforj.kotlin.dsl.component.tabbedpane.tabbedPane
import com.webforj.kotlin.extension.classNames
import com.webforj.kotlin.extension.plus
import com.webforj.kotlin.extension.px
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
@FrameTitle("Tabbed Pane Expanses and Themes")
class TabbedPaneExpanseThemeKotlinView: Composite<Div>() {
  private val self = boundComponent
  private val pane: TabbedPane

  init {
    self.apply {
      classNames + "window"
      flexLayout {
        spacing = 50.px
        choiceBox("Themes") {
          width = 200.px
          items(*Theme.entries
            .map { it to it.toString() }
            .toTypedArray())
          selectIndex(1)
          onSelect { pane.theme = it.selectedItem.key as Theme }
        }
        choiceBox("Expanses") {
          width = 200.px
          items(*Expanse.entries
            .map { it to it.toString() }
            .toTypedArray())
          selectIndex(5)
          onSelect { pane.expanse = it.selectedItem.key as Expanse }
        }
      }
      pane = tabbedPane {
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
      }
    }
  }
}
