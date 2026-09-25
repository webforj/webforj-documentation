package com.webforj.samples.views.infinitescroll

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.icons.FeatherIcon
import com.webforj.component.infinitescroll.InfiniteScroll
import com.webforj.concern.HasComponents
import com.webforj.kotlin.dsl.MultiSlotSetter
import com.webforj.kotlin.dsl.WebforjDsl
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.html.elements.span
import com.webforj.kotlin.dsl.component.icons.featherIcon
import com.webforj.kotlin.dsl.component.infiniitescroll.infiniteScroll
import com.webforj.kotlin.extension.classNames
import com.webforj.kotlin.extension.percent
import com.webforj.kotlin.extension.plusAssign
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.size
import com.webforj.kotlin.extension.styles
import com.webforj.kotlin.extension.vh
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Fully Customized Loading")
@StyleSheet("ws://css/infinitescroll/infinitescrollcustom.css")
class InfiniteScrollCustomLoadingKotlinView : Composite<Div>() {
  private val self = boundComponent

  init {
    self.apply {
      height = 100.vh
      styles["overflow"] = "auto"

      infiniteScroll {
        classNames += "is"
        height = 100.percent
        var index = 0

        val canvas = div {
          maxWidth = 600.px
          classNames += "is-canvas"
        }
        contentSlot {
          div {
            classNames += "custom-loading"

            featherIcon(FeatherIcon.CLOUD) {
              size = 32.px to 32.px
              classNames += "loading-icon"
            }
            span("Loading awesome content...")
          }
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

private fun @WebforjDsl InfiniteScroll.contentSlot(block: @WebforjDsl HasComponents.() -> Unit) {
  MultiSlotSetter(block).setSlot(this, InfiniteScroll::addToContent)
}
