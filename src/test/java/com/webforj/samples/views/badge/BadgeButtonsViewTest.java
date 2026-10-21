package com.webforj.samples.views.badge;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.Expanse;
import com.webforj.component.badge.Badge;
import com.webforj.component.badge.BadgeTheme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class BadgeButtonsViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new BadgeButtonsView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldAttachCountBadgesThemedIndependentlyOfTheButtons(WebforjTestbed context) {
    context.add(new BadgeButtonsView());

    var notifications = ComponentLocator.of(Button.class).byText("Notifications").locate();
    var messages = ComponentLocator.of(Button.class).byText("Messages").locate();
    var notificationsBadge = (Badge) notifications.getBadge();
    var messagesBadge = (Badge) messages.getBadge();

    assertEquals(ButtonTheme.PRIMARY, notifications.getTheme());
    assertEquals("5", notificationsBadge.getText());
    assertEquals(BadgeTheme.DANGER, notificationsBadge.getTheme());
    assertEquals(ButtonTheme.DEFAULT, messages.getTheme());
    assertEquals("12", messagesBadge.getText());
    assertEquals(BadgeTheme.PRIMARY, messagesBadge.getTheme());
  }

  @Test
  void shouldKeepTheSameBadgeOnEveryButtonSize(WebforjTestbed context) {
    context.add(new BadgeButtonsView());

    var sized =
        ComponentLocator.of(Button.class)
            .filter(button -> ((Badge) button.getBadge()).getText().equals("3"))
            .all();

    assertEquals(List.of(Expanse.values()), sized.stream().map(Button::getExpanse).toList());
    sized.forEach(
        button -> assertEquals(BadgeTheme.DANGER, ((Badge) button.getBadge()).getTheme()));
  }
}
