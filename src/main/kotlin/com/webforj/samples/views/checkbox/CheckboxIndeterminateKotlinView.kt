package com.webforj.samples.views.checkbox

import com.webforj.component.Composite
import com.webforj.component.event.ToggleEvent
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.optioninput.CheckBox
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.kotlin.dsl.component.optioninput.checkBox
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Checkbox Indeterminate")
class CheckboxIndeterminateKotlinView: Composite<FlexLayout>() {
  private lateinit var indeterminate: CheckBox
  private lateinit var child1: CheckBox
  private lateinit var child2: CheckBox

  init {
      boundComponent.apply {
        direction = FlexDirection.COLUMN
        margin = "var(--dwc-space-l)"
        flexLayout {
          horizontal()
          indeterminate = checkBox("Parent") {
            isIndeterminate = true
            onToggle(::indeterminateToggle)
          }
        }
        flexLayout {
          horizontal()
          styles["margin-left"] = "30px"
          child1 = checkBox("Child 1", false) {
            onToggle(::onCheck)
          }
        }
        flexLayout {
          horizontal()
          styles["margin-left"] = "30px"
          child2 = checkBox("Child 2", true) {
            onToggle(::onCheck)
          }
        }
      }
  }

  private fun onCheck(e: ToggleEvent) {
    if (child1.isChecked && child2.isChecked) {
      indeterminate.isChecked = true
    } else {
      if (child1.isChecked || child2.isChecked) {
        indeterminate.isChecked = false
        indeterminate.isIndeterminate = true
      } else {
        indeterminate.isIndeterminate = false
        indeterminate.isChecked = false
      }
    }
  }

  private fun indeterminateToggle(e: ToggleEvent) {
    if (e.isToggled) {
      child1.isChecked = true
      child2.isChecked = true
    } else {
      child1.isChecked = false
      child2.isChecked = false
    }
  }
}
