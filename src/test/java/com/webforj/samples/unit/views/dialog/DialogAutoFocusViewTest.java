package com.webforj.samples.unit.views.dialog;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.dialog.Dialog;
import com.webforj.samples.views.dialog.DialogAutoFocusView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class DialogAutoFocusViewTest {
  @Test
  void shouldOpenTheInviteWithAutoFocusOnItsFirstField(WebforjTestbed context) {
    context.add(new DialogAutoFocusView());

    var dialog = ComponentLocator.of(Dialog.class).locate();

    UserInteraction.click(ComponentLocator.of(Button.class).byText("Invite teammate").locate());

    assertTrue(dialog.isOpened());
    assertTrue(dialog.isAutoFocus());
  }

  @Test
  void shouldCloseTheInviteFromEitherFooterAction(WebforjTestbed context) {
    context.add(new DialogAutoFocusView());

    var dialog = ComponentLocator.of(Dialog.class).locate();
    var invite = ComponentLocator.of(Button.class).byText("Invite teammate").locate();

    UserInteraction.click(invite);
    UserInteraction.click(
        ComponentLocator.of(Button.class).within(dialog).byText("Cancel").locate());

    assertFalse(dialog.isOpened());

    UserInteraction.click(invite);
    UserInteraction.click(
        ComponentLocator.of(Button.class).within(dialog).byText("Send invite").locate());

    assertFalse(dialog.isOpened());
  }
}
