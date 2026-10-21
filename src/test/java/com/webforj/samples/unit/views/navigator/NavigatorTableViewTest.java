package com.webforj.samples.unit.views.navigator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.act.component.navigator.NavigatorInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.navigator.Navigator;
import com.webforj.component.table.Table;
import com.webforj.samples.views.navigator.NavigatorTableView;
import com.webforj.samples.views.table.MusicRecord;
import com.webforj.samples.views.table.Service;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class NavigatorTableViewTest {
  @Test
  @SuppressWarnings("unchecked")
  void shouldShowTheNavigatedPageOfTheSharedRepositoryInTheTable(WebforjTestbed context) {
    context.add(new NavigatorTableView());

    var nav = ComponentLocator.of(Navigator.class).locate();
    Table<MusicRecord> table = ComponentLocator.of(Table.class).locate();
    var records = Service.getMusicRecords().findAll().map(MusicRecord::getNumber).toList();

    assertEquals(
        records.subList(0, 10),
        table.getRepository().findAll().map(MusicRecord::getNumber).toList());

    UserInteraction.perform(nav, NavigatorInteraction.moveNext());

    assertEquals(
        records.subList(10, 20),
        table.getRepository().findAll().map(MusicRecord::getNumber).toList());
  }
}
