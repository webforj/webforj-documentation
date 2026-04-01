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

  private Div self = getBoundComponent();

  public TableNestedColumnGroupsView() {

    Table<MusicRecord> table = new Table<>();
    table.setWidth("100vw");
    table.setHeight("100vh");
    table.setStriped(true);

    table.addColumn("Title", MusicRecord::getTitle).setMinWidth(120f);
    table.addColumn("Artist", MusicRecord::getArtist).setMinWidth(120f);
    table.addColumn("Genre", MusicRecord::getMusicType).setMinWidth(80f);
    table.addColumn("Tracks", MusicRecord::getNumberOfTracks).setMinWidth(60f);
    table.addColumn("Playing Time", MusicRecord::getPlayingTime).setMinWidth(80f);
    table.addColumn("Cost", record -> {
      return String.format("$%.2f", record.getCost());
    }).setAlignment(Column.Alignment.RIGHT).setMinWidth(70f);
    table.addColumn("Retail", record -> {
      return String.format("$%.2f", record.getRetail());
    }).setAlignment(Column.Alignment.RIGHT).setMinWidth(70f);

    table.setColumnsToAutoFit();

    ColumnGroup catalog = ColumnGroup.of("catalog", "Catalog")
        .add("Title")
        .add("Artist")
        .add("Genre");

    ColumnGroup details = ColumnGroup.of("details", "Details")
        .add(ColumnGroup.of("media", "Media").add("Tracks").add("Playing Time"))
        .add(ColumnGroup.of("pricing", "Pricing").add("Cost").add("Retail"));

    table.setColumnGroups(List.of(catalog, details));
    table.setRepository(Service.getMusicRecords());

    self.add(table);
  }
}