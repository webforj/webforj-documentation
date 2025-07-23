package com.webforj.samples.views.table;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.table.Table;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Table Column Comparator")
public class TableColumnComparatorView extends Composite<Div> {

  public TableColumnComparatorView() {

    Table<MusicRecord> table = new Table<>();
    table.setWidth("100vw");
    table.setHeight("100vh");

    table.addColumn("Number", MusicRecord::getNumber).setComparator((a, b) -> {
      return Integer.parseInt(a.getNumber()) - Integer.parseInt(b.getNumber());
    });
    table.addColumn("Title", MusicRecord::getTitle);
    table.addColumn("Artist", MusicRecord::getArtist);
    table.addColumn("Genre", MusicRecord::getMusicType);
    table.addColumn("Cost", MusicRecord::getCost);

    table.getColumns().forEach(column -> column.setSortable(true));
    table.setRepository(Service.getMusicRecords());

    getBoundComponent().add(table);
  }
}
