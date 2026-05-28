package com.webforj.samples.views.upload;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Span;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.progressbar.ProgressBar;
import com.webforj.component.upload.Upload;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@StyleSheet("ws://css/upload/uploadEvents.css")
@FrameTitle("Upload Events")
public class UploadEventsView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Upload upload = new Upload();
  private final ProgressBar progress = new ProgressBar();
  private final Span status = new Span("Idle");

  public UploadEventsView() {
    self.addClassName("upload-events")
        .setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-m)");

    upload.setMaxFiles(25d);
    upload.setMaxFileSize(5d * 1024d * 1024d);

    progress.setValue(0).setText("{{x}}%");
    status.addClassName("upload-events__status");

    upload.onChange(
        ev -> {
          progress.setValue(0);
          status.setText("Idle");
        });

    upload.onListProgress(
        ev -> {
          int done = ev.getListTotal() - ev.getListRemaining();
          progress.setValue((int) ev.getListProgress());
          status.setText("Uploading " + done + " of " + ev.getListTotal());
        });

    upload.onComplete(
        ev -> {
          int uploaded = ev.getUploadedFiles().size();
          int failed = ev.getFailedFiles().size();
          progress.setValue(100);
          status.setText("Done. " + uploaded + " uploaded, " + failed + " failed.");
        });

    self.add(new H3("File uploader"), upload, progress, status);
  }
}
