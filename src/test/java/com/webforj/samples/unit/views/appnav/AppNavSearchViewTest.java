package com.webforj.samples.unit.views.appnav;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.layout.appnav.AppNav;
import com.webforj.component.layout.appnav.AppNavItem;
import com.webforj.samples.views.appnav.AppNavSearchPageView;
import com.webforj.samples.views.appnav.AppNavSearchView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest(routeClasses = {AppNavSearchView.class, AppNavSearchPageView.class})
class AppNavSearchViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = context.navigate(AppNavSearchView.class);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldOfferTheSearchFieldWithItsPlaceholderAndEmptyMessage(WebforjTestbed context) {
    context.navigate(AppNavSearchView.class);

    var nav = ComponentLocator.of(AppNav.class).locate();

    assertTrue(nav.getSearch().isFieldVisible());
    assertEquals("Search menu", nav.getSearch().getPlaceholder());
    assertEquals("Nothing found", nav.getSearch().getEmptyMessage());
  }

  @Test
  void shouldKeepTheDashboardPinnedAsTheShortcutThatStaysVisibleWhileSearching(
      WebforjTestbed context) {
    context.navigate(AppNavSearchView.class);

    var nav = ComponentLocator.of(AppNav.class).locate();
    var dashboard = ComponentLocator.of(AppNavItem.class).byText("Dashboard").locate();

    assertTrue(nav.getPinning().isEnabled());
    assertEquals(List.of(dashboard), nav.getPinning().getItems());
  }
}
