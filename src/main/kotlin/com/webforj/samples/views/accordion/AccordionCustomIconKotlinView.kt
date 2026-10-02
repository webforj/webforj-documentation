package com.webforj.samples.views.accordion

import com.webforj.component.Composite
import com.webforj.component.icons.FeatherIcon
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.accordion.accordionPanel
import com.webforj.kotlin.dsl.component.accordion.iconSlot
import com.webforj.kotlin.dsl.component.html.elements.paragraph
import com.webforj.kotlin.dsl.component.icons.featherIcon
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Accordion - Custom Icon")
class AccordionCustomIconKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      spacing = "var(--dwc-space-m)"
      padding = "var(--dwc-space-l)"
      margin = "0 auto"
      maxWidth = 700.px
      accordionPanel("Plus Icon Panel") {
        iconSlot { featherIcon(FeatherIcon.PLUS) }
        paragraph("This panel uses a custom expand/collapse icon via the icon slot.")
      }
    }
  }
}
