package com.webforj.samples.views.appnav;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.badge.Badge;
import com.webforj.component.icons.Icon;
import com.webforj.component.layout.appnav.AppNav;
import com.webforj.component.layout.appnav.AppNavItem;
import com.webforj.component.layout.appnav.AppNavLabel;
import com.webforj.concern.HasText;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest(routeClasses = {AppNavLabelView.class, AppNavLabelPageView.class})
class AppNavLabelViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = context.navigate(AppNavLabelView.class);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldTitleTheItemsThatFollowEachLabelWithoutMakingTheLabelAnItem(WebforjTestbed context) {
    context.navigate(AppNavLabelView.class);

    var nav = ComponentLocator.of(AppNav.class).locate();

    assertEquals(
        List.of(
            "Dashboard",
            "Analytics",
            "Overview",
            "Reports",
            "Commerce",
            "Orders",
            "Products",
            "Customers",
            "Account",
            "Profile",
            "Billing"),
        nav.getComponents().stream().map(entry -> ((HasText<?>) entry).getText()).toList());
    assertEquals(
        List.of(
            "Dashboard",
            "Overview",
            "Reports",
            "Orders",
            "Products",
            "Customers",
            "Profile",
            "Billing"),
        nav.getItems().stream().map(AppNavItem::getText).toList());
  }

  @Test
  void shouldShowTheIconBeforeAndTheBadgeAfterTheAnalyticsLabel(WebforjTestbed context) {
    context.navigate(AppNavLabelView.class);

    var analytics = ComponentLocator.of(AppNavLabel.class).byText("Analytics").locate();
    var prefix = assertInstanceOf(Icon.class, analytics.getPrefixComponent());
    var suffix = assertInstanceOf(Badge.class, analytics.getSuffixComponent());

    assertEquals("chart-pie", prefix.getName());
    assertEquals("2", suffix.getText());
  }
}
