package com.webforj.samples.views.loading;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.card.Card;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.loading.Loading;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class LoadingDemoViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new LoadingDemoView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldOverlayOnlyTheVideoLessonsCard(WebforjTestbed context) {
    context.add(new LoadingDemoView());

    var loading = ComponentLocator.of(Loading.class).locate();
    var lessons =
        ComponentLocator.of(Card.class)
            .has(ComponentLocator.of(Paragraph.class).byText("Video Lessons"))
            .locate();
    var guide =
        ComponentLocator.of(Card.class)
            .has(ComponentLocator.of(Paragraph.class).byText("User Guide"))
            .locate();

    assertTrue(loading.isVisible());
    verifyThat(lessons).hasComponent(loading);
    assertFalse(ComponentLocator.of(Loading.class).within(guide).isPresent());
  }
}
