package com.webforj.samples.unit.views.applayout.mobiledrawer;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.layout.applayout.AppLayout;
import com.webforj.component.tabbedpane.TabbedPane;
import com.webforj.samples.views.applayout.mobiledrawer.AppLayoutMobileDrawerContentView;
import com.webforj.samples.views.applayout.mobiledrawer.AppLayoutMobileDrawerView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest(
    routeClasses = {AppLayoutMobileDrawerView.class, AppLayoutMobileDrawerContentView.class})
class AppLayoutMobileDrawerViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = context.navigate(AppLayoutMobileDrawerView.class);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldShowTheBottomTabNavigationInTheRevealingFooter(WebforjTestbed context) {
    context.navigate(AppLayoutMobileDrawerView.class);

    var layout = ComponentLocator.of(AppLayout.class).locate();
    var footerMenu = ComponentLocator.of(TabbedPane.class).locate();

    assertEquals(TabbedPane.Placement.BOTTOM, footerMenu.getPlacement());
    assertTrue(layout.isFooterReveal());
    assertTrue(layout.isHeaderReveal());
  }
}
