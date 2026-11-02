package com.webforj.samples.unit.views.table;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.table.Table;
import com.webforj.samples.views.table.MusicRecord;
import com.webforj.samples.views.table.TableSortOrderView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class TableSortOrderViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new TableSortOrderView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldPrioritizeArtistBeforeCost(WebforjTestbed context) {
    context.add(new TableSortOrderView());

    Table<MusicRecord> table = ComponentLocator.of(Table.class).locate();

    assertTrue(table.isMultiSorting());
    assertEquals(1, table.getColumnById("Artist").getSortIndex());
    assertEquals(2, table.getColumnById("Cost").getSortIndex());
  }
}
