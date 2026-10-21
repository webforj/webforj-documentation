package com.webforj.samples.unit.views.optiondialog.confirm;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    context.add(new ConfirmDialogConstructorView());

    assertEquals(1, context.getShownOptionDialogs().size());

    var dialog = context.getShownOptionDialogs().getFirst();

    assertEquals(ShownOptionDialog.Kind.CONFIRM, dialog.getKind());
    assertEquals("Deletion", dialog.getTitle());
    assertEquals(
        "Are you sure you want to delete this file? This action cannot be reverted.",
        dialog.getMessage());
  }
}
