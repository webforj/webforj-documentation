package com.webforj.samples.views.alert

import com.basis.bbj.comm.al
import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.alert.Alert
import com.webforj.component.button.Button
import com.webforj.component.button.ButtonTheme
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.alert.alert
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Closable Alert Kotlin")
class ClosableAlertKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      spacing = "var(--dwc-space-m)"
      alignment = FlexAlignment.CENTER
      justifyContent = FlexJustifyContent.CENTER
      margin = "var(--dwc-space-l)"
      val alert = alert("Heads up! This alert can be dismissed.", Theme.INFO, true) {
        maxWidth = 400.px
      }
      button("Show Alert", ButtonTheme.PRIMARY) {
        isVisible = false
        onClick {
          alert.open()
          isVisible = false
        }
        alert.onClose {
          isVisible = true
        }
      }
    }
  }
}
