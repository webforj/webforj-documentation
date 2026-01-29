package com.webforj.samples.views.navigator

import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.navigator.Navigator
import com.webforj.component.navigator.Navigator.Layout
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.vertical
import com.webforj.kotlin.dsl.component.list.choiceBox
import com.webforj.kotlin.dsl.component.navigator.navigator
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Navigator Layout")
class NavigatorLayoutKotlinView: Composite<Div>() {

  init {
      boundComponent.apply {
        styles["padding"] = "20px"
        flexLayout {
          vertical()
          maxWidth = "400px"
          val navLayout = choiceBox {
            insert("NONE", "PAGES", "PREVIEW", "QUICK_JUMP")
            selectIndex(2)
          }
          navigator(totalItems = 100) {
            paginator.max = 5
            addClassName("nav")
            navLayout.onSelect {
              when (it.selectedItem.text) {
                "NONE" -> layout = Layout.NONE
                "PAGES" -> layout = Layout.PAGES
                "PREVIEW" -> layout = Layout.PREVIEW
                "QUICK_JUMP" -> layout = Layout.QUICK_JUMP
                else -> {}
              }
            }
          }
        }
      }
  }
}
