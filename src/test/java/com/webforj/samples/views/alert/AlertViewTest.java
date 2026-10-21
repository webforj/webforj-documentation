package com.webforj.samples.views.alert;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.Theme;
import com.webforj.component.alert.Alert;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class AlertViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new AlertView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldHoldTheMessageAndTheViewButtonInAPrimaryAlert(WebforjTestbed context) {
    context.add(new AlertView());

    var alert = ComponentLocator.of(Alert.class).locate();

    assertEquals(Theme.PRIMARY, alert.getTheme());
    assertTrue(
        ComponentLocator.of(Paragraph.class)
            .within(alert)
            .byText("The requested information is ready to be viewed.")
            .isPresent());
    assertTrue(ComponentLocator.of(Button.class).within(alert).byText("View").isPresent());
  }
}
