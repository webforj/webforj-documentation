package com.webforj.samples.views.table;

import static com.webforj.component.optiondialog.OptionDialog.showMessageDialog;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.table.Table;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.config.RouteConfig;

@Route(RouteConfig.TABLE_SINGLE_SELECTION)
@FrameTitle("Table Single Selection")
public class TableSingleSelectionView extends Composite<Div> {

  public TableSingleSelectionView() {

    Table<MusicRecord> table = new Table<>();
    table.setWidth("100vw");
    table.setHeight("100vh");

    table.addColumn("Number", MusicRecord::getNumber);
    table.addColumn("Title", MusicRecord::getTitle);
    table.addColumn("Artist", MusicRecord::getArtist);
    table.addColumn("Genre", MusicRecord::getMusicType);
    table.addColumn("Cost", MusicRecord::getCost);

    table.setRepository(Service.getMusicRecords());
    table.setSelectionMode(Table.SelectionMode.SINGLE);

    table.onItemSelect(ev -> showMessageDialog(
        "You have selected " + ev.getItem().getTitle() + " by " + ev.getItem().getArtist(),
        "Record Number " + ev.getItem().getNumber()));

    getBoundComponent().add(table);
  }
}
