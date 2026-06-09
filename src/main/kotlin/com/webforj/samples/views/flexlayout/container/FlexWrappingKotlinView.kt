package com.webforj.samples.views.flexlayout.container

import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.html.elements.Div
import com.webforj.component.layout.flexlayout.FlexWrap
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Flex Wrapping")
class FlexWrappingKotlinView : Composite<Div>() {
  private val self = boundComponent

  init {
    self.apply {
      flexLayout {
        horizontal()
        width = 200.px
        wrap = FlexWrap.WRAP
        styles["border"] = "1px black dotted"

        button("Button 1", ButtonTheme.PRIMARY)
        button("Button 2")
        button("Button 3")
      }
    }
  }
}
