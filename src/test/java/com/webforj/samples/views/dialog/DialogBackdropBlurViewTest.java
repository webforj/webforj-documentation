package com.webforj.samples.views.dialog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class DialogBackdropBlurViewTest {
  @Test
  void shouldRemoveTheBackdropWhenNoneIsPicked(WebforjTestbed context) {
    context.add(new DialogBackdropBlurView());

    var dialog = ComponentLocator.of(Dialog.class).locate();

    UserInteraction.check(ComponentLocator.of(RadioButton.class).byText("None").locate());

    assertFalse(dialog.isBackdrop());
  }

  @Test
  void shouldPaintTheGradientBackdropUntilDefaultIsPickedAgain(WebforjTestbed context) {
    context.add(new DialogBackdropBlurView());

    var dialog = ComponentLocator.of(Dialog.class).locate();

    UserInteraction.check(ComponentLocator.of(RadioButton.class).byText("Custom").locate());

    assertTrue(dialog.isBackdrop());
    assertEquals(
        """
            linear-gradient(
          90deg,
          hsl(200, 70%, 40%),
          hsl(250, 70%, 40%)
        )
        """,
        dialog.getStyle("--dwc-dialog-modal-background"));

    UserInteraction.check(ComponentLocator.of(RadioButton.class).byText("Default").locate());

    assertTrue(dialog.isBackdrop());
    assertEquals("", dialog.getStyle("--dwc-dialog-modal-background"));
  }

  @Test
  void shouldBlurTheBackdropOnlyWhileTheSwitchIsOn(WebforjTestbed context) {
    context.add(new DialogBackdropBlurView());

    var dialog = ComponentLocator.of(Dialog.class).locate();
    var blur = ComponentLocator.of(RadioButton.class).byText("Blur").locate();

    UserInteraction.check(blur);
    assertTrue(dialog.isBlurred());

    UserInteraction.uncheck(blur);
    assertFalse(dialog.isBlurred());
  }
}
