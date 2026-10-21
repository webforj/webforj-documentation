package com.webforj.samples.unit.views.table;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.table.Table;
import com.webforj.samples.views.table.Person;
import com.webforj.samples.views.table.TableCellStylingView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class TableCellStylingViewTest {
  @Test
  @SuppressWarnings("unchecked")
  void shouldHighlightOnlyTheAgeCellsOverThirty(WebforjTestbed context) {
    context.add(new TableCellStylingView());

    Table<Person> table = ComponentLocator.of(Table.class).locate();
    var age = table.getColumnById("Age");
    var name = table.getColumnById("Name");

    assertEquals(
        List.of("cell-highlight"),
        table.getCellPartProvider().apply(new Person("A", 31, "X"), age));
    assertEquals(List.of(), table.getCellPartProvider().apply(new Person("A", 30, "X"), age));
    assertEquals(List.of(), table.getCellPartProvider().apply(new Person("A", 31, "X"), name));
  }
}
