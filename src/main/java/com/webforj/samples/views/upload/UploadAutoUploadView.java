package com.webforj.samples.views.upload;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextArea;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.H3;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.toast.Toast;
import com.webforj.component.upload.Upload;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@StyleSheet("ws://css/upload/upload.css")
@FrameTitle("Upload Auto Upload")
public class UploadAutoUploadView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final TextField title = new TextField().setLabel("Subject").setRequired(true);
  private final TextArea description =
      new TextArea().setLabel("Describe the issue").setRows(4).setRequired(true);
  private final Upload attachments = new Upload();

  public UploadAutoUploadView() {
    self.addClassName("upload-demo")
        .setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-m)");

    attachments.addFilter("Attachments", "*.pdf;*.jpg;*.png;*.txt");
    attachments.setMaxFiles(5d);
    attachments.setMaxFileSize(5d * 1024d * 1024d);
    attachments.setAutoUpload(Upload.AutoUpload.ON_SELECT);
    attachments.setVisible(false, Upload.Part.UPLOAD_BUTTON);

    Button submit =
        new Button(
            "Submit ticket",
            ButtonTheme.PRIMARY,
            ev -> Toast.show("Ticket submitted", Theme.SUCCESS));

    self.add(new H3("Submit a ticket"), title, description, attachments, submit);
  }
}
