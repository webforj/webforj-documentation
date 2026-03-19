package com.webforj.samples.views.navigator

import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.navigator.Navigator.Layout
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.vertical
import com.webforj.kotlin.dsl.component.list.choiceBox
import com.webforj.kotlin.dsl.component.navigator.navigator
import com.webforj.kotlin.extension.classNames
import com.webforj.kotlin.extension.plus
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Navigator Layout")
class NavigatorLayoutKotlinView: Composite<Div>() {
  private val self = boundComponent

  init {
      self.apply {
        styles["padding"] = 20.px
        flexLayout {
          vertical()
          maxWidth = 400.px
          val navLayout = choiceBox {
            Layout.entries.forEach { add(it, it.name) }
            selectIndex(2)
          }
          navigator(totalItems = 100) {
            paginator.max = 5
            classNames + "nav"
            navLayout.onSelect {
              layout = it.selectedItem.key as Layout
            }
          }
        }
      }
  }
}
