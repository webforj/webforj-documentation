package com.webforj.samples.views.lists.choicebox

import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.field.numberField
import com.webforj.kotlin.dsl.component.list.choiceBox
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Choicebox Max Rows")
class ChoiceboxMaxRowKotlinView: Composite<FlexLayout>() {

  init {
      boundComponent.apply {
        direction = FlexDirection.COLUMN
        margin = "20px 0 0 20px"
        spacing = "20px"
        setWidth(200f)
        val choiceBox = choiceBox("States") {
          val states = arrayOf("Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware",
            "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana",
            "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska",
            "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio",
            "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas",
            "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"
          )
          insert(*states)
          selectIndex(0)
        }
        val numberField = numberField("Number of Rows") {
          step = 1.0
          max = 50.0
          min = 0.0
          isRequired = true
          value = choiceBox.maxRowCount.toDouble()
        }
        button("Apply", ButtonTheme.PRIMARY) {
          numberField.onValueChange {
            isEnabled = !numberField.isInvalid
          }
          onClick {
            choiceBox.takeUnless { numberField.isInvalid }
              ?.let {
                choiceBox.maxRowCount = numberField.value.toInt()
              } ?: numberField.focus()
          }
        }
      }
  }
}
