package com.webforj.samples.unit.views.alert;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.Theme;
import com.webforj.component.alert.Alert;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.samples.views.alert.AlertThemesView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@WebforjTest
class AlertThemesViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new AlertThemesView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @ParameterizedTest
  @EnumSource(Theme.class)
  void shouldShowAnAlertInTheThemeItNames(Theme theme, WebforjTestbed context) {
    context.add(new AlertThemesView());

    var alert =
        ComponentLocator.of(Alert.class)
            .has(
                ComponentLocator.of(Paragraph.class)
                    .byText("This is an alert with the %s theme!".formatted(theme)))
            .locate();

    assertEquals(theme, alert.getTheme());
  }
}
