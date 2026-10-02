package com.webforj.samples.views.lists.combobox

import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.field.numberField
import com.webforj.kotlin.dsl.component.list.comboBox
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("ComboBox Max Row")
class ComboBoxMaxRowKotlinView : Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      margin = "20px 0 0 20px"
      spacing = 20.px
      width = 200.px
      val comboBox = comboBox("States") {
        val states = arrayOf(
          "Alabama",
          "Alaska",
          "Arizona",
          "Arkansas",
          "California",
          "Colorado",
          "Connecticut",
          "Delaware",
          "Florida",
          "Georgia",
          "Hawaii",
          "Idaho",
          "Illinois",
          "Indiana",
          "Iowa",
          "Kansas",
          "Kentucky",
          "Louisiana",
          "Maine",
          "Maryland",
          "Massachusetts",
          "Michigan",
          "Minnesota",
          "Mississippi",
          "Missouri",
          "Montana",
          "Nebraska",
          "Nevada",
          "New Hampshire",
          "New Jersey",
          "New Mexico",
          "New York",
          "North Carolina",
          "North Dakota",
          "Ohio",
          "Oklahoma",
          "Oregon",
          "Pennsylvania",
          "Rhode Island",
          "South Carolina",
          "South Dakota",
          "Tennessee",
          "Texas",
          "Utah",
          "Vermont",
          "Virginia",
          "Washington",
          "West Virginia",
          "Wisconsin",
          "Wyoming"
        )
        insert(*states)
        selectIndex(0)
      }
      val numberField = numberField("Number of Rows") {
        step = 1.0
        max = 50.0
        min = 0.0
        isRequired = true
        value = comboBox.maxRowCount.toDouble()
      }
      val select = button("Apply", ButtonTheme.PRIMARY) {
        onClick {
          comboBox.maxRowCount = numberField.value.toInt()
        }
      }
      numberField.onValueChange {
        select.isEnabled = !numberField.isInvalid
      }
    }
  }
}
