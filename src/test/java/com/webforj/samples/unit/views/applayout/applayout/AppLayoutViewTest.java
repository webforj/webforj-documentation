package com.webforj.samples.unit.views.applayout.applayout;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.appnav.AppNavItem;
import com.webforj.samples.views.applayout.applayout.AppLayoutContentView;
import com.webforj.samples.views.applayout.applayout.AppLayoutView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest(routeClasses = {AppLayoutView.class, AppLayoutContentView.class})
class AppLayoutViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = context.navigate(AppLayoutView.class);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldShowTheContentOfTheDrawerItemTheUserClicks(WebforjTestbed context) {
    context.navigate(AppLayoutView.class);

    UserInteraction.click(ComponentLocator.of(AppNavItem.class).byText("Orders").locate());

    assertTrue(
        ComponentLocator.of(Paragraph.class).byText("Content for Orders goes here").isPresent());
  }
}
