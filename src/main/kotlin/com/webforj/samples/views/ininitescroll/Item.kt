package com.webforj.samples.views.ininitescroll

import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.icons.FeatherIcon
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.icons.featherIcon
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.kotlin.dsl.component.layout.flexlayout.vertical
import kotlin.random.Random

class Item: Composite<Div>() {
  val random = Random.Default
  val names = arrayOf(
    "John", "Jane", "Alice", "Bob", "Charlie", "Diana",
    "Ethan", "Fiona", "George", "Hannah", "Ian", "Jill"
  );

  init {
      boundComponent.apply {
        flexLayout {
          horizontal()
          justifyContent = FlexJustifyContent.BETWEEN
          alignment = FlexAlignment.CENTER
          flexLayout {
            vertical()
            div(names.random()).addClassName("item-name")
            div {
              addClassName("item-excerpt")
              text = ("Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
                + "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
            }
          }
          featherIcon(FeatherIcon.ARROW_RIGHT).setMinSize("24px", "24px")
        }
      }
  }
}
