package com.webforj.samples.views.progressbar

import com.webforj.Interval
import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.button.Button
import com.webforj.component.html.elements.Div
import com.webforj.component.progressbar.ProgressBar
import com.webforj.component.progressbar.ProgressBar.Orientation
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.button.prefix
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.kotlin.dsl.component.layout.flexlayout.vertical
import com.webforj.kotlin.dsl.component.progressbar.progressBar
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Progress Bar Orientation")
class ProgressBarOrientationKotlinView: Composite<Div>() {
  private lateinit var start: Button
  private lateinit var pause: Button
  private lateinit var reset: Button

  init {
      boundComponent.apply {
        flexLayout {
          horizontal()
          styles["max-width"] = "320px"
          styles["margin"] = "0 auto"
          styles["padding"] = "20px"
          flexLayout {
            vertical()
            start = button("Start") {
              prefix { tablerIcon("player-play") }
            }
            pause = button("Pause") {
              prefix { tablerIcon("player-pause") }
            }
            reset = button("Reset") {
              prefix { tablerIcon("refresh") }
            }
          }
          val bar = progressBar(15, text = "Reactor Heating Up: {{x}}%") {
            styles["--dwc-progressbar-width"] = "160px"
            styles["--dwc-progressbar-height"] = "125px"
            theme = Theme.SUCCESS
            value = 25
            orientation = Orientation.VERTICAL
            isStriped = true
            isAnimated = true
            onValueChange {
              if (it.value in 25..<75) {
                theme = Theme.WARNING
              } else if (it.value >= 75) {
                theme = Theme.DANGER
              } else {
                theme = Theme.SUCCESS
              }
            }
          }

          val interval = Interval(0.1f) {
            bar.value += 1
            if (bar.value >= bar.max) {
              it.interval.stop()
              start.isEnabled = false
              pause.isEnabled = false
              bar.isAnimated = false
            }
          }
          start.onClick {
            bar.isAnimated = true
            start.isEnabled = false
            pause.isEnabled = true

            interval.start()
          }
          pause.onClick {
            bar.isAnimated = false
            start.isEnabled = true
            pause.isEnabled = false

            interval.stop()
          }
          reset.onClick {
            bar.value = 0
            bar.isAnimated = true
            start.isEnabled = false
            pause.isEnabled = true

            interval.restart()
          }
        }
      }
  }
}
