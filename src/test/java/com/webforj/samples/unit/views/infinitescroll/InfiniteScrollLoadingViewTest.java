package com.webforj.samples.unit.views.infinitescroll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.act.component.infinitescroll.InfiniteScrollInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.html.elements.Div;
import com.webforj.component.infinitescroll.InfiniteScroll;
import com.webforj.samples.views.infinitescroll.InfiniteScrollLoadingView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class InfiniteScrollLoadingViewTest {
  @Test
  void shouldShowTheCustomTextAndIconWhileLoading(WebforjTestbed context) {
    context.add(new InfiniteScrollLoadingView());

    var scroll = ComponentLocator.of(InfiniteScroll.class).locate();

    assertEquals("Fetching more records...", scroll.getText());
    assertEquals("tabler:cloud-download", scroll.getIcon());
  }

  @Test
  void shouldLoadEightMoreRecordsWhenTheUserReachesTheEnd(WebforjTestbed context) {
    context.add(new InfiniteScrollLoadingView());

    var scroll = ComponentLocator.of(InfiniteScroll.class).locate();
    var items = ComponentLocator.of(Div.class).byClassName("is-canvas").locate();

    UserInteraction.perform(scroll, InfiniteScrollInteraction.scroll());

    assertEquals(8, items.getComponents().size());
    assertFalse(scroll.isCompleted());
  }
}
