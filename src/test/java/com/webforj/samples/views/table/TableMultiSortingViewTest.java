package com.webforj.samples.views.table;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.act.component.table.TableInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.table.Column;
import com.webforj.component.table.Table;
import com.webforj.data.repository.OrderCriteria.Direction;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

@WebforjTest
class TableMultiSortingViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new TableMultiSortingView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldSortByGenreFirstAndByCostWithinEachGenre(WebforjTestbed context) {
    context.add(new TableMultiSortingView());

    Table<MusicRecord> table = ComponentLocator.of(Table.class).locate();
    var genre = table.getColumnById("Genre");
    var cost = table.getColumnById("Cost");
    var expected =
        table
            .getRepository()
            .findAll()
            .sorted(
                Comparator.comparing(MusicRecord::getMusicType)
                    .thenComparing(MusicRecord::getCost, Comparator.reverseOrder()))
            .toList();

    Map<Column<MusicRecord, ?>, Direction> criteria = new LinkedHashMap<>();
    criteria.put(genre, Direction.ASC);
    criteria.put(cost, Direction.DESC);
    UserInteraction.perform(table, TableInteraction.sort(criteria));

    assertTrue(table.isMultiSorting());
    assertEquals(expected, table.getRepository().findAll().toList());
    assertEquals(Column.SortDirection.ASC, genre.getSortDirection());
    assertEquals(1, genre.getSortIndex());
    assertEquals(Column.SortDirection.DESC, cost.getSortDirection());
    assertEquals(2, cost.getSortIndex());
  }
}
