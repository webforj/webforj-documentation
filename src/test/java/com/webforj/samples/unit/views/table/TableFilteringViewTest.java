package com.webforj.samples.unit.views.table;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.TextField;
import com.webforj.component.table.Table;
import com.webforj.samples.views.table.MusicRecord;
import com.webforj.samples.views.table.Service;
import com.webforj.samples.views.table.TableFilteringView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@WebforjTest
class TableFilteringViewTest {
  @ParameterizedTest
  @ValueSource(strings = {"Abbey Road", "road", "ABBEY ROAD"})
  @SuppressWarnings("unchecked")
  void shouldShowOnlyTheRecordsWhoseTitleContainsTheSearchIgnoringCase(
      String search, WebforjTestbed context) {
    context.add(new TableFilteringView());

    Table<MusicRecord> table = ComponentLocator.of(Table.class).locate();

    UserInteraction.type(ComponentLocator.of(TextField.class).locate(), search);

    assertEquals(
        List.of("Abbey Road"), table.getRepository().findAll().map(MusicRecord::getTitle).toList());
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldShowNoRecordsForTheNameOfAnArtistBecauseOnlyTitlesAreSearched(WebforjTestbed context) {
    context.add(new TableFilteringView());

    Table<MusicRecord> table = ComponentLocator.of(Table.class).locate();

    UserInteraction.type(ComponentLocator.of(TextField.class).locate(), "ABBA");

    assertEquals(0, table.getRepository().findAll().count());
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldShowEveryRecordAgainWhenTheUserClearsTheSearch(WebforjTestbed context) {
    context.add(new TableFilteringView());

    Table<MusicRecord> table = ComponentLocator.of(Table.class).locate();
    var search = ComponentLocator.of(TextField.class).locate();

    UserInteraction.type(search, "Abbey Road");
    UserInteraction.type(search, "");

    assertEquals(Service.getMusicRecords().size(), table.getRepository().findAll().count());
  }
}
