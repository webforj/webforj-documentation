package com.webforj.samples.views.lists.choicebox

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.kotlin.dsl.component.list.choiceBox
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@StyleSheet("ws://css/lists/combobox/comboBoxDropDownType.css")
@Route
@FrameTitle("Choicebox Dropdown Type")
class ChoiceboxDropdownTypeKotlinView: Composite<Div>() {

  init {
      boundComponent.apply {
        addClassName("frame")
        choiceBox("Department") {
          dropdownType = "demo-dropdown-type"
          val categories = arrayOf(
            "Electronics", "Health and Beauty", "Fashion", "Kitchen", "Furniture",
            "Pet Supplies", "Toys and Games"
          )
          insert(*categories)
          selectIndex(0)
        }
      }
  }
}
