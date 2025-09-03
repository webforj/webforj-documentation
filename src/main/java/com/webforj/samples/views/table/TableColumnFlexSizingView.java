package com.webforj.samples.views.table;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.NumberField;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.table.Column;
import com.webforj.component.table.Table;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Table Column Flex Sizing")
public class TableColumnFlexSizingView extends Composite<FlexLayout> {

  private static final double DEFAULT_TITLE_FLEX  = 2.0;
  private static final double DEFAULT_ARTIST_FLEX = 1.5;
  private static final double DEFAULT_GENRE_FLEX  = 1.0;

  private static final double MIN_FLEX = 0.1;
  private static final double MAX_FLEX = 10.0;

  private Table<MusicRecord> table;
  private Column<MusicRecord, String> titleColumn;
  private Column<MusicRecord, String> artistColumn;
  private Column<MusicRecord, String> genreColumn;

  private NumberField titleFlexField;
  private NumberField artistFlexField;
  private NumberField genreFlexField;

  public TableColumnFlexSizingView() {
    FlexLayout layout = getBoundComponent();
    layout.setDirection(FlexDirection.COLUMN);
    layout.setPadding("var(--dwc-space-l)");
    layout.setSpacing("var(--dwc-space-l)");
    layout.setHeight("100vh");

    FlexLayout controls = createControls();
    table = createTable();

    layout.add(controls, table);
  }

  private FlexLayout createControls() {
    FlexLayout controls = new FlexLayout();
    controls.setDirection(FlexDirection.ROW);
    controls.setSpacing("var(--dwc-space-l)");
    controls.setStyle("align-items", "flex-end");
    controls.setStyle("flex-wrap", "wrap");

    FlexLayout titleControl = createFlexControl("Title", DEFAULT_TITLE_FLEX, this::updateTitleFlex);
    titleFlexField = (NumberField) titleControl.getComponents().get(1);

    FlexLayout artistControl = createFlexControl("Artist", DEFAULT_ARTIST_FLEX, this::updateArtistFlex);
    artistFlexField = (NumberField) artistControl.getComponents().get(1);

    FlexLayout genreControl = createFlexControl("Genre", DEFAULT_GENRE_FLEX, this::updateGenreFlex);
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
    control.setDirection(FlexDirection.COLUMN);
    control.setSpacing("5px");
    control.setMinWidth("120px");

    Paragraph labelEl = new Paragraph(label + " Flex:");
    labelEl.setStyle("margin", "0");
    labelEl.setStyle("font-weight", "bold");
    labelEl.setStyle("font-size", "14px");

    NumberField field = new NumberField();
    field.setValue(defaultValue);
    field.setMin(MIN_FLEX);
    field.setMax(MAX_FLEX);
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
    table.setColumnsToResizable(true);

    table.addColumn("Number", MusicRecord::getNumber)
        .setWidth(80f)
        .setResizable(false); // keep fixed

    titleColumn = table.addColumn("Title", MusicRecord::getTitle)
        .setFlex((float) DEFAULT_TITLE_FLEX)
        .setMinWidth(120f);

    artistColumn = table.addColumn("Artist", MusicRecord::getArtist)
        .setFlex((float) DEFAULT_ARTIST_FLEX)
        .setMinWidth(100f);

    genreColumn = table.addColumn("Genre", MusicRecord::getMusicType)
        .setFlex((float) DEFAULT_GENRE_FLEX)
        .setMinWidth(80f);

    table.addColumn("Cost", record -> String.format("$%.2f", record.getCost()))
        .setWidth(80f)
        .setAlignment(Column.Alignment.RIGHT)
        .setResizable(false); 

    table.setRepository(Service.getMusicRecords());
    return table;
  }

  private void updateTitleFlex() {
    Double v = titleFlexField.getValue();
    if (v == null) return;
    titleColumn.setFlex(v.floatValue());
    table.refreshColumns();
  }

  private void updateArtistFlex() {
    Double v = artistFlexField.getValue();
    if (v == null) return;
    artistColumn.setFlex(v.floatValue());
    table.refreshColumns();
  }

  private void updateGenreFlex() {
    Double v = genreFlexField.getValue();
    if (v == null) return;
    genreColumn.setFlex(v.floatValue());
    table.refreshColumns();
  }

  private void resetToDefaults() {
    titleFlexField.setValue(DEFAULT_TITLE_FLEX);
    artistFlexField.setValue(DEFAULT_ARTIST_FLEX);
    genreFlexField.setValue(DEFAULT_GENRE_FLEX);

    titleColumn.setFlex((float) DEFAULT_TITLE_FLEX);
    artistColumn.setFlex((float) DEFAULT_ARTIST_FLEX);
    genreColumn.setFlex((float) DEFAULT_GENRE_FLEX);
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