package com.webforj.samples.unit.views.badge;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.badge.Badge;
import com.webforj.component.badge.BadgeExpanse;
import com.webforj.samples.views.badge.BadgeSizesView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class BadgeSizesViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new BadgeSizesView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldShowTheNineSizesFromXxxsmallToXxxlarge(WebforjTestbed context) {
    context.add(new BadgeSizesView());

    var badges = ComponentLocator.of(Badge.class).all();

    assertEquals(
        List.of(
            BadgeExpanse.XXXSMALL,
            BadgeExpanse.XXSMALL,
            BadgeExpanse.XSMALL,
            BadgeExpanse.SMALL,
            BadgeExpanse.MEDIUM,
            BadgeExpanse.LARGE,
            BadgeExpanse.XLARGE,
            BadgeExpanse.XXLARGE,
            BadgeExpanse.XXXLARGE),
        badges.subList(0, 9).stream().map(Badge::getExpanse).toList());
  }
}
