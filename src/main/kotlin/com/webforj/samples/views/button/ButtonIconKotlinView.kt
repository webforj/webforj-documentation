package com.webforj.samples.views.button

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.button.icon
import com.webforj.kotlin.dsl.component.html.elements.img
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.kotlin.extension.percent
import com.webforj.kotlin.extension.prefix
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.size
import com.webforj.kotlin.extension.styles
import com.webforj.kotlin.extension.suffix
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Button Icon")
class ButtonIconKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
      self.apply {
        spacing = "var(--dwc-space-l)"
        margin = "var(--dwc-space-l)"
        styles["flex-wrap"] = "wrap"
        width = 100.percent

        button("Notifications") {
          prefix { tablerIcon("bell") }
        }
        button("Search") {
          suffix { tablerIcon("external-link") }
        }
        button {
          icon {
            img("https://documentation.webforj.com/img/webforj.svg") {
              size = 100.px to 30.px
            }
          }
        }
      }
  }
}
