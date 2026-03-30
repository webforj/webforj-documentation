package com.webforj.samples.views.badge

import com.webforj.component.Composite
import com.webforj.component.badge.BadgeTheme
import com.webforj.component.icons.FeatherIcon
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.layout.flexlayout.FlexWrap
import com.webforj.kotlin.dsl.component.badge.badge
import com.webforj.kotlin.dsl.component.html.elements.h3
import com.webforj.kotlin.dsl.component.icons.featherIcon
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Badge - Icons")
class BadgeIconsKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      spacing = "var(--dwc-space-m)"
      padding = "var(--dwc-space-l)"
      margin = "0 auto"
      maxWidth = 700.px
      h3("Icon + Text (label prop)")
      iconTextRow()
      h3("Icon Only")
      iconOnlyRow()
      h3("All Themes with Icon + Text")
      allThemesRow()
    }
  }

  private fun FlexLayout.iconTextRow() {
    flexLayout {
      horizontal()
      wrap = FlexWrap.WRAP
      spacing = "var(--dwc-space-s)"
      alignment = FlexAlignment.CENTER
      badge("Done") {
        theme = BadgeTheme.SUCCESS
        featherIcon(FeatherIcon.CHECK_CIRCLE)
      }
      badge("Error") {
        theme = BadgeTheme.DANGER
        featherIcon(FeatherIcon.X_CIRCLE)
      }
      badge("Info") {
        theme = BadgeTheme.PRIMARY
        featherIcon(FeatherIcon.INFO)
      }
      badge("Warning") {
        theme = BadgeTheme.WARNING
        featherIcon(FeatherIcon.ALERT_TRIANGLE)
      }
    }
  }

  private fun FlexLayout.iconOnlyRow() {
    flexLayout {
      horizontal()
      wrap = FlexWrap.WRAP
      spacing = "var(--dwc-space-s)"
      alignment = FlexAlignment.CENTER
      badge {
        theme = BadgeTheme.SUCCESS
        featherIcon(FeatherIcon.CHECK)
      }
      badge {
        theme = BadgeTheme.DANGER
        featherIcon(FeatherIcon.X)
      }
      badge {
        theme = BadgeTheme.PRIMARY
        featherIcon(FeatherIcon.BELL)
      }
      badge {
        theme = BadgeTheme.GRAY
        featherIcon(FeatherIcon.STAR)
      }
    }
  }

  private fun FlexLayout.allThemesRow() {
    flexLayout {
      horizontal()
      wrap = FlexWrap.WRAP
      spacing = "var(--dwc-space-s)"
      alignment = FlexAlignment.CENTER
      badge("New") {
        theme = BadgeTheme.PRIMARY
        featherIcon(FeatherIcon.STAR)
      }
      badge("Live") {
        theme = BadgeTheme.SUCCESS
        featherIcon(FeatherIcon.RADIO)
      }
      badge("Alert") {
        theme = BadgeTheme.DANGER
        featherIcon(FeatherIcon.ALERT_CIRCLE)
      }
      badge("Draft") {
        theme = BadgeTheme.GRAY
        featherIcon(FeatherIcon.EDIT)
      }
      badge("Beta") {
        theme = BadgeTheme.WARNING
        featherIcon(FeatherIcon.ZAP)
      }
    }
  }

}
