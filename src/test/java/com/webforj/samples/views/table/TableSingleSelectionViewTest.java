package com.webforj.samples.views.table;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.table.Table;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class TableSingleSelectionViewTest {
  @Test
  @SuppressWarnings("unchecked")
  void shouldDescribeTheRecordInADialogEachTimeTheUserSelectsARow(WebforjTestbed context) {
    context.add(new TableSingleSelectionView());

    Table<MusicRecord> table = ComponentLocator.of(Table.class).locate();
    var rows = table.getRepository().findAll().limit(2).toList();

    UserInteraction.select(table, rows.get(0));
    UserInteraction.select(table, rows.get(1));

    var dialogs = context.getShownOptionDialogs();

    assertEquals(
        "You have selected " + rows.get(0).getTitle() + " by " + rows.get(0).getArtist(),
        dialogs.get(0).getMessage());
    assertEquals("Record Number " + rows.get(0).getNumber(), dialogs.get(0).getTitle());
    assertEquals(
        "You have selected " + rows.get(1).getTitle() + " by " + rows.get(1).getArtist(),
        dialogs.get(1).getMessage());
    assertEquals("Record Number " + rows.get(1).getNumber(), dialogs.get(1).getTitle());
    assertEquals(List.of(rows.get(1)), table.getSelectedItems());
  }
}
