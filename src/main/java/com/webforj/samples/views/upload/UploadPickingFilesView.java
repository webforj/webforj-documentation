package com.webforj.samples.views.upload;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.H3;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.upload.Upload;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@StyleSheet("ws://css/upload/upload.css")
@FrameTitle("Upload Picking Files")
public class UploadPickingFilesView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Upload upload = new Upload();

  public UploadPickingFilesView() {
    self.addClassName("upload-demo")
        .setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-m)");

    configUpload();

    self.add(new H3("Gallery uploader"), upload);
  }

  public void configUpload() {
    upload
        .addFilter("Photos", "*.jpg;*.jpeg;*.png;*.heic")
        .addFilter("Videos", "*.mp4;*.mov")
        .setActiveFilter("Photos")
        .setMultiFilterSelection(true)
        .setAllFilesFilterEnabled(false)
        .setSelectionMode(Upload.SelectionMode.MULTIPLE)
        .setMaxFiles(20d)
        .setMaxFileSize(10d * 1024d * 1024d)
        .setFileSystemAccess(false)
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
}
