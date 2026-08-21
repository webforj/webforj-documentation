package com.webforj.samples.views.spinner

import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.spinner.SpinnerExpanse
import com.webforj.kotlin.dsl.component.spinner.spinner
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Spinner Themes")
class SpinnerThemeDemoKotlinView : Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.ROW
      alignment = FlexAlignment.CENTER
      justifyContent = FlexJustifyContent.CENTER
      spacing = "var(--dwc-space-m)"
      margin = "var(--dwc-space-l)"
      listOf(
        Theme.DEFAULT, Theme.PRIMARY, Theme.SUCCESS, Theme.DANGER,
        Theme.WARNING, Theme.GRAY, Theme.INFO
      ).forEach {
        spinner(it, SpinnerExpanse.MEDIUM)
      }
    }
  }
}
