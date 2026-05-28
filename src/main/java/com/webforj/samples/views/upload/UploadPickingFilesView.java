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

    upload.addFilter("Photos", "*.jpg;*.jpeg;*.png;*.heic");
    upload.addFilter("Videos", "*.mp4;*.mov");
    upload.setActiveFilter("Photos");
    upload.setMultiFilterSelection(true);
    upload.setAllFilesFilterEnabled(false);
    upload.setSelectionMode(Upload.SelectionMode.MULTIPLE);
    upload.setMaxFiles(20d);
    upload.setMaxFileSize(10d * 1024d * 1024d);

    self.add(new H3("Gallery uploader"), upload);
  }
}
