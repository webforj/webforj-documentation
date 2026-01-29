package com.webforj.samples.views.loading

import com.webforj.Interval
import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.Expanse
import com.webforj.component.Theme
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.spinner.SpinnerExpanse
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.loading.loading
import com.webforj.kotlin.dsl.component.spinner.spinner
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Loading Spinners")
@StyleSheet("ws://css/loadingstyles/loadingspinnerdemo.css")
class LoadingSpinnerDemoKotlinView : Composite<FlexLayout>() {

  init {
    boundComponent.apply {
      div {
        addClassName("card")
        loading("Displaying spinner with all themes...") {
          isBackdropVisible = false
          var state = 1
          Interval(3f) {
            when (state) {
              1 -> {
                spinner.apply {
                  theme = Theme.DEFAULT
                  expanse = SpinnerExpanse.SMALL
                }
                text = "Displaying default theme, small expanse"
              }

              2 -> {
                spinner.apply {
                  theme = Theme.DANGER
                  expanse = SpinnerExpanse.MEDIUM
                }
                text = "Displaying danger theme, medium expanse"
              }

              3 -> {
                spinner.apply {
                  theme = Theme.GRAY
                  expanse = SpinnerExpanse.LARGE
                }
                text = "Displaying gray theme, large expanse"
              }

              4 -> {
                spinner.apply {
                  theme = Theme.INFO
                  expanse = SpinnerExpanse.SMALL
                }
                text = "Displaying info theme, small expanse"
              }

              5 -> {
                spinner.apply {
                  theme = Theme.PRIMARY
                  expanse = SpinnerExpanse.MEDIUM
                }
                text = "Displaying primary theme, medium expanse"
              }

              6 -> {
                spinner.apply {
                  theme = Theme.SUCCESS
                  expanse = SpinnerExpanse.LARGE
                }
                text = "Displaying success theme, large expanse"
              }

              7 -> {
                spinner.apply {
                  theme = Theme.WARNING
                  expanse = SpinnerExpanse.SMALL
                }
                text = "Displaying warning theme, small expanse"
              }

              8 -> {
                spinner.apply {
                  isClockwise = false
                }
                text = "Now moving counterclockwise..."
              }

              9 -> {
                spinner.apply {
                  speed = 500
                }
                text = "Going faster..."
              }

              else -> {
                it.interval.stop()
                spinner.theme = Theme.PRIMARY
                text = "Demo complete!"
              }
            }
            state++
          }.start()
          open()
        }
      }
    }
  }
}
