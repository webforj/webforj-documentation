package com.webforj.samples.views.icon

import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.button.prefix
import com.webforj.kotlin.dsl.component.button.suffix
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Icon Action Buttons")
class IconPrefixSuffixKotlinView: Composite<FlexLayout>() {

  init {
      boundComponent.apply {
        direction = FlexDirection.ROW
        margin = "var(--dwc-space-l)"
        spacing = "var(--dwc-space-l)"
        button("Next", ButtonTheme.PRIMARY) {
          suffix { tablerIcon("arrow-narrow-right") }
        }
        button("Filter") {
          prefix { tablerIcon("filter") }
        }
      }
  }
}
