package com.webforj.samples.views.ininitescroll

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.icons.FeatherIcon
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.icons.featherIcon
import com.webforj.kotlin.dsl.component.infiniitescroll.infiniteScroll
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.kotlin.dsl.component.layout.flexlayout.vertical
import com.webforj.kotlin.extension.classNames
import com.webforj.kotlin.extension.percent
import com.webforj.kotlin.extension.plus
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.kotlin.extension.vh
import com.webforj.router.annotation.Route
import kotlin.random.Random

@Route
@StyleSheet("ws://css/infinitescroll/infinitescroll.css")
class InfiniteScrollKotlinView: Composite<Div>() {
  private val self = boundComponent

  init {
      self.apply {
        height = 100.vh
        styles["overflow"] = "auto"
        infiniteScroll {
          height = 100.percent
          val canvas = div {
            maxWidth = 600.px
            classNames + "is-canvas"
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
            index += 8
            update()
          }
        }
      }
  }

}

