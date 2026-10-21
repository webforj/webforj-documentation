package com.webforj.samples.views.refresher;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.act.component.refresher.RefresherInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.Theme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.refresher.Refresher;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class RefresherThemesViewTest {
  @Test
  void shouldMoveTheRefresherAndTheListToTheNextThemeOnEveryPull(WebforjTestbed context) {
    context.add(new RefresherThemesView());

    var refresher = ComponentLocator.of(Refresher.class).locate();
    var canvas = ComponentLocator.of(Div.class).byClassName("is-canvas").locate();
    var refresherThemes = new ArrayList<Theme>();
    var canvasThemes = new ArrayList<String>();

    for (int pull = 0; pull < 7; pull++) {
      UserInteraction.perform(refresher, RefresherInteraction.refresh());
      refresherThemes.add(refresher.getTheme());
      canvasThemes.add(canvas.getAttribute("theme"));
    }

    assertEquals(
        List.of(
            Theme.SUCCESS,
            Theme.WARNING,
            Theme.DANGER,
            Theme.INFO,
            Theme.GRAY,
            Theme.DANGER,
            Theme.PRIMARY),
        refresherThemes);
    assertEquals(
        List.of("success", "warning", "danger", "info", "gray", "danger", "primary"), canvasThemes);
  }
}
