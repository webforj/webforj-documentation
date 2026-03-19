package com.webforj.samples.views.icon

import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.kotlin.extension.prefix
import com.webforj.kotlin.extension.prefixSlot
import com.webforj.kotlin.extension.suffix
import com.webforj.kotlin.extension.suffixSlot
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Icon Action Buttons")
class IconPrefixSuffixKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
      self.apply {
        direction = FlexDirection.ROW
        margin = "var(--dwc-space-l)"
        spacing = "var(--dwc-space-l)"
        button("Next", ButtonTheme.PRIMARY) {
          suffixSlot { tablerIcon("arrow-narrow-right") }
        }
        button("Filter") {
          prefixSlot { tablerIcon("filter") }
        }
      }
  }
}
