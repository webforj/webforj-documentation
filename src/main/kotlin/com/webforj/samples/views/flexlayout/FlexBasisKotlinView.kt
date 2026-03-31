package com.webforj.samples.views.flexlayout

import com.webforj.component.Composite
import com.webforj.component.button.Button
import com.webforj.component.button.ButtonTheme
import com.webforj.component.field.NumberField
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.layout.flexlayout.FlexWrap
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.field.numberField
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.kotlin.dsl.component.layout.flexlayout.vertical
import com.webforj.kotlin.extension.percent
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Flex Basis")
class FlexBasisKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent
  private lateinit var boxLayout: FlexLayout
  private lateinit var numberField: NumberField
  private lateinit var basisButton: Button
  private val buttons = arrayListOf<Button>()
  private var selected = 0

  init {
    self.apply {
      flexLayout {
        horizontal()
        padding = 20.px
        flexLayout {
          vertical()
          numberField = numberField("Basis") {
            min = 75.0
            tooltipText = "Set the flex basis width (in pixels)"
            isRequired = true
          }
          basisButton = button("Set Basis") {
            tooltipText = "Select a box item first"
            onClick {
              numberField.value?.let {
                buttons.forEach {
                  val width = if (it.theme == ButtonTheme.PRIMARY) {
                    numberField.value.px
                  } else {
                    75.px
                  }
                  boxLayout.setItemBasis(width, it)
                }
              }
            }
          }
          button("Reset", ButtonTheme.OUTLINED_GRAY) {
            onClick {
              basisButton.tooltipText = "Select a box item first"
              selected = 0
              buttons.forEach {
                it.theme = ButtonTheme.OUTLINED_PRIMARY
                boxLayout.setItemBasis(75.px, it)
              }
            }
          }
        }
        boxLayout = flexLayout {
          horizontal()
          wrap = FlexWrap.WRAP
          padding = 20.px
          styles["border"] = "1px solid var(--dwc-color-default)"
          for (i in 1..5) {
            buttons.add(button("Box $i", ButtonTheme.OUTLINED_PRIMARY) {
              styles["transition"] = "flex-basis var(--dwc-transition-medium) var(--dwc-ease-inOutExpo)"
              onClick {
                if (theme == ButtonTheme.OUTLINED_PRIMARY) {
                  theme = ButtonTheme.PRIMARY
                  basisButton.tooltipText = "Set the basis for ${++selected} box item(s)"
                } else {
                  theme = ButtonTheme.OUTLINED_PRIMARY
                  basisButton.tooltipText = "Set the basis for ${--selected} box item(s)"
                }
              }
            })
          }
        }
        setItemBasis(100.percent, boxLayout)
      }
    }
  }
}
