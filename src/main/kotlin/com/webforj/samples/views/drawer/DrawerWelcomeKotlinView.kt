package com.webforj.samples.views.drawer

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.button.Button
import com.webforj.component.button.ButtonTheme
import com.webforj.component.drawer.Drawer
import com.webforj.component.layout.applayout.AppLayout
import com.webforj.component.layout.applayout.AppLayout.DrawerPlacement
import com.webforj.component.layout.appnav.AppNav
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.drawer.drawer
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.html.elements.h1
import com.webforj.kotlin.dsl.component.html.elements.h2
import com.webforj.kotlin.dsl.component.html.elements.h3
import com.webforj.kotlin.dsl.component.html.elements.img
import com.webforj.kotlin.dsl.component.html.elements.paragraph
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.kotlin.dsl.component.layout.applayout.appDrawerToggle
import com.webforj.kotlin.dsl.component.layout.applayout.appLayout
import com.webforj.kotlin.dsl.component.layout.applayout.drawerSlot
import com.webforj.kotlin.dsl.component.layout.applayout.headerSlot
import com.webforj.kotlin.dsl.component.layout.appnav.appNav
import com.webforj.kotlin.dsl.component.layout.appnav.appNavItem
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.vertical
import com.webforj.kotlin.dsl.component.toolbar.startSlot
import com.webforj.kotlin.dsl.component.toolbar.titleSlot
import com.webforj.kotlin.dsl.component.toolbar.toolbar
import com.webforj.kotlin.extension.classNames
import com.webforj.kotlin.extension.percent
import com.webforj.kotlin.extension.plus
import com.webforj.kotlin.extension.prefixSlot
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.size
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@StyleSheet("ws://css/drawer/drawerWelcome.css")
@Route
@FrameTitle("Drawer Welcome App")
class DrawerWelcomeKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent
  private var openWelcome: Button? = null

  init {
    self.apply {
      appLayout {
        isHeaderReveal = true
        drawerPlacement = DrawerPlacement.LEFT
        h1("Application Title")
        openWelcome = button("Open Welcome Drawer")
        headerSlot {
          toolbar {
            startSlot { appDrawerToggle() }
            titleSlot { h3("webforj Application") }
          }
        }
        drawerSlot {
          div {
            toolbar {
              classNames + "webforJ-logo"
              width = 100.percent
              img("ws://img/webforj_icon.svg", "logo")
            }
            appNav {
              isAutoOpen = true
              addAppNavItem("Dashboard", "dashboard")
              addAppNavItem("Orders", "shopping-cart")
              addAppNavItem("Customers", "users")
              addAppNavItem("Products", "box")
              addAppNavItem("Documents", "files")
            }
          }
        }
      }
      val drawer = drawer {
        placement = Drawer.Placement.BOTTOM_CENTER
        classNames + "welcome__drawer"
        open()
        flexLayout {
          vertical()
          alignment = FlexAlignment.CENTER
          justifyContent = FlexJustifyContent.CENTER
          img("/fun.svg", "A gathering of people.") {
            size = 200.px to 200.px
          }
          h2("Welcome to webforJ")
          paragraph("Lorem Ipsum is simply dummy text of the printing and typesetting industry") {
            styles["text-align"] = "center"
          }
          button("Get Started", ButtonTheme.PRIMARY) {
            onClick { this@drawer.close() }
          }
        }
      }
      openWelcome?.onClick { drawer.open() }
    }
  }

  private fun AppNav.addAppNavItem(text: String, icon: String) {
    appNavItem(text, path = "/drawercwelcome") {
      prefixSlot { tablerIcon(icon) }
    }
  }
}
