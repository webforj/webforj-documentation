package com.webforj.samples.views.accordion

import com.webforj.component.Composite
import com.webforj.component.icons.FeatherIcon
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.accordion.accordionPanel
import com.webforj.kotlin.dsl.component.accordion.headerSlot
import com.webforj.kotlin.dsl.component.html.elements.paragraph
import com.webforj.kotlin.dsl.component.html.elements.span
import com.webforj.kotlin.dsl.component.icons.featherIcon
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Accordion - Custom Header")
class AccordionCustomHeaderKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      spacing = "var(--dwc-space-m)"
      padding = "var(--dwc-space-l)"
      margin = "0 auto"
      maxWidth = 700.px
      customHeaderPanel(FeatherIcon.SETTINGS, "Custom Header with Icon",
        "The header slot lets you fully customize the header content.")
      customHeaderPanel(FeatherIcon.USER, "User Settings",
        "Another panel with a custom header using the user icon.")
    }
  }

  private fun FlexLayout.customHeaderPanel(
    icon: FeatherIcon,
    title: String,
    content: String
  ) {
    accordionPanel {
      headerSlot {
        flexLayout {
          direction = FlexDirection.ROW
          spacing = "var(--dwc-space-s)"
          featherIcon(icon)
          span(title)
        }
      }
      paragraph(content)
    }
  }
}
