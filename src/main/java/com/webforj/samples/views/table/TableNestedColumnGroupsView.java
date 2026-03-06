package com.webforj.samples.views.table;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.table.Column;
import com.webforj.component.table.ColumnGroup;
import com.webforj.component.table.Table;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

import java.util.List;

@Route
@FrameTitle("Table Nested Column Groups")
public class TableNestedColumnGroupsView extends Composite<Div> {

  public TableNestedColumnGroupsView() {

    Table<MusicRecord> table = new Table<>();
    table.setWidth("100vw");
    table.setHeight("100vh");

    table.addColumn("Number", MusicRecord::getNumber);
    table.addColumn("Title", MusicRecord::getTitle);
    table.addColumn("Artist", MusicRecord::getArtist);
    table.addColumn("Genre", MusicRecord::getMusicType);
    table.addColumn("Tracks", MusicRecord::getNumberOfTracks);
    table.addColumn("Playing Time", MusicRecord::getPlayingTime);
    table.addColumn("Cost", record -> {
      return String.format("$%.2f", record.getCost());
    }).setAlignment(Column.Alignment.RIGHT);
    table.addColumn("Retail", record -> {
      return String.format("$%.2f", record.getRetail());
    }).setAlignment(Column.Alignment.RIGHT);

    ColumnGroup details = ColumnGroup.of("details", "Album Details")
        .add(ColumnGroup.of("media", "Media Info")
            .add("Tracks")
            .add("Playing Time"))
        .add(ColumnGroup.of("pricing", "Pricing")
            .add("Cost")
            .add("Retail"));

    ColumnGroup catalog = ColumnGroup.of("catalog", "Catalog")
        .add("Title")
        .add("Artist")
        .add("Genre");

    table.setColumnGroups(List.of(catalog, details));
    table.setRepository(Service.getMusicRecords());

    getBoundComponent().add(table);
  }
}