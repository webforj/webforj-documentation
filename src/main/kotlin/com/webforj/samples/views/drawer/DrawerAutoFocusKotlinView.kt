package com.webforj.samples.views.drawer

import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.drawer.drawer
import com.webforj.kotlin.dsl.component.drawer.footerSlot
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.optioninput.checkBox
import com.webforj.kotlin.extension.percent
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Drawer AutoFocus")
class DrawerAutoFocusKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      margin = "var(--dwc-space-m)"
      val openDrawerButton = button("Open Preferences")
      val drawer = drawer("Notification Preferences") {
        isAutoFocus = true
        open()
        flexLayout(FlexDirection.COLUMN) {
          spacing = "var(--dwc-space-s)"
          checkBox("Email Notifications")
          checkBox("SMS Notifications")
          checkBox("Push Notifications")
        }
        footerSlot {
          button("Save Preferences", ButtonTheme.PRIMARY) {
            width = 100.percent
          }
        }

      }
      openDrawerButton.onClick { drawer.open() }
    }
  }
}
