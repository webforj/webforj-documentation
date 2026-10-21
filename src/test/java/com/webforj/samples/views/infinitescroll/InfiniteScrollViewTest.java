package com.webforj.samples.views.infinitescroll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.act.component.infinitescroll.InfiniteScrollInteraction;
import com.webforj.automation.act.exception.InteractionRejectedException;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.html.elements.Div;
import com.webforj.component.infinitescroll.InfiniteScroll;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class InfiniteScrollViewTest {
  @Test
  void shouldLoadEightMoreItemsEachTimeTheUserReachesTheEnd(WebforjTestbed context) {
    context.add(new InfiniteScrollView());

    var scroll = ComponentLocator.of(InfiniteScroll.class).locate();
    var items = ComponentLocator.of(Div.class).byClassName("is-canvas").locate();

    UserInteraction.perform(scroll, InfiniteScrollInteraction.scroll());
    assertEquals(8, items.getComponents().size());

    UserInteraction.perform(scroll, InfiniteScrollInteraction.scroll());
    assertEquals(16, items.getComponents().size());
    assertFalse(scroll.isCompleted());
  }

  @Test
  void shouldCompleteAfterFortyEightItemsAndLoadNoMore(WebforjTestbed context) {
    context.add(new InfiniteScrollView());

    var scroll = ComponentLocator.of(InfiniteScroll.class).locate();
    var items = ComponentLocator.of(Div.class).byClassName("is-canvas").locate();

    for (int batch = 1; batch <= 7; batch++) {
      UserInteraction.perform(scroll, InfiniteScrollInteraction.scroll());
    }

    assertTrue(scroll.isCompleted());
    assertEquals(48, items.getComponents().size());
    assertThrows(
        InteractionRejectedException.class,
        () -> UserInteraction.perform(scroll, InfiniteScrollInteraction.scroll()));
  }
}
