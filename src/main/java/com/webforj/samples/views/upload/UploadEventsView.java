package com.webforj.samples.views.upload;

import com.webforj.component.Composite;
import com.webforj.component.card.Card;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Span;
import com.webforj.component.layout.columnslayout.ColumnsLayout;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.progressbar.ProgressBar;
import com.webforj.component.upload.Upload;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Upload Events")
public class UploadEventsView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Upload upload = new Upload();
  private final ProgressBar progress = new ProgressBar();
  private final Span status = new Span("Idle");

  public UploadEventsView() {
    self.setHeight("100vh")
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER);

    upload.setMaxFiles(25d);
    upload.setMaxFileSize(5d * 1024d * 1024d);
    upload.setFileSystemAccess(false);
    upload.onUpload(
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

    progress.setValue(0).setText("{{x}}%");

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

    self.add(createCard());
  }

  private Card createCard() {
    Card card = new Card();
    card.setWidth(520);
    // card.addClassName("card");
    card.setStyle("--dwc-card-title-font-size", "var(--dwc-font-size-xl)");
    card.addToTitle(new H3("File uploader"));
    ColumnsLayout wrapper = new ColumnsLayout(upload, progress, status);
    card.addToBody(wrapper);
    return card;
  }
}
