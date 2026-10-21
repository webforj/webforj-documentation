package com.webforj.samples.views.badge;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.badge.Badge;
import com.webforj.component.icons.Icon;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class BadgeIconsViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new BadgeIconsView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldShowAnIconBesideTheTextOrAnIconAlone(WebforjTestbed context) {
    context.add(new BadgeIconsView());

    var withText = ComponentLocator.of(Badge.class).byText("Done").locate();
    var iconOnly =
        ComponentLocator.of(Badge.class)
            .has(ComponentLocator.of(Icon.class).byName("check"))
            .locate();

    assertEquals(
        "check-circle", ComponentLocator.of(Icon.class).within(withText).locate().getName());
    assertEquals("", iconOnly.getText());
  }
}
