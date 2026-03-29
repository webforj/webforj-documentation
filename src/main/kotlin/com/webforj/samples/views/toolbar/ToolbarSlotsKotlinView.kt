package com.webforj.samples.views.toolbar

import com.webforj.annotation.InlineStyleSheet
import com.webforj.component.Composite
import com.webforj.component.layout.applayout.AppLayout
import com.webforj.kotlin.dsl.component.html.elements.h1
import com.webforj.kotlin.dsl.component.html.elements.h3
import com.webforj.kotlin.dsl.component.html.elements.paragraph
import com.webforj.kotlin.dsl.component.icons.iconButton
import com.webforj.kotlin.dsl.component.layout.applayout.headerSlot
import com.webforj.kotlin.dsl.component.toolbar.endSlot
import com.webforj.kotlin.dsl.component.toolbar.startSlot
import com.webforj.kotlin.dsl.component.toolbar.titleSlot
import com.webforj.kotlin.dsl.component.toolbar.toolbar
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Toolbar Slots")
@InlineStyleSheet(
  """
    dwc-toolbar {
      --dwc-toolbar-background: hsl(265, 100%, 47%);
      --dwc-toolbar-color: white;
      --dwc-icon-button-hover-color: var(--dwc-toolbar-color);
      --dwc-icon-button-active-color: var(--dwc-toolbar-color);
    }

    """
)
class ToolbarSlotsKotlinView: Composite<AppLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      drawerPlacement = AppLayout.DrawerPlacement.HIDDEN
      styles["--dwc-app-layout-header-height"] = 52.px
      h1("Application Title")
      paragraph("Content goes here")
      headerSlot {
        toolbar {
          titleSlot { h3("Application") }
          startSlot { iconButton("menu-2", "tabler") }
          endSlot {
            iconButton("settings", "tabler")
            iconButton("user", "tabler")
          }
          h3("Toolbar Conent")
        }
      }
    }
  }
}
