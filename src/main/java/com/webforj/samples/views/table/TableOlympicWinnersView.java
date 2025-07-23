package com.webforj.samples.views.table;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.util.Arrays;
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

  public TableOlympicWinnersView() {

    Table<JsonObject> table = new Table<>();
    table.setWidth("100vw");
    table.setHeight("100vh");

    List<String> columnsList = Arrays.asList("athlete", "age", "country", "year", "sport", "gold",
        "silver", "bronze", "total");

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
    table.getColumnById("athlete").setPinDirection(Column.PinDirection.LEFT).setMinWidth(200f);
    table.getColumnById("total").setPinDirection(Column.PinDirection.RIGHT);
    table.setClientSorting(true);

    List<JsonObject> data = new Gson().fromJson(
        Assets.contentOf(Assets.resolveContextUrl("context://data/olympic-winners.json")),
        new TypeToken<List<JsonObject>>() {
        });

    table.setItems(data);

    getBoundComponent().add(table);
  }
}
