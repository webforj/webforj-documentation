package com.webforj.samples.views.terminal;

import static org.junit.jupiter.api.Assertions.assertSame;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.terminal.Terminal;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class TerminalThemePickerViewTest {
  @Test
  void shouldStyleTheTerminalWithTheFirstThemeOfTheList(WebforjTestbed context) {
    context.add(new TerminalThemePickerView());

    var choice = ComponentLocator.of(ChoiceBox.class).locate();

    assertSame(
        choice.getItems().get(0).getKey(), ComponentLocator.of(Terminal.class).locate().getTheme());
  }

  @Test
  void shouldStyleTheTerminalWithTheThemeTheUserPicks(WebforjTestbed context) {
    context.add(new TerminalThemePickerView());

    var choice = ComponentLocator.of(ChoiceBox.class).locate();

    UserInteraction.select(
        choice,
        choice.getItems().stream()
            .filter(item -> item.getText().equals("Dracula Soft"))
            .findFirst()
            .orElseThrow());

    assertSame(
        choice.getSelected().getKey(), ComponentLocator.of(Terminal.class).locate().getTheme());
  }
}
