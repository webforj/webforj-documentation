package com.webforj.samples.views.upload;

import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.flexlayout.FlexWrap;
import com.webforj.component.upload.Upload;
import com.webforj.component.upload.UploadTheme;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Upload Themes")
public class UploadThemesView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public UploadThemesView() {
    self.setWrap(FlexWrap.WRAP)
        .setSpacing("var(--dwc-space-m)")
        .setStyle("padding", "var(--dwc-space-l)");

    UploadTheme[] paired = {
      UploadTheme.DEFAULT, UploadTheme.OUTLINED_DEFAULT,
      UploadTheme.PRIMARY, UploadTheme.OUTLINED_PRIMARY,
      UploadTheme.SUCCESS, UploadTheme.OUTLINED_SUCCESS,
      UploadTheme.WARNING, UploadTheme.OUTLINED_WARNING,
      UploadTheme.DANGER, UploadTheme.OUTLINED_DANGER,
      UploadTheme.INFO, UploadTheme.OUTLINED_INFO,
      UploadTheme.GRAY, UploadTheme.OUTLINED_GRAY
    };

    for (UploadTheme theme : paired) {
      Upload upload = new Upload();
      upload.addFilter("Files", "*.*");
      upload.setTheme(theme);
      self.add(upload);
      self.setItemBasis("calc(50% - var(--dwc-space-m))", upload);
    }
  }
}
