package com.webforj.samples.unit.views.tabbedpane;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.Expanse;
import com.webforj.component.Theme;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.tabbedpane.TabbedPane;
import com.webforj.samples.views.tabbedpane.TabbedPaneExpanseThemeView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@WebforjTest
class TabbedPaneExpanseThemeViewTest {
  @ParameterizedTest
  @EnumSource(Theme.class)
  void shouldApplyTheThemeTheUserPicks(Theme theme, WebforjTestbed context) {
    context.add(new TabbedPaneExpanseThemeView());

    var pane = ComponentLocator.of(TabbedPane.class).locate();

    UserInteraction.selectIndex(
        ComponentLocator.of(ChoiceBox.class).byLabel("Themes").locate(), theme.ordinal());

    assertEquals(theme, pane.getTheme());
  }

  @ParameterizedTest
  @EnumSource(value = Expanse.class, mode = EnumSource.Mode.EXCLUDE, names = "MEDIUM")
  void shouldApplyTheExpanseTheUserPicks(Expanse expanse, WebforjTestbed context) {
    context.add(new TabbedPaneExpanseThemeView());

    var pane = ComponentLocator.of(TabbedPane.class).locate();

    UserInteraction.selectIndex(
        ComponentLocator.of(ChoiceBox.class).byLabel("Expanses").locate(), expanse.ordinal());

    assertEquals(expanse, pane.getExpanse());
  }
}
