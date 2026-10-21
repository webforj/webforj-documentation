package com.webforj.samples.views.table;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.JsonObject;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.table.Table;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class TableOlympicWinnersViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new TableOlympicWinnersView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldHandAllOlympicWinnersToTheTableAtOnce(WebforjTestbed context) {
    context.add(new TableOlympicWinnersView());

    Table<JsonObject> table = ComponentLocator.of(Table.class).locate();

    assertEquals(8618, table.getRepository().findAll().count());
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldShowAnEmptyCellForAMissingValue(WebforjTestbed context) {
    context.add(new TableOlympicWinnersView());

    Table<JsonObject> table = ComponentLocator.of(Table.class).locate();

    assertEquals("", table.getColumnById("age").getValue(new JsonObject()));
  }
}
