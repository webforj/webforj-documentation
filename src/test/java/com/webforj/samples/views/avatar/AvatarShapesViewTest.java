package com.webforj.samples.views.avatar;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.avatar.Avatar;
import com.webforj.component.avatar.AvatarShape;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class AvatarShapesViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new AvatarShapesView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldShowTheSameAvatarAsCircleAndAsSquare(WebforjTestbed context) {
    context.add(new AvatarShapesView());

    var avatars = ComponentLocator.of(Avatar.class).all();

    assertEquals(
        List.of(AvatarShape.CIRCLE, AvatarShape.SQUARE),
        avatars.stream().map(Avatar::getShape).toList());
  }
}
