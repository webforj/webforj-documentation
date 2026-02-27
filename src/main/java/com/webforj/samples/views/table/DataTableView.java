package com.webforj.samples.views.table;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
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
import com.webforj.utilities.Assets;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Data Table")
public class DataTableView extends Composite<Div> {
  private final Div self = getBoundComponent();

  private String searchTerm = "";
  private CollectionRepository<JsonObject> repository;
  private Paginator paginator;

  public DataTableView() {
    // Initialize data from JSON asset
    List<JsonObject> data = new Gson().fromJson(
        Assets.contentOf(Assets.resolveContextUrl("context://data/olympic-winners.json")),
        new TypeToken<List<JsonObject>>() {});

    // Setup repository with filter
    repository = new CollectionRepository<>(data);
    repository.setBaseFilter(json -> {
      JsonElement athlete = json.get("athlete");
      return athlete != null && !athlete.isJsonNull()
          && athlete.getAsString().toLowerCase().contains(searchTerm);
    });

    // Setup pagination
    paginator = new Paginator(repository);
    paginator.setMax(5);

    // Build and add layout
    FlexLayout layout = FlexLayout.create(buildTableHeader(), buildTable(), buildTableFooter())
        .vertical()
        .contentAlign()
        .center()
        .build()
        .setPadding("var(--dwc-space-l)");

    self.add(layout);
  }

  private FlexLayout buildTableHeader() {
    // Search field with filter callback
    TextField search = new TextField(Type.SEARCH, "Search")
        .setPlaceholder("Search by athlete...");
    search.onModify(ev -> {
      searchTerm = ev.getText().toLowerCase();
      paginator.setCurrent(1);
      repository.commit();
    });

    // Page size selector
    ChoiceBox pages = new ChoiceBox("Entries per page")
      .insert("10", "25", "50", "100")
      .selectIndex(0);
    pages.onSelect(e ->
        paginator.setSize(Integer.parseInt(e.getSelectedItem().getText())));

    return FlexLayout.create(pages, search)
        .horizontal()
        .justify()
        .between()
        .build();
  }

  private Table<JsonObject> buildTable() {
    Table<JsonObject> table = new Table<JsonObject>()
        .setHeight("400px")
        .setSelectionMode(SelectionMode.MULTIPLE)
        .setHeaderCheckboxSelection(false);

    // Define columns using helper method
    List<String> columnsList = List.of("athlete", "age", "country", "year", "total");

    for (String column : columnsList) {
      table.addColumn(column, json -> extractColumnValue(json, column));
    }

    // Configure columns
    table.getColumns().forEach(column -> column.setSortable(true));

    table.getColumnById("athlete")
        .setPinDirection(PinDirection.LEFT)
        .setMinWidth(200f);

    table.getColumnById("total")
        .setPinDirection(PinDirection.RIGHT);

    table.setRepository(repository);

    return table;
  }

  // Helper method for column value extraction
  private static String extractColumnValue(JsonObject json, String column) {
    JsonElement element = json.get(column);
    if (element == null || element.isJsonNull()) {
      return "";
    }
    return element.getAsString();
  }

  private FlexLayout buildTableFooter() {
    Navigator pages = new Navigator(paginator, Layout.PAGES)
        .setAutoDisable(true);

    Navigator preview = new Navigator(paginator, Layout.PREVIEW)
        .setHideMainButtons(true)
        .setStyle("border", "0")
        .setText("`Showing ${startIndex + 1} to ${endIndex + 1} of ${totalItems} entries`");

    return FlexLayout.create(pages, preview)
        .horizontal()
        .justify()
        .between()
        .build();
  }
}
