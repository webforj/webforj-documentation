package com.webforj.samples.views.refresher

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.html.elements.Div
import com.webforj.component.icons.FeatherIcon
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.icons.featherIcon
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.kotlin.dsl.component.layout.flexlayout.vertical
import com.webforj.kotlin.dsl.component.refresher.refresher
import com.webforj.kotlin.extension.attributes
import com.webforj.kotlin.extension.classNames
import com.webforj.kotlin.extension.minSize
import com.webforj.kotlin.extension.plus
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.router.annotation.Route
import java.util.*

@Route
@StyleSheet("ws://css/refresher/refresher.css")
class RefresherThemesKotlinView : Composite<Div>() {
  private val self = boundComponent
  private val themes = listOf(
    Theme.PRIMARY, Theme.SUCCESS, Theme.WARNING,
    Theme.DANGER, Theme.INFO, Theme.GRAY, Theme.DANGER
  )
  private var themeIndex = 0
  private val random = Random()
  private val names = arrayOf(
    "John", "Jane", "Alice", "Bob", "Charlie", "Diana",
    "Ethan", "Fiona", "George", "Hannah", "Ian", "Jill"
  )

  init {
    self.apply {
      val canvas = div {
        classNames + "is-canvas"
        attributes["theme"] = Theme.INFO.name.lowercase();
      }
      refresher {
        theme = Theme.INFO
        onRefresh {
          canvas.removeAll()
          for (i in 1..8) {
            canvas.add(Item())
          }
          val next = themeIndex++
          val nextTheme = themes[next]
          theme = nextTheme
          canvas.attributes["theme"] = nextTheme.name.lowercase()
          finish()
        }
      }
      for (i in 1..8) {
        canvas.add(Item())
      }
    }
  }

  inner class Item : Composite<Div>() {
    private val self = boundComponent

    init {
      val name = names[random.nextInt(names.size)]
      self.apply {
        flexLayout {
          horizontal()
          justifyContent = FlexJustifyContent.BETWEEN
          alignment = FlexAlignment.CENTER
          flexLayout {
            vertical()
            div(name) {
              classNames + "item-name"
            }
            div {
              classNames + "item-excerpt"
              text = ("Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
                + "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")

            }
          }
          featherIcon(FeatherIcon.ARROW_RIGHT) {
            minSize = 24.px to 24.px
          }
        }
      }
    }
  }

}
