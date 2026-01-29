package com.webforj.samples.views.icon

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Icon Basics")
class IconBasicsKotlinView: Composite<FlexLayout>() {

  init {
      boundComponent.apply {
        direction = FlexDirection.ROW
        margin = "var(--dwc-space-l)"
        spacing = "var(--dwc-space-m)"
        tablerIcon("message")
        tablerIcon("trash")
        tablerIcon("edit")
      }
  }
}
