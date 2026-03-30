package com.webforj.samples.views.accordion

import com.webforj.component.Composite
import com.webforj.component.accordion.Accordion
import com.webforj.component.button.ButtonTheme
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.accordion.accordion
import com.webforj.kotlin.dsl.component.accordion.accordionPanel
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.html.elements.h3
import com.webforj.kotlin.dsl.component.html.elements.paragraph
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.kotlin.extension.classNames
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Accordion - Multiple Mode")
class AccordionMultipleKotlinView : Composite<FlexLayout>() {
  private val self = boundComponent
  private val accordion: Accordion

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      spacing = "var(--dwc-space-m)"
      padding = "var(--dwc-space-l)"
      margin = "0 auto"
      maxWidth = 700.px
      h3("Accordion Group (multiple mode)")
      paragraph("Multiple panels can be open simultaneously.")
      flexLayout {
        horizontal()
        spacing = "var(--dwc-space-s)"
        button("Open All") {
          theme = ButtonTheme.PRIMARY
          onClick { accordion.openAll() }
        }
        button("Close All") {
          onClick { accordion.closeAll() }
        }
      }
      accordion = accordion {
        isMultiple = true
        panel("Panel A", "This panel is opened.")
        panel("Panel B", "This panel is also opened. Both can be open at the same time.")
        panel("Panel C", "Content for panel C.", false)
      }
    }
  }

  private fun Accordion.panel(label: String, content: String, opened: Boolean = true) {
    accordionPanel(label) {
      paragraph(content)
      if (opened) {
        open()
      }
    }
  }

}
