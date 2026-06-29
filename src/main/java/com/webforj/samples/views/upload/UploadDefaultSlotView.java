package com.webforj.samples.views.upload;

import com.webforj.UploadedFile;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.H3;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optiondialog.FileChooserFilter;
import com.webforj.component.table.Table;
import com.webforj.component.upload.Upload;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

@Route
@FrameTitle("Upload Default Slot")
public class UploadDefaultSlotView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Table<List<String>> table = new Table<>();

  public UploadDefaultSlotView() {
    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-m)")
        .setStyle("padding", "var(--dwc-space-l)");

    Upload upload = new Upload(List.of(new FileChooserFilter("CSV Files", "*.csv")));
    upload.setPreset(Upload.Preset.HEADLESS);
    upload.setHeight("22em");
    upload.setFileSystemAccess(false);

    table.setStriped(true);
    table.setWidth("100%");
    table.setHeight("100%");
    table.addColumn("Drop a CSV file here to load rows", row -> "");
    upload.add(table);

    upload.onChange(ev -> upload.upload());

    upload.onUpload(
        ev -> {
          if (!ev.getFiles().isEmpty()) {
            File temp = null;
            try {
              UploadedFile first = ev.getFiles().get(0);
              temp =
                  first.move(
                      new File(
                              System.getProperty("java.io.tmpdir"),
                              System.currentTimeMillis() + "-" + first.getSanitizedClientName())
                          .getAbsolutePath());
              loadCsv(temp);
            } catch (Exception ex) {
              // skip
            } finally {
              if (temp != null) {
                temp.delete();
              }
            }
          }

          ev.getFiles()
              .forEach(
                  file -> {
                    try {
                      file.delete();
                    } catch (Exception ex) {
                      // skip
                    }
                  });
        });

    self.add(new H3("Import contacts"), upload);
  }

  private void loadCsv(File file) throws IOException {
    try (Reader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8);
        CSVParser parser =
            CSVFormat.DEFAULT
                .builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build()
                .parse(reader)) {
      List<String> headers = parser.getHeaderNames();
      List<List<String>> rows = new ArrayList<>();
      for (CSVRecord record : parser) {
        List<String> row = new ArrayList<>(headers.size());
        for (String header : headers) {
          row.add(record.get(header));
        }
        rows.add(row);
      }
      rebuildColumns(headers);
      table.setItems(rows);
    }
  }

  private void rebuildColumns(List<String> headers) {
    new ArrayList<>(table.getColumns()).forEach(table::removeColumn);
    for (int i = 0; i < headers.size(); i++) {
      final int index = i;
      table.addColumn(headers.get(i), row -> index < row.size() ? row.get(index) : "");
    }
    table.refreshColumns();
  }
}
