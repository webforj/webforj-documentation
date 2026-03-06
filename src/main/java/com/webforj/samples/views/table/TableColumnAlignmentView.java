package com.webforj.samples.views.table;

import com.webforj.component.table.Table;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.table.Column;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Table Column Alignment")
public class TableColumnAlignmentView extends Composite<Div> {
  // self field enables fluent method chaining from the bound component
  private final Div self = getBoundComponent();

  public TableColumnAlignmentView() {
    Table<MusicRecord> table = new Table<MusicRecord>()
        .setWidth("100vw")
        .setHeight("100vh");

    table.addColumn("Number", MusicRecord::getNumber);
    table.addColumn("Title", MusicRecord::getTitle);
    table.addColumn("Genre", MusicRecord::getMusicType);
    table.addColumn("Cost", record -> String.format("$%.2f", record.getCost()))
        .setAlignment(Column.Alignment.RIGHT);

    table.setRepository(Service.getMusicRecords());

    self.add(table);
  }
}
