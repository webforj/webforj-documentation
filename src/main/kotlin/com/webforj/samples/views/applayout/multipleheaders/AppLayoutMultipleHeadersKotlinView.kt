package com.webforj.samples.views.applayout.multipleheaders

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.layout.applayout.AppLayout
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
import com.webforj.samples.views.applayout.AbstractContentView
import com.webforj.samples.views.applayout.drawerLogo

@Route
@StyleSheet("ws://css/applayout/applayout.css")
@FrameTitle("AppLayout Multiple Headers")
class AppLayoutMultipleHeadersKotlinView: Composite<AppLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      isDrawerHeaderVisible = false
      isDrawerFooterVisible = true
      isHeaderReveal = true
      headerSlot {
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
        div {
          classNames += "app-layout-drawer"
          drawerLogo()
          appNav {
            appNavItem("Dashboard", view = AppLayoutMultipleHeaderContentKotlinView::class, routeParameters = ParametersBag.of("name=Dashboard")) {
              prefixSlot { tablerIcon("dashboard") }
            }
            appNavItem("Orders", view = AppLayoutMultipleHeaderContentKotlinView::class, routeParameters = ParametersBag.of("name=Orders")) {
              prefixSlot { tablerIcon("shopping-cart") }
            }
            appNavItem("Customers", view = AppLayoutMultipleHeaderContentKotlinView::class, routeParameters = ParametersBag.of("name=Customers")) {
              prefixSlot { tablerIcon("user") }
            }
            appNavItem("Products", view = AppLayoutMultipleHeaderContentKotlinView::class, routeParameters = ParametersBag.of("name=Products")) {
              prefixSlot { tablerIcon("box") }
            }
            appNavItem("Documents", view = AppLayoutMultipleHeaderContentKotlinView::class, routeParameters = ParametersBag.of("name=Documents")) {
              prefixSlot { tablerIcon("files") }
            }
            appNavItem("Tasks", view = AppLayoutMultipleHeaderContentKotlinView::class, routeParameters = ParametersBag.of("name=Tasks")) {
              prefixSlot { tablerIcon("checklist") }
            }
            appNavItem("Analytics", view = AppLayoutMultipleHeaderContentKotlinView::class, routeParameters = ParametersBag.of("name=Analytics")) {
              prefixSlot { tablerIcon("chart-dots-2") }
            }
          }
        }
      }
    }
  }
}

@Route(value = "/content/:name", outlet = AppLayoutMultipleHeaderContentKotlinView::class)
class AppLayoutMultipleHeaderContentKotlinView : AbstractContentView()
