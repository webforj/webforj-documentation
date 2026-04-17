package com.webforj.samples.views.toast

import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.toast.Toast
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.list.choiceBox
import com.webforj.kotlin.dsl.component.list.items
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.vh
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Toast Placements")
class ToastPlacementKotlinView : Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.ROW
      alignment = FlexAlignment.CENTER
      justifyContent = FlexJustifyContent.CENTER
      height = 100.vh
      spacing = "var(--dwc-space-m)"

      val placementChoiceBox = choiceBox {
        minWidth = 160.px
        val placements = listOf(
          Toast.Placement.TOP,
          Toast.Placement.TOP_LEFT,
          Toast.Placement.TOP_RIGHT,
          Toast.Placement.CENTER,
          Toast.Placement.BOTTOM,
          Toast.Placement.BOTTOM_LEFT,
          Toast.Placement.BOTTOM_RIGHT
        )
        items(*placements.map { it to it.name }.toTypedArray())
        selectIndex(4)
      }
      button("Show Toast", ButtonTheme.PRIMARY) {
        onClick {
          Toast.show("This is a toast notification", placementChoiceBox.selectedItem.key as Toast.Placement)
        }
      }
    }
  }
}
