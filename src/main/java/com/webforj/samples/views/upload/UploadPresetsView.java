package com.webforj.samples.views.upload;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.H4;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.upload.Upload;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@StyleSheet("ws://css/upload/uploadPresets.css")
@FrameTitle("Upload Presets")
public class UploadPresetsView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public UploadPresetsView() {
    self.addClassName("upload-presets")
        .setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-m)");

    self.add(
        presetCard("FULL", Upload.Preset.FULL),
        presetCard("INLINE", Upload.Preset.INLINE),
        presetCard("BUTTON_ONLY", Upload.Preset.BUTTON_ONLY),
        presetCard("DROPZONE", Upload.Preset.DROPZONE));
  }

  private FlexLayout presetCard(String label, Upload.Preset preset) {
    Upload upload = new Upload();
    upload.addFilter("Files", "*.*");
    upload.setPreset(preset);

    FlexLayout wrapper =
        new FlexLayout().setDirection(FlexDirection.COLUMN).setSpacing("var(--dwc-space-s)");
    wrapper.addClassName("upload-presets__subcard");

    H4 heading = new H4(label);
    heading.addClassName("upload-presets__subcard-title");

    wrapper.add(heading, upload);
    return wrapper;
  }
}
