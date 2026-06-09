package com.webforj.samples.views.applayout.stickytoolbar

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.layout.applayout.AppLayout
import com.webforj.component.layout.appnav.AppNav
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.html.elements.h3
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.kotlin.dsl.component.layout.applayout.appDrawerToggle
import com.webforj.kotlin.dsl.component.layout.applayout.drawerSlot
import com.webforj.kotlin.dsl.component.layout.applayout.headerSlot
import com.webforj.kotlin.dsl.component.layout.appnav.appNav
import com.webforj.kotlin.dsl.component.layout.appnav.appNavItem
import com.webforj.kotlin.dsl.component.tabbedpane.prefixSlot
import com.webforj.kotlin.dsl.component.tabbedpane.tab
import com.webforj.kotlin.dsl.component.tabbedpane.tabbedPane
import com.webforj.kotlin.dsl.component.toolbar.startSlot
import com.webforj.kotlin.dsl.component.toolbar.titleSlot
import com.webforj.kotlin.dsl.component.toolbar.toolbar
import com.webforj.kotlin.extension.classNames
import com.webforj.kotlin.extension.plusAssign
import com.webforj.kotlin.extension.prefixSlot
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import com.webforj.router.history.ParametersBag
import com.webforj.samples.views.applayout.drawerLogo

@StyleSheet("ws://css/applayout/applayout.css")
@Route
@FrameTitle("AppLayout Sticky Toolbar")
class AppLayoutStickyToolbarKotlinView: Composite<AppLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      isDrawerHeaderVisible = false
      classNames += "layout--collapse"
      headerSlot {
        isHeaderFixed = false
        isHeaderReveal = true
        toolbar {
          startSlot { appDrawerToggle() }
          titleSlot { h3("Application") }
        }
        toolbar {
          isCompact = true
          startSlot {
            tabbedPane {
              isBorderless = true
              isBodyHidden = true
              tab("Sales") {
                prefixSlot { tabbedPane("report-money") }
              }
              tab("Enterprise") {
                prefixSlot { tabbedPane("building") }
              }
              tab("Payments") {
                prefixSlot { tabbedPane("credit-card") }
              }
              tab("History") {
                prefixSlot { tabbedPane("history") }
              }
            }
          }
        }
      }
      drawerSlot {
        drawerLogo()
        div {
          appNav {
            item("Dashboard", "dashboard")
            item("Orders", "shopping-cart")
            item("Customers", "user")
            item("Products", "box")
            item("Documents", "files")
            item("Tasks", "checklist")
            item("Analytics", "chart-dots-2")
          }
        }
      }
    }
  }

  private fun AppNav.item(text: String, icon: String) {
    appNavItem(text, view = AppLayoutStickyToolbarContentKotlinView::class, routeParameters = ParametersBag.of("name=$text")) {
      prefixSlot {
        tablerIcon(icon)
      }
    }
  }
}
