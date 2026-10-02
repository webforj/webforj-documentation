package com.webforj.samples.views.loading

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.button.ButtonTheme
import com.webforj.component.icons.FeatherIcon
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.html.elements.paragraph
import com.webforj.kotlin.dsl.component.icons.featherIcon
import com.webforj.kotlin.dsl.component.loading.loading
import com.webforj.kotlin.extension.classNames
import com.webforj.kotlin.extension.plus
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Loading Basics")
@StyleSheet("ws://css/loadingstyles/loadingdemo.css")
class LoadingDemoKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.ROW
      margin = "var(--dwc-space-l)"
      div {
        classNames + "card"
        paragraph("User Guide")
        featherIcon(FeatherIcon.BOOK) {
          classNames + "icon"
        }
        button("Buy", ButtonTheme.PRIMARY)
      }
      div {
        classNames + "card"
        paragraph("Video Lessons")
        featherIcon(FeatherIcon.YOUTUBE) {
          classNames + "icon"
        }
        button("Buy", ButtonTheme.PRIMARY)
        loading("Loading... Please wait.") {
          classNames + "loading-overlay"
          open()
          spinner.theme = Theme.PRIMARY
        }
      }
    }
  }
}
