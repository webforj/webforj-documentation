package com.webforj.samples.unit.views.optiondialog.confirm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.component.optiondialog.ConfirmDialog;
import com.webforj.samples.views.optiondialog.confirm.ConfirmDialogConstructorView;
import com.webforj.testbed.ShownOptionDialog;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ConfirmDialogConstructorViewTest {
  @Test
  void shouldAskTheUserToConfirmTheDeletion(WebforjTestbed context) {
    context.answerConfirmDialog(ConfirmDialog.Result.OK);
    var reopened =
        assertThrows(
            IllegalStateException.class, () -> context.add(new ConfirmDialogConstructorView()));

    var shown = context.getShownOptionDialogs();

    assertEquals(2, shown.size());
    assertEquals(ShownOptionDialog.Kind.CONFIRM, shown.get(0).getKind());
    assertEquals("Deletion", shown.get(0).getTitle());
    assertEquals(
        "Are you sure you want to delete this file? This action cannot be reverted.",
        shown.get(0).getMessage());
    assertEquals(shown.get(0).getTitle(), shown.get(1).getTitle());
    assertTrue(reopened.getMessage().contains("no answer was scripted"));
  }
}
