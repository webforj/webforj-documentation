package com.webforj.samples.views.optiondialog.filesave;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.testbed.ShownOptionDialog;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class FileSaveDialogFiltersViewTest {
  @Test
  void shouldReportThePathTheUserChose(WebforjTestbed context) {
    context.answerFileSaveDialog("/data/export.csv");
    context.add(new FileSaveDialogFiltersView());

    var shown = context.getShownOptionDialogs();

    assertEquals(2, shown.size());
    assertEquals(ShownOptionDialog.Kind.FILE_SAVE, shown.get(0).getKind());
    assertEquals("Save As", shown.get(0).getTitle());
    assertEquals("Saved file to: /data/export.csv", shown.get(1).getMessage());
  }

  @Test
  void shouldReportThatNoPathWasChosenWhenTheUserCancels(WebforjTestbed context) {
    context.answerFileSaveDialog(null);
    context.add(new FileSaveDialogFiltersView());

    var shown = context.getShownOptionDialogs();

    assertEquals(2, shown.size());
    assertEquals("Path Selected", shown.get(1).getTitle());
    assertEquals("No path is selected", shown.get(1).getMessage());
  }
}
