package com.webforj.samples.views.flexlayout.item

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.button.Button
import com.webforj.component.button.ButtonTheme
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.layout.flexlayout.FlexWrap
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.kotlin.dsl.component.list.choiceBox
import com.webforj.kotlin.extension.classNames
import com.webforj.kotlin.extension.plus
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@StyleSheet("ws://css/flexlayout/container/flexContainerBuilder.css")
@FrameTitle("Flex Item Self Align")
class FlexSelfAlignKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent
  private lateinit var boxLayout: FlexLayout
  private lateinit var alignButton: Button

  init {
    self.apply {
      flexLayout {
        horizontal()
        choiceBox {
          classNames + "flex__options"
          label = "Self ALignment Options"
          FlexAlignment.entries.forEach {
            val label = it.value
            val text = label.uppercase()[0]+ label.substring(1)
            add(it, text)
          }
          selectIndex(0)
          onSelect {
            val alignment = it.selectedItem.key as FlexAlignment
            boxLayout.setItemAlignment(alignment, alignButton)
          }
        }
        boxLayout = flexLayout {
          horizontal()
          wrap = FlexWrap.WRAP
          classNames + "button__container"
          for (i in 1..5) {
            val button = button("Button $i", ButtonTheme.PRIMARY)
            setItemOrder(i, button)
            if (i == 5) {
              alignButton = button
              alignButton.theme = ButtonTheme.DANGER
              alignButton.text = "Align Me!"
              setItemAlignment(FlexAlignment.START, alignButton)
            }
          }
        }
      }
    }
  }
}
