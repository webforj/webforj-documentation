package com.webforj.samples.views.applayout.multipleheaders;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.html.elements.H3;
import com.webforj.component.layout.applayout.AppDrawerToggle;
import com.webforj.component.layout.toolbar.Toolbar;
import com.webforj.component.tabbedpane.Tab;
import com.webforj.component.tabbedpane.TabbedPane;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest(
    routeClasses = {AppLayoutMultipleHeadersView.class, AppLayoutMultipleHeaderContentView.class})
class AppLayoutMultipleHeadersViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = context.navigate(AppLayoutMultipleHeadersView.class);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldStackTheTitleToolbarAndTheSecondaryMenuToolbar(WebforjTestbed context) {
    context.navigate(AppLayoutMultipleHeadersView.class);

    var toolbars = ComponentLocator.of(Toolbar.class).all();

    assertEquals(2, toolbars.size());
    assertTrue(ComponentLocator.of(AppDrawerToggle.class).within(toolbars.get(0)).isPresent());
    assertTrue(
        ComponentLocator.of(H3.class).within(toolbars.get(0)).byText("Application").isPresent());
    assertEquals(
        List.of("Sales", "Enterprise", "Payments", "History"),
        ComponentLocator.of(TabbedPane.class).within(toolbars.get(1)).locate().getTabs().stream()
            .map(Tab::getText)
            .toList());
  }
}
