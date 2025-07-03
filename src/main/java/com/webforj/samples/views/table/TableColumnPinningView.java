package com.webforj.samples.views.table;

import static com.webforj.component.optiondialog.OptionDialog.showMessageDialog;


import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.table.Column;
import com.webforj.component.table.Table;
import com.webforj.component.table.renderer.ButtonRenderer;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.config.RouteConfig;

@Route(RouteConfig.TABLE_COLUMN_PINNING)
@FrameTitle("Table Column Pinning")
public class TableColumnPinningView extends Composite<Div> {

  public TableColumnPinningView() {

    Table<MusicRecord> table = new Table<>();
    table.setWidth("100vw");
    table.setHeight("100vh");

    table.addColumn("Number", MusicRecord::getNumber).setPinDirection(Column.PinDirection.LEFT);
    table.addColumn("Title", MusicRecord::getTitle);
    table.addColumn("Artist", MusicRecord::getArtist);
    table.addColumn("Tracks Number", MusicRecord::getNumberOfTracks);
    table.addColumn("Genre", MusicRecord::getMusicType);
    table.addColumn("Cost", record -> {
      return String.format("$%.2f", record.getCost());
    }).setAlignment(Column.Alignment.RIGHT);

    ButtonRenderer<MusicRecord> editRenderer = new ButtonRenderer<>("Edit", e -> {
      showMessageDialog("You asked to edit record number <b>" + e.getItem().getNumber() + "</b>.","Edit Record");
    });
    table.addColumn(editRenderer).setAlignment(Column.Alignment.CENTER)
        .setPinDirection(Column.PinDirection.RIGHT);

    table.setRepository(Service.getMusicRecords());
    table.setRowHeight(42);

    getBoundComponent().add(table);
  }
}
