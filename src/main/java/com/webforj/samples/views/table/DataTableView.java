package com.webforj.samples.views.table;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.util.Arrays;
import java.util.List;

import com.webforj.component.table.Column.PinDirection;
import com.webforj.component.table.Table;
import com.webforj.component.table.Table.SelectionMode;
import com.webforj.component.Composite;
import com.webforj.component.field.TextField;
import com.webforj.component.field.TextField.Type;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.navigator.Navigator;
import com.webforj.component.navigator.Navigator.Layout;
import com.webforj.data.Paginator;
import com.webforj.data.repository.CollectionRepository;
import com.webforj.data.repository.Repository;
import com.webforj.utilities.Assets;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Data Table")
public class DataTableView extends Composite<Div> {
  private String searchTerm = "";
  private Repository<JsonObject> repository;
  private Paginator paginator;

  public DataTableView() {
    List<JsonObject> data = new Gson().fromJson(
        Assets.contentOf(Assets.resolveContextUrl("context://data/olympic-winners.json")),
        new TypeToken<List<JsonObject>>() {});

    repository = new CollectionRepository<>(data);
    repository.setFilter((JsonObject json) -> {
      String athlete = json.get("athlete").getAsString();
      return athlete.toLowerCase().contains(this.searchTerm);
    });

    paginator = new Paginator(repository);
    paginator.setMax(5);

    FlexLayout layout = FlexLayout.create(buildTableHeader(), buildTable(), buildTableFooter())
        .vertical().contentAlign().center().build().setPadding("var(--dwc-space-l)");

    getBoundComponent().add(layout);
  }

  FlexLayout buildTableHeader() {
    TextField search = new TextField(Type.SEARCH, "Search");
    search.setPlaceholder("Search by athlete...");
    search.onModify(ev -> {
      this.searchTerm = ev.getText().toLowerCase();
      paginator.setCurrent(1);
      repository.commit();
    });

    ChoiceBox pages = new ChoiceBox("Entries per page");
    pages.insert("10", "25", "50", "100");
    pages.selectIndex(0);
    pages.onSelect(e -> {
      paginator.setSize(Integer.parseInt(e.getSelectedItem().getText()));
    });

    return FlexLayout.create(pages, search).horizontal().justify().between().build();
  }

  Table<JsonObject> buildTable() {
    Table<JsonObject> table = new Table<>();
    table.setHeight("400px");
    table.setSelectionMode(SelectionMode.MULTIPLE);
    table.setHeaderCheckboxSelection(false);

    List<String> columnsList = Arrays.asList("athlete", "age", "country", "year", "total");

    for (String column : columnsList) {
      table.addColumn(column, (JsonObject person) -> {
        JsonElement element = person.get(column);
        if (!element.isJsonNull()) {
          return element.getAsString();
        }

        return "";
      });
    }

    table.getColumns().forEach(column -> column.setSortable(true));
    table.getColumnById("athlete").setPinDirection(PinDirection.LEFT).setMinWidth(200f);
    table.getColumnById("total").setPinDirection(PinDirection.RIGHT);
    
    

    table.setRepository(repository);

    return table;
  }

  FlexLayout buildTableFooter() {
    Navigator pages = new Navigator(paginator, Layout.PAGES);
    pages.setAutoDisable(true);

    Navigator preview = new Navigator(paginator, Layout.PREVIEW);
    preview.setHideMainButtons(true);
    preview.setStyle("border", "0");
    preview.setText("`Showing ${startIndex + 1} to ${endIndex + 1} of ${totalItems} entries`");

    return FlexLayout.create(pages, preview).horizontal().justify().between().build();
  }
}
