package com.webforj.samples.views.applayout.applayoutdrawerutility;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.html.elements.Div;
import com.webforj.component.icons.IconButton;
import com.webforj.component.layout.applayout.AppLayout;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest(
    routeClasses = {AppLayoutDrawerUtilityView.class, AppLayoutDrawerUtilityContentView.class})
class AppLayoutDrawerUtilityViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = context.navigate(AppLayoutDrawerUtilityView.class);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldShowTheTitleAndTheActionsInTheOpenDrawerHeader(WebforjTestbed context) {
    context.navigate(AppLayoutDrawerUtilityView.class);

    var layout = ComponentLocator.of(AppLayout.class).locate();

    assertTrue(layout.isDrawerOpened());
    assertTrue(layout.isDrawerHeaderVisible());
    assertTrue(ComponentLocator.of(Div.class).byText("Joe Smith").isPresent());
    assertEquals(
        "Pin drawer",
        ComponentLocator.of(IconButton.class).byName("pin").locate().getTooltipText());
    assertEquals(
        "Buy premium",
        ComponentLocator.of(IconButton.class).byName("rocket").locate().getTooltipText());
  }
}
