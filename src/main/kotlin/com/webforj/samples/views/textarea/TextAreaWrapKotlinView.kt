package com.webforj.samples.views.textarea

import com.webforj.component.Composite
import com.webforj.component.field.TextArea
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.field.textArea
import com.webforj.kotlin.dsl.component.list.choiceBox
import com.webforj.kotlin.dsl.component.list.items
import com.webforj.kotlin.extension.percent
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Word Wrap and Line Wrapping Demo")
class TextAreaWrapKotlinView : Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      justifyContent = FlexJustifyContent.CENTER
      alignment = FlexAlignment.CENTER
      spacing = "var(--dwc-space-m)"
      margin = "50px auto"
      maxWidth = 300.px

      val wrapStylesBox = choiceBox("Select a word wrap style") {
        width = 100.percent
        items(
          TextArea.WrapStyle.WORD_BOUNDARIES to "Word Boundaries",
          TextArea.WrapStyle.CHARACTER_BOUNDARIES to "Character Boundaries"
        )
        selectIndex(1)
      }
      textArea("Text Preview") {
        value =
          "Honorificabilitudinitatibus califragilisticexpialidocious Taumatawhakatangihangakoauauotamateaturipukakapikimaungahoronukupokaiwhenuakitanatahu グレートブリテンおよび北アイルランド連合王国という言葉は本当に長い言葉"
        placeholder = "Enter text to see wrapping behavior..."
        rows = 2
        width = 100.percent
        height = 200.px
        isLineWrap = true
        wrapStyle = TextArea.WrapStyle.CHARACTER_BOUNDARIES
        wrapStylesBox.onSelect {
          wrapStyle = it.selectedItem.key as TextArea.WrapStyle
        }
      }
    }
  }
}
