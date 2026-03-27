package com.webforj.samples.views.slider

import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.button.ButtonTheme
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.toast.Toast
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.kotlin.dsl.component.slider.slider
import com.webforj.kotlin.extension.prefixSlot
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Donation Slider Demo")
class DonationSliderKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent
  private var currentDonationValue = 50

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      justifyContent = FlexJustifyContent.CENTER
      spacing = "var(--dwc-space-l)"
      margin = "var(--dwc-space-l)"
      alignment = FlexAlignment.CENTER;
      slider(max = 80, min = 0) {
        isTicksVisible = true
        majorTickSpacing = 10
        minorTickSpacing = 5
        isLabelsVisible = true
        isSnapToTicks = true
        theme = Theme.GRAY
        width = 500.px
        labels = mapOf(
          0 to "$0",
          10 to "$10",
          20 to "$20",
          30 to "$30",
          40 to "$40",
          50 to "$50",
          60 to "$60",
          70 to "$70",
          80 to "$80",
        )
        onValueChange {
          currentDonationValue = it.value
        }
      }
      button("Confirm Donation", ButtonTheme.GRAY) {
        prefixSlot { tablerIcon("tip-jar-euro") }
        onClick { showTestMessage(currentDonationValue) }
      }
    }
  }

  private fun showTestMessage(value: Int) {
    Toast().apply {
      text = $$"Thank you for your generous contribution of $$$value!"
      placement = Toast.Placement.BOTTOM
      theme = Theme.SUCCESS
      duration = 1000
      open()
    }
  }
}
