package com.webforj.samples.unit.views.table;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.act.component.table.TableInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.table.Table;
import com.webforj.data.repository.OrderCriteria.Direction;
import com.webforj.samples.views.table.MusicRecord;
import com.webforj.samples.views.table.TableColumnComparatorView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

@WebforjTest
class TableColumnComparatorViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new TableColumnComparatorView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldOrderTheRecordsByTheirNumberWhenTheUserSortsByNumber(WebforjTestbed context) {
    context.add(new TableColumnComparatorView());

    Table<MusicRecord> table = ComponentLocator.of(Table.class).locate();

    UserInteraction.perform(
        table, TableInteraction.sort(Map.of(table.getColumnById("Number"), Direction.DESC)));

    List<String> numbers = table.getRepository().findAll().map(MusicRecord::getNumber).toList();
    assertEquals(List.of("000110", "000103", "000100"), numbers.subList(0, 3));
    assertEquals("000001", numbers.get(numbers.size() - 1));
  }
}
