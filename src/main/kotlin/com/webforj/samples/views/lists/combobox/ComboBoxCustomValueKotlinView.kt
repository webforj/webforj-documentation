package com.webforj.samples.views.lists.combobox

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.list.comboBox
import com.webforj.kotlin.dsl.component.optioninput.switch
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("ComboBox Custom Value")
class ComboBoxCustomValueKotlinView : Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      margin = "20px 0 0 20px"
      spacing = 20.px
      width = 200.px
      val customValue = comboBox("Department") {
        val categories = arrayOf(
          "Electronics", "Health and Beauty", "Fashion", "Kitchen", "Furniture",
          "Pet Supplies", "Toys and Games"
        )
        insert(*categories)
        isAllowCustomValue = false
      }
      switch("Toggle Custom Value") {
        onToggle {
          customValue.isAllowCustomValue = !customValue.isAllowCustomValue
        }
      }
    }
  }
}
