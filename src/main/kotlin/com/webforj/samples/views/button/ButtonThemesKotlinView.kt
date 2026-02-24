package com.webforj.samples.views.button

import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.layout.flexlayout.FlexWrap
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.html.elements.div
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Button Themes")
class ButtonThemesKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
      self.apply {
        direction = FlexDirection.COLUMN
        spacing = "var(--dwc-space-l)"
        margin = "var(--dwc-space-l)"

        div {
          styles["overflow-x"] = "auto"
          styles["white-space"] = "nowrap"
          styles["margin"] = "var(--dwc-space-l)"

          flexLayout(FlexDirection.COLUMN) {
            wrap = FlexWrap.NOWRAP
            spacing = "var(--dwc-space-l)"

            val solidRow = flexLayout(FlexDirection.ROW) {
              wrap = FlexWrap.NOWRAP
              spacing = "var(--dwc-space-s)"
            }
            val outlinedRow = flexLayout(FlexDirection.ROW) {
              wrap = FlexWrap.NOWRAP
              spacing = "var(--dwc-space-s)"
              styles["margin-bottom"] = "var(--dwc-space-l)"
            }
            ButtonTheme.entries.forEach {
              if ("OUTLINE" !in it.name) {
                solidRow.button(it.name, it) {
                  minWidth = 200.px
                  maxWidth = 200.px
                }
                outlinedRow.button("OUTLINED_${it.name}",
                  ButtonTheme.valueOf("OUTLINED_${it.name}")) {
                  minWidth = 200.px
                  maxWidth = 200.px
                }
              }
            }
          }
        }
      }
  }
}
