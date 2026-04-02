package com.webforj.samples.views.flexlayout.item

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.button.Button
import com.webforj.component.button.ButtonTheme
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.layout.flexlayout.FlexWrap
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.field.maskedNumberField
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.kotlin.dsl.component.layout.flexlayout.vertical
import com.webforj.kotlin.extension.classNames
import com.webforj.kotlin.extension.percent
import com.webforj.kotlin.extension.plus
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.size
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@StyleSheet("ws://css/flexlayout/container/flexContainerBuilder.css")
@Route
@FrameTitle("Flex Order")
class FlexOrderKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent
  private lateinit var boxLayout: FlexLayout
  private lateinit var orderButton: Button

  init {
    self.apply {
      flexLayout {
        horizontal()
        wrap = FlexWrap.WRAP
        flexLayout {
          vertical()
          alignment = FlexAlignment.STRETCH
          classNames + "flex_options"
          val order = maskedNumberField("5") {
            label = "Order:"
            width = 100.percent
            invalidMessage = "Order can not be empty"
            isNegateable = false
          }
          button("Set Order") {
            size = 100.percent to 34.px
            onClick {
              if (order.text.isEmpty()) {
                order.isInvalid = true
              } else {
                val itemOrder = order.text.toInt()
                boxLayout.setItemOrder(itemOrder, orderButton)
                orderButton.text = "Order: $itemOrder"
              }
            }
          }
        }
        boxLayout = flexLayout {
          horizontal()
          wrap = FlexWrap.WRAP
          classNames + "button__container--single-row"
          for (i in 1..5) {
            val button = button("Order: $i", ButtonTheme.PRIMARY)
            setItemOrder(i, button)
            if (i == 5) {
              button.theme = ButtonTheme.DANGER
              orderButton = button
            }
          }
        }
      }
    }
  }
}
