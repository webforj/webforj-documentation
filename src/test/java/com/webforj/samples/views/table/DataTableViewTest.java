package com.webforj.samples.views.table;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonObject;
import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.act.component.navigator.NavigatorInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.TextField;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.navigator.Navigator;
import com.webforj.component.table.Table;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@WebforjTest
class DataTableViewTest {
  @ParameterizedTest
  @MethodSource("providePageSizes")
  void shouldSplitAllWinnersIntoPagesOfThePickedSize(
      int index, int size, int pages, WebforjTestbed context) {
    context.add(new DataTableView());

    var paginator = locatePages().getPaginator();

    UserInteraction.selectIndex(ComponentLocator.of(ChoiceBox.class).locate(), index);

    assertEquals(8618, paginator.getTotalItems());
    assertEquals(size, paginator.getSize());
    assertEquals(pages, paginator.getTotalPages());
  }

  @Test
  void shouldMoveThroughThePagesWithTheNavigator(WebforjTestbed context) {
    context.add(new DataTableView());

    var pages = locatePages();
    var paginator = pages.getPaginator();

    UserInteraction.perform(pages, NavigatorInteraction.moveNext());

    assertEquals(2, paginator.getCurrent());
    assertEquals(10, paginator.getStartIndex());
    assertEquals(19, paginator.getEndIndex());

    UserInteraction.perform(pages, NavigatorInteraction.moveLast());

    assertEquals(862, paginator.getCurrent());
    assertEquals(8617, paginator.getEndIndex());

    UserInteraction.perform(pages, NavigatorInteraction.moveFirst());

    assertEquals(1, paginator.getCurrent());
    assertEquals(0, paginator.getStartIndex());
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldShowOnlyMatchingAthletesFromTheFirstPageWhenTheUserSearches(WebforjTestbed context) {
    context.add(new DataTableView());

    Table<JsonObject> table = ComponentLocator.of(Table.class).locate();
    var pages = locatePages();
    var paginator = pages.getPaginator();
    var search = ComponentLocator.of(TextField.class).locate();

    UserInteraction.perform(pages, NavigatorInteraction.moveNext());
    UserInteraction.type(search, "Michael Phelps");

    assertEquals(1, paginator.getCurrent());
    assertEquals(3, paginator.getTotalItems());
    assertTrue(
        table
            .getRepository()
            .findAll()
            .allMatch(row -> row.get("athlete").getAsString().equals("Michael Phelps")));

    UserInteraction.type(search, "nobody-XYZ-123");

    assertEquals(0, paginator.getTotalItems());

    UserInteraction.type(search, "");

    assertEquals(8618, paginator.getTotalItems());
  }

  private static Navigator locatePages() {
    return ComponentLocator.of(Navigator.class)
        .filter(n -> n.getLayout() == Navigator.Layout.PAGES)
        .locate();
  }

  private static Stream<Arguments> providePageSizes() {
    return Stream.of(
        Arguments.of(0, 10, 862),
        Arguments.of(1, 25, 345),
        Arguments.of(2, 50, 173),
        Arguments.of(3, 100, 87));
  }
}
