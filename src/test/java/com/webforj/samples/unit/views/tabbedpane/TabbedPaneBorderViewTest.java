package com.webforj.samples.unit.views.tabbedpane;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.component.tabbedpane.TabbedPane;
import com.webforj.samples.views.tabbedpane.TabbedPaneBorderView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class TabbedPaneBorderViewTest {
  @Test
  void shouldHideAndShowTheBorderWithTheHideBorderSwitch(WebforjTestbed context) {
    context.add(new TabbedPaneBorderView());

    var pane = ComponentLocator.of(TabbedPane.class).locate();
    var border = ComponentLocator.of(RadioButton.class).byText("Hide Border").locate();

    UserInteraction.check(border);
    assertTrue(pane.isBorderless());

    UserInteraction.uncheck(border);
    assertFalse(pane.isBorderless());
  }

  @Test
  void shouldHideAndShowTheActiveIndicatorWithTheHideActiveIndicatorSwitch(WebforjTestbed context) {
    context.add(new TabbedPaneBorderView());

    var pane = ComponentLocator.of(TabbedPane.class).locate();
    var indicator = ComponentLocator.of(RadioButton.class).byText("Hide Active Indicator").locate();

    UserInteraction.check(indicator);
    assertTrue(pane.isHideActiveIndicator());

    UserInteraction.uncheck(indicator);
    assertFalse(pane.isHideActiveIndicator());
  }
}
