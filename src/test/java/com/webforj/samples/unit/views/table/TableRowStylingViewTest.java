package com.webforj.samples.unit.views.table;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.table.Table;
import com.webforj.samples.views.table.Person;
import com.webforj.samples.views.table.TableRowStylingView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class TableRowStylingViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new TableRowStylingView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldHighlightOnlyTheRowsOfPeopleOverThirty(WebforjTestbed context) {
    context.add(new TableRowStylingView());

    Table<Person> table = ComponentLocator.of(Table.class).locate();

    assertEquals(
        List.of("Bob", "David", "Frank"),
        table.getItems().stream()
            .filter(person -> table.getRowPartProvider().apply(person).contains("row-highlight"))
            .map(Person::getName)
            .toList());
  }
}
