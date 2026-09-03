package com.webforj.samples.views.terminal

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.terminal.TerminalTheme
import com.webforj.kotlin.dsl.build
import com.webforj.kotlin.dsl.component.list.choiceBox
import com.webforj.kotlin.dsl.component.list.items
import com.webforj.kotlin.dsl.component.terminal.terminal
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
    self.build {
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
        themes.values.firstOrNull()?.let { theme = it }
        var esc = '\u001B'
        writeln(
          """

                    $esc[1m$esc[47;31m FAIL $esc[0m$esc[90m src/com/webforj/samples/TerminalThemeTest.java$esc[0m $esc[41;97m (2.345s) $esc[0m

                    $esc[31m  * should apply the selected theme correctly$esc[0m

                    $esc[90m    expect($esc[0m$esc[31mreceived$esc[0m$esc[90m).toBe($esc[0m$esc[32mexpected$esc[0m$esc[90m) // Object.is equality$esc[0m

                        Expected: $esc[32m"SolarizedDark"$esc[0m
                        Received: $esc[31m"Dracula"$esc[0m

                        42 |
                        43 | @Test
                    >   44 | assertEquals(applyTheme($esc[33m"SolarizedDark"$esc[0m), selectedTheme);
                           |                                      $esc[41;97m ^ $esc[0m
                        45 |

                    $esc[90m    at TerminalThemeTest.applyTheme($esc[36mTerminalThemeTest.java:44$esc[0m)$esc[0m

                    $esc[32m 3 passed$esc[0m - $esc[31m1 failed$esc[0m - $esc[33m 2 skipped$esc[0m

                    Total: 6 tests
                    Time: 2.3s
                """.trimIndent());
        themeChoiceBox.onValueChange {
          theme = it.value as TerminalTheme
        }
      }
    }
  }
}
