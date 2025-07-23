package com.webforj.samples.views.table;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.table.Column.SortDirection;
import com.webforj.component.table.Table;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Table Multi Sorting with Sort Order")
public class TableSortOrderView extends Composite<Div> {

  public TableSortOrderView() {

    Table<MusicRecord> table = new Table<>();
    table.setWidth("100vw");
    table.setHeight("100vh");

    table.setMultiSorting(true);

    table.addColumn("Title", MusicRecord::getTitle).setSortable(true);
    table.addColumn("Artist", MusicRecord::getArtist)
        .setSortable(true)
        .setSortDirection(SortDirection.ASC)
        .setSortIndex(1);
    table.addColumn("Genre", MusicRecord::getMusicType).setSortable(true);
    table.addColumn("Cost", MusicRecord::getCost)
        .setSortable(true)
        .setSortDirection(SortDirection.DESC)
        .setSortIndex(2);

    table.setRepository(Service.getMusicRecords());
    getBoundComponent().add(table);
  }
}