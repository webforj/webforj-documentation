package com.webforj.samples.views.flexlayout.container

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.kotlin.dsl.component.layout.flexlayout.vertical
import com.webforj.kotlin.dsl.component.list.choiceBox
import com.webforj.kotlin.extension.classNames
import com.webforj.kotlin.extension.plus
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import com.webforj.samples.views.flexlayout.box

@Route
@StyleSheet("ws://css/flexlayout/container/flexContainerBuilder.css")
@FrameTitle("Flex Positioning")
class FlexPositioningKotlinView: Composite<Div>() {
  private val self = boundComponent
  private lateinit var boxLayout: FlexLayout
  private val HUE = 36

  init {
    self.apply {
      flexLayout {
        horizontal()
        flexLayout {
          vertical()
          choiceBox {
            classNames + "flex__options"
            label = "Justify Options"
            FlexJustifyContent.entries.forEach {
              val label = it.value
              val text = label.uppercase()[0] + label.substring(1)
              add(it, text)
            }
            selectIndex(0)
            onSelect {
              boxLayout.justifyContent = it.selectedItem.key as FlexJustifyContent
            }
          }
          choiceBox {
            classNames + "flex__options"
            label = "Alignment Options"
            FlexAlignment.entries.forEach {
              val label = it.value
              val text = label.uppercase()[0] + label.substring(1)
              add(it, text)
            }
            selectIndex(0)
            onSelect {
              boxLayout.alignment = it.selectedItem.key as FlexAlignment
            }
          }
        }
        boxLayout = flexLayout {
          horizontal()
          classNames + "button__container--single-row"
          height = 200.px
          for (i in 1..4) {
            val hue = (HUE * i).toString()
            box(i) {
              styles["background"] = "hsla($hue, 50%, 75%, 0.25)"
              styles["border"] = "2px solid hsl($hue, 50%, 35%)"
              styles["color"] = "hsla($hue, 50%, 25%)"
            }
          }
        }
      }
    }
  }
}
