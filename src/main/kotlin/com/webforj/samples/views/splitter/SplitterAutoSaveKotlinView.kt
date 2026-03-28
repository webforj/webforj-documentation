package com.webforj.samples.views.splitter

import com.webforj.Page
import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.splitter.Splitter
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.element.element
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.kotlin.dsl.component.layout.flexlayout.vertical
import com.webforj.kotlin.dsl.component.splitter.detailSlot
import com.webforj.kotlin.dsl.component.splitter.masterSlot
import com.webforj.kotlin.dsl.component.splitter.splitter
import com.webforj.kotlin.extension.attributes
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import com.webforj.samples.components.SplitterBox

@Route
@FrameTitle("Splitter Auto Save")
class SplitterAutoSaveKotlinView: Composite<Div>() {
  private val self = boundComponent
  private val splitters = arrayListOf<Splitter>()

  init {
    self.apply {
      flexLayout {
        styles["padding"] = "var(--dwc-space-m)"
        vertical()
        setItemAlignment(FlexAlignment.END, flexLayout {
          horizontal()
          element("dwc-icon-button") {
            attributes["name"] = "reload"
            attributes["theme"] = "primary"
            addEventListener("click") {
              Page.getCurrent().reload()
            }
          }
          button("Clear State") {
            onClick {
              splitters.forEach { it.cleanState() }
              Page.getCurrent().reload()
            }
          }
        })
        splitters.add(splitter("masterSplitter") {
          orientation = Splitter.Orientation.VERTICAL
          isAutosave = true
          masterSlot {
            splitter("topSplitter") {
              positionRelative = 30.0
              isAutosave = true
              masterSlot {
                splitterBox("Top Left", SplitterBox.Theme.PRIMARY)
              }
              detailSlot {
                splitterBox("Top Right", SplitterBox.Theme.INFO)
              }
            }.also { splitters.add(it) }
          }
          detailSlot {
            splitter("bottomSplitter") {
              positionRelative = 70.0
              isAutosave = true
              masterSlot {
                splitterBox("Bottom Left", SplitterBox.Theme.SUCCESS)
              }
              detailSlot {
                splitterBox("Bottom Right", SplitterBox.Theme.WARNING)
              }
            }.also { splitters.add(it) }
          }
        })
      }
    }
  }
}
