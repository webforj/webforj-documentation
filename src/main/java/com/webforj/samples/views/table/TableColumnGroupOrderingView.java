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
@FrameTitle("Table Column Group Ordering")
public class TableColumnGroupOrderingView extends Composite<Div> {

  private Div self = getBoundComponent();

  public TableColumnGroupOrderingView() {

    Table<MusicRecord> table = new Table<>();
    table.setWidth("100vw");
    table.setHeight("100vh");
    table.setStriped(true);

    // Columns added in this order:
    // Number, Title, Artist, Genre, Label, Cost
    table.addColumn("Number", MusicRecord::getNumber).setMinWidth(70f);
    table.addColumn("Title", MusicRecord::getTitle).setMinWidth(120f);
    table.addColumn("Artist", MusicRecord::getArtist).setMinWidth(120f);
    table.addColumn("Genre", MusicRecord::getMusicType).setMinWidth(80f);
    table.addColumn("Label", MusicRecord::getLabel).setMinWidth(80f);
    table.addColumn("Cost", record -> {
      return String.format("$%.2f", record.getCost());
    }).setAlignment(Column.Alignment.RIGHT).setMinWidth(70f);

    table.setColumnsToAutoFit();

    // Number and Label are ungrouped — they keep their
    // natural position relative to the grouped columns.
    ColumnGroup music = ColumnGroup.of("music", "Music")
        .add("Title")
        .add("Artist")
        .add("Genre");

    ColumnGroup pricing = ColumnGroup.of("pricing", "Pricing")
        .add("Cost");

    table.setColumnGroups(List.of(music, pricing));
    table.setRepository(Service.getMusicRecords());

    self.add(table);
  }
}