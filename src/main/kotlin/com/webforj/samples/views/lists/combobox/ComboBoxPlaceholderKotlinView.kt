package com.webforj.samples.views.lists.combobox

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.list.comboBox
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("ComboBox Placeholders")
class ComboBoxPlaceholderKotlinView: Composite<FlexLayout>() {

  init {
      boundComponent.apply {
        margin = "20px 0 0 20px"
        spacing = "20px"
        comboBox {
          val categories = arrayOf(
            "Electronics", "Health and Beauty", "Fashion", "Kitchen", "Furniture",
            "Pet Supplies", "Toys and Games"
          )
          placeholder = "Example Placeholder"
          insert(*categories)
        }
      }
  }
}
