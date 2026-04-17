package com.webforj.samples.views.accordion

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.accordion.accordionPanel
import com.webforj.kotlin.dsl.component.html.elements.h3
import com.webforj.kotlin.dsl.component.html.elements.paragraph
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Accordion - Standalone Panels")
class AccordionBasicKotlinView : Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      spacing = "var(--dwc-space-m)"
      padding = "var(--dwc-space-l)"
      margin = "0 auto"
      maxWidth = 700.px
      h3("Standalone Panels")
      paragraph("Each panel works independently without a group wrapper.")
      panel("Section One", "This panel starts opened. Each panel operates independently.", true)
      panel("Section Two", "Content for section two. Click the header to expand.")
      panel("Section Three", "Content for section three.")
    }
  }

  private fun FlexLayout.panel(label: String, content: String, opened: Boolean = false) {
    accordionPanel(label) {
      paragraph(content)
      if (opened) {
        open()
      }
    }
  }

}
