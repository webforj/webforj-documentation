package com.webforj.samples.views.lists.combobox

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.kotlin.dsl.component.list.comboBox
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@StyleSheet("ws://css/lists/combobox/comboBoxDropDownType.css")
@Route
@FrameTitle("ComboBox Dropdown Type")
class ComboBoxDropdownTypeKotlinView: Composite<Div>() {

  init {
      boundComponent.apply {
        addClassName("frame")
        comboBox("Department") {
          val categories = arrayOf(
            "Electronics", "Health and Beauty", "Fashion", "Kitchen", "Furniture",
            "Pet Supplies", "Toys and Games"
          )
          dropdownType = "demo-dropdown-type"
          insert(*categories)
          selectIndex(0)
        }
      }
  }
}
