package com.webforj.samples.unit.views.applayout.fullnavbar;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.layout.applayout.AppLayout;
import com.webforj.samples.views.applayout.fullnavbar.AppLayoutFullNavbarContentView;
import com.webforj.samples.views.applayout.fullnavbar.AppLayoutFullNavbarView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest(routeClasses = {AppLayoutFullNavbarView.class, AppLayoutFullNavbarContentView.class})
class AppLayoutFullNavbarViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = context.navigate(AppLayoutFullNavbarView.class);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldStretchTheHeaderOverTheDrawerInsteadOfShiftingItBesideTheDrawer(
      WebforjTestbed context) {
    context.navigate(AppLayoutFullNavbarView.class);

    var layout = ComponentLocator.of(AppLayout.class).locate();

    assertFalse(layout.isHeaderOffscreen());
  }
}
