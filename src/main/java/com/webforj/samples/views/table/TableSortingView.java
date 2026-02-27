package com.webforj.samples.views.table;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.table.Table;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Table Sorting")
public class TableSortingView extends Composite<Div> {
  // self field enables fluent method chaining from the bound component
  private final Div self = getBoundComponent();

  public TableSortingView() {
    Table<MusicRecord> table = new Table<MusicRecord>()
        .setWidth("100vw")
        .setHeight("100vh");

    table.addColumn("Title", MusicRecord::getTitle);
    table.addColumn("Artist", MusicRecord::getArtist);
    table.addColumn("Genre", MusicRecord::getMusicType);
    table.addColumn("Cost", MusicRecord::getCost);

    table.getColumns().forEach(column -> column.setSortable(true));
    table.setRepository(Service.getMusicRecords());

    self.add(table);
  }
}
