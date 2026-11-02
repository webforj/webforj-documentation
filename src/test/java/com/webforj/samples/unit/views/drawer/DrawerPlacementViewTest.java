package com.webforj.samples.unit.views.drawer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.drawer.Drawer;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.samples.views.drawer.DrawerPlacementView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@WebforjTest
class DrawerPlacementViewTest {
  @ParameterizedTest
  @CsvSource({
    "Top, TOP",
    "Top Center, TOP_CENTER",
    "Bottom, BOTTOM",
    "Bottom Center, BOTTOM_CENTER",
    "Right, RIGHT"
  })
  void shouldPlaceTheDrawerAtThePickedEdge(
      String option, Drawer.Placement placement, WebforjTestbed context) {
    context.add(new DrawerPlacementView());

    var drawer = ComponentLocator.of(Drawer.class).locate();

    UserInteraction.check(ComponentLocator.of(RadioButton.class).byText(option).locate());

    assertEquals(placement, drawer.getPlacement());
  }

  @Test
  void shouldPlaceTheDrawerOnTheLeftAgain(WebforjTestbed context) {
    context.add(new DrawerPlacementView());

    var drawer = ComponentLocator.of(Drawer.class).locate();

    UserInteraction.check(ComponentLocator.of(RadioButton.class).byText("Top").locate());
    UserInteraction.check(ComponentLocator.of(RadioButton.class).byText("Left").locate());

    assertEquals(Drawer.Placement.LEFT, drawer.getPlacement());
  }
}
