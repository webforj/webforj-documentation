package com.webforj.samples.unit.views.table;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.table.ColumnGroup;
import com.webforj.component.table.Table;
import com.webforj.samples.views.table.MusicRecord;
import com.webforj.samples.views.table.TableColumnGroupsView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WebforjTest
class TableColumnGroupsViewTest {
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
    var view = new TableColumnGroupsView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldGroupTitleArtistAndGenreUnderCatalogAndCostUnderPricing(WebforjTestbed context) {
    context.add(new TableColumnGroupsView());

    Table<MusicRecord> table = ComponentLocator.of(Table.class).locate();
    var groups = table.getColumnGroups();

    assertEquals(
        List.of("Catalog", "Pricing"), groups.stream().map(ColumnGroup::getLabel).toList());
    assertEquals(
        List.of("Title", "Artist", "Genre"),
        groups.get(0).getChildren().stream().map(ColumnGroup::getId).toList());
    assertEquals(
        List.of("Cost"), groups.get(1).getChildren().stream().map(ColumnGroup::getId).toList());
  }
}
