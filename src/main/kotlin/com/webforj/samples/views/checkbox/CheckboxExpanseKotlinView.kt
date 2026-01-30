package com.webforj.samples.views.checkbox

import com.webforj.component.Composite
import com.webforj.component.Expanse
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.layout.flexlayout.FlexWrap
import com.webforj.kotlin.dsl.component.optioninput.checkBox
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Checkbox Expanses")
class CheckboxExpanseKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
     self.apply {
        wrap = FlexWrap.WRAP
        margin = "var(--dwc-space-l)"
        spacing = "50px"
        justifyContent = FlexJustifyContent.CENTER
        width = "100%"
        Expanse.entries.reversed().forEach {
          checkBox(it.name) {
            expanse = it
          }
        }
      }
  }
}
