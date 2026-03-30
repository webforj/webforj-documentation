package com.webforj.samples.views.accordion

import com.webforj.component.Composite
import com.webforj.component.accordion.Accordion
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.accordion.accordion
import com.webforj.kotlin.dsl.component.accordion.accordionPanel
import com.webforj.kotlin.dsl.component.html.elements.h3
import com.webforj.kotlin.dsl.component.html.elements.paragraph
import com.webforj.kotlin.dsl.component.optioninput.switch
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Accordion - Disabled State")
class AccordionDisabledKotlinView : Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      spacing = "var(--dwc-space-m)"
      padding = "var(--dwc-space-l)"
      margin = "0 auto"
      maxWidth = 700.px
      h3("Disabled Panels")
      accordionPanel("This panel is disabled") {
        isEnabled = false
        paragraph("You should not be able to see this content.")
      }
      accordionPanel("Disabled but opened") {
        isEnabled = false
        open()
        paragraph("You should not be able to see this content.")
      }
      h3("Disable entire accordion group")
      val toggle = switch("Accordion enabled") {
        isChecked = true
      }
      val accordion = accordion {
        panel("Panel A", "This panel is opened.", true)
        panel("Panel B", "This panel is also opened. Both can be open at the same time.")
        panel("Panel C", "Content for panel C.")
      }
      toggle.onToggle { accordion.isEnabled = it.isToggled }
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
