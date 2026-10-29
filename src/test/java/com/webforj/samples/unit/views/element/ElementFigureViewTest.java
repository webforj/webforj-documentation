package com.webforj.samples.unit.views.element;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.element.Element;
import com.webforj.samples.views.element.ElementFigureView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ElementFigureViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new ElementFigureView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldAttributeTheQuoteWithMarkup(WebforjTestbed context) {
    context.add(new ElementFigureView());

    assertEquals(
        "<strong>Dana Lee</strong>, Engineering Lead",
        ComponentLocator.of(Element.class)
            .filter(e -> e.getNodeName().equals("figcaption"))
            .locate()
            .getHtml());
  }
}
