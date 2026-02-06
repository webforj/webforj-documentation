package com.webforj.samples.views.applayout.applayoutdrawerutility

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.layout.applayout.AppLayout
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.html.elements.h3
import com.webforj.kotlin.dsl.component.icons.iconButton
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.kotlin.dsl.component.layout.applayout.appDrawerToggle
import com.webforj.kotlin.dsl.component.layout.applayout.drawer
import com.webforj.kotlin.dsl.component.layout.applayout.drawerHeaderActions
import com.webforj.kotlin.dsl.component.layout.applayout.drawerTitle
import com.webforj.kotlin.dsl.component.layout.applayout.header
import com.webforj.kotlin.dsl.component.layout.appnav.appNav
import com.webforj.kotlin.dsl.component.layout.appnav.appNavItem
import com.webforj.kotlin.dsl.component.layout.appnav.prefix
import com.webforj.kotlin.dsl.component.toolbar.start
import com.webforj.kotlin.dsl.component.toolbar.title
import com.webforj.kotlin.dsl.component.toolbar.toolbar
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import com.webforj.router.history.ParametersBag
import com.webforj.samples.views.applayout.AbstractContentView
import com.webforj.samples.views.applayout.drawerLogo

@StyleSheet("ws://css/applayout/applayout.css")
@Route
@FrameTitle("AppLayout")
class AppLayoutDrawerUtilityKotlinView: Composite<AppLayout>() {

  init {
    boundComponent.apply {
      header {
        toolbar {
          start { appDrawerToggle() }
          title { h3("Application") }
        }
      }
      drawer {
        div {
          drawerLogo()
          appNav {
            appNavItem("Dashboard", view = AppLayoutDrawerUtilityContentKotlinView::class, routeParameters = ParametersBag.of("name=Dashboard")) {
              prefix { tablerIcon("dashboard") }
            }
            appNavItem("Orders", view = AppLayoutDrawerUtilityContentKotlinView::class, routeParameters = ParametersBag.of("name=Orders")) {
              tablerIcon("shopping-cart")
            }
            appNavItem("Customers", view = AppLayoutDrawerUtilityContentKotlinView::class, routeParameters = ParametersBag.of("name=Customers")) {
              prefix { tablerIcon("user") }
            }
            appNavItem("Products", view = AppLayoutDrawerUtilityContentKotlinView::class, routeParameters = ParametersBag.of("name=Products")) {
              prefix { tablerIcon("box") }
            }
            appNavItem("Documents", view = AppLayoutDrawerUtilityContentKotlinView::class, routeParameters = ParametersBag.of("name=Documents")) {
              prefix { tablerIcon("files") }
            }
            appNavItem("Tasks", view = AppLayoutDrawerUtilityContentKotlinView::class, routeParameters = ParametersBag.of("name=Tasks")) {
              prefix { tablerIcon("checklist") }
            }
            appNavItem("Analytics", view = AppLayoutDrawerUtilityContentKotlinView::class, routeParameters = ParametersBag.of("name=Analytics")) {
              prefix { tablerIcon("chart-dots-2") }
            }
          }
        }
      }
      drawerTitle { div("Jow Smith") }
      drawerHeaderActions {
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
