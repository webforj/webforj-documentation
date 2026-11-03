package com.webforj.samples.unit.views.table;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.act.component.table.TableInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.table.Column;
import com.webforj.component.table.Table;
import com.webforj.data.repository.OrderCriteria.Direction;
import com.webforj.samples.views.table.MusicRecord;
import com.webforj.samples.views.table.TableSortingView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

@WebforjTest
class TableSortingViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new TableSortingView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldSortTheTitlesAscendingThenDescendingThenNotAtAll(WebforjTestbed context) {
    context.add(new TableSortingView());

    Table<MusicRecord> table = ComponentLocator.of(Table.class).locate();
    var title = table.getColumnById("Title");
    var original = titlesOf(table);

    UserInteraction.perform(table, TableInteraction.sort(Map.of(title, Direction.ASC)));

    assertEquals(original.stream().sorted().toList(), titlesOf(table));
    assertEquals(Column.SortDirection.ASC, title.getSortDirection());

    UserInteraction.perform(table, TableInteraction.sort(Map.of(title, Direction.DESC)));

    assertEquals(original.stream().sorted(Comparator.reverseOrder()).toList(), titlesOf(table));
    assertEquals(Column.SortDirection.DESC, title.getSortDirection());

    UserInteraction.perform(table, TableInteraction.sort(Map.of()));

    assertEquals(original, titlesOf(table));
    assertEquals(Column.SortDirection.NONE, title.getSortDirection());
  }

  private static List<String> titlesOf(Table<MusicRecord> table) {
    return table.getRepository().findAll().map(MusicRecord::getTitle).toList();
  }
}
