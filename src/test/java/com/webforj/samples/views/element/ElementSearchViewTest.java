package com.webforj.samples.views.element;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.element.Element;
import com.webforj.component.element.event.ElementDefinedEvent;
import com.webforj.component.toast.Toast;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@WebforjTest
class ElementSearchViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new ElementSearchView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldConfirmOnceTheSearchFieldTookTheFocus(WebforjTestbed context) {
    context.add(new ElementSearchView());

    var search =
        ComponentLocator.of(Element.class).filter(e -> e.getNodeName().equals("input")).locate();

    UserInteraction.click(ComponentLocator.of(Button.class).byText("Focus search").locate());
    assertFalse(ComponentLocator.of(Toast.class).isPresent());

    UserInteraction.fireEvent(new ElementDefinedEvent(search, Map.of()));
    context.answerExecuteJsAsync(null);

    assertEquals("Search focused", ComponentLocator.of(Toast.class).locate().getText());
  }
}
