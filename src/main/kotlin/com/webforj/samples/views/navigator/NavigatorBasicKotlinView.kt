package com.webforj.samples.views.navigator

import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.navigator.event.NavigatorChangeEvent.Direction.FIRST
import com.webforj.component.navigator.event.NavigatorChangeEvent.Direction.LAST
import com.webforj.component.navigator.event.NavigatorChangeEvent.Direction.NEXT
import com.webforj.component.navigator.event.NavigatorChangeEvent.Direction.PREVIOUS
import com.webforj.kotlin.dsl.component.navigator.navigator
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Navigator Basics")
class NavigatorBasicKotlinView : Composite<Div>() {
  private val self = boundComponent

  init {
    var count = 0
    self.apply {
      styles["padding"] = 20.px
      navigator("Value: $count") {
        onChange {
          when (it.direction) {
            NEXT -> count++
            PREVIOUS -> count--
            FIRST -> count = 0
            LAST -> count = 10
          }

          if (count < 0) {
            count = 0
          } else if (count > 10) {
            count = 10
          }

          text = "Value: $count"
        }
      }
    }
  }
}
