package com.webforj.samples.views.toolbar

import com.webforj.component.Composite
import com.webforj.component.layout.applayout.AppLayout
import com.webforj.kotlin.dsl.component.html.elements.h1
import com.webforj.kotlin.dsl.component.html.elements.h3
import com.webforj.kotlin.dsl.component.html.elements.paragraph
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.kotlin.dsl.component.layout.applayout.appDrawerToggle
import com.webforj.kotlin.dsl.component.layout.applayout.headerSlot
import com.webforj.kotlin.dsl.component.tabbedpane.prefixSlot
import com.webforj.kotlin.dsl.component.tabbedpane.tab
import com.webforj.kotlin.dsl.component.tabbedpane.tabbedPane
import com.webforj.kotlin.dsl.component.toolbar.startSlot
import com.webforj.kotlin.dsl.component.toolbar.titleSlot
import com.webforj.kotlin.dsl.component.toolbar.toolbar
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Toolbar Compact")
class ToolbarCompactKotlinView: Composite<AppLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      drawerPlacement = AppLayout.DrawerPlacement.HIDDEN
      styles["--dwc-app-layout-hearer-height"] = 80.px
      h1("Application Title")
      paragraph("Content goes here")
      headerSlot {
        toolbar {
          titleSlot { h3("Application") }
          startSlot { appDrawerToggle() }
          toolbar {
            isCompact = true
            tabbedPane {
              isBorderless = true
              isBodyHidden = true
              tab("Sales") {
                prefixSlot { tablerIcon("report-money") }
              }
              tab("Enterprise") {
                prefixSlot { tablerIcon("building") }
              }
              tab("Payments") {
                prefixSlot { tablerIcon("credit-card") }
              }
              tab("History") {
                prefixSlot { tablerIcon("history") }
              }
            }
          }
        }
      }
    }
  }
}
