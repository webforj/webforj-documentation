package com.webforj.samples.views.textarea

import com.webforj.annotation.InlineStyleSheet
import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.field.textArea
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@InlineStyleSheet("dwc-textarea::part(input) { text-transform: capitalize; }")
@Route
@FrameTitle("Predicted Text Demo")
class TextAreaPredictedTextKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      spacing = "var(--dwc-space-m)"
      margin = "50px auto"
      maxWidth = 400.px
      textArea {
        height = 200.px
        placeholder = "Start typing to see suggestions..."
        helperText = """
            Type something to see suggestions, for instance, type 'Sky is'.
            Then wait for a few seconds to see the suggestions. You can insert the suggestion
            by pressing Tab or ArrowRight key or simply ignore it by typing further.
            """.trimIndent()
        onValueChange { event ->
          event.value.takeIf { it.isNotEmpty() }?.let { input ->
            try {
             TextPredictionService.predict(input).takeIf {
               it.isNotEmpty() && it.length >= input.length
             }?.let {
               predictedText = input + it.substring(input.length)
             } ?: run { predictedText = "" }
            } catch (_: Exception) {
              predictedText = ""
            }
          } ?: run { predictedText = "" }
        }
      }
    }
  }

}
