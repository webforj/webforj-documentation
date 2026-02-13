package com.webforj.samples.views.applayout.stickytoolbar

import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.html.elements.h2
import com.webforj.kotlin.dsl.component.html.elements.paragraph
import com.webforj.kotlin.extension.classNames
import com.webforj.kotlin.extension.plusAssign
import com.webforj.router.annotation.Route
import com.webforj.samples.views.applayout.AbstractContentView

@Route(value = "/content/:name", outlet = AppLayoutStickyToolbarKotlinView::class)
class AppLayoutStickyToolbarContentKotlinView: AbstractContentView() {
  private val self = boundComponent

  init {
    self.apply {
      for (i in 0..<10) {
        div {
          classNames += "card"
          h2("What is Lorem Ipsum ?")
          paragraph("""
            Lorem Ipsum is simply dummy text of the printing and typesetting 
            industry. Lorem Ipsum has been the industry's standard dummy text 
            ever since the 1500s when an unknown printer took a galley of type 
            and scrambled it to make a type specimen book. It has survived not 
            only five centuries, but also the leap into electronic 
            typesetting, remaining essentially unchanged. It was popularized 
            in the 1960s with the release of Letraset sheets containing Lorem 
            Ipsum passages, and more recently with desktop publishing software 
            like Aldus PageMaker including versions of Lorem Ipsum.
            """.trimIndent()
          )
        }
      }
    }
    super.contentLabel.isVisible = true
  }
}