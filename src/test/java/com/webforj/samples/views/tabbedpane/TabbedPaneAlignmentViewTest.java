package com.webforj.samples.views.tabbedpane;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.tabbedpane.TabbedPane;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@WebforjTest
class TabbedPaneAlignmentViewTest {
  @ParameterizedTest
  @EnumSource(value = TabbedPane.Alignment.class, mode = EnumSource.Mode.EXCLUDE, names = "AUTO")
  void shouldAlignTheTabsAsTheUserPicks(TabbedPane.Alignment alignment, WebforjTestbed context) {
    context.add(new TabbedPaneAlignmentView());

    var pane = ComponentLocator.of(TabbedPane.class).locate();

    UserInteraction.selectIndex(
        ComponentLocator.of(ChoiceBox.class).byLabel("Alignment").locate(), alignment.ordinal());

    assertEquals(alignment, pane.getAlignment());
  }
}
