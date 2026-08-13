package com.webforj.samples.views.upload;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Span;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.toast.Toast;
import com.webforj.component.upload.Upload;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@BundleEntry("css/upload/upload.css")
@FrameTitle("Upload Basic")
public class UploadView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final TextField name = new TextField().setLabel("Full name").setRequired(true);
  private final Upload resumeUpload = new Upload();
  private final Button submit =
      new Button(
          "Submit application",
          ButtonTheme.PRIMARY,
          ev -> Toast.show("Application submitted", Theme.SUCCESS));

  public UploadView() {
    self.addClassName("upload-demo")
        .addClassName("upload-demo--narrow")
        .setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-m)");

    configUpload();

    self.add(
        new H3("Submit your application"),
        name,
        fieldGroup("Resume", "PDF or DOCX, up to 5 MB.", resumeUpload),
        submit);
  }

  private void configUpload() {
    resumeUpload
        .addFilter("Resume", "*.pdf;*.docx")
        .setActiveFilter("Resume")
        .setSelectionMode(Upload.SelectionMode.SINGLE)
        .setMaxFileSize(5d * 1024d * 1024d)
        .setAutoUpload(Upload.AutoUpload.ON_SELECT)
        .setVisible(false, Upload.Part.UPLOAD_BUTTON)
        .setFileSystemAccess(false)
        .setAllFilesFilterEnabled(false)
        .setStyle("margin-bottom", "var(--dwc-space-s)")
        .onUpload(
            e ->
                e.getFiles()
                    .forEach(
                        file -> {
                          try {
                            file.delete();
                          } catch (Exception ex) {
                            // skip
                          }
                        }));
  }

  private FlexLayout fieldGroup(String label, String helperText, Upload upload) {
    FlexLayout group =
        new FlexLayout().setDirection(FlexDirection.COLUMN).setSpacing("var(--dwc-space-2xs)");
    Span fieldLabel = new Span(label);
    fieldLabel.addClassName("upload-demo__field-label");
    Span helper = new Span(helperText);
    helper.addClassName("upload-demo__helper");
    group.add(fieldLabel, upload, helper);

    return group;
  }
}
