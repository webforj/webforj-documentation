package com.webforj.samples.views.navigator

import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.navigator.Navigator
import com.webforj.component.navigator.Navigator.Layout
import com.webforj.kotlin.dsl.component.html.elements.paragraph
import com.webforj.kotlin.dsl.component.navigator.navigator
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Navigator Pagination")
class NavigatorPagesKotlinView: Composite<Div>() {

  init {
      boundComponent.apply {
        styles["padding"] = "20px"
        val text = paragraph("Navigate with the buttons below")
        navigator(totalItems = 100, layout = Layout.PAGES) {
          paginator.max = 5
          onChange {
            var start = it.startIndex
            var end = it.endIndex
            if (end != 0) {
              start++
              end++
            }
            text.text = "Showing $start to $end of 100"
          }
        }
      }
  }
}
