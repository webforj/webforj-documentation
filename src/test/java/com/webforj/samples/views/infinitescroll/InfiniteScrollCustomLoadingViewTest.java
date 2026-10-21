package com.webforj.samples.views.infinitescroll;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.act.component.infinitescroll.InfiniteScrollInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Span;
import com.webforj.component.icons.Icon;
import com.webforj.component.infinitescroll.InfiniteScroll;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class InfiniteScrollCustomLoadingViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new InfiniteScrollCustomLoadingView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldReplaceTheDefaultIndicatorWithTheCloudAndItsText(WebforjTestbed context) {
    context.add(new InfiniteScrollCustomLoadingView());

    var scroll = ComponentLocator.of(InfiniteScroll.class).locate();
    var indicator =
        ComponentLocator.of(Div.class).within(scroll).byClassName("custom-loading").locate();

    verifyThat(scroll).hasComponent(indicator);
    assertTrue(
        ComponentLocator.of(Icon.class)
            .within(indicator)
            .filter(icon -> "cloud".equals(icon.getName()))
            .isPresent());
    assertTrue(
        ComponentLocator.of(Span.class)
            .within(indicator)
            .byText("Loading awesome content...")
            .isPresent());
  }

  @Test
  void shouldLoadEightMoreItemsWhenTheUserReachesTheEnd(WebforjTestbed context) {
    context.add(new InfiniteScrollCustomLoadingView());

    var scroll = ComponentLocator.of(InfiniteScroll.class).locate();
    var items = ComponentLocator.of(Div.class).byClassName("is-canvas").locate();

    UserInteraction.perform(scroll, InfiniteScrollInteraction.scroll());

    assertEquals(8, items.getComponents().size());
    assertFalse(scroll.isCompleted());
  }
}
