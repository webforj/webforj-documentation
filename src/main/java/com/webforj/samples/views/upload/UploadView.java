package com.webforj.samples.views.upload;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.toast.Toast;
import com.webforj.component.upload.Upload;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@StyleSheet("ws://css/upload/upload.css")
@FrameTitle("Upload Basic")
public class UploadView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final TextField name = new TextField().setLabel("Full name").setRequired(true);
  private final Upload resume = new Upload();

  public UploadView() {
    self.addClassName("upload-demo")
        .addClassName("upload-demo--narrow")
        .setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-m)");

    resume.addFilter("Resume", "*.pdf;*.docx");
    resume.setSelectionMode(Upload.SelectionMode.SINGLE);
    resume.setMaxFileSize(5d * 1024d * 1024d);
    resume.setAutoUpload(Upload.AutoUpload.ON_SELECT);
    resume.setVisible(false, Upload.Part.UPLOAD_BUTTON);

    Button submit =
        new Button(
            "Submit application",
            ButtonTheme.PRIMARY,
            ev -> Toast.show("Application submitted", Theme.SUCCESS));

    self.add(
        new H3("Submit your application"),
        name,
        fieldGroup("Resume", "PDF or DOCX, up to 5 MB.", resume),
        submit);
  }

  private FlexLayout fieldGroup(String label, String helperText, Upload upload) {
    FlexLayout group =
        new FlexLayout().setDirection(FlexDirection.COLUMN).setSpacing("var(--dwc-space-2xs)");
    Paragraph fieldLabel = new Paragraph(label);
    fieldLabel.addClassName("upload-demo__field-label");
    Paragraph helper = new Paragraph(helperText);
    helper.addClassName("upload-demo__helper");
    group.add(fieldLabel, upload, helper);
    return group;
  }
}
