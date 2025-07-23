package com.webforj.samples.views.table;

import com.webforj.component.table.Table;
import com.webforj.component.Composite;
import com.webforj.component.field.TextField;
import com.webforj.component.field.TextField.Type;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.data.repository.Repository;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Table Filtering")
public class TableFilteringView extends Composite<Div> {
  private String searchTerm = "";

  public TableFilteringView() {
    Repository<MusicRecord> repository = Service.getMusicRecords();
    repository.setFilter((MusicRecord r) -> {
      String title = r.getTitle();
      return title.toLowerCase().contains(this.searchTerm);
    });

    TextField searchField = buildSearchField(repository);
    Table<MusicRecord> table = buildTable(repository);

    FlexLayout layout =
        FlexLayout.create().vertical().contentAlign().center().build().setHeight("500px");
    layout.add(searchField, table);

    getBoundComponent().setStyle("padding", "30px").add(layout);
  }

  TextField buildSearchField(Repository<MusicRecord> repository) {
    TextField search = new TextField(Type.SEARCH, "Search");
    search.setPlaceholder("Search by title...");
    search.onModify(ev -> {
      this.searchTerm = ev.getText().toLowerCase();
      repository.commit();
    });

    return search;
  }

  Table<MusicRecord> buildTable(Repository<MusicRecord> repository) {
    Table<MusicRecord> table = new Table<>();

    table.addColumn("Title", MusicRecord::getTitle);
    table.addColumn("Artist", MusicRecord::getArtist);
    table.addColumn("Genre", MusicRecord::getMusicType);
    table.addColumn("Cost", MusicRecord::getCost);

    table.setRepository(repository);

    return table;
  }
}
