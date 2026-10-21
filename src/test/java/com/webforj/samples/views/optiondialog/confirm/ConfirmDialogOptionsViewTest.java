package com.webforj.samples.views.optiondialog.confirm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.component.optiondialog.ConfirmDialog;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@WebforjTest
class ConfirmDialogOptionsViewTest {
  @ParameterizedTest
  @CsvSource({
    "FIRST_CUSTOM_BUTTON, Discarded, Changes discarded",
    "SECOND_CUSTOM_BUTTON, Saved, Changes saved"
  })
  void shouldReportTheChoiceOfTheCustomButtonAndAskAgain(
      ConfirmDialog.Result answer, String title, String message, WebforjTestbed context) {
    context.answerConfirmDialog(answer);

    IllegalStateException pending =
        assertThrows(IllegalStateException.class, ConfirmDialogOptionsView::new);

    var shown = context.getShownOptionDialogs();

    assertTrue(pending.getMessage().contains("no answer was scripted"));
    assertEquals(3, shown.size());
    assertEquals("Unsaved changes", shown.get(0).getTitle());
    assertEquals(
        "There are unsaved changes. Do you want to discard or save them?",
        shown.get(0).getMessage());
    assertEquals(title, shown.get(1).getTitle());
    assertEquals(message, shown.get(1).getMessage());
    assertEquals("Unsaved changes", shown.get(2).getTitle());
  }
}
