package com.webforj.samples.views.alert

import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.button.ButtonTheme
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.alert.alert
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.html.elements.paragraph
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Form Confirmation Alert Kotlin")
class AlertKotlinView: Composite<FlexLayout>() {

  init {
    boundComponent.apply {
      direction = FlexDirection.COLUMN
      spacing = "var(--dwc-space-m)"
      alignment = FlexAlignment.CENTER
      justifyContent = FlexJustifyContent.CENTER
      margin = "var(--dwc-space-l)"
      alert {
        theme = Theme.PRIMARY
        maxWidth = "500px"
        paragraph("The requested information is ready to be viewed.")
        button("View", ButtonTheme.PRIMARY)
      }
    }
  }
}