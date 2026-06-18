package com.webforj.samples.views.accordion

import com.webforj.component.Composite
import com.webforj.component.accordion.Accordion
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.accordion.accordion
import com.webforj.kotlin.dsl.component.accordion.accordionPanel
import com.webforj.kotlin.dsl.component.html.elements.h3
import com.webforj.kotlin.dsl.component.html.elements.paragraph
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Accordion - Single Mode Group")
class AccordionGroupKotlinView : Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      spacing = "var(--dwc-space-m)"
      padding = "var(--dwc-space-l)"
      margin = "0 auto"
      maxWidth = 700.px
      h3("Accordion Group (single mode)")
      paragraph("Only one panel can be open at a time.")
      accordion {
        panel("What is webforJ?", "webforJ is a Java framework for building web applications.", true)
        panel("How do grouped panels work?", "Panels inside an Accordion are coordinated. By default, expanding one collapses the others.")
        panel("Can I have multiple groups?", "Yes, each Accordion instance manages its own set of panels independently.")
      }
    }
  }

  private fun Accordion.panel(label: String, content: String, opened: Boolean = false) {
    accordionPanel(label) {
      paragraph(content)
      if (opened) {
        open()
      }
    }
  }

}
