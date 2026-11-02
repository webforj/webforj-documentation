package com.webforj.samples.unit.views.table;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.table.Column;
import com.webforj.component.table.Table;
import com.webforj.samples.views.table.MusicRecord;
import com.webforj.samples.views.table.TableColumnAlignmentView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WebforjTest
class TableColumnAlignmentViewTest {
  private Locale previous;

  @BeforeEach
  void pinTheLocale() {
    previous = Locale.getDefault();
    Locale.setDefault(Locale.US);
  }

  @AfterEach
  void restoreTheLocale() {
    Locale.setDefault(previous);
  }

  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new TableColumnAlignmentView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldRightAlignTheCostColumnAndKeepTheOthersLeftAligned(WebforjTestbed context) {
    context.add(new TableColumnAlignmentView());

    Table<MusicRecord> table = ComponentLocator.of(Table.class).locate();

    assertEquals(
        Map.of(
            "Number", Column.Alignment.LEFT,
            "Title", Column.Alignment.LEFT,
            "Genre", Column.Alignment.LEFT,
            "Cost", Column.Alignment.RIGHT),
        table.getColumns().stream().collect(Collectors.toMap(Column::getId, Column::getAlignment)));
  }
}
