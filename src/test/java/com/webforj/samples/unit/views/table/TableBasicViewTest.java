package com.webforj.samples.unit.views.table;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.table.Column;
import com.webforj.component.table.Table;
import com.webforj.samples.views.table.MusicRecord;
import com.webforj.samples.views.table.Service;
import com.webforj.samples.views.table.TableBasicView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class TableBasicViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new TableBasicView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldShowEveryRecordOfTheRepositoryInTheFiveColumns(WebforjTestbed context) {
    context.add(new TableBasicView());

    Table<MusicRecord> table = ComponentLocator.of(Table.class).locate();
    var records = Service.getMusicRecords().findAll().toList();
    var first = table.getRepository().findAll().findFirst().orElseThrow();

    assertEquals(
        List.of("Number", "Title", "Artist", "Genre", "Cost"),
        table.getColumns().stream().map(Column::getId).toList());
    assertEquals(records.size(), table.getRepository().findAll().count());
    assertEquals(records.get(0).getTitle(), table.getColumnById("Title").getValue(first));
  }
}
