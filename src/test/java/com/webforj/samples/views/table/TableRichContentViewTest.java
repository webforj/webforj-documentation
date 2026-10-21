package com.webforj.samples.views.table;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.table.Table;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.HashSet;
import org.junit.jupiter.api.Test;

@WebforjTest
class TableRichContentViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new TableRichContentView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldRenderTheRichContentColumns(WebforjTestbed context) {
    context.add(new TableRichContentView());

    Table<MusicRecord> table = ComponentLocator.of(Table.class).locate();

    assertInstanceOf(
        TableRichContentView.AvatarRenderer.class,
        table.getColumnById("Title & Artist").getRenderer());
    assertInstanceOf(
        TableRichContentView.BadgeRenderer.class, table.getColumnById("Cost").getRenderer());
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldSelectEveryRowAndClearTheSelection(WebforjTestbed context) {
    context.add(new TableRichContentView());

    Table<MusicRecord> table = ComponentLocator.of(Table.class).locate();
    var rows = table.getRepository().findAll().toList();

    UserInteraction.select(table, rows.get(0));
    verifyThat(table).hasSelected(rows.get(0));

    rows.forEach(row -> UserInteraction.select(table, row));
    assertEquals(new HashSet<>(rows), new HashSet<>(table.getSelectedItems()));

    rows.forEach(row -> UserInteraction.deselect(table, row));
    verifyThat(table).hasNoSelection();
  }
}
