package com.webforj.samples.views.table;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.table.Column;
import com.webforj.component.table.Column.PinDirection;
import com.webforj.component.table.ColumnGroup;
import com.webforj.component.table.Table;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

import java.util.List;

@Route
@FrameTitle("Table Pinned Column Groups")
public class TablePinnedColumnGroupsView extends Composite<Div> {

  private Div self = getBoundComponent();

  public TablePinnedColumnGroupsView() {

    Table<MusicRecord> table = new Table<>();
    table.setWidth("100vw");
    table.setHeight("100vh");
    table.setStriped(true);

    table.addColumn("Number", MusicRecord::getNumber).setMinWidth(90f);
    table.addColumn("Title", MusicRecord::getTitle).setMinWidth(200f);
    table.addColumn("Artist", MusicRecord::getArtist).setMinWidth(200f);
    table.addColumn("Genre", MusicRecord::getMusicType).setMinWidth(150f);
    table.addColumn("Label", MusicRecord::getLabel).setMinWidth(150f);
    table.addColumn("Cost", record -> {
      return String.format("$%.2f", record.getCost());
    }).setAlignment(Column.Alignment.RIGHT).setMinWidth(100f);

    ColumnGroup identity = ColumnGroup.of("identity", "Identity")
        .setPinDirection(PinDirection.LEFT)
        .add("Number")
        .add("Title");

    ColumnGroup catalog = ColumnGroup.of("catalog", "Catalog")
        .add("Artist")
        .add("Genre")
        .add("Label");

    ColumnGroup pricing = ColumnGroup.of("pricing", "Pricing")
        .add("Cost");

    table.setColumnGroups(List.of(identity, catalog, pricing));
    table.setRepository(Service.getMusicRecords());

    self.add(table);
  }
}