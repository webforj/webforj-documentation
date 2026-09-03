package com.webforj.samples.views.infinitescroll

import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.icons.FeatherIcon
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.concern.HasComponents
import com.webforj.kotlin.dsl.WebforjDsl
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.icons.featherIcon
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.kotlin.dsl.component.layout.flexlayout.vertical
import com.webforj.kotlin.dsl.init
import com.webforj.kotlin.extension.classNames
import com.webforj.kotlin.extension.minSize
import com.webforj.kotlin.extension.plusAssign
import com.webforj.kotlin.extension.px

private val names =
  listOf(
    "John",
    "Jane",
    "Alice",
    "Bob",
    "Charlie",
    "Diana",
    "Ethan",
    "Fiona",
    "George",
    "Hannah",
    "Ian",
    "Jill"
  )

internal class Item : Composite<Div>() {
  private val self = boundComponent

  init {
    self.apply {
      flexLayout {
        horizontal()
        justifyContent = FlexJustifyContent.BETWEEN
        alignment = FlexAlignment.CENTER

        flexLayout {
          vertical()

          div(names.random()) {
            classNames += "item-name"
          }
          div("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.") {
            classNames += "item-excerpt"
          }
        }
        featherIcon(FeatherIcon.ARROW_RIGHT) {
          minSize = 24.px to 24.px
        }
      }
    }
  }
}

internal fun @WebforjDsl HasComponents.item(): Item = init(Item()) {}
