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
  private final FlexLayout self = getBoundComponent();

  private final Table<MusicRecord> table = new Table<>();
  private final Column<MusicRecord, ?> numberCol = table.addColumn("Number", MusicRecord::getNumber);
  private final Column<MusicRecord, ?> titleCol = table.addColumn("Title", MusicRecord::getTitle);
  private final Column<MusicRecord, ?> artistCol = table.addColumn("Artist", MusicRecord::getArtist);
  private final Column<MusicRecord, ?> genreCol = table.addColumn("Genre", MusicRecord::getMusicType);
  private final Column<MusicRecord, ?> costCol = table.addColumn("Cost", r -> String.format("$%.2f", r.getCost()))
    .setAlignment(Column.Alignment.RIGHT);

  public TableColumnAutoSizingView() {
    self.setDirection(FlexDirection.COLUMN)
      .setPadding("var(--dwc-space-l)")
      .setSpacing("var(--dwc-space-l)")
      .setHeight("100vh")
      .setStyle("box-sizing", "border-box")
      .add(createControls(), createTable());
  }

  private FlexLayout createControls() {
    FlexLayout controls = new FlexLayout()
      .setDirection(FlexDirection.ROW)
      .setStyle("gap", "15px")
      .setStyle("align-items", "center")
      .setStyle("flex-wrap", "wrap");

    Button autoSizeAllBtn = new Button("Auto-Size All Columns", e -> autoSizeAllColumns());

    Button autoFitBtn = new Button("Auto-Fit to Table Width", e -> autoFitColumns());

    Button autoSizeTitleBtn = new Button("Auto-Size Title Only", e -> autoSizeTitleColumn());

    Button resetBtn = new Button("Reset to Default", e -> resetToDefaults());

    controls.add(autoSizeAllBtn, autoFitBtn, autoSizeTitleBtn, resetBtn);
    return controls;
  }

  private Table<MusicRecord> createTable() {
    table.setWidth("100%")
      .setHeight("450px")
      .setStriped(true)
      .setRepository(Service.getMusicRecords());

    applyDefaultColumnSizing();
    return table;
  }

  private void applyDefaultColumnSizing() {
    numberCol.setFlex(0f).setWidth(50f).setMinWidth(40f);
    titleCol.setFlex(0f).setWidth(100f).setMinWidth(80f);
    artistCol.setFlex(1f).setMinWidth(100f);  
    genreCol.setFlex(0f).setWidth(80f).setMinWidth(60f);
    costCol.setFlex(0f).setWidth(60f).setMinWidth(50f);
  }
  
  private void autoSizeAllColumns() {
    table.setColumnsToAutoSize();
  }

  private void autoFitColumns() {
    table.setColumnsToAutoFit();
  }

  private void autoSizeTitleColumn() {
    table.setColumnToAutoSize(titleCol);
  }

  private void resetToDefaults() {
    applyDefaultColumnSizing();
    table.refreshColumns();
  }
}
