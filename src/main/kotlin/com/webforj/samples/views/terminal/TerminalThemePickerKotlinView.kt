package com.webforj.samples.views.terminal

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.terminal.TerminalTheme
import com.webforj.kotlin.dsl.component.list.choiceBox
import com.webforj.kotlin.dsl.component.list.items
import com.webforj.kotlin.extension.percent
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.rem
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.size
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import com.webforj.utilities.Assets

@Route
@FrameTitle("Terminal Theme Picker")
class TerminalThemePickerKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent
  private val themes = run {
    val mapType = object: TypeToken<Map<String, TerminalTheme>>() {}.type
    Gson().fromJson<Map<String, TerminalTheme>>(Assets.contentOf(Assets.resolveContextUrl("context://terminal-themes.json")), mapType)
  }

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      alignment = FlexAlignment.CENTER
      styles["margin"] = "var(--dwc-space-m) auto"
      maxWidth = 800.px
      val themeChoiceBox = choiceBox("Select a Theme") {
        theme = ButtonTheme.GRAY
        width = 200.px
        styles["margin"] = 1.rem
        items(*themes.map { (key, value) -> value to key }.toTypedArray())
        selectIndex(0)
      }
      terminal {
        styles["margin"] = "0px var(--dwc-space-m)"
        styles["border"] = "1px solid var(--dwc-color-default)"
        size = 100.percent to 400.px
        writeln(
          """

                    \u001B[1m\u001B[47;31m FAIL \u001B[0m\u001B[90m src/com/webforj/samples/TerminalThemeTest.java\u001B[0m \u001B[41;97m (2.345s) \u001B[0m

                    \u001B[31m  * should apply the selected theme correctly\u001B[0m

                    \u001B[90m    expect(\u001B[0m\u001B[31mreceived\u001B[0m\u001B[90m).toBe(\u001B[0m\u001B[32mexpected\u001B[0m\u001B[90m) // Object.is equality\u001B[0m

                        Expected: \u001B[32m"SolarizedDark"\u001B[0m
                        Received: \u001B[31m"Dracula"\u001B[0m

                        42 |
                        43 | @Test
                    >   44 | assertEquals(applyTheme(\u001B[33m"SolarizedDark"\u001B[0m), selectedTheme);
                           |                                      \u001B[41;97m ^ \u001B[0m
                        45 |

                    \u001B[90m    at TerminalThemeTest.applyTheme(\u001B[36mTerminalThemeTest.java:44\u001B[0m)\u001B[0m

                    \u001B[32m 3 passed\u001B[0m - \u001B[31m1 failed\u001B[0m - \u001B[33m 2 skipped\u001B[0m

                    Total: 6 tests
                    Time: 2.3s
                """);
        themeChoiceBox.onValueChange {
          theme = it.value as TerminalTheme
        }
      }
    }
  }
}
