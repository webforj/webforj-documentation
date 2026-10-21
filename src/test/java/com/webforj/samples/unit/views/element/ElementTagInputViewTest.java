package com.webforj.samples.unit.views.element;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.element.Element;
import com.webforj.component.html.elements.Div;
import com.webforj.samples.views.element.ElementTagInputView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.Map;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;

@WebforjTest
class ElementTagInputViewTest {
  @Test
  void shouldAddTagsFromDeliveredPayloadsAndIgnoreBlankTags(WebforjTestbed context) {
    context.add(new ElementTagInputView());

    var input =
        ComponentLocator.of(Element.class).filter(e -> e.getNodeName().equals("input")).locate();

    UserInteraction.perform(input, enterTag("Java"));
    UserInteraction.perform(input, enterTag("webforJ"));
    UserInteraction.perform(input, enterTag(" "));

    assertEquals(1, ComponentLocator.of(Div.class).byText("Java").count());
    assertEquals(1, ComponentLocator.of(Div.class).byText("webforJ").count());
    assertEquals(0, ComponentLocator.of(Div.class).byText(" ").count());
  }

  private static Consumer<Element> enterTag(String tag) {
    return element -> UserInteraction.fireEvent(element, "keydown", Map.of("tag", tag));
  }
}
