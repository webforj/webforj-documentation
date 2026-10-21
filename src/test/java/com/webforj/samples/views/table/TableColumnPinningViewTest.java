package com.webforj.samples.views.table;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.table.Column;
import com.webforj.component.table.Table;
import com.webforj.component.table.event.renderer.RendererClickEvent;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

@WebforjTest
class TableColumnPinningViewTest {
  @Test
  @SuppressWarnings("unchecked")
  void shouldPinTheNumberColumnLeftAndTheEditColumnRight(WebforjTestbed context) {
    context.add(new TableColumnPinningView());

    Table<MusicRecord> table = ComponentLocator.of(Table.class).locate();

    assertEquals(
        List.of(
            Column.PinDirection.LEFT,
            Column.PinDirection.AUTO,
            Column.PinDirection.AUTO,
            Column.PinDirection.AUTO,
            Column.PinDirection.AUTO,
            Column.PinDirection.AUTO,
            Column.PinDirection.RIGHT),
        table.getColumns().stream().map(Column::getPinDirection).toList());
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldAskToEditTheRecordWhoseEditButtonTheUserClicks(WebforjTestbed context) {
    context.add(new TableColumnPinningView());

    Table<MusicRecord> table = ComponentLocator.of(Table.class).locate();
    var item = table.getRepository().findAll().skip(2).findFirst().orElseThrow();
    var edit = table.getColumns().get(6);

    UserInteraction.fireEvent(
        new RendererClickEvent<>(
            table,
            Map.of(
                "itemKey", table.getItemKeysRegistry().getKey(item), "columnKey", edit.getId())));

    assertEquals(
        "You asked to edit record number <b>" + item.getNumber() + "</b>.",
        context.getShownOptionDialogs().get(0).getMessage());
    assertEquals("Edit Record", context.getShownOptionDialogs().get(0).getTitle());
  }
}
