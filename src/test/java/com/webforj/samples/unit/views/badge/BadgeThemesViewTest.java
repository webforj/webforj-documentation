package com.webforj.samples.unit.views.badge;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.badge.Badge;
import com.webforj.component.badge.BadgeTheme;
import com.webforj.samples.views.badge.BadgeThemesView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class BadgeThemesViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new BadgeThemesView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldShowTheSevenFilledThemesAndTheirSevenOutlinedVariants(WebforjTestbed context) {
    context.add(new BadgeThemesView());

    var badges = ComponentLocator.of(Badge.class).all();

    assertEquals(List.of(BadgeTheme.values()), badges.stream().map(Badge::getTheme).toList());
  }
}
