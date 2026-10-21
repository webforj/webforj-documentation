package com.webforj.samples.views.optiondialog.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.testbed.ShownOptionDialog;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class InputDialogBasicViewTest {
  @Test
  void shouldWaitForTheConfirmationCodeBeforeDeleting(WebforjTestbed context) {
    context.answerInputDialog("incorrect");
    context.answerInputDialog("7ANfB");
    context.add(new InputDialogBasicView());

    var shown = context.getShownOptionDialogs();

    assertEquals(ShownOptionDialog.Kind.INPUT, shown.getFirst().getKind());
    assertEquals(
        List.of(
            "Delete Repository",
            "Repository Deletion Failed",
            "Delete Repository",
            "Repository Deleted"),
        shown.stream().map(ShownOptionDialog::getTitle).toList());
    assertEquals("Repository was deleted successfully", shown.getLast().getMessage());
  }

  @Test
  void shouldAbortWithoutAskingAgainWhenTheUserCancels(WebforjTestbed context) {
    context.answerInputDialog(null);
    context.add(new InputDialogBasicView());

    var shown = context.getShownOptionDialogs();

    assertEquals(
        List.of("Delete Repository", "Repository Deletion Aborted"),
        shown.stream().map(ShownOptionDialog::getTitle).toList());
    assertEquals("Aborted repository deletion.", shown.getLast().getMessage());
  }
}
