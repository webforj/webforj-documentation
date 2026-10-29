package com.webforj.samples.unit.views.drawer;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.drawer.Drawer;
import com.webforj.samples.views.drawer.DrawerContactView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class DrawerContactViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new DrawerContactView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldReopenTheContactListAtTheBottomCenterWhenTheUserAsksForContacts(
      WebforjTestbed context) {
    context.add(new DrawerContactView());

    var drawer = ComponentLocator.of(Drawer.class).locate();

    UserInteraction.close(drawer);

    assertFalse(drawer.isOpened());

    UserInteraction.click(ComponentLocator.of(Button.class).byText("Open Contacts").locate());

    assertTrue(drawer.isOpened());
    assertEquals(Drawer.Placement.BOTTOM_CENTER, drawer.getPlacement());
  }
}
