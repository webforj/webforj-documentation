package com.webforj.samples.unit.views.badge;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.badge.Badge;
import com.webforj.component.tabbedpane.TabbedPane;
import com.webforj.samples.views.badge.BadgeTabbedPaneView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class BadgeTabbedPaneViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new BadgeTabbedPaneView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldKeepTheCountOfEveryTabWhenAnotherTabIsSelected(WebforjTestbed context) {
    context.add(new BadgeTabbedPaneView());

    var pane =
        ComponentLocator.of(TabbedPane.class)
            .filter(tabbedPane -> tabbedPane.getTab(0).getText().equals("Inbox"))
            .locate();
    var tabs = pane.getTabs();

    UserInteraction.select(pane, tabs.get(1));

    assertEquals("12", ((Badge) tabs.get(0).getSuffixComponent()).getText());
    assertEquals("3", ((Badge) tabs.get(1).getSuffixComponent()).getText());
    assertEquals("1", ((Badge) tabs.get(2).getSuffixComponent()).getText());
    assertNull(tabs.get(3).getSuffixComponent());
  }
}
