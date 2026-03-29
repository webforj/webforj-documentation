package com.webforj.samples.views.toast

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.toast.toast
import com.webforj.kotlin.extension.classNames
import com.webforj.kotlin.extension.plus
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Toast Themes")
@StyleSheet("ws://css/toast/toastTheme.css")
class ToastThemeKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      margin = "var(--dwc-space-l)"
      toast("The application has a new update available", -1, Theme.DEFAULT) {
        classNames + "custom-theme"
        open()
      }
    }
  }
}
