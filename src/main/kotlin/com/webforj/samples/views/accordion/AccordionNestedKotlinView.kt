package com.webforj.samples.views.accordion

import com.webforj.component.Composite
import com.webforj.component.accordion.Accordion
import com.webforj.component.accordion.AccordionPanel
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
@FrameTitle("Accordion - Nested")
class AccordionNestedKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      spacing = "var(--dwc-space-m)"
      padding = "var(--dwc-space-l)"
      margin = "0 auto"
      maxWidth = 700.px
      h3("Nested Accordions")
      paragraph("Accordions can be nested inside other accordion panels.")
      accordionPanel("Outer Panel (contains nested accordion)") {
        open()
        accordion {
          for (c in listOf('A', 'B', 'C')) {
            innerAccordion(c)
          }
        }
      }
      accordionPanel("Sibling Panel") {
        paragraph("This is a sibling of the outer panel.")
      }
    }
  }

  private fun Accordion.innerAccordion(char: Char) {
    accordionPanel("Inner Panel $char") {
      paragraph("Nested content $char")
    }
  }
}
