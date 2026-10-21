package com.webforj.samples.unit.views.table;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.table.Table;
import com.webforj.samples.views.table.MusicRecord;
import com.webforj.samples.views.table.TableMultiSelectionView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.Set;
import org.junit.jupiter.api.Test;

@WebforjTest
class TableMultiSelectionViewTest {
  @Test
  @SuppressWarnings("unchecked")
  void shouldSelectSeveralRowsThroughACheckboxOnEachRow(WebforjTestbed context) {
    context.add(new TableMultiSelectionView());

    Table<MusicRecord> table = ComponentLocator.of(Table.class).locate();

    assertEquals(Table.SelectionMode.MULTIPLE, table.getSelectionMode());
    assertTrue(table.isCheckboxSelection());
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldListEverySelectedRecordInTheDialog(WebforjTestbed context) {
    context.add(new TableMultiSelectionView());

    Table<MusicRecord> table = ComponentLocator.of(Table.class).locate();
    var rows = table.getRepository().findAll().limit(2).toList();

    UserInteraction.select(table, rows.get(0));
    UserInteraction.select(table, rows.get(1));

    var dialogs = context.getShownOptionDialogs();
    var message = dialogs.get(dialogs.size() - 1).getMessage();
    var listed =
        message
            .substring(message.indexOf("<ul><li>") + 8, message.indexOf("</li></ul>"))
            .split("</li><li>");

    assertTrue(message.startsWith("<html> You have selected the following records"));
    assertEquals(Set.of(rows.get(0).getTitle(), rows.get(1).getTitle()), Set.of(listed));
    assertEquals("Record Selection", dialogs.get(dialogs.size() - 1).getTitle());
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldReportThatNoRecordIsSelectedOnceTheUserDeselectsEveryRow(WebforjTestbed context) {
    context.add(new TableMultiSelectionView());

    Table<MusicRecord> table = ComponentLocator.of(Table.class).locate();
    var rows = table.getRepository().findAll().limit(2).toList();

    UserInteraction.select(table, rows.get(0));
    UserInteraction.select(table, rows.get(1));
    UserInteraction.deselect(table, rows.get(0));
    UserInteraction.deselect(table, rows.get(1));

    var dialogs = context.getShownOptionDialogs();

    assertEquals("There are no records selected", dialogs.get(dialogs.size() - 1).getMessage());
  }
}
