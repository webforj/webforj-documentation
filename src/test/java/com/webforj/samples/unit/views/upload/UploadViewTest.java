package com.webforj.samples.unit.views.upload;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.optiondialog.FileChooserFilter;
import com.webforj.component.toast.Toast;
import com.webforj.component.upload.Upload;
import com.webforj.samples.views.upload.UploadView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class UploadViewTest {
  @Test
  void shouldRestrictTheResumeToOnePdfOrDocxOfAtMostFiveMegabytes(WebforjTestbed context) {
    context.add(new UploadView());

    var upload = ComponentLocator.of(Upload.class).locate();

    assertEquals(
        List.of("*.pdf;*.docx"),
        upload.getFilters().stream().map(FileChooserFilter::getPattern).toList());
    assertFalse(upload.isAllFilesFilterEnabled());
    assertEquals(Upload.SelectionMode.SINGLE, upload.getSelectionMode());
    assertEquals(5 * 1024 * 1024, upload.getMaxFileSize().longValue());
  }

  @Test
  void shouldUploadOnSelectInsteadOfShowingTheUploadButton(WebforjTestbed context) {
    context.add(new UploadView());

    var upload = ComponentLocator.of(Upload.class).locate();

    assertEquals(Upload.AutoUpload.ON_SELECT, upload.getAutoUpload());
    assertFalse(upload.isVisible(Upload.Part.UPLOAD_BUTTON));
    assertTrue(upload.isVisible(Upload.Part.PICKER_BUTTON));
    assertTrue(upload.isVisible(Upload.Part.DROP_LABEL));
    assertTrue(upload.isVisible(Upload.Part.LIST));
  }

  @Test
  void shouldConfirmTheApplicationOnSubmit(WebforjTestbed context) {
    context.add(new UploadView());

    UserInteraction.click(ComponentLocator.of(Button.class).locate());

    assertEquals("Application submitted", ComponentLocator.of(Toast.class).locate().getText());
  }
}
