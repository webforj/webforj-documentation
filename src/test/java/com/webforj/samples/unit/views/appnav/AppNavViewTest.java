package com.webforj.samples.unit.views.appnav;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.appnav.AppNavItem;
import com.webforj.samples.views.appnav.AppNavPageView;
import com.webforj.samples.views.appnav.AppNavView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest(routeClasses = {AppNavView.class, AppNavPageView.class})
class AppNavViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = context.navigate(AppNavView.class);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldShowThePageOfTheNestedItemTheUserClicks(WebforjTestbed context) {
    context.navigate(AppNavView.class);

    var inbox = ComponentLocator.of(AppNavItem.class).byText("Inbox").locate();

    UserInteraction.click(
        ComponentLocator.of(AppNavItem.class).within(inbox).byText("Social").locate());

    assertEquals(
        "Content for <strong>Social</strong> goes here",
        ComponentLocator.of(Paragraph.class).locate().getHtml());
  }

  @Test
  void shouldStayOnTheCurrentPageWhenTheUserClicksTheInboxGroupHeader(WebforjTestbed context) {
    context.navigate(AppNavView.class);

    var inbox = ComponentLocator.of(AppNavItem.class).byText("Inbox").locate();
    UserInteraction.click(
        ComponentLocator.of(AppNavItem.class).within(inbox).byText("Social").locate());

    UserInteraction.click(inbox);

    assertEquals(
        "Content for <strong>Social</strong> goes here",
        ComponentLocator.of(Paragraph.class).locate().getHtml());
  }
}
