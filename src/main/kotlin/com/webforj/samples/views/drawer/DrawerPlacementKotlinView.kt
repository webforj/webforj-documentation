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
  private val self = boundComponent

  init {
    self.apply {
      margin = "var(--dwc-space-l)"
      val openDrawer = button("Open Placement")
      drawer("Drawer Placement Options") {
        open()
        styles["--dwc-drawer-max-width"] = "fit-content"
        openDrawer.onClick { open() }
        flexLayout(FlexDirection.COLUMN) {
          spacing = "var(--dwc-space-s)"
          radioButtonGroup("Placement Options") {
            Placement.entries.forEach { placement ->
              val text = placement.name.split("_")
                .joinToString(" ") {
                  it.lowercase().replaceFirstChar { char ->
                    char.uppercase()
                  }
                }
              val checked = placement == Placement.LEFT
              radioButton(text, checked) {
                setUserData("placement", placement)
              }
            }
            /*radioButton("Top") {
              setUserData("placement", Placement.TOP)
            }
            radioButton("Top Center") {
              setUserData("placement", Placement.TOP_CENTER)
            }
            radioButton("Bottom") {
              setUserData("placement", Placement.BOTTOM)
            }
            radioButton("Bottom Center") {
              setUserData("placement", Placement.BOTTOM_CENTER)
            }
            radioButton("Left", true) {
              setUserData("placement", Placement.LEFT)
            }
            radioButton("Right") {
              setUserData("placement", Placement.RIGHT)
            }*/
            onChange { e ->
              e.checked?.let {
                this@drawer.placement = it.getUserData("placement") as Placement
              }
            }
          }
        }
      }
    }
  }
}