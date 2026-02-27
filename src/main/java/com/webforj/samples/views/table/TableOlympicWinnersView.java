package com.webforj.samples.views.table;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.table.Column;
import com.webforj.component.table.Table;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.utilities.Assets;

@Route
@FrameTitle("Olympic Winners Table")
public class TableOlympicWinnersView extends Composite<Div> {
  // self field enables fluent method chaining from the bound component
  private final Div self = getBoundComponent();

  public TableOlympicWinnersView() {
    Table<JsonObject> table = new Table<JsonObject>()
        .setWidth("100vw")
        .setHeight("100vh");

    List<String> columnsList = List.of("athlete", "age", "country", "year", "sport",
        "gold", "silver", "bronze", "total");

    for (String column : columnsList) {
      table.addColumn(column, json -> extractColumnValue(json, column));
    }

    table.getColumns().forEach(column -> column.setSortable(true));
    table.getColumnById("athlete")
        .setPinDirection(Column.PinDirection.LEFT)
        .setMinWidth(200f);
    table.getColumnById("total")
        .setPinDirection(Column.PinDirection.RIGHT);
    table.setClientSorting(true);

    List<JsonObject> data = new Gson().fromJson(
        Assets.contentOf(Assets.resolveContextUrl("context://data/olympic-winners.json")),
        new TypeToken<List<JsonObject>>() {});

    table.setItems(data);

    self.add(table);
  }

  // Helper method for column value extraction
  private static String extractColumnValue(JsonObject json, String column) {
    JsonElement element = json.get(column);
    if (element == null || element.isJsonNull()) {
      return "";
    }
    return element.getAsString();
  }
}
