package com.webforj.samples.views.applayout.applayout

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.layout.applayout.AppLayout
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.html.elements.h3
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.kotlin.dsl.component.layout.applayout.drawer
import com.webforj.kotlin.dsl.component.layout.applayout.header
import com.webforj.kotlin.dsl.component.layout.appnav.appNav
import com.webforj.kotlin.dsl.component.layout.appnav.appNavItem
import com.webforj.kotlin.dsl.component.toolbar.title
import com.webforj.kotlin.dsl.component.toolbar.toolbar
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import com.webforj.router.history.ParametersBag
import com.webforj.samples.views.applayout.AbstractContentView
import com.webforj.samples.views.applayout.drawerLogo

@Route
@StyleSheet("ws://css/applayout/applayout.css")
@FrameTitle("AppLayout")
class AppLayoutKotlinView: Composite<AppLayout>() {

  init {
    boundComponent.apply {
      header {
        toolbar {
          title { h3("Application") }
        }
      }
      drawer {
        div {
          drawerLogo()
          appNav {
            appNavItem("Dashboard", view = AppLayoutContentKotlinView::class, routeParameters = ParametersBag.of("name=Dashboard")) {
              tablerIcon("dashboard")
            }
            appNavItem("Orders", view = AppLayoutContentKotlinView::class, routeParameters = ParametersBag.of("name=Orders")) {
              tablerIcon("shopping-cart")
            }
            appNavItem("Customers", view = AppLayoutContentKotlinView::class, routeParameters = ParametersBag.of("name=Customers")) {
              tablerIcon("user")
            }
            appNavItem("Products", view = AppLayoutContentKotlinView::class, routeParameters = ParametersBag.of("name=Products")) {
              tablerIcon("box")
            }
            appNavItem("Documents", view = AppLayoutContentKotlinView::class, routeParameters = ParametersBag.of("name=Documents")) {
              tablerIcon("files")
            }
            appNavItem("Tasks", view = AppLayoutContentKotlinView::class, routeParameters = ParametersBag.of("name=Tasks")) {
              tablerIcon("checklist")
            }
            appNavItem("Analytics", view = AppLayoutContentKotlinView::class, routeParameters = ParametersBag.of("name=Analytics")) {
              tablerIcon("chart-dots-2")
            }
          }
        }
      }
    }
  }
}

@Route(value = "/content/:name", outlet = AppLayoutKotlinView::class)
class AppLayoutContentKotlinView: AbstractContentView()