package com.webforj.samples.views.applayout.applayout

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.layout.applayout.AppLayout
import com.webforj.component.layout.appnav.AppNav
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.html.elements.h3
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.kotlin.dsl.component.layout.applayout.drawerSlot
import com.webforj.kotlin.dsl.component.layout.applayout.headerSlot
import com.webforj.kotlin.dsl.component.layout.appnav.appNav
import com.webforj.kotlin.dsl.component.layout.appnav.appNavItem
import com.webforj.kotlin.dsl.component.toolbar.titleSlot
import com.webforj.kotlin.dsl.component.toolbar.toolbar
import com.webforj.kotlin.extension.prefixSlot
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import com.webforj.router.history.ParametersBag
import com.webforj.samples.views.applayout.applayout.AppLayoutContentKotlinView
import com.webforj.samples.views.applayout.drawerLogo

@Route
@StyleSheet("ws://css/applayout/applayout.css")
@FrameTitle("AppLayout")
class AppLayoutKotlinView: Composite<AppLayout>() {
  private val self = boundComponent

  init {
    boundComponent.apply {
      headerSlot {
        toolbar {
          titleSlot { h3("Application") }
        }
      }
      drawerSlot {
        div {
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
    }
  }

  private fun AppNav.item(text: String, icon: String) {
    appNavItem(text, view = AppLayoutContentKotlinView::class, routeParameters = ParametersBag.of("name=$text")) {
      prefixSlot {
        tablerIcon(icon)
      }
    }
  }
}