package com.webforj.samples.views.spinner

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.html.elements.Div
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.spinner.SpinnerExpanse
import com.webforj.kotlin.dsl.component.html.elements.h3
import com.webforj.kotlin.dsl.component.html.elements.paragraph
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.vertical
import com.webforj.kotlin.dsl.component.spinner.spinner
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Spinner Basics")
@StyleSheet("ws://css/spinnerstyles/spinnerdemo.css")
class SpinnerDemoKotlinView: Composite<Div>() {
  private val self = boundComponent

  init {
    self.apply {
      styles["margin-left"] = 20.px
      flexLayout {
        vertical()
        justifyContent = FlexJustifyContent.CENTER
        h3("Complete your job application:")
        flexLayout {
          tablerIcon("checks")
          paragraph("Select the position you wish to apply for")
        }
        flexLayout {
          tablerIcon("checks")
          paragraph("Provide your current location details")
        }
        flexLayout {
          spinner(Theme.PRIMARY, SpinnerExpanse.XXXSMALL)
          paragraph("Uploading your resume")
        }
      }
    }
  }
}
