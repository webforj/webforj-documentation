package com.webforj.samples.unit.views.refresher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.act.component.refresher.RefresherInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.html.elements.Div;
import com.webforj.component.refresher.Refresher;
import com.webforj.samples.views.refresher.RefresherI18nView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class RefresherI18nViewTest {
  @Test
  void shouldShowThePullReleaseAndRefreshLabelsInFrench(WebforjTestbed context) {
    context.add(new RefresherI18nView());

    var i18n = ComponentLocator.of(Refresher.class).locate().getI18n();

    assertEquals("Tirez pour actualiser", i18n.getPull());
    assertEquals("Relâchez pour actualiser", i18n.getRelease());
    assertEquals("Actualisation en cours...", i18n.getRefresh());
  }

  @Test
  void shouldReloadTheListWhenTheUserPullsToRefresh(WebforjTestbed context) {
    context.add(new RefresherI18nView());

    var refresher = ComponentLocator.of(Refresher.class).locate();
    var canvas = ComponentLocator.of(Div.class).byClassName("is-canvas").locate();
    var previous = List.copyOf(canvas.getComponents());

    UserInteraction.perform(refresher, RefresherInteraction.refresh());

    assertEquals(8, canvas.getComponents().size());
    assertTrue(canvas.getComponents().stream().noneMatch(previous::contains));
  }
}
