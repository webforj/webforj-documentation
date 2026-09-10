package com.webforj.samples.views.toolbar

import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.html.elements.h1
import com.webforj.kotlin.dsl.component.icons.iconButton
import com.webforj.kotlin.dsl.component.toolbar.endSlot
import com.webforj.kotlin.dsl.component.toolbar.startSlot
import com.webforj.kotlin.dsl.component.toolbar.titleSlot
import com.webforj.kotlin.dsl.component.toolbar.toolbar
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Toolbar Themes")
class ToolbarThemeKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      spacing = "var(--dwc-space-m)"
      margin = "var(--dwc-space-m) var(--dwc-space-m)"
      Theme.entries.forEach {
        toolbar {
          theme = it
          titleSlot { h1(it.name) }
          startSlot { iconButton("menu-2", "tabler") }
          endSlot {
            iconButton("settings", "tabler")
            iconButton("user", "tabler")
          }
        }
      }
    }
  }
}
