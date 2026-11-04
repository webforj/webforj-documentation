package com.webforj.samples.unit.views.tabbedpane;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.tabbedpane.TabbedPane;
import com.webforj.samples.views.tabbedpane.TabbedPanePlacementView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@WebforjTest
class TabbedPanePlacementViewTest {
  @ParameterizedTest
  @EnumSource(value = TabbedPane.Placement.class, mode = EnumSource.Mode.EXCLUDE, names = "TOP")
  void shouldPlaceTheTabsWhereTheUserPicks(TabbedPane.Placement placement, WebforjTestbed context) {
    context.add(new TabbedPanePlacementView());

    var pane = ComponentLocator.of(TabbedPane.class).locate();

    UserInteraction.selectIndex(
        ComponentLocator.of(ChoiceBox.class).byLabel("Placement").locate(), placement.ordinal());

    assertEquals(placement, pane.getPlacement());
  }
}
