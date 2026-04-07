package com.webforj.samples.views.busyindicator

import com.webforj.App
import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.button.ButtonTheme
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.field.textField
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.vertical
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Busy Basics")
class BusyDemoKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      justifyContent = FlexJustifyContent.CENTER
      margin = "var(--dwc-space-l)"
      flexLayout {
        vertical()
        textField("Name") {
          width = 500.px
        }
        textField("Email") {
          width = 500.px
        }
        button("Submit") {
          theme = ButtonTheme.PRIMARY
          onClick {
            App.getBusyIndicator().apply {
              text = "Submitting form... Please wait."
              isBackdropVisible = true
              open()
              spinner.apply { theme = Theme.PRIMARY }
            }
          }
        }
      }
    }
    App.getBusyIndicator().apply {
      text = "Submitting form... Please wait."
      isBackdropVisible = true
      open()
      spinner.apply { theme = Theme.PRIMARY }
    }
  }

}
