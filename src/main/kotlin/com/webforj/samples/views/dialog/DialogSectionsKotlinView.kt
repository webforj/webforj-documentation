package com.webforj.samples.views.dialog

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.dialog.dialog
import com.webforj.kotlin.dsl.component.dialog.footer
import com.webforj.kotlin.dsl.component.dialog.header
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Dialog Sections")
class DialogSectionsKotlinView: Composite<FlexLayout>() {

  init {
      boundComponent.apply {
        dialog {
          header { div("Header") }
          div("Content")
          footer { div("Footer") }
          setCloseable(false)
          open()
        }
      }
  }
}
