package com.webforj.samples.unit.views.optiondialog.filechooser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.samples.views.optiondialog.filechooser.FileChooserDialogBasicView;
import com.webforj.testbed.ShownOptionDialog;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class FileChooserDialogBasicViewTest {
  @Test
  void shouldReportTheDirectoryTheUserChose(WebforjTestbed context) {
    context.answerFileChooserDialog("/data/reports");
    context.add(new FileChooserDialogBasicView());

    var shown = context.getShownOptionDialogs();

    assertEquals(2, shown.size());
    assertEquals(ShownOptionDialog.Kind.FILE_CHOOSER, shown.get(0).getKind());
    assertEquals("Choose Directory to save", shown.get(0).getTitle());
    assertEquals("Directory Selected", shown.get(1).getTitle());
    assertEquals("Directory selected: /data/reports", shown.get(1).getMessage());
  }

  @Test
  void shouldReportThatNoDirectoryWasChosenWhenTheUserCloses(WebforjTestbed context) {
    context.answerFileChooserDialog(null);
    context.add(new FileChooserDialogBasicView());

    var shown = context.getShownOptionDialogs();

    assertEquals(2, shown.size());
    assertEquals("Directory Selection Failed", shown.get(1).getTitle());
    assertEquals("No directory selected", shown.get(1).getMessage());
  }
}
