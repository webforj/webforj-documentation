package com.webforj.samples.views.appnav;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.act.component.appnav.AppNavItemInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.layout.appnav.AppNav;
import com.webforj.component.layout.appnav.AppNavItem;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest(routeClasses = {AppNavPinningView.class, AppNavPinningPageView.class})
class AppNavPinningViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = context.navigate(AppNavPinningView.class);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldStartWithTheDashboardInTheFavoritesGroup(WebforjTestbed context) {
    context.navigate(AppNavPinningView.class);

    var nav = ComponentLocator.of(AppNav.class).locate();
    var dashboard = ComponentLocator.of(AppNavItem.class).byText("Dashboard").locate();

    assertEquals("Favorites", nav.getPinning().getTitle());
    assertEquals(List.of(dashboard), nav.getPinning().getItems());
  }

  @Test
  void shouldAddTheItemToTheFavoritesWhenTheUserPinsIt(WebforjTestbed context) {
    context.navigate(AppNavPinningView.class);

    var nav = ComponentLocator.of(AppNav.class).locate();
    var dashboard = ComponentLocator.of(AppNavItem.class).byText("Dashboard").locate();
    var paid = ComponentLocator.of(AppNavItem.class).byText("Paid").locate();

    UserInteraction.perform(paid, AppNavItemInteraction.pin());

    assertEquals(List.of(dashboard, paid), nav.getPinning().getItems());
  }

  @Test
  void shouldEmptyTheFavoritesWhenTheUserUnpinsTheDashboard(WebforjTestbed context) {
    context.navigate(AppNavPinningView.class);

    var nav = ComponentLocator.of(AppNav.class).locate();
    var dashboard = ComponentLocator.of(AppNavItem.class).byText("Dashboard").locate();

    UserInteraction.perform(dashboard, AppNavItemInteraction.unpin());

    assertTrue(nav.getPinning().getItems().isEmpty());
  }
}
