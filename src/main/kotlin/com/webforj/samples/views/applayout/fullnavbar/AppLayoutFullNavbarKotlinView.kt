package com.webforj.samples.views.applayout.fullnavbar

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.layout.applayout.AppLayout
import com.webforj.kotlin.dsl.component.html.elements.h3
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.kotlin.dsl.component.layout.applayout.appDrawerToggle
import com.webforj.kotlin.dsl.component.layout.applayout.drawer
import com.webforj.kotlin.dsl.component.layout.applayout.header
import com.webforj.kotlin.dsl.component.layout.appnav.appNav
import com.webforj.kotlin.dsl.component.layout.appnav.appNavItem
import com.webforj.kotlin.dsl.component.toolbar.start
import com.webforj.kotlin.dsl.component.toolbar.title
import com.webforj.kotlin.dsl.component.toolbar.toolbar
import com.webforj.kotlin.extension.prefix
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import com.webforj.router.history.ParametersBag
import com.webforj.samples.views.applayout.AbstractContentView

@Route
@StyleSheet("ws://css/applayout/applayout.css")
@FrameTitle("AppLayout Full Navbar")
class AppLayoutFullNavbarKotlinView: Composite<AppLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      isDrawerHeaderVisible = false
      isDrawerFooterVisible = true
      isHeaderOffscreen = false
      header {
        toolbar {
          start { appDrawerToggle() }
          title { h3(" Application") }
        }
      }
      drawer {
        appNav {
          appNavItem("Dashboard", view = AppLayoutFullNavbarContentKotlinView::class, routeParameters = ParametersBag.of("name=Dashboard")) {
            prefix { tablerIcon("dashboard") }
          }
          appNavItem("Orders", view = AppLayoutFullNavbarContentKotlinView::class, routeParameters = ParametersBag.of("name=Orders")) {
            prefix { tablerIcon("shopping-cart") }
          }
          appNavItem("Customers", view = AppLayoutFullNavbarContentKotlinView::class, routeParameters = ParametersBag.of("name=Customers")) {
            prefix { tablerIcon("user") }
          }
          appNavItem("Products", view = AppLayoutFullNavbarContentKotlinView::class, routeParameters = ParametersBag.of("name=Products")) {
            prefix { tablerIcon("box") }
          }
          appNavItem("Documents", view = AppLayoutFullNavbarContentKotlinView::class, routeParameters = ParametersBag.of("name=Documents")) {
            prefix { tablerIcon("files") }
          }
          appNavItem("Tasks", view = AppLayoutFullNavbarContentKotlinView::class, routeParameters = ParametersBag.of("name=Tasks")) {
            prefix { tablerIcon("checklist") }
          }
          appNavItem("Analytics", view = AppLayoutFullNavbarContentKotlinView::class, routeParameters = ParametersBag.of("name=Analytics")) {
            prefix { tablerIcon("chart-dots-2") }
          }
        }
      }
    }
  }
}

@Route(value = "/content/:name", outlet = AppLayoutFullNavbarKotlinView::class)
class AppLayoutFullNavbarContentKotlinView : AbstractContentView()
