package com.webforj.samples.views.applayout.mobiledrawer

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.Expanse
import com.webforj.component.layout.applayout.AppLayout
import com.webforj.component.layout.appnav.AppNav
import com.webforj.component.tabbedpane.TabbedPane
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.html.elements.h1
import com.webforj.kotlin.dsl.component.html.elements.h3
import com.webforj.kotlin.dsl.component.html.elements.paragraph
import com.webforj.kotlin.dsl.component.icons.iconButton
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.kotlin.dsl.component.layout.applayout.appDrawerToggle
import com.webforj.kotlin.dsl.component.layout.applayout.drawerSlot
import com.webforj.kotlin.dsl.component.layout.applayout.footerSlot
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

@Route
@StyleSheet("ws://css/applayout/applayout.css")
@FrameTitle("AppLayout Mobile Drawer")
class AppLayoutMobileDrawerKotlinView: Composite<AppLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      isHeaderReveal = true
      headerSlot {
        toolbar {
          startSlot {
            appDrawerToggle()
          }
          titleSlot { h3("Application") }
        }
      }
      drawerSlot {
        div {
          classNames += "app-layout-drawer"
          drawerLogo()
          appNav {
            item("Dashboard", "dashboard")
            item("Orders", "shopping-cart")
            item("Customers", "users")
            item("Products", "box")
            item("Documents", "files")
            item("Tasks", "checklist")
            item("Analytics", "chart-dots-2")
          }
        }
      }
      h1("Application Title")
      paragraph("Content goes here...")
      isFooterReveal = true
      footerSlot {
        tabbedPane {
          isBodyHidden = true
          isBorderless = true
          placement = TabbedPane.Placement.BOTTOM
          alignment = TabbedPane.Alignment.STRETCH
          expanse = Expanse.XLARGE
          tab("") {
            prefixSlot { iconButton("dashboard", "tabler") }
          }
          tab("") {
            prefixSlot { iconButton("shopping-cart", "tabler") }
          }
          tab("") {
            prefixSlot { iconButton("users", "tabler") }
          }
          tab("") {
            prefixSlot { iconButton("box", "tabler") }
          }
          tab("") {
            prefixSlot { iconButton("files", "tabler") }
          }
        }
      }
    }
  }

  private fun AppNav.item(text: String, icon: String) {
    appNavItem(text, view = AppLayoutMobileDrawerContentKotlinView::class, routeParameters = ParametersBag.of("name=$text")) {
      prefixSlot {
        tablerIcon(icon)
      }
    }
  }
}
