package com.webforj.samples.views.markdownviewer

import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.markdown.markdownViewer
import com.webforj.kotlin.extension.percent
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("MarkdownViewer")
class MarkdownViewerKotlinView: Composite<Div>() {
  private val self = boundComponent

  init {
    self.apply {
      styles["display"] = "flex"
      styles["justifyContent"] = "center"
      styles["padding"] = "var(--dwc-space-l)"
      div {
        styles["maxWidth"] = 600.px
        styles["width"] = 100.percent
        styles["padding"] = "var(--dwc-space-l)"
        styles["background"] = "var(--dwc-surface-3)"
        styles["border"] = "1px solid var(--dwc-color-default)"
        styles["borderRadius"] = "var(--dwc-border-radius-l)"
        markdownViewer("""
        # Welcome to MarkdownViewer

        This component renders **bold**, *italic*, ~~strikethrough~~, and `inline code`.

        ## Lists

        - First item
        - Second item
        - Third item

        ## Blockquote

        > Markdown makes formatting easy
        > without writing HTML.

        ## Code Block

        ```java
        MarkdownViewer viewer = new MarkdownViewer();
        viewer.setContent("# Hello World");
        ```

        ## Emoticons

        Feeling happy :) or sad :( or surprised :o

        That's awesome! :D

        """.trimIndent())
      }
    }
  }
}
