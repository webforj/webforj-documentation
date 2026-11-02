package com.webforj.samples.unit.views.avatar;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.avatar.Avatar;
import com.webforj.component.avatar.AvatarExpanse;
import com.webforj.samples.views.avatar.AvatarExpansesView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class AvatarExpansesViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new AvatarExpansesView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldShowTheNineSizesFromXxxsmallToXxxlarge(WebforjTestbed context) {
    context.add(new AvatarExpansesView());

    var avatars = ComponentLocator.of(Avatar.class).all();

    assertEquals(
        List.of(
            AvatarExpanse.XXXSMALL,
            AvatarExpanse.XXSMALL,
            AvatarExpanse.XSMALL,
            AvatarExpanse.SMALL,
            AvatarExpanse.MEDIUM,
            AvatarExpanse.LARGE,
            AvatarExpanse.XLARGE,
            AvatarExpanse.XXLARGE,
            AvatarExpanse.XXXLARGE),
        avatars.stream().map(Avatar::getExpanse).toList());
  }
}
