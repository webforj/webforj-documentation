package com.webforj.samples.views.drawer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.drawer.Drawer;
import com.webforj.component.layout.applayout.AppLayout;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class DrawerWelcomeViewTest {
  @Test
  void shouldKeepTheWelcomeDrawerOpenWhileTheNavigationDrawerOpens(WebforjTestbed context) {
    context.add(new DrawerWelcomeView());

    var layout = ComponentLocator.of(AppLayout.class).locate();
    var welcome = ComponentLocator.of(Drawer.class).locate();

    UserInteraction.open(layout);

    assertTrue(layout.isDrawerOpened());
    assertTrue(welcome.isOpened());
  }

  @Test
  void shouldDismissAndReopenTheWelcomeDrawerWithoutClosingTheNavigationDrawer(
      WebforjTestbed context) {
    context.add(new DrawerWelcomeView());

    var layout = ComponentLocator.of(AppLayout.class).locate();
    var welcome = ComponentLocator.of(Drawer.class).locate();

    UserInteraction.open(layout);
    UserInteraction.click(
        ComponentLocator.of(Button.class).within(welcome).byText("Get Started").locate());

    assertFalse(welcome.isOpened());
    assertTrue(layout.isDrawerOpened());

    UserInteraction.click(ComponentLocator.of(Button.class).byText("Open Welcome Drawer").locate());

    assertTrue(welcome.isOpened());
    assertTrue(layout.isDrawerOpened());
  }
}
