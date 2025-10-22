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
  private Column<MusicRecord, ?> numberCol;
  private Column<MusicRecord, ?> titleCol;
  private Column<MusicRecord, ?> artistCol;
  private Column<MusicRecord, ?> genreCol;
  private Column<MusicRecord, ?> costCol;

  public TableColumnAutoSizingView() {
    FlexLayout layout = getBoundComponent();
    layout.setDirection(FlexDirection.COLUMN)
      .setPadding("var(--dwc-space-l)")
      .setSpacing("var(--dwc-space-l)")
      .setHeight("100vh");
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

    numberCol = table.addColumn("Number", MusicRecord::getNumber);
    titleCol = table.addColumn("Title", MusicRecord::getTitle);
    artistCol = table.addColumn("Artist", MusicRecord::getArtist);
    genreCol = table.addColumn("Genre", MusicRecord::getMusicType);
    costCol = table.addColumn("Cost", r -> String.format("$%.2f", r.getCost()))
        .setAlignment(Column.Alignment.RIGHT);

    applyDefaultColumnSizing();
    table.setRepository(Service.getMusicRecords());
    return table;
  }

  private void applyDefaultColumnSizing() {
    numberCol.setFlex(0f).setWidth(50f).setMinWidth(40f);
    titleCol.setFlex(0f).setWidth(100f).setMinWidth(80f);
    artistCol.setFlex(0f).setWidth(200f).setMinWidth(100f);
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