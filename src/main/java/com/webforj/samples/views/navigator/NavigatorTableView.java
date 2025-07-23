package com.webforj.samples.views.navigator;

import com.webforj.component.table.Table;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.navigator.Navigator;
import com.webforj.component.navigator.Navigator.Layout;
import com.webforj.data.repository.Repository;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.views.table.MusicRecord;
import com.webforj.samples.views.table.Service;

@Route
@FrameTitle("Navigator Table")
public class NavigatorTableView extends Composite<Div> {

  public NavigatorTableView() {
    Repository<MusicRecord> repo = Service.getMusicRecords();

    Navigator nav = new Navigator(repo, Layout.PAGES);
    nav.setAutoDisable(true);
    nav.getPaginator().setMax(5);
    nav.setStyle("margin-right", "20px");

    Table<MusicRecord> table = new Table<>();
    table.setHeight("400px");

    table.addColumn("Number", MusicRecord::getNumber);
    table.addColumn("Title", MusicRecord::getTitle);
    table.addColumn("Artist", MusicRecord::getArtist);
    table.addColumn("Genre", MusicRecord::getMusicType);
    table.addColumn("Cost", MusicRecord::getCost);
    table.setRepository(repo);

    FlexLayout layout = FlexLayout.create(table, nav).vertical().build();
    layout.setItemAlignment(FlexAlignment.END, nav);

    getBoundComponent().add(layout);
  }
}
