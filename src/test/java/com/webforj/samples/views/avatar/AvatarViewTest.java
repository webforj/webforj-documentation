package com.webforj.samples.views.avatar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.avatar.Avatar;
import com.webforj.component.avatar.AvatarExpanse;
import com.webforj.component.avatar.AvatarTheme;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.html.elements.H4;
import com.webforj.component.html.elements.Img;
import com.webforj.component.html.elements.Span;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class AvatarViewTest {
  @Test
  void shouldShowTheProfileImageOrTheInitialsComputedFromTheName(WebforjTestbed context) {
    context.add(new AvatarView());

    var withImage = ComponentLocator.of(Avatar.class).byText("Sarah Chen").locate();
    var withInitials = ComponentLocator.of(Avatar.class).byText("Marcus Johnson").locate();

    assertTrue(ComponentLocator.of(Img.class).within(withImage).isPresent());
    assertFalse(ComponentLocator.of(Img.class).within(withInitials).isPresent());
    assertEquals("MJ", withInitials.getInitials());
  }

  @Test
  void shouldOpenTheEnlargedProfileOfTheClickedMember(WebforjTestbed context) {
    context.add(new AvatarView());

    var member = ComponentLocator.of(Avatar.class).byText("Sarah Chen").locate();

    assertFalse(ComponentLocator.of(Dialog.class).isPresent());

    UserInteraction.click(member);

    var dialog = ComponentLocator.of(Dialog.class).locate();
    assertTrue(dialog.isOpened());
    assertTrue(ComponentLocator.of(H4.class).within(dialog).byText("Sarah Chen").isPresent());
    assertTrue(ComponentLocator.of(Span.class).within(dialog).byText("Product Lead").isPresent());

    var avatar = ComponentLocator.of(Avatar.class).within(dialog).locate();
    assertEquals(AvatarExpanse.XXLARGE, avatar.getExpanse());
    assertEquals(AvatarTheme.SUCCESS, avatar.getTheme());
  }
}
