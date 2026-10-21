package com.webforj.samples.views.upload;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.UploadedFile;
import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.html.elements.Span;
import com.webforj.component.progressbar.ProgressBar;
import com.webforj.component.upload.Upload;
import com.webforj.component.upload.event.UploadChangeEvent;
import com.webforj.component.upload.event.UploadCompleteEvent;
import com.webforj.component.upload.event.UploadListProgressEvent;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

@WebforjTest
class UploadEventsViewTest {
  @Test
  void shouldShowTheBatchProgressWhileTheFilesTransfer(WebforjTestbed context) {
    context.add(new UploadEventsView());

    var upload = ComponentLocator.of(Upload.class).locate();
    var progress = ComponentLocator.of(ProgressBar.class).locate();
    var status = ComponentLocator.of(Span.class).locate();

    UserInteraction.fireEvent(
        new UploadListProgressEvent(
            upload,
            Map.of(
                "listTotal",
                2,
                "listRemaining",
                1,
                "listTransferredBytes",
                200L,
                "listTotalBytes",
                400L,
                "entries",
                List.of(
                    Map.of("name", "first.txt", "size", 100L, "loaded", 100L, "state", "complete"),
                    Map.of(
                        "name",
                        "second.txt",
                        "size",
                        300L,
                        "loaded",
                        100L,
                        "state",
                        "inProgress")))));

    assertEquals(50, progress.getValue());
    assertEquals("Uploading 1 of 2", status.getText());
  }

  @Test
  void shouldSummarizeTheFinishedBatch(WebforjTestbed context) {
    context.add(new UploadEventsView());

    var upload = ComponentLocator.of(Upload.class).locate();
    var progress = ComponentLocator.of(ProgressBar.class).locate();
    var status = ComponentLocator.of(Span.class).locate();

    UserInteraction.fireEvent(
        new UploadCompleteEvent(
            upload,
            Map.of(
                "uploaded",
                List.of(new UploadedFile("first.txt"), new UploadedFile("second.txt")),
                "failed",
                List.of(new UploadedFile("failed.txt")))));

    assertEquals(100, progress.getValue());
    assertEquals("Done. 2 uploaded, 1 failed.", status.getText());
  }

  @Test
  void shouldResetTheProgressWhenTheUserPicksNewFiles(WebforjTestbed context) {
    context.add(new UploadEventsView());

    var upload = ComponentLocator.of(Upload.class).locate();
    var progress = ComponentLocator.of(ProgressBar.class).locate();
    var status = ComponentLocator.of(Span.class).locate();

    UserInteraction.fireEvent(
        new UploadCompleteEvent(
            upload,
            Map.of("uploaded", List.of(new UploadedFile("first.txt")), "failed", List.of())));
    UserInteraction.fireEvent(
        new UploadChangeEvent(upload, Map.of("files", List.of(new UploadedFile("next.txt")))));

    assertEquals(0, progress.getValue());
    assertEquals("Idle", status.getText());
  }
}
