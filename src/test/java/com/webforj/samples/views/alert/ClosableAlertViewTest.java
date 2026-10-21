package com.webforj.samples.views.alert;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.alert.Alert;
import com.webforj.component.button.Button;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ClosableAlertViewTest {
  @Test
  void shouldShowTheShowAlertButtonWhenTheUserDismissesTheAlert(WebforjTestbed context) {
    context.add(new ClosableAlertView());

    var alert = ComponentLocator.of(Alert.class).locate();
    var reopen = ComponentLocator.of(Button.class).byText("Show Alert").locate();

    UserInteraction.close(alert);

    assertFalse(alert.isOpened());
    assertTrue(reopen.isVisible());
  }

  @Test
  void shouldReopenTheSameAlertWhenTheUserClicksShowAlert(WebforjTestbed context) {
    context.add(new ClosableAlertView());

    var alert = ComponentLocator.of(Alert.class).locate();
    var reopen = ComponentLocator.of(Button.class).byText("Show Alert").locate();
    UserInteraction.close(alert);

    UserInteraction.click(reopen);

    assertTrue(alert.isOpened());
    assertFalse(reopen.isVisible());
  }
}
