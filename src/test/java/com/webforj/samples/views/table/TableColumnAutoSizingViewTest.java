package com.webforj.samples.views.table;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.act.component.table.TableInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.table.Table;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class TableColumnAutoSizingViewTest {
  @Test
  @SuppressWarnings("unchecked")
  void shouldRestoreTheDefaultSizingAfterTheUserResizesAColumn(WebforjTestbed context) {
    context.add(new TableColumnAutoSizingView());

    Table<MusicRecord> table = ComponentLocator.of(Table.class).locate();
    var title = table.getColumnById("Title");

    UserInteraction.perform(table, TableInteraction.resizeColumn(title, 250f));
    UserInteraction.click(ComponentLocator.of(Button.class).byText("Reset to Default").locate());

    assertEquals(100f, title.getWidth());
    assertEquals(0f, title.getFlex());
    assertEquals(1f, table.getColumnById("Artist").getFlex());
  }
}
