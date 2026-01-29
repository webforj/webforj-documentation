package com.webforj.samples.views.icon

import com.webforj.component.Composite
import com.webforj.component.icons.FontAwesomeIcon
import com.webforj.component.icons.TablerIcon
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.icons.fontAwesomeIcon
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Icon Variations")
class IconVariationsKotlinView: Composite<FlexLayout>() {

  init {
      boundComponent.apply {
        direction = FlexDirection.ROW
        margin = "var(--dwc-space-l)"
        spacing = "var(--dwc-space-m)"
        fontAwesomeIcon("envelope")
        fontAwesomeIcon("envelope", FontAwesomeIcon.Variate.SOLID)
        fontAwesomeIcon("instagram", FontAwesomeIcon.Variate.BRAND)
        tablerIcon("calendar")
        tablerIcon("calendar", TablerIcon.Variate.FILLED)
      }
  }
}
