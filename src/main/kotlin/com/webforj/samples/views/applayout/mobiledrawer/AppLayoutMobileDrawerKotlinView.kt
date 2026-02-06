package com.webforj.samples.views.applayout.mobiledrawer

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.Expanse
import com.webforj.component.layout.applayout.AppLayout
import com.webforj.component.tabbedpane.TabbedPane
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.html.elements.h1
import com.webforj.kotlin.dsl.component.html.elements.h3
import com.webforj.kotlin.dsl.component.html.elements.paragraph
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.kotlin.dsl.component.layout.applayout.appDrawerToggle
import com.webforj.kotlin.dsl.component.layout.applayout.drawer
import com.webforj.kotlin.dsl.component.layout.applayout.footer
import com.webforj.kotlin.dsl.component.layout.applayout.header
import com.webforj.kotlin.dsl.component.layout.appnav.appNav
import com.webforj.kotlin.dsl.component.layout.appnav.appNavItem
import com.webforj.kotlin.dsl.component.tabbedpane.prefix
import com.webforj.kotlin.dsl.component.tabbedpane.tab
import com.webforj.kotlin.dsl.component.tabbedpane.tabbedPane
import com.webforj.kotlin.dsl.component.toolbar.start
import com.webforj.kotlin.dsl.component.toolbar.title
import com.webforj.kotlin.dsl.component.toolbar.toolbar
import com.webforj.kotlin.extension.prefix
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import com.webforj.router.history.ParametersBag
import com.webforj.samples.views.applayout.AbstractContentView
import com.webforj.samples.views.applayout.drawerLogo

@Route
@StyleSheet("ws://css/applayout/applayout.css")
@FrameTitle("AppLayout Mobile Drawer")
class AppLayoutMobileDrawerKotlinView: Composite<AppLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      isHeaderReveal = true
      header {
        toolbar {
          start {
            appDrawerToggle()
          }
          title { h3("Application") }
        }
      }
      drawer {
        div {
          addClassName("app-layout-drawer")
          drawerLogo()
          appNav {
            appNavItem("Dashboard", view = AppLayoutMobileDrawerContentKotlinView::class, routeParameters = ParametersBag.of("name=Dashboard")) {
              prefix { tablerIcon("dashboard") }
            }
            appNavItem("Orders", view = AppLayoutMobileDrawerContentKotlinView::class, routeParameters = ParametersBag.of("name=Orders")) {
              prefix { tablerIcon("shopping-cart") }
            }
            appNavItem("Customers", view = AppLayoutMobileDrawerContentKotlinView::class, routeParameters = ParametersBag.of("name=Customers")) {
              prefix { tablerIcon("user") }
            }
            appNavItem("Products", view = AppLayoutMobileDrawerContentKotlinView::class, routeParameters = ParametersBag.of("name=Products")) {
              prefix { tablerIcon("box") }
            }
            appNavItem("Documents", view = AppLayoutMobileDrawerContentKotlinView::class, routeParameters = ParametersBag.of("name=Documents")) {
              prefix { tablerIcon("files") }
            }
            appNavItem("Tasks", view = AppLayoutMobileDrawerContentKotlinView::class, routeParameters = ParametersBag.of("name=Tasks")) {
              prefix { tablerIcon("checklist") }
            }
            appNavItem("Analytics", view = AppLayoutMobileDrawerContentKotlinView::class, routeParameters = ParametersBag.of("name=Analytics")) {
              prefix { tablerIcon("chart-dots-2") }
            }
          }
        }
      }
      h1("Application Title")
      paragraph("Content goes here...")
      isFooterReveal = true
      footer {
        tabbedPane {
          isBodyHidden = true
          isBorderless = true
          placement = TabbedPane.Placement.BOTTOM
          alignment = TabbedPane.Alignment.STRETCH
          expanse = Expanse.XLARGE
          tab("") {
            prefix { tabbedPane("dashboard") }
          }
          tab("") {
            prefix { tabbedPane("shopping-cart") }
          }
          tab("") {
            prefix { tabbedPane("users") }
          }
          tab("") {
            prefix { tabbedPane("box") }
          }
          tab("") {
            prefix { tabbedPane("files") }
          }
        }
      }
    }
  }
}

@Route(value = "/content/:name", outlet = AppLayoutMobileDrawerKotlinView::class)
class AppLayoutMobileDrawerContentKotlinView : AbstractContentView()
