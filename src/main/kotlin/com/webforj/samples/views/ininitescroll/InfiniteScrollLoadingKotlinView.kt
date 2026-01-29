package com.webforj.samples.views.ininitescroll

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.icons.TablerIcon
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.infiniitescroll.infiniteScroll
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Custom Loading Indicator")
@StyleSheet("ws://css/infinitescroll/infinitescroll.css")
class InfiniteScrollLoadingKotlinView: Composite<Div>() {

  init {
      boundComponent.apply {
        height = "100vh"
        styles["overflow"] = "auto"
        infiniteScroll("Fetching more records...") {
          addClassName("is")
          height = "100%"
          val canvas = div {
            maxWidth = "600px"
            addClassName("is-canvas")
          }
          setIcon(TablerIcon.create("cloud-download"))
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
