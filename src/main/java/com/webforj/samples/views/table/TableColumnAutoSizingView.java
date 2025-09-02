package com.webforj.samples.views.table;

import static com.webforj.component.optiondialog.OptionDialog.showMessageDialog;

import java.util.EnumSet;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.table.Column;
import com.webforj.component.table.Table;
import com.webforj.component.table.Table.Border;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Table Column Auto Sizing")
public class TableColumnAutoSizingView extends Composite<FlexLayout> {

  private Table<MusicRecord> table;

  public TableColumnAutoSizingView() {
    FlexLayout layout = getBoundComponent();
    layout.setDirection(FlexDirection.COLUMN);
    layout.setStyle("padding", "10px");
    layout.setStyle("gap", "10px");
    layout.setStyle("height", "100vh");
    layout.setStyle("box-sizing", "border-box");

    FlexLayout buttonPanel = new FlexLayout();
    buttonPanel.setDirection(FlexDirection.ROW);
    buttonPanel.setStyle("gap", "10px");

    table = new Table<>();
    table.setWidth("100%");
    table.setHeight("100%");
    table.setStriped(true);
    table.setBordersVisible(EnumSet.of(Border.AROUND, Border.ROWS, Border.COLUMNS));

    table.addColumn("Number", MusicRecord::getNumber);
    table.addColumn("Title", MusicRecord::getTitle).setFlex(1);
    table.addColumn("Artist", MusicRecord::getArtist);
    table.addColumn("Genre", MusicRecord::getMusicType);
    table.addColumn("Cost", MusicRecord::getCost);

    table.setRepository(Service.getMusicRecords());

    Button sizeToFitBtn = new Button("Size To Fit");
    sizeToFitBtn.onClick(e -> sizeToFit());

    Button autoSizeAllBtn = new Button("Auto-size All");
    autoSizeAllBtn.onClick(e -> autoSizeAll());

    Button autoSizeTitleBtn = new Button("Auto-size (TITLE)");
    autoSizeTitleBtn.onClick(e -> autoSizeTitle());

    Button getAllSizesBtn = new Button("Get All sizes");
    getAllSizesBtn.onClick(e -> showColumnWidths());

    table.onColumnResize(e -> {
      StringBuilder sb = new StringBuilder();
      var column = e.getColumn();
      sb.append("Column: ").append(column.getLabel())
          .append(" - New Width: ").append(column.getWidth())
          .append(" - Old Width: ").append(e.getOldWidth())
          .append("\n");

      showMessageDialog(sb.toString(), "Table Column Sizes");
    });

    Button refreshBtn = new Button("Refresh");
    refreshBtn.onClick(e -> table.refresh());

    buttonPanel.add(sizeToFitBtn, autoSizeAllBtn, autoSizeTitleBtn, getAllSizesBtn, refreshBtn);
    layout.add(buttonPanel, table);
  }

  private void sizeToFit() {
    table.setColumnsToAutoFit().thenAccept(ignored -> showColumnWidths());
  }

  private void autoSizeAll() {
    table.setColumnsToAutoSize().thenAccept(ignored -> showColumnWidths());
  }

  private void autoSizeTitle() {
    Column<MusicRecord, ?> titleColumn = table.getColumns().stream()
        .filter(col -> "Title".equals(col.getLabel()))
        .findFirst()
        .orElse(null);

    if (titleColumn != null) {
      table.setColumnToAutoSize(titleColumn)
          .thenAccept(ignored -> showColumnWidths());
    }
  }

  public void showColumnWidths() {
    StringBuilder sb = new StringBuilder();
    table.getColumns().forEach(column -> {
      sb.append("Column: ").append(column.getLabel())
          .append(" - Width: ").append(column.getWidth())
          .append(" - Flex: ").append(column.getFlex())
          .append("\n");
    });

    showMessageDialog(sb.toString(), "Table Column Widths");
  }
}