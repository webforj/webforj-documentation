package com.webforj.samples.views.upload;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Span;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optiondialog.FileChooserFilter;
import com.webforj.component.upload.Upload;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import java.util.List;

@Route
@StyleSheet("ws://css/upload/upload.css")
@FrameTitle("Upload Default Slot")
public class UploadDefaultSlotView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public UploadDefaultSlotView() {
    self.addClassName("upload-demo")
        .addClassName("upload-demo--narrow")
        .setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-m)");

    Upload upload = new Upload(List.of(new FileChooserFilter("Spreadsheets", "*.csv;*.xlsx")));
    upload.setPreset(Upload.Preset.HEADLESS);
    upload.setMaxFileSize(2d * 1024d * 1024d);
    upload.setHeight("16em");
    upload.onChange(ev -> upload.upload());

    FlexLayout dropSurface =
        new FlexLayout()
            .setDirection(FlexDirection.COLUMN)
            .setAlignment(FlexAlignment.CENTER)
            .setJustifyContent(FlexJustifyContent.CENTER)
            .setSpacing("var(--dwc-space-xs)");
    dropSurface.setHeight("100%");

    Span title = new Span("Drop your contact file here");
    title.addClassName("upload-demo__drop-title");
    Span hint = new Span("CSV or Excel, up to 2 MB.");
    hint.addClassName("upload-demo__drop-hint");
    dropSurface.add(TablerIcon.create("cloud-upload"), title, hint);

    upload.add(dropSurface);

    self.add(new H3("Import contacts"), upload);
  }
}
