package com.webforj.samples.views.upload;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.webforj.UploadedFile;
import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.act.component.upload.UploadInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.upload.Upload;
import com.webforj.component.upload.UploadTheme;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class UploadThemesViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new UploadThemesView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldCombineThePrimaryThemeWithTheInlinePreset(WebforjTestbed context) {
    context.add(new UploadThemesView());

    var upload = ComponentLocator.of(Upload.class).locate();

    assertEquals(UploadTheme.PRIMARY, upload.getTheme());
    assertEquals(Upload.Preset.INLINE, upload.getPreset());
  }

  @Test
  void shouldReceiveTheUploadedFileAndRemoveItFromTheCache(WebforjTestbed context) {
    context.add(new UploadThemesView());

    var upload = ComponentLocator.of(Upload.class).locate();
    List<UploadedFile> uploaded = new ArrayList<>();
    upload.onUpload(e -> uploaded.addAll(e.getFiles()));

    context.pickFile("notes.txt", "notes.txt".getBytes(StandardCharsets.UTF_8));
    UserInteraction.perform(upload, UploadInteraction.upload("notes.txt"));

    assertEquals(List.of("notes.txt"), uploaded.stream().map(UploadedFile::getClientName).toList());
    assertFalse(uploaded.stream().anyMatch(UploadedFile::exists));
  }
}
