package com.webforj.samples.views.table;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.table.ColumnGroup;
import com.webforj.component.table.Table;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WebforjTest
class TableNestedColumnGroupsViewTest {
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
    var view = new TableNestedColumnGroupsView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldNestTheMediaAndPricingGroupsInsideTheDetailsGroup(WebforjTestbed context) {
    context.add(new TableNestedColumnGroupsView());

    Table<MusicRecord> table = ComponentLocator.of(Table.class).locate();
    var details = table.getColumnGroups().get(1);
    var media = details.getChildren().get(0);
    var pricing = details.getChildren().get(1);

    assertEquals(
        List.of("Catalog", "Details"),
        table.getColumnGroups().stream().map(ColumnGroup::getLabel).toList());
    assertEquals(
        List.of("Media", "Pricing"),
        details.getChildren().stream().map(ColumnGroup::getLabel).toList());
    assertEquals(
        List.of("Tracks", "Playing Time"),
        media.getChildren().stream().map(ColumnGroup::getId).toList());
    assertEquals(
        List.of("Cost", "Retail"), pricing.getChildren().stream().map(ColumnGroup::getId).toList());
  }
}
