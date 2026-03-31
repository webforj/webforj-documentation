package com.webforj.samples.views.applayout.applayoutdrawerutility

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.layout.applayout.AppLayout
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.html.elements.h3
import com.webforj.kotlin.dsl.component.icons.iconButton
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.kotlin.dsl.component.layout.applayout.appDrawerToggle
import com.webforj.kotlin.dsl.component.layout.applayout.drawerHeaderActionsSlot
import com.webforj.kotlin.dsl.component.layout.applayout.drawerSlot
import com.webforj.kotlin.dsl.component.layout.applayout.drawerTitleSlot
import com.webforj.kotlin.dsl.component.layout.applayout.headerSlot
import com.webforj.kotlin.dsl.component.layout.appnav.appNav
import com.webforj.kotlin.dsl.component.layout.appnav.appNavItem
import com.webforj.kotlin.dsl.component.toolbar.startSlot
import com.webforj.kotlin.dsl.component.toolbar.titleSlot
import com.webforj.kotlin.dsl.component.toolbar.toolbar
import com.webforj.kotlin.extension.prefixSlot
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import com.webforj.router.history.ParametersBag
import com.webforj.samples.views.applayout.AbstractContentView
import com.webforj.samples.views.applayout.drawerLogo

@StyleSheet("ws://css/applayout/applayout.css")
@Route
@FrameTitle("AppLayout")
class AppLayoutDrawerUtilityKotlinView: Composite<AppLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      headerSlot {
        toolbar {
          startSlot { appDrawerToggle() }
          titleSlot { h3("Application") }
        }
      }
      drawerSlot {
        div {
          drawerLogo()
          appNav {
            appNavItem("Dashboard", view = AppLayoutDrawerUtilityContentKotlinView::class, routeParameters = ParametersBag.of("name=Dashboard")) {
              prefixSlot { tablerIcon("dashboard") }
            }
            appNavItem("Orders", view = AppLayoutDrawerUtilityContentKotlinView::class, routeParameters = ParametersBag.of("name=Orders")) {
              prefixSlot { tablerIcon("shopping-cart") }
            }
            appNavItem("Customers", view = AppLayoutDrawerUtilityContentKotlinView::class, routeParameters = ParametersBag.of("name=Customers")) {
              prefixSlot { tablerIcon("user") }
            }
            appNavItem("Products", view = AppLayoutDrawerUtilityContentKotlinView::class, routeParameters = ParametersBag.of("name=Products")) {
              prefixSlot { tablerIcon("box") }
            }
            appNavItem("Documents", view = AppLayoutDrawerUtilityContentKotlinView::class, routeParameters = ParametersBag.of("name=Documents")) {
              prefixSlot { tablerIcon("files") }
            }
            appNavItem("Tasks", view = AppLayoutDrawerUtilityContentKotlinView::class, routeParameters = ParametersBag.of("name=Tasks")) {
              prefixSlot { tablerIcon("checklist") }
            }
            appNavItem("Analytics", view = AppLayoutDrawerUtilityContentKotlinView::class, routeParameters = ParametersBag.of("name=Analytics")) {
              prefixSlot { tablerIcon("chart-dots-2") }
            }
          }
        }
      }
      drawerTitleSlot { div("Jow Smith") }
      drawerHeaderActionsSlot {
        iconButton("pin", "tabler") {
          tooltipText = "Pin drawer"
        }
        iconButton("rocket", "tabler") {
          tooltipText = "Buy premium"
        }
      }
      isDrawerHeaderVisible = true
      isDrawerOpened = true
    }
  }
}

@Route(value = "/content/:name", outlet = AppLayoutDrawerUtilityKotlinView::class)
class AppLayoutDrawerUtilityContentKotlinView : AbstractContentView()
