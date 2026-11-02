package com.webforj.samples.unit.views.navigator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.act.component.navigator.NavigatorInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.navigator.Navigator;
import com.webforj.samples.views.navigator.NavigatorBasicView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class NavigatorBasicViewTest {
  @Test
  void shouldSetTheValueTextByTheDirectionOfTheClickedButton(WebforjTestbed context) {
    context.add(new NavigatorBasicView());

    var nav = ComponentLocator.of(Navigator.class).locate();

    assertEquals("Value: 0", nav.getText());

    UserInteraction.perform(nav, NavigatorInteraction.moveNext());
    assertEquals("Value: 1", nav.getText());

    UserInteraction.perform(nav, NavigatorInteraction.moveLast());
    assertEquals("Value: 10", nav.getText());

    UserInteraction.perform(nav, NavigatorInteraction.movePrevious());
    assertEquals("Value: 9", nav.getText());

    UserInteraction.perform(nav, NavigatorInteraction.moveFirst());
    assertEquals("Value: 0", nav.getText());
  }

  @Test
  void shouldKeepTheValueBetweenZeroAndTen(WebforjTestbed context) {
    context.add(new NavigatorBasicView());

    var nav = ComponentLocator.of(Navigator.class).locate();

    UserInteraction.perform(nav, NavigatorInteraction.movePrevious());
    assertEquals("Value: 0", nav.getText());

    UserInteraction.perform(nav, NavigatorInteraction.moveLast());
    UserInteraction.perform(nav, NavigatorInteraction.moveNext());
    assertEquals("Value: 10", nav.getText());
  }
}
