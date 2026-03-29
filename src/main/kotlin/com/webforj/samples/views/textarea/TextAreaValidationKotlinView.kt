package com.webforj.samples.views.textarea

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.field.TextArea
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.layout.flexlayout.FlexWrap
import com.webforj.kotlin.dsl.component.field.numberField
import com.webforj.kotlin.dsl.component.field.textArea
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.kotlin.extension.prefixSlot
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.Route

@Route
@StyleSheet("TextAreaValidationView.css")
class TextAreaValidationKotlinView : Composite<FlexLayout>() {
  private val self = boundComponent
  private val textArea: TextArea
  private val status: TextArea
  private var maxLength = 256
  private var maxParagraphLength = 64
  private var maxLines = 10

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      spacing = "var(--dwc-space-m)"
      margin = "50px auto"
      maxWidth = "500px"
      styles["padding"] = "var(--dwc-space-m)"
      styles["background-color"] = "var(--dwc-surface-3)"
      styles["border-radius"] = "var(--dwc-border-radius)"
      styles["border"] = "1px solid var(--dwc-color-default)"
      textArea = textArea("Validation Playground") {
        value = "The quick brown fox jumps over the lazy dog."
        this.maxLength = this@TextAreaValidationKotlinView.maxLength
        paragraphLengthLimit = maxParagraphLength
        lineCountLimit = maxLines
        height = 200.px
      }
      flexLayout {
        horizontal()
        justifyContent = FlexJustifyContent.BETWEEN
        wrap = FlexWrap.WRAP
        numberField("Max Length") {
          tooltipText = "Sets the maximum number of characters allowed across all text."
          min = 1.0
          max = 500.0
          value = maxLength.toDouble()
          styles["flex"] = "1 0 auto"
          prefixSlot { tablerIcon("ruler") }
          onValueChange { e ->
            handleNumericChange(e.value, 1, 200) {
              maxLength = it
              textArea.maxLength = maxLength
            }
          }
        }
        numberField("Max Lines") {
          tooltipText = "Restricts how many lines the text area can contain."
          min = 1.0
          max = 20.0
          value = maxLines.toDouble()
          styles["flex"] = "1 0 auto"
          prefixSlot { tablerIcon("list-numbers") }
          onValueChange { e ->
            handleNumericChange(e.value, 1, 20) {
              maxLines = it
              textArea.lineCountLimit = maxLines
            }
          }
        }
        numberField("Max Paragraph Length") {
          tooltipText = "Limits how many characters each paragraph (or line) can have."
          min = 1.0
          max = 200.0
          value = maxParagraphLength.toDouble()
          styles["flex"] = "1 0 auto"
          prefixSlot { tablerIcon("indent-increase") }
          onValueChange { e ->
            handleNumericChange(e.value, 1, 200) {
              maxParagraphLength = it
              textArea.paragraphLengthLimit = maxParagraphLength
            }
          }
        }
      }
      status = textArea("Current Status") {
        height = 120.px
        isReadOnly = true
      }
    }
  }

  private fun handleNumericChange(
    value: Double,
    min: Int,
    max: Int,
    updater: (Int) -> Unit
  ) {
    value.takeIf { it >= min && it <= max }?.let {
      updater(value.toInt())
      updateStatus()
    }
  }

  private fun updateStatus() {
    val paragraphSizes = textArea.paragraphs.takeIf { it.isNotEmpty() }?.let { paragraphs ->
      paragraphs.map { "[${paragraphs.indexOf(it)}=${it.length}]" }
        .fold("", String::plus)
    } ?: "[]"
    status.value = """
      Current Length (including \n): ${textArea.text.length}
      Current Line Count: ${textArea.paragraphs.size}
      Paragraph Sizes: $paragraphSizes
    """.trimIndent()
  }
}
