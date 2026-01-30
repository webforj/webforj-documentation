package com.webforj.samples.views.alert

import com.webforj.component.Composite
import com.webforj.component.Expanse
import com.webforj.component.Theme
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.alert.alert
import com.webforj.kotlin.dsl.component.html.elements.paragraph
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Alert Expanses Kotlin")
class AlertExpansesKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      justifyContent = FlexJustifyContent.CENTER
      alignment = FlexAlignment.CENTER
      spacing = "var(--dwc-space-m)"
      margin = "var(--dwc-space-xl) auto"
      width = "100%"
      Expanse.entries.reversed().forEach {
        alert {
          flexLayout {
            tablerIcon("alert-square-rounded")
            paragraph("This alert uses the ${it.name} expanse.")
            horizontal()
            alignment = FlexAlignment.CENTER
          }
          expanse = it
          isClosable = false
          theme = Theme.SUCCESS
          width = "380px"
        }
      }
    }
  }
}