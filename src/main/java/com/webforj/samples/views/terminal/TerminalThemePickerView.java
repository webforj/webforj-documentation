package com.webforj.samples.views.terminal;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.webforj.component.Composite;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.terminal.Terminal;
import com.webforj.component.terminal.TerminalTheme;
import com.webforj.data.event.ValueChangeEvent;
import com.webforj.router.annotation.Route;
import com.webforj.utilities.Assets;
import com.webforj.router.annotation.FrameTitle;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

@Route
@FrameTitle("Terminal Theme Picker")
public class TerminalThemePickerView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Terminal terminal = new Terminal();
  private final ChoiceBox themeChoiceBox = new ChoiceBox();
  private final Map<String, TerminalTheme> themes = new LinkedHashMap<>();

  public TerminalThemePickerView() {
    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setStyle("margin", "var(--dwc-space-m) auto")
        .setMaxWidth("800px");

    terminal.setStyle("margin", "0px var(--dwc-space-m)")
        .setStyle("border", "1px solid var(--dwc-color-default)")
        .setSize("100%", "400px")
        .writeln(
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

    loadThemes();

    themeChoiceBox.setLabel("Select a Theme")
        .setTheme(ButtonTheme.GRAY)
        .setWidth("200px")
        .setStyle("margin", "1rem")
        .selectIndex(0)
        .onValueChange(this::applySelectedTheme);

    if (!themes.isEmpty()) {
      String firstTheme = themes.keySet().iterator().next();
      themeChoiceBox.setValue(firstTheme);
      applyTheme(themes.get(firstTheme));
    }

    self.add(themeChoiceBox, terminal);
  }

  private void loadThemes() {
    Type mapType = new TypeToken<Map<String, TerminalTheme>>() {}.getType();
    Map<String, TerminalTheme> loadedThemes = new Gson()
        .fromJson(Assets.contentOf(Assets.resolveContextUrl("context://terminal-themes.json")), mapType);

    themes.putAll(loadedThemes);

    // Use forEach with lambda for cleaner iteration
    loadedThemes.forEach((key, value) -> themeChoiceBox.add(value, key));
  }

  private void applySelectedTheme(ValueChangeEvent<Object> e) {
    // Use pattern matching for instanceof
    if (!(e.getValue() instanceof TerminalTheme selected)) {
      return;
    }
    applyTheme(selected);
  }

  private void applyTheme(TerminalTheme theme) {
    terminal.setTheme(theme);
  }
}
