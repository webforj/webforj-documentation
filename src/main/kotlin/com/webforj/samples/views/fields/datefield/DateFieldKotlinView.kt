package com.webforj.samples.views.fields.datefield

import com.webforj.component.Composite
import com.webforj.component.field.DateField
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.data.event.ValueChangeEvent
import com.webforj.kotlin.dsl.component.field.dateField
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import java.time.LocalDate

@Route
@FrameTitle("Date Field Demo")
class DateFieldKotlinView: Composite<FlexLayout>() {

  companion object {
    private val TODAY = LocalDate.now()
    private val MAX_DATE = TODAY.plusYears(1)
  }

  private val self = boundComponent
  private val departureField: DateField
  private val returnField: DateField

  init {
    self.apply {
      direction = FlexDirection.ROW
      spacing = "var(--dwc-space-l)"
      margin = "var(--dwc-space-m)"

      departureField = dateField("Departure Date:", TODAY) {
        width = 200.px
        min = TODAY
        max = MAX_DATE
        onValueChange(::syncDates)
      }
      returnField = dateField("Return Date:", TODAY) {
        width = 200.px
        min = TODAY
        max = MAX_DATE
        onValueChange(::syncDates)
      }
    }
  }

  private fun syncDates(e: ValueChangeEvent<LocalDate>) {
    val dep = departureField.value.clamp()
    val ret = returnField.value.clamp()

    e.source.takeIf { ret < dep }?.let {
      if (it == departureField) {
        returnField.value = dep
      } else {
        departureField.value = ret
      }
    }
  }

  private fun LocalDate.clamp() = when {
    this < TODAY -> TODAY
    this > MAX_DATE -> MAX_DATE
    else -> this
  }

}
