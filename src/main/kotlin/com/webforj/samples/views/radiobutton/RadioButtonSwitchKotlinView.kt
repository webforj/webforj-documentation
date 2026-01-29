package com.webforj.samples.views.radiobutton

import com.webforj.component.Composite
import com.webforj.component.Expanse
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.optioninput.radioButton
import com.webforj.kotlin.dsl.component.optioninput.switch
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Radio Button Switch")
class RadioButtonSwitchKotlinView: Composite<FlexLayout>() {

  init {
      boundComponent.apply {
        direction = FlexDirection.ROW
        spacing = "1em"
        margin = "20px"
        radioButton("Normal RadioButton") {
          expanse = Expanse.XLARGE
        }
        switch("Switch RadioButton") {
          expanse = Expanse.XLARGE
        }
      }
  }
}
