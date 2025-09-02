package com.webforj.samples.views.table;

import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.table.Column;
import com.webforj.component.table.Table;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Table Column User Interactions")
public class TableColumnInteractionsView extends Composite<FlexLayout> {

  public TableColumnInteractionsView() {
    FlexLayout layout = getBoundComponent();
    layout.setDirection(FlexDirection.COLUMN);
    layout.setStyle("padding", "20px");
    layout.setStyle("gap", "20px");
    layout.setStyle("height", "100vh");
    layout.setStyle("box-sizing", "border-box");

    Table<MusicRecord> table = createTable();
    layout.add(table);
  }

  private Table<MusicRecord> createTable() {
    Table<MusicRecord> table = new Table<>();
    table.setWidth("100%");
    table.setHeight("500px");
    table.setStriped(true);

    table.addColumn("Number", MusicRecord::getNumber)
        .setWidth(80f)
        .setResizable(true)
        .setMovable(true);

    table.addColumn("Title", MusicRecord::getTitle)
        .setFlex(2f)
        .setMinWidth(150f)
        .setResizable(true)
        .setMovable(true);

    table.addColumn("Artist", MusicRecord::getArtist)
        .setFlex(1.5f)
        .setMinWidth(120f)
        .setResizable(true)
        .setMovable(true);

    table.addColumn("Genre", MusicRecord::getMusicType)
        .setWidth(120f)
        .setResizable(true)
        .setMovable(true);

    table.addColumn("Cost", record -> String.format("$%.2f", record.getCost()))
        .setWidth(100f)
        .setAlignment(Column.Alignment.RIGHT)
        .setResizable(true)
        .setMovable(true);

    table.setRepository(Service.getMusicRecords());
    return table;
  }
}