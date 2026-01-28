package com.webforj.samples.views.drawer

import com.webforj.component.Composite
import com.webforj.component.drawer.Drawer
import com.webforj.component.drawer.Drawer.Placement
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.drawer.drawer
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.optioninput.radioButton
import com.webforj.kotlin.dsl.component.optioninput.radioButtonGroup
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Drawer Placement")
class DrawerPlacementKotlinView: Composite<FlexLayout>() {

  init {
    boundComponent.apply {
      margin = "var(--dwc-space-l)"
      val openDrawer = button("Open Placement")
      val drawer = drawer("Drawer Placement Options") {
        styles["--dwc-drawer-max-width"] = "fit-content"
        flexLayout(FlexDirection.COLUMN) {
          spacing = "var(--dwc-space-s)"
          radioButtonGroup ("Placement Options") {
            val topOption = radioButton("Top")
            val topCenterOption = radioButton("Top Center")
            val bottomOption = radioButton("Bottom")
            val bottomCenterOption = radioButton("Bottom Center")
            val leftOption = radioButton("Left", true)
            val rightOption = radioButton("Right")
            this@flexLayout.add(topOption, topCenterOption, bottomOption, bottomCenterOption, leftOption, rightOption)
            onValueChange {
              val selected = checked
              selected?.let {
                when(selected.text) {
                  "Top" -> this@drawer.placement = Placement.TOP
                  "Top Center" -> this@drawer.placement = Placement.TOP_CENTER
                  "Bottom" -> this@drawer.placement = Placement.BOTTOM
                  "Bottom Center" -> this@drawer.placement = Placement.BOTTOM_CENTER
                  "Right" -> this@drawer.placement = Placement.RIGHT
                  else -> this@drawer.placement = Placement.LEFT
                }
              }
            }
          }
        }
        open()
      }
      openDrawer.onClick { drawer.open() }
    }
  }
}
