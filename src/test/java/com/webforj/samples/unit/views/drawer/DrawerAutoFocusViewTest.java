package com.webforj.samples.unit.views.drawer;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.drawer.Drawer;
import com.webforj.samples.views.drawer.DrawerAutoFocusView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class DrawerAutoFocusViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new DrawerAutoFocusView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldReopenThePreferencesWithAutoFocusOnTheFirstCheckBox(WebforjTestbed context) {
    context.add(new DrawerAutoFocusView());

    var drawer = ComponentLocator.of(Drawer.class).locate();

    UserInteraction.close(drawer);
    UserInteraction.click(ComponentLocator.of(Button.class).byText("Open Preferences").locate());

    assertTrue(drawer.isOpened());
    assertTrue(drawer.isAutoFocus());
  }
}
