package com.webforj.samples.views.applayout.stickytoolbar

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.layout.applayout.AppLayout
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.html.elements.h3
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.kotlin.dsl.component.layout.applayout.appDrawerToggle
import com.webforj.kotlin.dsl.component.layout.applayout.drawer
import com.webforj.kotlin.dsl.component.layout.applayout.header
import com.webforj.kotlin.dsl.component.layout.appnav.appNav
import com.webforj.kotlin.dsl.component.layout.appnav.appNavItem
import com.webforj.kotlin.dsl.component.tabbedpane.prefix
import com.webforj.kotlin.dsl.component.tabbedpane.tab
import com.webforj.kotlin.dsl.component.tabbedpane.tabbedPane
import com.webforj.kotlin.dsl.component.toolbar.start
import com.webforj.kotlin.dsl.component.toolbar.title
import com.webforj.kotlin.dsl.component.toolbar.toolbar
import com.webforj.kotlin.extension.classNames
import com.webforj.kotlin.extension.plusAssign
import com.webforj.kotlin.extension.prefix
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
      header {
        isHeaderFixed = false
        isHeaderReveal = true
        toolbar {
          start { appDrawerToggle() }
          title { h3("Application") }
        }
        toolbar {
          isCompact = true
          start {
            tabbedPane {
              isBorderless = true
              isBodyHidden = true
              tab("Sales") {
                prefix { tabbedPane("report-money") }
              }
              tab("Enterprise") {
                prefix { tabbedPane("building") }
              }
              tab("Payments") {
                prefix { tabbedPane("credit-card") }
              }
              tab("History") {
                prefix { tabbedPane("history") }
              }
            }
          }
        }
      }
      drawer {
        drawerLogo()
        div {
          appNav {
            appNavItem("Dashboard", view = AppLayoutStickyToolbarContentKotlinView::class, routeParameters = ParametersBag.of("name=Dashboard")) {
              prefix { tablerIcon("dashboard") }
            }
            appNavItem("Orders", view = AppLayoutStickyToolbarContentKotlinView::class, routeParameters = ParametersBag.of("name=Orders")) {
              prefix { tablerIcon("shopping-cart") }
            }
            appNavItem("Customers", view = AppLayoutStickyToolbarContentKotlinView::class, routeParameters = ParametersBag.of("name=Customers")) {
              prefix { tablerIcon("user") }
            }
            appNavItem("Products", view = AppLayoutStickyToolbarContentKotlinView::class, routeParameters = ParametersBag.of("name=Products")) {
              prefix { tablerIcon("box") }
            }
            appNavItem("Documents", view = AppLayoutStickyToolbarContentKotlinView::class, routeParameters = ParametersBag.of("name=Documents")) {
              prefix { tablerIcon("files") }
            }
            appNavItem("Tasks", view = AppLayoutStickyToolbarContentKotlinView::class, routeParameters = ParametersBag.of("name=Tasks")) {
              prefix { tablerIcon("checklist") }
            }
            appNavItem("Analytics", view = AppLayoutStickyToolbarContentKotlinView::class, routeParameters = ParametersBag.of("name=Analytics")) {
              prefix { tablerIcon("chart-dots-2") }
            }
          }
        }
      }
    }
  }
}