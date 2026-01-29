package com.webforj.samples.views.radiobutton

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.layout.flexlayout.FlexWrap
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.vertical
import com.webforj.kotlin.dsl.component.optioninput.radioButton
import com.webforj.kotlin.dsl.component.optioninput.radioButtonGroup
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Radio Button Group")
class RadioButtonGroupKotlinView: Composite<FlexLayout>() {

  init {
      boundComponent.apply {
        direction = FlexDirection.COLUMN
        spacing = "1em"
        margin = "20px"
        flexLayout {
          vertical()
          wrap = FlexWrap.WRAP
          addClassName("layout")
          val sAgree = radioButton("Strongly Agree")
          val agree = radioButton("Agree")
          val neutral = radioButton("Neutral")
          val disagree = radioButton("Disagree")
          val sDisagree = radioButton("Strongly Disagree")
          radioButtonGroup {
            add(sDisagree, disagree, neutral, agree, sAgree)
          }
        }
      }
  }
}
