package com.webforj.samples.views.radiobutton

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.concern.HasTextPosition
import com.webforj.concern.HasTextPosition.Position
import com.webforj.kotlin.dsl.component.optioninput.checkBox
import com.webforj.kotlin.dsl.component.optioninput.radioButton
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Radio Button Text")
class RadioButtonTextKotlinView: Composite<FlexLayout>() {

  init {
    boundComponent.apply {
      direction = FlexDirection.COLUMN
      spacing = "1em"
      margin = "20px"
      radioButton("Right aligned (default)")
      radioButton("Left aligned") {
        textPosition = Position.LEFT
      }
      checkBox("CheckBox")
    }
  }
}
