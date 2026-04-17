package com.webforj.samples.views.busyindicator

import com.webforj.App
import com.webforj.BusyIndicator
import com.webforj.Interval
import com.webforj.Interval.ElapsedEvent
import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.html.elements.Div
import com.webforj.component.spinner.SpinnerExpanse
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Busy Spinners")
class BusySpinnerDemoKotlinView: Composite<Div>() {
  private val indicator: BusyIndicator
  private var state = 1

  init {
    indicator = App.getBusyIndicator().apply {
      text = "Displaying spinner with all themes..."
      open()
    }
    Interval(3f, ::handleElapsedEvent).start()
  }

  private fun handleElapsedEvent(event: ElapsedEvent) {
    when (state) {
      1 -> updateIndicator(
        Theme.DEFAULT, SpinnerExpanse.SMALL,
        "Displaying default theme, small expanse"
      )

      2 -> updateIndicator(
        Theme.DANGER, SpinnerExpanse.MEDIUM,
        "Displaying danger theme, medium expanse"
      )

      3 -> updateIndicator(
        Theme.GRAY, SpinnerExpanse.LARGE,
        "Displaying gray theme, large expanse"
      )

      4 -> updateIndicator(
        Theme.INFO, SpinnerExpanse.SMALL,
        "Displaying info theme, small expanse"
      )

      5 -> updateIndicator(
        Theme.PRIMARY, SpinnerExpanse.MEDIUM,
        "Displaying primary theme, medium expanse"
      )

      6 -> updateIndicator(
        Theme.SUCCESS, SpinnerExpanse.LARGE,
        "Displaying success theme, large expanse"
      )

      7 -> updateIndicator(
        Theme.WARNING, SpinnerExpanse.SMALL,
        "Displaying warning theme, small expanse"
      )

      8 -> {
        indicator.spinner.isClockwise = false
        indicator.text = "Now moving counterclockwise..."
      }

      9 -> {
        indicator.spinner.speed = 500
        indicator.text = "Going faster..."
      }

      else -> {
        event.interval.stop()
        indicator.spinner.theme = Theme.PRIMARY
        indicator.text = "Demo complete!"
      }
    }
    state++
  }

  private fun updateIndicator(theme: Theme?, expanse: SpinnerExpanse?, text: String?) {
    indicator.apply {
      this.text = text
      spinner.apply {
        this.theme = theme
        this.expanse = expanse
      }
    }
  }
}
