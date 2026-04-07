package com.webforj.samples.views.appnav

import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.layout.applayout.AppLayout
import com.webforj.kotlin.dsl.component.html.elements.h1
import com.webforj.kotlin.dsl.component.html.elements.strong
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.kotlin.dsl.component.layout.applayout.appDrawerToggle
import com.webforj.kotlin.dsl.component.layout.applayout.drawerSlot
import com.webforj.kotlin.dsl.component.layout.applayout.headerSlot
import com.webforj.kotlin.dsl.component.layout.appnav.appNav
import com.webforj.kotlin.dsl.component.layout.appnav.appNavItem
import com.webforj.kotlin.dsl.component.toolbar.startSlot
import com.webforj.kotlin.dsl.component.toolbar.titleSlot
import com.webforj.kotlin.dsl.component.toolbar.toolbar
import com.webforj.kotlin.extension.prefixSlot
import com.webforj.kotlin.extension.suffixSlot
import com.webforj.router.annotation.Route
import com.webforj.router.history.ParametersBag

@Route
class AppNavKotlinView: Composite<AppLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      isHeaderOffscreen = false
      isDrawerHeaderVisible = true
      headerSlot {
        toolbar {
          theme = Theme.PRIMARY
          startSlot { appDrawerToggle() }
          titleSlot { h1("Application") }
        }
      }
      drawerSlot{
        appNav {
          isAutoOpen = true
          appNavItem("Inbox") {
            prefixSlot { tablerIcon("inbox") }
            suffixSlot { strong("54") }
            appNavItem("Primary", view = AppNavPageKotlinView::class, routeParameters = ParametersBag.of("id=Primary")) {
              prefixSlot { tablerIcon("mailbox") }
            }
            appNavItem("Promotions", view = AppNavPageKotlinView::class, routeParameters = ParametersBag.of("id=Promotions")) {
              prefixSlot { tablerIcon("tag") }
            }
            appNavItem("Social", view = AppNavPageKotlinView::class, routeParameters = ParametersBag.of("id=Social")) {
              prefixSlot { tablerIcon("users") }
            }
            appNavItem("Updates", view = AppNavPageKotlinView::class, routeParameters = ParametersBag.of("id=Updates")) {
              prefixSlot { tablerIcon("bell") }
            }
            appNavItem("Forums", view = AppNavPageKotlinView::class, routeParameters = ParametersBag.of("id=Forums")) {
              prefixSlot { tablerIcon("message-circle") }
            }
          }
          appNavItem("Sent", view = AppNavPageKotlinView::class, routeParameters = ParametersBag.of("id=Sent")) {
            prefixSlot { tablerIcon("send") }
          }
          appNavItem("Archived", view = AppNavPageKotlinView::class, routeParameters = ParametersBag.of("id=Archived")) {
            prefixSlot { tablerIcon("archive") }
          }
          appNavItem("Trash", view = AppNavPageKotlinView::class, routeParameters = ParametersBag.of("id=Trash")) {
            prefixSlot { tablerIcon("trash") }
          }
          appNavItem("Spam", view = AppNavPageKotlinView::class, routeParameters = ParametersBag.of("id=Spam")) {
            prefixSlot { tablerIcon("alert-hexagon") }
          }
          appNavItem("About") {
            prefixSlot { tablerIcon("info-circle") }
            appNavItem("webforJ", "https://webforj.com/") {
              prefixSlot { tablerIcon("external-link") }
            }
            appNavItem("GitHub", "https://github.com/webforj/webforj") {
              prefixSlot { tablerIcon("brand-github") }
            }
            appNavItem("Documentation", "https://documentation.webforj.com/") {
              prefixSlot { tablerIcon("book") }
            }
          }
        }
      }
    }
  }
}
