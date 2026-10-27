package com.webforj.samples.views.upload;

import com.webforj.component.Composite;
import com.webforj.component.card.Card;
import com.webforj.component.html.elements.H4;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.upload.Upload;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Upload Presets")
public class UploadPresetsView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public UploadPresetsView() {
    self.setSize("560", "100vh")
        .setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setSpacing("var(--dwc-space-m)");

    self.add(
        presetCard("FULL", Upload.Preset.FULL),
        presetCard("INLINE", Upload.Preset.INLINE),
        presetCard("BUTTON_ONLY", Upload.Preset.BUTTON_ONLY),
        presetCard("DROPZONE", Upload.Preset.DROPZONE));
  }

  private Card presetCard(String label, Upload.Preset preset) {
    Card wrapper = new Card();
    Upload upload = new Upload();
    upload.addFilter("Files", "*.*");
    upload.setPreset(preset);
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

    wrapper.addToTitle(new H4(label));
    wrapper.setStyle("--dwc-card-title-font-size", "var(--dwc-font-size-l)");
    wrapper.setWidth(560);
    wrapper.addToBody(upload);

    return wrapper;
  }
}
