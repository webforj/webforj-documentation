package com.webforj.samples.views.ininitescroll

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.icons.FeatherIcon
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.html.elements.span
import com.webforj.kotlin.dsl.component.icons.featherIcon
import com.webforj.kotlin.dsl.component.infiniitescroll.infiniteScroll
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Fully Customized Loading")
@StyleSheet("ws://css/infinitescroll/infinitescrollcustom.css")
class InfiniteScrollCustomLoadingKotlinView: Composite<Div>() {

  init {
      boundComponent.apply {
        height = "100vh"
        styles["overflow"] = "auto"
        infiniteScroll {
          addClassName("is")
          height = "100%"
          val canvas = div {
            maxWidth = "600px"
            addClassName("is-canvas")
          }
          div {
            addClassName("custom-loading")
            featherIcon(FeatherIcon.CLOUD) {
              setSize("32px", "32px")
              addClassName("loading-icon")
            }
            span("Loading awesome content...")
          }
          var index = 0
          onScroll {
            if (index > 40) {
              isCompleted = true
              update()
              return@onScroll
            }

            for (i in 0..<8) {
              canvas.add(Item())
            }

            index++
            update()
          }
        }
      }
  }
}
