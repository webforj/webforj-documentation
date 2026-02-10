package com.webforj.samples.views.fields.maskednumberfield

import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.button.ButtonTheme
import com.webforj.component.field.MaskedNumberField
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.toast.Toast
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.field.maskedNumberField
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.kotlin.extension.prefix
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Tip Calculator")
class MaskedNumberFieldKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent
  private val billAmountField: MaskedNumberField
  private val tipPercentageField: MaskedNumberField

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      justifyContent = FlexJustifyContent.CENTER
      alignment = FlexAlignment.CENTER
      margin = "var(--dwc-space-m)"
      billAmountField = maskedNumberField("Bill Amount") {
        mask = "\$######.##"
        value = 300.0
        width = 250.px
        prefix { tablerIcon("file-invoice") }
      }
      tipPercentageField = maskedNumberField("Tip Percentage (%)") {
        mask = "###%"
        value = 15.0
        width = 250.px
        prefix { tablerIcon("circle-percentage") }
      }
      button("Calculate Tip", ButtonTheme.PRIMARY) {
        prefix { tablerIcon("calculator") }
        onClick { handleCalculation() }
      }
    }
  }

  private fun handleCalculation() {
    try {
      val billAmount = billAmountField.value
      val tipPercentage = tipPercentageField.value
      if (billAmount <= 0 || tipPercentage <= 0) {
        Toast.show("Please enter valid positive values.", Theme.DANGER)
        return
      }

      val tipAmount = billAmount * (tipPercentage / 100)
      val totalAmount = billAmount + tipAmount
      Toast.show($$"""
        Tip: $$${String.format("%.2f", tipAmount)}
        Total: $$${String.format("%.2f", totalAmount)}
      """.trimIndent(), Theme.GRAY)
    } catch (_: NumberFormatException) {
      Toast.show("Please enter valid numbers.", Theme.DANGER)
    }
  }
}