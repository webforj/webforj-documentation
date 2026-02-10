package com.webforj.samples.views.dialog

import com.webforj.component.Composite
import com.webforj.component.Theme
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.dialog.dialog
import com.webforj.kotlin.dsl.component.dialog.header
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.list.choiceBox
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Dialog Themes")
class DialogThemesKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
      self.apply {
        dialog {
          header { div("Themes") }
          styles["display"] = "flex"
          styles["justify-content"] = "center"
          choiceBox("Select Theme") {
            styles["flex"] = "1"
            Theme.entries.forEach {
              add(it, it.name)
            }
            selectIndex(1)
            onSelect {
              this@dialog.theme = it.selectedItem.key as Theme
            }
          }
          open()
          setCloseable(false)
        }
      }
  }
}
