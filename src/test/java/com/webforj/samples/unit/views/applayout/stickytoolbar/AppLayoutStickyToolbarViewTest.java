package com.webforj.samples.unit.views.applayout.stickytoolbar;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.layout.applayout.AppLayout;
import com.webforj.samples.views.applayout.stickytoolbar.AppLayoutStickyToolbarContentView;
import com.webforj.samples.views.applayout.stickytoolbar.AppLayoutStickyToolbarView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest(
    routeClasses = {AppLayoutStickyToolbarView.class, AppLayoutStickyToolbarContentView.class})
class AppLayoutStickyToolbarViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = context.navigate(AppLayoutStickyToolbarView.class);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldRevealTheHeaderAndCollapseItToTheStickyToolbar(WebforjTestbed context) {
    context.navigate(AppLayoutStickyToolbarView.class);

    var layout = ComponentLocator.of(AppLayout.class).locate();

    assertTrue(layout.isHeaderReveal());
    assertTrue(layout.getClassNameList().contains("layout--collapse"));
  }
}
