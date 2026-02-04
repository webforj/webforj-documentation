package com.webforj.samples.views.button

import com.webforj.component.Composite
import com.webforj.component.Expanse
import com.webforj.component.button.Button
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.list.ListItem
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.list.choiceBox
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Button Expanses")
class ButtonExpansesKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      spacing = "var(--dwc-space-l)"
      margin = "var(--dwc-space-l)"

      val categories = Expanse.entries.reversed()
        .filter { it != Expanse.NONE }
        .map { ListItem(it, it.name) }

      val expanses = choiceBox {
        insert(categories)
        selectIndex(0)
        width = "100px"
      }
      button("None") {
        expanses.onSelect {
          expanse = it.selectedItem.key as Expanse
          text = it.selectedItem.text
        }
      }

    }
  }
}
