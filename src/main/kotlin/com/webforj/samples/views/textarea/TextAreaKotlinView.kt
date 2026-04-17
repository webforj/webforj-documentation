package com.webforj.samples.views.textarea

import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.button.ButtonTheme
import com.webforj.component.field.TextArea
import com.webforj.component.html.elements.Paragraph
import com.webforj.component.layout.flexlayout.*
import com.webforj.component.toast.Toast
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.field.textArea
import com.webforj.kotlin.dsl.component.html.elements.paragraph
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.extension.percent
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Feedback TextArea Demo")
class TextAreaKotlinView : Composite<FlexLayout>() {
  private val self = boundComponent
  private val maxCharacters = 200

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      justifyContent = FlexJustifyContent.CENTER
      alignment = FlexAlignment.CENTER
      width = 100.percent
      margin = "50px auto"
      maxWidth = 400.px

      val feedbackArea = textArea("What do you think about this demo?") {
        placeholder = "Enter your feedback here..."
        width = 100.percent
        maxWidth = 800.px
        maxLength = maxCharacters
        isLineWrap = true
        wrapStyle = TextArea.WrapStyle.WORD_BOUNDARIES
      }
      flexLayout {
        direction = FlexDirection.ROW
        justifyContent = FlexJustifyContent.BETWEEN
        alignContent = FlexContentAlignment.CENTER
        width = 100.percent

        val charCount = paragraph {
          styles["font-size"] = "12px"
          styles["color"] = "var(--dwc-color-gray-text)"
          updateCharCount(feedbackArea.value)
        }
        feedbackArea.onValueChange { charCount.updateCharCount(feedbackArea.value) }
        button("Submit Feedback", ButtonTheme.GRAY) {
          onClick {
            feedbackArea.value.trim().takeIf { it.isNotEmpty() }?.let {
              Toast.show("Thank you for your feedback!", Theme.SUCCESS)
              feedbackArea.value = ""
              charCount.updateCharCount("")
            }
          }
        }
      }
    }
  }

  private fun Paragraph.updateCharCount(text: String) {
    this.text = "Characters: ${text.length} / $maxCharacters"
  }
}
