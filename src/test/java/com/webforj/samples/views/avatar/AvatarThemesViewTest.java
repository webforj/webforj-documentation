package com.webforj.samples.views.avatar;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.avatar.Avatar;
import com.webforj.component.avatar.AvatarTheme;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class AvatarThemesViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new AvatarThemesView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldShowTheSevenThemesFilledAndThenOutlined(WebforjTestbed context) {
    context.add(new AvatarThemesView());

    var avatars = ComponentLocator.of(Avatar.class).all();

    assertEquals(
        List.of(
            AvatarTheme.DEFAULT,
            AvatarTheme.GRAY,
            AvatarTheme.PRIMARY,
            AvatarTheme.SUCCESS,
            AvatarTheme.WARNING,
            AvatarTheme.DANGER,
            AvatarTheme.INFO,
            AvatarTheme.OUTLINED_DEFAULT,
            AvatarTheme.OUTLINED_GRAY,
            AvatarTheme.OUTLINED_PRIMARY,
            AvatarTheme.OUTLINED_SUCCESS,
            AvatarTheme.OUTLINED_WARNING,
            AvatarTheme.OUTLINED_DANGER,
            AvatarTheme.OUTLINED_INFO),
        avatars.stream().map(Avatar::getTheme).toList());
  }
}
