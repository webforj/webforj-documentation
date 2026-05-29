package com.webforj.samples.views.flexlayout.container

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.kotlin.dsl.component.list.choiceBox
import com.webforj.kotlin.extension.classNames
import com.webforj.kotlin.extension.plus
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import com.webforj.samples.views.flexlayout.box

@Route
@StyleSheet("ws://css/flexlayout/container/flexContainerBuilder.css")
@FrameTitle("Flex Direction")
class FlexDirectionKotlinView: Composite<Div>() {
  private val self = boundComponent
  private val HUE = 36

  init {
    self.apply {
      flexLayout {
        horizontal()
        val directives = choiceBox {
          classNames + "flex__options"
          label = "Direction Options"
          FlexDirection.entries.forEach {
            val label = it.value
            val text = it.name[0] + label.substring(1)
            add(it, text)
          }
          selectIndex(0)
        }
        val boxLayout = flexLayout {
          horizontal()
          classNames + "button__container--single-row"
          for (i in 1..4) {
            val hue = (HUE * i).toString()
            box(i) {
              styles["background"] = "hsla($hue, 50%, 75%, 0.25)"
              styles["border"] = "2px solid hsl($hue, 50%, 35%)"
              styles["color"] = "hsla($hue, 50%, 25%)"
            }
          }
        }
        directives.onSelect {
          boxLayout.direction = it.selectedItem.key as FlexDirection
        }
      }
    }
  }
}
