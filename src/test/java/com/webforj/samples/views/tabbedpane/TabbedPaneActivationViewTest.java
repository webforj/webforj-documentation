package com.webforj.samples.views.tabbedpane;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.component.tabbedpane.TabbedPane;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class TabbedPaneActivationViewTest {
  @Test
  void shouldSwitchBetweenManualAndAutomaticActivationAndNameTheMode(WebforjTestbed context) {
    context.add(new TabbedPaneActivationView());

    var pane = ComponentLocator.of(TabbedPane.class).locate();
    var toggle = ComponentLocator.of(RadioButton.class).locate();

    UserInteraction.check(toggle);
    UserInteraction.uncheck(toggle);

    assertEquals(TabbedPane.Activation.MANUAL, pane.getActivation());
    assertEquals("Manual", toggle.getText());

    UserInteraction.check(toggle);

    assertEquals(TabbedPane.Activation.AUTO, pane.getActivation());
    assertEquals("Automatic", toggle.getText());
  }
}
