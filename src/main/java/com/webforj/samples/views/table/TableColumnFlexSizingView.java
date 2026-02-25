package com.webforj.samples.views.table;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.NumberField;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.flexlayout.FlexWrap;
import com.webforj.component.table.Column;
import com.webforj.component.table.Table;

import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Table Column Flexible Sizing")
public class TableColumnFlexSizingView extends Composite<FlexLayout> {

  private Table<MusicRecord> table;
  private Column<MusicRecord, String> titleColumn;
  private Column<MusicRecord, String> artistColumn;
  private Column<MusicRecord, String> genreColumn;

  private NumberField titleFlexField;
  private NumberField artistFlexField;
  private NumberField genreFlexField;

  public TableColumnFlexSizingView() {
    FlexLayout layout = getBoundComponent();
    layout.setDirection(FlexDirection.COLUMN)
        .setPadding("var(--dwc-space-l)")
        .setSpacing("var(--dwc-space-l)")
        .setHeight("100vh");

    FlexLayout controls = createControls();
    table = createTable();

    layout.add(controls, table);
  }

  private FlexLayout createControls() {
    FlexLayout controls = new FlexLayout();

    controls.setDirection(FlexDirection.ROW)
        .setSpacing("var(--dwc-space-l)")
        .setAlignment(FlexAlignment.END)
        .setWrap(FlexWrap.WRAP);

    FlexLayout titleControl = createFlexControl("Title", 2.0,
        () -> updateColumnFlex(titleFlexField, titleColumn));
    titleFlexField = (NumberField) titleControl.getComponents().get(1);

    FlexLayout artistControl = createFlexControl("Artist", 1.5,
        () -> updateColumnFlex(artistFlexField, artistColumn));
    artistFlexField = (NumberField) artistControl.getComponents().get(1);

    FlexLayout genreControl = createFlexControl("Genre", 1.0,
        () -> updateColumnFlex(genreFlexField, genreColumn));
    genreFlexField = (NumberField) genreControl.getComponents().get(1);

    Button resetBtn = new Button("Reset to Defaults", ButtonTheme.OUTLINED_PRIMARY);
    resetBtn.onClick(e -> resetToDefaults());

    Button equalBtn = new Button("Equal Flex", ButtonTheme.OUTLINED_PRIMARY);
    equalBtn.onClick(e -> setEqualFlex());

    controls.add(titleControl, artistControl, genreControl, resetBtn, equalBtn);
    return controls;
  }

  private FlexLayout createFlexControl(String label, double defaultValue, Runnable onChange) {
    FlexLayout control = new FlexLayout();

    control.setDirection(FlexDirection.COLUMN)
        .setSpacing("5px")
        .setMinWidth("120px");

    com.webforj.component.html.elements.Paragraph labelEl = new com.webforj.component.html.elements.Paragraph(
        label + " Flex:");
    labelEl.setStyle("margin", "0");
    labelEl.setStyle("font-weight", "bold");
    labelEl.setStyle("font-size", "14px");

    NumberField field = new NumberField();
    field.setValue(defaultValue);
    field.setMin(0.1);
    field.setMax(10.0);
    field.setStep(0.1);
    field.setWidth("100px");
    field.onModify(e -> onChange.run());

    control.add(labelEl, field);
    return control;
  }

  private Table<MusicRecord> createTable() {
    Table<MusicRecord> table = new Table<>();
    table.setWidth("100%");
    table.setHeight("400px");
    table.setStriped(true);

    table.addColumn("Number", MusicRecord::getNumber)
        .setWidth(80f)
        .setResizable(false);

    titleColumn = table.addColumn("Title", MusicRecord::getTitle)
        .setFlex(2f)
        .setMinWidth(120f)
        .setResizable(true);

    artistColumn = table.addColumn("Artist", MusicRecord::getArtist)
        .setFlex(1.5f)
        .setMinWidth(100f)
        .setResizable(true);

    genreColumn = table.addColumn("Genre", MusicRecord::getMusicType)
        .setFlex(1f)
        .setMinWidth(80f)
        .setResizable(true);

    table.addColumn("Cost", record -> String.format("$%.2f", record.getCost()))
        .setWidth(80f)
        .setAlignment(Column.Alignment.RIGHT)
        .setResizable(false);

    table.setRepository(Service.getMusicRecords());
    table.onColumnResize(e -> {
      titleFlexField.setValue(Math.round(titleColumn.getWidth() / 10.0) / 10.0);
      artistFlexField.setValue(Math.round(artistColumn.getWidth() / 10.0) / 10.0);
      genreFlexField.setValue(Math.round(genreColumn.getWidth() / 10.0) / 10.0);
    });

    return table;
  }

  private void updateColumnFlex(NumberField field, Column<MusicRecord, String> column) {
    Double value = field.getValue();

    if (value != null && value > 0) {
      column.setFlex(value.floatValue());
      table.refreshColumns();
    }
  }

  private void resetToDefaults() {
    titleFlexField.setValue(2.0);
    artistFlexField.setValue(1.5);
    genreFlexField.setValue(1.0);

    titleColumn.setFlex(2f);
    artistColumn.setFlex(1.5f);
    genreColumn.setFlex(1f);
    table.refreshColumns();
  }

  private void setEqualFlex() {
    titleFlexField.setValue(1.0);
    artistFlexField.setValue(1.0);
    genreFlexField.setValue(1.0);

    titleColumn.setFlex(1f);
    artistColumn.setFlex(1f);
    genreColumn.setFlex(1f);
    table.refreshColumns();
  }
}