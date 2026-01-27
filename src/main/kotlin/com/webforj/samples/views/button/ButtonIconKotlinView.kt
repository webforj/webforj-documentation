package com.webforj.samples.views.button

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.button.icon
import com.webforj.kotlin.dsl.component.button.prefix
import com.webforj.kotlin.dsl.component.button.suffix
import com.webforj.kotlin.dsl.component.html.elements.img
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Button Icon")
class ButtonIconKotlinView: Composite<FlexLayout>() {

  init {
      boundComponent.apply {
        spacing = "var(--dwc-space-l)"
        margin = "var(--dwc-space-l)"
        styles["flex-wrap"] = "wrap"
        width = "100%"
        button("Notification") {
          prefix { tablerIcon("bell") }
        }
        button("Search") {
          suffix { tablerIcon("external-link") }
        }
        button {
          icon {
            img("https://documentation.webforj.com/img/webforj.svg") {
              setSize("100px", "30px")
            }
          }
        }
      }
  }
}
