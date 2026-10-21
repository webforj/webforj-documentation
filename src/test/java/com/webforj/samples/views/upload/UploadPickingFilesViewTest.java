package com.webforj.samples.views.upload;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.UploadedFile;
import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.act.component.upload.UploadInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.optiondialog.FileChooserFilter;
import com.webforj.component.upload.Upload;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class UploadPickingFilesViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new UploadPickingFilesView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldLetTheUserPickUpToTwentyFilesAtOnce(WebforjTestbed context) {
    context.add(new UploadPickingFilesView());

    var upload = ComponentLocator.of(Upload.class).locate();

    assertEquals(Upload.SelectionMode.MULTIPLE, upload.getSelectionMode());
    assertEquals(20, upload.getMaxFiles().intValue());
  }

  @Test
  void shouldOfferOnlyThePhotoAndVideoFiltersStartingWithPhotos(WebforjTestbed context) {
    context.add(new UploadPickingFilesView());

    var upload = ComponentLocator.of(Upload.class).locate();

    assertEquals(
        List.of("Photos", "Videos"),
        upload.getFilters().stream().map(FileChooserFilter::getDescription).toList());
    assertEquals("Photos", upload.getActiveFilter().getDescription());
    assertTrue(upload.isMultiFilterSelection());
    assertFalse(upload.isAllFilesFilterEnabled());
  }

  @Test
  void shouldReceiveThePickedPhotosAndRemoveThemFromTheCache(WebforjTestbed context) {
    context.add(new UploadPickingFilesView());

    var upload = ComponentLocator.of(Upload.class).locate();
    List<UploadedFile> uploaded = new ArrayList<>();
    upload.onUpload(e -> uploaded.addAll(e.getFiles()));

    context.pickFile("beach.jpg", "beach.jpg".getBytes(StandardCharsets.UTF_8));
    context.pickFile("sunset.png", "sunset.png".getBytes(StandardCharsets.UTF_8));
    UserInteraction.perform(upload, UploadInteraction.upload("beach.jpg", "sunset.png"));

    assertEquals(
        List.of("beach.jpg", "sunset.png"),
        uploaded.stream().map(UploadedFile::getClientName).toList());
    assertFalse(uploaded.stream().anyMatch(UploadedFile::exists));
  }
}
