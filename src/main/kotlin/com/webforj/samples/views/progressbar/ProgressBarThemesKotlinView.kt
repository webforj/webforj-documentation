package com.webforj.samples.views.progressbar

import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.html.elements.Div
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.vertical
import com.webforj.kotlin.dsl.component.progressbar.progressBar
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Progress Bar Themes")
class ProgressBarThemesKotlinView: Composite<Div>() {

  init {
      boundComponent.apply {
        flexLayout {
          vertical()
          maxWidth = "320px"
          styles["margin"] = "0 auto"
          styles["padding"] = "20px"
          Theme.entries.forEach {
            progressBar {
              isAnimated = true
              value = 50
              isStriped = true
              text = "${it.name} {{x}}%"
              theme = it
            }
          }
        }
      }
  }
}
