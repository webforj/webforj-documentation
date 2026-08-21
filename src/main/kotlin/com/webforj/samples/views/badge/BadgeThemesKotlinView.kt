package com.webforj.samples.views.badge

import com.webforj.component.Composite
import com.webforj.component.badge.BadgeTheme
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.layout.flexlayout.FlexWrap
import com.webforj.kotlin.dsl.component.badge.badge
import com.webforj.kotlin.dsl.component.html.elements.h3
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Badge - Themes")
class BadgeThemesKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      spacing = "var(--dwc-space-m)"
      padding = "var(--dwc-space-l)"
      margin = "0 auto"
      maxWidth = 700.px
      val themes = BadgeTheme.entries.partition { it.name.startsWith("OUTLINED") }
      h3("Default")
      createBadgeRow(themes.second.map { theme ->
        theme.name.let { it[0] + it.substring(1).lowercase() } to theme
      })
      h3("Outlined")
      createBadgeRow(themes.first.map { theme ->
        theme.name.split("_")[1].let { it[0] + it.substring(1).lowercase() } to theme
      })
    }
  }

  private fun FlexLayout.createBadgeRow(themes: List<Pair<String, BadgeTheme>>) {
    flexLayout {
      horizontal()
      wrap = FlexWrap.WRAP
      spacing = "var(--dwc-space-s)"
      alignment = FlexAlignment.CENTER;
      themes.forEach { badge(it.first, it.second) }
    }
  }
}
