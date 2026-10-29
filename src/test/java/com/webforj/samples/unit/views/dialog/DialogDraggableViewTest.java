package com.webforj.samples.unit.views.dialog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.samples.views.dialog.DialogDraggableView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class DialogDraggableViewTest {
  @Test
  void shouldLetTheUserMoveTheDialogAndSnapItWithinEightyPixelsOfAnEdge(WebforjTestbed context) {
    context.add(new DialogDraggableView());

    var dialog = ComponentLocator.of(Dialog.class).locate();

    assertTrue(dialog.isMoveable());
    assertTrue(dialog.isSnapToEdge());
    assertEquals(80, dialog.getSnapThreshold());
  }

  @Test
  void shouldStopSnappingToTheEdgesWhileTheSwitchIsOff(WebforjTestbed context) {
    context.add(new DialogDraggableView());

    var dialog = ComponentLocator.of(Dialog.class).locate();
    var snapToEdge =
        ComponentLocator.of(RadioButton.class).byText("Snap to viewport edges").locate();

    UserInteraction.uncheck(snapToEdge);
    assertFalse(dialog.isSnapToEdge());

    UserInteraction.check(snapToEdge);
    assertTrue(dialog.isSnapToEdge());
  }
}
