package com.webforj.samples.unit.views.optiondialog.filesave;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.samples.views.optiondialog.filesave.FileSaveDialogBasicView;
import com.webforj.testbed.ShownOptionDialog;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class FileSaveDialogBasicViewTest {
  @Test
  void shouldReportThePathTheUserChose(WebforjTestbed context) {
    context.answerFileSaveDialog("/data/report.xls");
    var reopened =
        assertThrows(IllegalStateException.class, () -> context.add(new FileSaveDialogBasicView()));

    var shown = context.getShownOptionDialogs();

    assertEquals(3, shown.size());
    assertEquals(shown.get(0).getTitle(), shown.get(2).getTitle());
    assertTrue(reopened.getMessage().contains("no answer was scripted"));
    assertEquals(ShownOptionDialog.Kind.FILE_SAVE, shown.get(0).getKind());
    assertEquals("Save As", shown.get(0).getTitle());
    assertEquals("Saved file to: /data/report.xls", shown.get(1).getMessage());
  }

  @Test
  void shouldReportThatNoPathWasChosenWhenTheUserCancels(WebforjTestbed context) {
    context.answerFileSaveDialog(null);
    var reopened =
        assertThrows(IllegalStateException.class, () -> context.add(new FileSaveDialogBasicView()));

    var shown = context.getShownOptionDialogs();

    assertEquals(3, shown.size());
    assertEquals(shown.get(0).getTitle(), shown.get(2).getTitle());
    assertTrue(reopened.getMessage().contains("no answer was scripted"));
    assertEquals("Path Selected", shown.get(1).getTitle());
    assertEquals("No path is selected", shown.get(1).getMessage());
  }
}
