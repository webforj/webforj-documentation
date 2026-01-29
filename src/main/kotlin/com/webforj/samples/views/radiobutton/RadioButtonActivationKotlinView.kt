package com.webforj.samples.views.radiobutton

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.optioninput.RadioButton.Activation
import com.webforj.kotlin.dsl.component.optioninput.radioButton
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Radio Button Activation")
class RadioButtonActivationKotlinView: Composite<FlexLayout>() {

  init {
      boundComponent.apply {
        direction = FlexDirection.COLUMN
        spacing = "1em"
        margin = "20px"
        radioButton("Auto Activated") {
          activation = Activation.AUTO
          focus()
        }
        radioButton("Auto Activated") {
          activation = Activation.AUTO
        }
        radioButton("Manually Activated")
        radioButton("Manually Activated")
      }
  }
}
