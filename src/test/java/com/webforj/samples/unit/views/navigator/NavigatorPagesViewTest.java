package com.webforj.samples.unit.views.navigator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.act.component.navigator.NavigatorInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.navigator.Navigator;
import com.webforj.samples.views.navigator.NavigatorPagesView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class NavigatorPagesViewTest {
  @Test
  void shouldShowAtMostFivePageLinks(WebforjTestbed context) {
    context.add(new NavigatorPagesView());

    var nav = ComponentLocator.of(Navigator.class).locate();

    assertEquals(10, nav.getPaginator().getTotalPages());
    assertArrayEquals(new int[] {1, 2, 3, 4, 5}, nav.getPaginator().getPages());

    UserInteraction.perform(nav, NavigatorInteraction.moveLast());

    assertArrayEquals(new int[] {6, 7, 8, 9, 10}, nav.getPaginator().getPages());
  }

  @Test
  void shouldShowTheItemRangeOfTheCurrentPage(WebforjTestbed context) {
    context.add(new NavigatorPagesView());

    var nav = ComponentLocator.of(Navigator.class).locate();
    var text = ComponentLocator.of(Paragraph.class).locate();

    UserInteraction.perform(nav, NavigatorInteraction.moveNext());
    assertEquals("Showing 11 to 20 of 100", text.getText());

    UserInteraction.perform(nav, NavigatorInteraction.moveLast());
    assertEquals("Showing 91 to 100 of 100", text.getText());

    UserInteraction.perform(nav, NavigatorInteraction.moveFirst());
    assertEquals("Showing 1 to 10 of 100", text.getText());
  }
}
