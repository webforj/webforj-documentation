package com.webforj.samples.views.markdownviewer

import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.list.choiceBox
import com.webforj.kotlin.dsl.component.list.items
import com.webforj.kotlin.dsl.component.markdown.markdownViewer
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Progressive Rendering Demo")
class MarkdownViewerProgressiveKotlinView : Composite<FlexLayout>() {
  private val self = boundComponent


  private val sampleContent = """
      # The Octopus: Nature's Escape Artist

      Octopuses are **incredibly intelligent** creatures with some remarkable abilities.

      ## Fun Facts

      - They have **three hearts** and blue blood
      - Each arm contains its own "mini-brain"
      - They can change color in just 200 milliseconds
      - Some species can edit their own RNA

      ## Escape Artists

      Octopuses are famous for escaping aquariums:

      ```
      1. Squeeze through tiny gaps
      2. Unscrew jar lids from inside
      3. Short out lights by splashing water
      4. Make a run for the ocean
      ```

      > "The octopus is the closest we will come to meeting an intelligent alien." - Peter Godfrey-Smith

      """.trimIndent()

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      spacing = "var(--dwc-space-m)"
      padding = "var(--dwc-space-l)"

      val speedChoice = choiceBox {
        label = "Render Speed"
        width = 200.px
        styles["align-self"] = "flex-end"
        items(
          "2" to "Slow (2)",
          "4" to "Default (4)",
          "6" to "Fast (6)",
          "10" to "Very Fast (10)",
        )
        selectIndex(1)
      }
      val viewer = markdownViewer {
        isProgressiveRender = true
        renderSpeed = 4
        minHeight = 350.px
        styles["padding"] = "var(--dwc-space-m)"
        styles["background"] = "var(--dwc-surface-3)"
        styles["border"] = "1px solid var(--dwc-color-default)"
        styles["borderRadius"] = "var(--dwc-border-radius-m)"
      }
      speedChoice.onSelect {
        viewer.renderSpeed = it.selectedItem.key.toString().toInt()
      }
      flexLayout {
        spacing = "var(--dwc-space-s)"

        val startButton = button("Start", ButtonTheme.PRIMARY)
        val stopButton = button("Stop", ButtonTheme.DANGER) {
          isEnabled = false
          onClick {
            viewer.stop()
            startButton.isEnabled = true
            isEnabled =  false
          }
        }
        startButton.onClick {
          viewer.clear()
          viewer.content = sampleContent
          startButton.isEnabled = false
          stopButton.isEnabled =  true

          viewer.whenRenderComplete().thenAccept {
            startButton.isEnabled = true
            stopButton.isEnabled =  false
          }
        }
      }
    }
  }
}
