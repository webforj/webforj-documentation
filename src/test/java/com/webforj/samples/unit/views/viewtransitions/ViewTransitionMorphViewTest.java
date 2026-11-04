package com.webforj.samples.unit.views.viewtransitions;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.html.elements.Div;
import com.webforj.samples.views.viewtransitions.ViewTransitionMorphView;
import com.webforj.samples.views.viewtransitions.components.BlogCard;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ViewTransitionMorphViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new ViewTransitionMorphView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldNameTheSharedImageForTheMorph(WebforjTestbed context) {
    var view = new ViewTransitionMorphView();
    context.add(view);

    var card = ComponentLocator.of(BlogCard.class).locate();

    assertEquals(
        "blog-image",
        ComponentLocator.of(Div.class).within(card).locate().getStyle("view-transition-name"));
  }
}
