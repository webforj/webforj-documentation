package com.webforj.samples.views.appnav

import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.layout.applayout.AppLayout
import com.webforj.kotlin.dsl.component.html.elements.h1
import com.webforj.kotlin.dsl.component.html.elements.strong
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
import com.webforj.kotlin.extension.suffix
import com.webforj.router.annotation.Route
import com.webforj.router.history.ParametersBag
import javax.security.auth.login.Configuration

@Route
class AppNavKotlinView: Composite<AppLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      isHeaderOffscreen = false
      isDrawerHeaderVisible = true
      header {
        toolbar {
          theme = Theme.PRIMARY
          start { appDrawerToggle() }
          title { h1("Application") }
        }
      }
      drawer {
        appNav {
          isAutoOpen = true
          appNavItem("Inbox") {
            prefix { tablerIcon("inbox") }
            suffix { strong("54") }
            appNavItem("Primary", view = AppNavPageKotlinView::class, routeParameters = ParametersBag.of("id=Primary")) {
              prefix { tablerIcon("mailbox") }
            }
            appNavItem("Promotions", view = AppNavPageKotlinView::class, routeParameters = ParametersBag.of("id=Promotions")) {
              prefix { tablerIcon("tag") }
            }
            appNavItem("Social", view = AppNavPageKotlinView::class, routeParameters = ParametersBag.of("id=Social")) {
              prefix { tablerIcon("users") }
            }
            appNavItem("Updates", view = AppNavPageKotlinView::class, routeParameters = ParametersBag.of("id=Updates")) {
              prefix { tablerIcon("bell") }
            }
            appNavItem("Forums", view = AppNavPageKotlinView::class, routeParameters = ParametersBag.of("id=Forums")) {
              prefix { tablerIcon("message-circle") }
            }
          }
          appNavItem("Sent", view = AppNavPageKotlinView::class, routeParameters = ParametersBag.of("id=Sent")) {
            prefix { tablerIcon("send") }
          }
          appNavItem("Archived", view = AppNavPageKotlinView::class, routeParameters = ParametersBag.of("id=Archived")) {
            prefix { tablerIcon("archive") }
          }
          appNavItem("Trash", view = AppNavPageKotlinView::class, routeParameters = ParametersBag.of("id=Trash")) {
            prefix { tablerIcon("trash") }
          }
          appNavItem("Spam", view = AppNavPageKotlinView::class, routeParameters = ParametersBag.of("id=Spam")) {
            prefix { tablerIcon("alert-hexagon") }
          }
          appNavItem("About") {
            prefix { tablerIcon("info-circle") }
            appNavItem("webforj", "https://webforj.com/") {
              prefix { tablerIcon("external-link") }
            }
            appNavItem("GitHub", "https://github.com/webforj/webforj") {
              prefix { tablerIcon("brand-github") }
            }
            appNavItem("Documentation", "https://documentation.webforj.com/") {
              prefix { tablerIcon("book") }
            }
          }
        }
      }
    }
  }
}