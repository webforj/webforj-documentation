package com.webforj.samples.views.busyindicator

import com.webforj.App
import com.webforj.Interval
import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.html.elements.Div
import com.webforj.component.spinner.SpinnerExpanse
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Busy Indicator")
class BusyIndicatorKotlinView : Composite<Div>() {
  private var retry = 1
  private val indicator = App.getBusyIndicator()

  init {
    indicator.text = "Initiating spoon pending sequence... Hold tight! This will take 3 seconds."

    indicator.open()

    Interval(3f) { event ->
      when (retry) {
        1 -> {
          indicator.spinner.apply {
            theme = Theme.WARNING
            speed = 200
            expanse = SpinnerExpanse.LARGE
          }
          indicator.text = "Attempt 1: Still trying to bend the spoon... It's trickier than it looks!, Trying harder..."
        }
        2 -> {
          indicator.spinner.theme = Theme.DANGER
          indicator.text = "Attempt 2: Spoon bending failed... Maybe it's magic-proof. Let's stop here."
        }
        else -> {
          event.interval.stop()
          indicator.spinner.theme = Theme.SUCCESS
          indicator.text = "Demo complete! Remember, it's not about bending spoons, it's about having fun!"
        }
      }
      retry++
    }.start()
  }
}
