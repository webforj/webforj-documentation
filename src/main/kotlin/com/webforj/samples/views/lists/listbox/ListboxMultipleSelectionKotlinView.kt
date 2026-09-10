package com.webforj.samples.views.lists.listbox

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.list.MultipleSelectableList.SelectionMode
import com.webforj.kotlin.dsl.component.list.listBox
import com.webforj.kotlin.dsl.component.optioninput.switch
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Listbox Multiple Selection")
class ListboxMultipleSelectionKotlinView : Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      width = 200.px
      margin = "20px 0 0 20px"
      spacing = 20.px
      val listBox = listBox("Select Department(s)") {
        val departments =
          arrayOf("Marketing and Sales", "IT Support", "Management and Admin", "Finance and HR")
        insert(*departments)
      }
      switch("Multiple Selection") {
        onToggle {
          listBox.selectionMode = if (it.isToggled) {
            SelectionMode.MULTIPLE
          } else {
            SelectionMode.SINGLE
          }
        }
      }
    }
  }
}
