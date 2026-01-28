package com.webforj.samples.views.drawer

import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.drawer.Drawer
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.drawer.addToFooter
import com.webforj.kotlin.dsl.component.drawer.drawer
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.optioninput.checkBox
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Drawer AutoFocus")
class DrawerAutoFocusKotlinView: Composite<FlexLayout>() {
  val drawer: Drawer

  init {
      boundComponent.apply {
        margin = "var(--dwc-space-m)"
        button("Open Preferences") {
          onClick { drawer.open() }
        }
        drawer = drawer("Notification Preferences") {
          isAutoFocus = true
          flexLayout(FlexDirection.COLUMN) {
            spacing = "var(--dwc-space-s)"
            checkBox("Email Notifications")
            checkBox("SMS Notifications")
            checkBox("Push Notifications")
          }
          addToFooter {
            button("Save Preferences", ButtonTheme.PRIMARY) {
              width = "100%"
            }
          }
        }
      }
  }
}
