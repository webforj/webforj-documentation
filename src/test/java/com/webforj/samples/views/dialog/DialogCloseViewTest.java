package com.webforj.samples.views.dialog;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.act.exception.InteractionRejectedException;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class DialogCloseViewTest {
  @Test
  void shouldSetTheEscapeAndOutsideClickPoliciesFromTheirSwitches(WebforjTestbed context) {
    context.add(new DialogCloseView());

    var dialog = ComponentLocator.of(Dialog.class).locate();
    var escape = ComponentLocator.of(RadioButton.class).byText("Escape key").locate();
    var outside = ComponentLocator.of(RadioButton.class).byText("Outside click").locate();

    UserInteraction.uncheck(escape);

    assertFalse(dialog.isCancelOnEscKey());
    assertTrue(dialog.isCancelOnOutsideClick());

    UserInteraction.uncheck(outside);
    assertFalse(dialog.isCancelOnOutsideClick());

    UserInteraction.check(escape);
    UserInteraction.check(outside);

    assertTrue(dialog.isCancelOnEscKey());
    assertTrue(dialog.isCancelOnOutsideClick());
  }

  @Test
  void shouldReopenTheDialogAfterTheUserClosesIt(WebforjTestbed context) {
    context.add(new DialogCloseView());

    var dialog = ComponentLocator.of(Dialog.class).locate();

    UserInteraction.close(dialog);
    assertFalse(dialog.isOpened());

    UserInteraction.click(ComponentLocator.of(Button.class).byText("Open dialog").locate());

    assertTrue(dialog.isOpened());
  }

  @Test
  void shouldKeepTheDialogOpenUntilACloseGestureIsAllowedAgain(WebforjTestbed context) {
    context.add(new DialogCloseView());

    var dialog = ComponentLocator.of(Dialog.class).locate();
    var escape = ComponentLocator.of(RadioButton.class).byText("Escape key").locate();

    UserInteraction.uncheck(escape);
    UserInteraction.uncheck(
        ComponentLocator.of(RadioButton.class).byText("Outside click").locate());

    assertThrows(InteractionRejectedException.class, () -> UserInteraction.close(dialog));
    assertTrue(dialog.isOpened());

    UserInteraction.check(escape);
    UserInteraction.close(dialog);

    assertFalse(dialog.isOpened());
  }
}
