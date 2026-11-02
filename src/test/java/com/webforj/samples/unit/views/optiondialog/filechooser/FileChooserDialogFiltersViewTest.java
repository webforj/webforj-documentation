package com.webforj.samples.unit.views.optiondialog.filechooser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.samples.views.optiondialog.filechooser.FileChooserDialogFiltersView;
import com.webforj.testbed.ShownOptionDialog;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class FileChooserDialogFiltersViewTest {
  @Test
  void shouldReportTheFileTheUserChose(WebforjTestbed context) {
    context.answerFileChooserDialog("/data/report.txt");
    var reopened =
        assertThrows(
            IllegalStateException.class, () -> context.add(new FileChooserDialogFiltersView()));

    var shown = context.getShownOptionDialogs();

    assertEquals(3, shown.size());
    assertEquals(shown.get(0).getTitle(), shown.get(2).getTitle());
    assertTrue(reopened.getMessage().contains("no answer was scripted"));
    assertEquals(ShownOptionDialog.Kind.FILE_CHOOSER, shown.get(0).getKind());
    assertEquals("Choose a file to download", shown.get(0).getTitle());
    assertEquals("File Selected", shown.get(1).getTitle());
    assertEquals("File selected: /data/report.txt", shown.get(1).getMessage());
  }

  @Test
  void shouldReportThatNoFileWasChosenWhenTheUserCloses(WebforjTestbed context) {
    context.answerFileChooserDialog(null);
    var reopened =
        assertThrows(
            IllegalStateException.class, () -> context.add(new FileChooserDialogFiltersView()));

    var shown = context.getShownOptionDialogs();

    assertEquals(3, shown.size());
    assertEquals(shown.get(0).getTitle(), shown.get(2).getTitle());
    assertTrue(reopened.getMessage().contains("no answer was scripted"));
    assertEquals("File Selection Failed", shown.get(1).getTitle());
    assertEquals("No file selected", shown.get(1).getMessage());
  }
}
