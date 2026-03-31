package com.webforj.samples.views.radiobutton

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.layout.flexlayout.FlexWrap
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.vertical
import com.webforj.kotlin.dsl.component.optioninput.radioButton
import com.webforj.kotlin.dsl.component.optioninput.radioButtonGroup
import com.webforj.kotlin.extension.classNames
import com.webforj.kotlin.extension.em
import com.webforj.kotlin.extension.plus
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Radio Button Group")
class RadioButtonGroupKotlinView: Composite<FlexLayout>() {

  init {
      boundComponent.apply {
        direction = FlexDirection.COLUMN
        spacing = 1.em
        margin = 20.px
        flexLayout {
          vertical()
          wrap = FlexWrap.WRAP
          classNames + "layout"
          radioButtonGroup {
            radioButton("Strongly Disagree")
            radioButton("Disagree")
            radioButton("Neutral")
            radioButton("Agree")
            radioButton("Strongly Agree")
          }
        }
      }
  }
}
