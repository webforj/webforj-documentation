package com.webforj.samples.views.table;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.table.Table;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.Set;
import org.junit.jupiter.api.Test;

@WebforjTest
class TableLayoutStylingViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new TableLayoutStylingView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldStripeTheRowsAndShowTheBordersAroundBetweenRowsAndBetweenColumns(
      WebforjTestbed context) {
    context.add(new TableLayoutStylingView());

    Table<Person> table = ComponentLocator.of(Table.class).locate();

    assertTrue(table.isStriped());
    assertEquals(
        Set.of(Table.Border.AROUND, Table.Border.ROWS, Table.Border.COLUMNS),
        table.getBordersVisible());
  }
}
