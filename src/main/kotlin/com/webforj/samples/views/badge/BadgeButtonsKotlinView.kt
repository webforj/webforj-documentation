package com.webforj.samples.views.badge

import com.webforj.component.Composite
import com.webforj.component.Expanse
import com.webforj.component.badge.BadgeExpanse
import com.webforj.component.badge.BadgeTheme
import com.webforj.component.button.ButtonTheme
import com.webforj.component.icons.FeatherIcon
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.layout.flexlayout.FlexWrap
import com.webforj.concern.HasComponents
import com.webforj.kotlin.dsl.component.badge.badge
import com.webforj.kotlin.dsl.component.button.badgeSlot
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.html.elements.h3
import com.webforj.kotlin.dsl.component.icons.featherIcon
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.kotlin.extension.prefixSlot
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Badge - Buttons")
class BadgeButtonsKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      spacing = "var(--dwc-space-m)"
      padding = "var(--dwc-space-l)"
      margin = "0 auto"
      maxWidth = 700.px
      h3("Badge on Buttons")
      notificationRow()
      h3("Button Sizes with Badge")
      buttonSizesRow()
    }
  }

  private fun FlexLayout.notificationRow() {
    flexLayout {
      horizontal()
      wrap = FlexWrap.WRAP
      spacing = "var(--dwc-space-l)"
      alignment = FlexAlignment.CENTER
      button("Notifications") {
        theme = ButtonTheme.PRIMARY
        prefixSlot { featherIcon(FeatherIcon.BELL) }
        badgeSlot { createBadge("5", BadgeTheme.DANGER, BadgeExpanse.XSMALL) }
      }
      button("Messages") {
        theme = ButtonTheme.DEFAULT
        prefixSlot { featherIcon(FeatherIcon.MAIL) }
        badgeSlot { createBadge("12", BadgeTheme.PRIMARY, BadgeExpanse.XSMALL) }
      }
    }
  }

  private fun FlexLayout.buttonSizesRow() {
    flexLayout {
      horizontal()
      wrap = FlexWrap.WRAP
      spacing = "var(--dwc-space-l)"
      alignment = FlexAlignment.CENTER
      Expanse.entries.forEach {
        val size = it.name.lowercase().let { s ->
          if (s.startsWith("x")) {
            s.substring(0, 2)
          } else {
            s.substring(0, 1)
          }
        }
        button(size) {
          theme = ButtonTheme.PRIMARY
          expanse = it
          badgeSlot { createBadge("3", BadgeTheme.DANGER, BadgeExpanse.XSMALL) }
        }
      }
    }
  }

  private fun HasComponents.createBadge(text: String, theme: BadgeTheme, expanse: BadgeExpanse) =
    badge(text) {
      this.theme = theme
      this.expanse = expanse
    }

}
