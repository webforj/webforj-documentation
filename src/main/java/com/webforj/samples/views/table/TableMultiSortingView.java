package com.webforj.samples.views.table;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.table.Table;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Table Multi Sorting")
public class TableMultiSortingView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public TableMultiSortingView() {
    Table<MusicRecord> table = new Table<MusicRecord>()
        .setWidth("100vw")
        .setHeight("100vh");

    table.addColumn("Title", MusicRecord::getTitle);
    table.addColumn("Artist", MusicRecord::getArtist);
    table.addColumn("Genre", MusicRecord::getMusicType);
    table.addColumn("Cost", MusicRecord::getCost);

    table.getColumns().forEach(column -> column.setSortable(true));
    table.setMultiSorting(true);
    table.setRepository(Service.getMusicRecords());

    self.add(table);
  }
}
