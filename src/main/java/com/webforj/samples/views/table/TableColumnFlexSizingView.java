package com.webforj.samples.views.table;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.NumberField;
import com.webforj.component.html.elements.Paragraph;
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
  private final FlexLayout self = getBoundComponent();

  private Table<MusicRecord> table;
  private Column<MusicRecord, String> titleColumn;
  private Column<MusicRecord, String> artistColumn;
  private Column<MusicRecord, String> genreColumn;

  private NumberField titleFlexField;
  private NumberField artistFlexField;
  private NumberField genreFlexField;

  public TableColumnFlexSizingView() {
    self.setDirection(FlexDirection.COLUMN)
        .setPadding("var(--dwc-space-l)")
        .setSpacing("var(--dwc-space-l)")
        .setHeight("100vh");

    self.add(createControls(), createTable());
  }

  private FlexLayout createControls() {
    FlexLayout controls = new FlexLayout()
        .setDirection(FlexDirection.ROW)
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
    FlexLayout control = new FlexLayout()
        .setDirection(FlexDirection.COLUMN)
        .setSpacing("5px")
        .setMinWidth("120px");

    Paragraph labelEl = new Paragraph(label + " Flex:")
        .setStyle("margin", "0")
        .setStyle("font-weight", "bold")
        .setStyle("font-size", "14px");

    NumberField field = new NumberField()
        .setValue(defaultValue)
        .setMin(0.1)
        .setMax(10.0)
        .setStep(0.1)
        .setWidth("100px");
    field.onModify(e -> onChange.run());

    control.add(labelEl, field);
    return control;
  }

  private Table<MusicRecord> createTable() {
    table = new Table<MusicRecord>()
        .setWidth("100%")
        .setHeight("400px")
        .setStriped(true);

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
  }

  private void setEqualFlex() {
    titleFlexField.setValue(1.0);
    artistFlexField.setValue(1.0);
    genreFlexField.setValue(1.0);

    titleColumn.setFlex(1f);
    artistColumn.setFlex(1f);
    genreColumn.setFlex(1f);
  }
}
