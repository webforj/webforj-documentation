package com.webforj.samples.views.table;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.table.Column;
import com.webforj.component.table.Table;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Table Column Auto-Sizing")
public class TableColumnAutoSizingView extends Composite<FlexLayout> {

  private Table<MusicRecord> table;

  public TableColumnAutoSizingView() {
    FlexLayout layout = getBoundComponent();
    layout.setDirection(FlexDirection.COLUMN);
    layout.setStyle("padding", "20px");
    layout.setStyle("gap", "20px");
    layout.setStyle("height", "100vh");
    layout.setStyle("box-sizing", "border-box");

    FlexLayout controls = createControls();
    table = createTable();
    
    layout.add(controls, table);
  }

  private FlexLayout createControls() {
    FlexLayout controls = new FlexLayout();
    controls.setDirection(FlexDirection.ROW);
    controls.setStyle("gap", "15px");
    controls.setStyle("align-items", "center");
    controls.setStyle("flex-wrap", "wrap");

    Button autoSizeAllBtn = new Button("Auto-Size All Columns");
    autoSizeAllBtn.onClick(e -> autoSizeAllColumns());
    
    Button autoFitBtn = new Button("Auto-Fit to Table Width");
    autoFitBtn.onClick(e -> autoFitColumns());

    Button autoSizeTitleBtn = new Button("Auto-Size Title Only");
    autoSizeTitleBtn.onClick(e -> autoSizeTitleColumn());

    Button resetBtn = new Button("Reset to Default");
    resetBtn.onClick(e -> resetToDefaults());

    controls.add(autoSizeAllBtn, autoFitBtn, autoSizeTitleBtn, resetBtn);
    return controls;
  }

  private Table<MusicRecord> createTable() {
    Table<MusicRecord> table = new Table<>();
    table.setWidth("100%");
    table.setHeight("450px"); 
    table.setStriped(true);

    table.addColumn("Number", MusicRecord::getNumber)
        .setWidth(50f)
        .setMinWidth(40f);  

    table.addColumn("Title", MusicRecord::getTitle)
        .setWidth(100f)  
        .setMinWidth(80f);  

    table.addColumn("Artist", MusicRecord::getArtist)
        .setWidth(200f)  
        .setMinWidth(100f);  

    table.addColumn("Genre", MusicRecord::getMusicType)
        .setWidth(80f)
        .setMinWidth(60f);

    table.addColumn("Cost", record -> String.format("$%.2f", record.getCost()))
        .setWidth(60f)
        .setMinWidth(50f)
        .setAlignment(Column.Alignment.RIGHT);

    table.setRepository(Service.getMusicRecords());
    return table;
  }

  private void autoSizeAllColumns() {
    table.setColumnsToAutoSize().thenAccept(ignored -> {
    });
  }

  private void autoFitColumns() {
    table.setColumnsToAutoFit().thenAccept(ignored -> {
    });
  }

  private void autoSizeTitleColumn() {
    Column<MusicRecord, ?> titleColumn = table.getColumns().stream()
        .filter(col -> "Title".equals(col.getLabel()))
        .findFirst()
        .orElse(null);

    if (titleColumn != null) {
      table.setColumnToAutoSize(titleColumn).thenAccept(ignored -> {
      });
    }
  }

  private void resetToDefaults() {
    
    table.getColumns().forEach(column -> {
      String label = column.getLabel();
      switch (label) {
        case "Number":
          column.setWidth(50f);
          break;
        case "Title":
          column.setWidth(100f); 
          break;
        case "Artist":
          column.setWidth(200f);  
          break;
        case "Genre":
          column.setWidth(80f);
          break;
        case "Cost":
          column.setWidth(60f);
          break;
      }
    });
    
    table.refreshColumns();
  }
}