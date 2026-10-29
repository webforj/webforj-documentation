package com.webforj.samples.unit.views.optiondialog.fileupload;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.samples.views.optiondialog.fileupload.FileUploadDialogBasicView;
import com.webforj.testbed.ShownOptionDialog;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class FileUploadDialogBasicViewTest {
  @Test
  void shouldConfirmTheUploadOfTheFileTheUserPicked(WebforjTestbed context) {
    context.answerFileUploadDialog("report.txt");
    context.add(new FileUploadDialogBasicView());

    var shown = context.getShownOptionDialogs();

    assertEquals(2, shown.size());
    assertEquals(ShownOptionDialog.Kind.FILE_UPLOAD, shown.get(0).getKind());
    assertEquals("Upload a file", shown.get(0).getTitle());
    assertEquals("File Uploaded", shown.get(1).getTitle());
    assertEquals("File uploaded successfully", shown.get(1).getMessage());
  }

  @Test
  void shouldReportThatNoFileWasPickedWhenTheUserCloses(WebforjTestbed context) {
    context.answerFileUploadDialog(null);
    context.add(new FileUploadDialogBasicView());

    var shown = context.getShownOptionDialogs();

    assertEquals(2, shown.size());
    assertEquals("File Selection Failed", shown.get(1).getTitle());
    assertEquals("No file selected", shown.get(1).getMessage());
  }
}
