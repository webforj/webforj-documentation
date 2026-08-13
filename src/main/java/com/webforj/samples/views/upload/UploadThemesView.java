package com.webforj.samples.views.upload;

import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.upload.Upload;
import com.webforj.component.upload.UploadTheme;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Upload Themes")
public class UploadThemesView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public UploadThemesView() {
    self.setDirection(FlexDirection.COLUMN).setStyle("padding", "var(--dwc-space-l)");

    Upload upload = new Upload();
    upload.addFilter("Files", "*.*");
    upload.setTheme(UploadTheme.PRIMARY);
    upload.setPreset(Upload.Preset.INLINE);
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

    self.add(upload);
  }
}
