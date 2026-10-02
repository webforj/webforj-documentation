package com.webforj.samples.views.radiobutton

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.concern.HasTextPosition.Position
import com.webforj.kotlin.dsl.component.optioninput.checkBox
import com.webforj.kotlin.dsl.component.optioninput.radioButton
import com.webforj.kotlin.extension.em
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Radio Button Text")
class RadioButtonTextKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      spacing = 1.em
      margin = 20.px
      radioButton("Right aligned (default)")
      radioButton("Left aligned") {
        textPosition = Position.LEFT
      }
      checkBox("CheckBox")
    }
  }
}
