package com.webforj.samples.views.badge

import com.webforj.component.Composite
import com.webforj.component.badge.BadgeExpanse
import com.webforj.component.badge.BadgeTheme
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.layout.flexlayout.FlexWrap
import com.webforj.concern.HasComponents
import com.webforj.kotlin.dsl.component.badge.badge
import com.webforj.kotlin.dsl.component.html.elements.h3
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Badge - Sizes")
class BadgeSizesKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      spacing = "var(--dwc-space-m)"
      padding = "var(--dwc-space-l)"
      margin = "0 auto"
      maxWidth = 700.px
      h3("All Sizes")
      allSizesRow()
      h3("Single Character (circular)")
      circularRow()
    }
  }

  private fun FlexLayout.allSizesRow() {
    flexLayout {
      horizontal()
      wrap = FlexWrap.WRAP
      spacing = "var(--dwc-space-s)"
      alignment = FlexAlignment.CENTER
      createBadge("3xs", BadgeExpanse.XXXSMALL)
      createBadge("2xs", BadgeExpanse.XXSMALL)
      createBadge("xs", BadgeExpanse.XSMALL)
      createBadge("s", BadgeExpanse.SMALL)
      createBadge("m", BadgeExpanse.MEDIUM)
      createBadge("l", BadgeExpanse.LARGE)
      createBadge("xl", BadgeExpanse.XLARGE)
      createBadge("2xl", BadgeExpanse.XXLARGE)
      createBadge("3xl", BadgeExpanse.XXXLARGE)
    }
  }

  private fun FlexLayout.circularRow() {
    flexLayout {
      horizontal()
      spacing = "var(--dwc-space-s)"
      alignment = FlexAlignment.CENTER
      createBadge("5", BadgeExpanse.XSMALL)
      createBadge("5", BadgeExpanse.SMALL)
      createBadge("5", BadgeExpanse.MEDIUM)
      createBadge("5", BadgeExpanse.LARGE)
      createBadge("5", BadgeExpanse.XLARGE)
    }
  }

  private fun HasComponents.createBadge(text: String, expanse: BadgeExpanse) =
    badge(text) {
      theme = BadgeTheme.PRIMARY
      this.expanse = expanse
    }
}
