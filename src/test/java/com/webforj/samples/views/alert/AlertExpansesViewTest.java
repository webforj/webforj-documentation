package com.webforj.samples.views.alert;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.Expanse;
import com.webforj.component.alert.Alert;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@WebforjTest
class AlertExpansesViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new AlertExpansesView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @ParameterizedTest
  @EnumSource(Expanse.class)
  void shouldShowAnAlertInTheExpanseItNames(Expanse expanse, WebforjTestbed context) {
    context.add(new AlertExpansesView());

    var alert =
        ComponentLocator.of(Alert.class)
            .has(
                ComponentLocator.of(Paragraph.class)
                    .byText("This alert uses the %s expanse.".formatted(expanse)))
            .locate();

    assertEquals(expanse, alert.getExpanse());
  }
}
