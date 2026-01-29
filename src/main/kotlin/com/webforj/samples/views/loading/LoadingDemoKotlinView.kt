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
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Loading Basics")
@StyleSheet("ws://css/loadingstyles/loadingdemo.css")
class LoadingDemoKotlinView: Composite<FlexLayout>() {

  init {
    boundComponent.apply {
      direction = FlexDirection.ROW
      margin = "var(--dwc-space-l)"
      div {
        addClassName("card")
        paragraph("User Guide")
        featherIcon(FeatherIcon.BOOK).addClassName("icon")
        button("Buy", ButtonTheme.PRIMARY)
      }
      div {
        addClassName("card")
        paragraph("Video Lessons")
        featherIcon(FeatherIcon.YOUTUBE).addClassName("icon")
        button("Buy", ButtonTheme.PRIMARY)
        loading("Loading... Please wait.") {
          spinner.theme = Theme.PRIMARY
          addClassName("loading-overlay")
          open()
        }
      }
    }
  }
}
