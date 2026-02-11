package com.webforj.samples.views.checkbox

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.layout.flexlayout.FlexWrap
import com.webforj.concern.HasTextPosition
import com.webforj.concern.HasTextPosition.Position
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.vertical
import com.webforj.kotlin.dsl.component.optioninput.checkBox
import com.webforj.kotlin.extension.percent
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Checkbox Horizontal Text")
class CheckboxHorizontalTextKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
      self.apply {
        direction = FlexDirection.ROW
        wrap = FlexWrap.WRAP
        spacing = "var(--dwc-space-l)"
        margin = "var(--dwc-space-l)"
        flexLayout {
          vertical()
          width = 100.percent
          checkBox("Daily", true)
          checkBox("Weekly")
          checkBox("Bi-Weekly")
          checkBox("Monthly")
          checkBox("Annually")
        }
        flexLayout {
          vertical()
          alignment = FlexAlignment.END
          width = 100.px
          checkBox("Daily", true) {
            textPosition = Position.LEFT
          }
          checkBox("Weekly") {
            textPosition = Position.LEFT
          }
          checkBox("Bi-Weekly") {
            textPosition = Position.LEFT
          }
          checkBox("Monthly") {
            textPosition = Position.LEFT
          }
          checkBox("Annually") {
            textPosition = Position.LEFT
          }
        }
      }
  }
}
