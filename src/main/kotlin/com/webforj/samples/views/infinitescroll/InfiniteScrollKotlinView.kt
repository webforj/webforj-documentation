package com.webforj.samples.views.infinitescroll

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.infiniitescroll.infiniteScroll
import com.webforj.kotlin.extension.classNames
import com.webforj.kotlin.extension.percent
import com.webforj.kotlin.extension.plusAssign
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.kotlin.extension.vh
import com.webforj.router.annotation.Route

@Route
@StyleSheet("ws://css/infinitescroll/infinitescroll.css")
class InfiniteScrollKotlinView : Composite<Div>() {
  private val self = boundComponent

  init {
    self.apply {
      height = 100.vh
      styles["overflow"] = "auto"

      infiniteScroll {
        height = 100.percent
        var index = 0

        val canvas = div {
          maxWidth = 600.px
          classNames += "is-canvas"
        }

        onScroll {
          if (index > 40) {
            isCompleted = true
            update()
            return@onScroll
          }

          repeat(8) {
            canvas.item()
          }

          index += 8
          update()
        }
      }
    }
  }
}
