package com.webforj.samples.views.optiondialog.filechooser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.testbed.ShownOptionDialog;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class FileChooserDialogFiltersViewTest {
  @Test
  void shouldReportTheFileTheUserChose(WebforjTestbed context) {
    context.answerFileChooserDialog("/data/report.txt");
    context.add(new FileChooserDialogFiltersView());

    var shown = context.getShownOptionDialogs();

    assertEquals(2, shown.size());
    assertEquals(ShownOptionDialog.Kind.FILE_CHOOSER, shown.get(0).getKind());
    assertEquals("Choose a file to download", shown.get(0).getTitle());
    assertEquals("File Selected", shown.get(1).getTitle());
    assertEquals("File selected: /data/report.txt", shown.get(1).getMessage());
  }

  @Test
  void shouldReportThatNoFileWasChosenWhenTheUserCloses(WebforjTestbed context) {
    context.answerFileChooserDialog(null);
    context.add(new FileChooserDialogFiltersView());

    var shown = context.getShownOptionDialogs();

    assertEquals(2, shown.size());
    assertEquals("File Selection Failed", shown.get(1).getTitle());
    assertEquals("No file selected", shown.get(1).getMessage());
  }
}
