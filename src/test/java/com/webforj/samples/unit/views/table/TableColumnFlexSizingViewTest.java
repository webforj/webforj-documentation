package com.webforj.samples.unit.views.table;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.field.NumberField;
import com.webforj.component.table.Table;
import com.webforj.samples.views.table.MusicRecord;
import com.webforj.samples.views.table.TableColumnFlexSizingView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class TableColumnFlexSizingViewTest {
  @Test
  @SuppressWarnings("unchecked")
  void shouldGiveTheTitleColumnTheFlexTheUserTypes(WebforjTestbed context) {
    context.add(new TableColumnFlexSizingView());

    Table<MusicRecord> table = ComponentLocator.of(Table.class).locate();

    UserInteraction.type(ComponentLocator.of(NumberField.class).byLabel("Title").locate(), "3");

    assertEquals(3f, table.getColumnById("Title").getFlex());
    assertEquals(1.5f, table.getColumnById("Artist").getFlex());
    assertEquals(1f, table.getColumnById("Genre").getFlex());
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldShareTheSpaceEquallyOnEqualFlexAndRestoreTheRatiosOnReset(WebforjTestbed context) {
    context.add(new TableColumnFlexSizingView());

    Table<MusicRecord> table = ComponentLocator.of(Table.class).locate();
    var titleFlex = ComponentLocator.of(NumberField.class).byLabel("Title").locate();

    UserInteraction.click(ComponentLocator.of(Button.class).byText("Equal Flex").locate());

    assertEquals(1f, table.getColumnById("Title").getFlex());
    assertEquals(1f, table.getColumnById("Artist").getFlex());
    assertEquals(1f, table.getColumnById("Genre").getFlex());
    assertEquals(1.0, titleFlex.getValue());

    UserInteraction.click(ComponentLocator.of(Button.class).byText("Reset to Defaults").locate());

    assertEquals(2f, table.getColumnById("Title").getFlex());
    assertEquals(1.5f, table.getColumnById("Artist").getFlex());
    assertEquals(1f, table.getColumnById("Genre").getFlex());
    assertEquals(2.0, titleFlex.getValue());
  }
}
