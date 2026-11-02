package com.webforj.samples.unit.views.composite;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.event.KeypressEvent;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.samples.views.composite.CompositeView;
import com.webforj.samples.views.composite.CompositeView.SimpleTaskItem;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class CompositeViewTest {
  @Test
  void shouldAddTheTypedTaskWhenTheUserPressesEnter(WebforjTestbed context) {
    context.add(new CompositeView());

    var input = ComponentLocator.of(TextField.class).locate();

    UserInteraction.type(input, "New Task");
    UserInteraction.keypress(input, KeypressEvent.Key.ENTER);

    assertEquals(4, ComponentLocator.of(SimpleTaskItem.class).count());
    verifyThat(ComponentLocator.of(Div.class).byText("New Task").locate()).isVisible();
    assertEquals("", input.getText());
  }

  @Test
  void shouldIgnoreABlankTask(WebforjTestbed context) {
    context.add(new CompositeView());

    var input = ComponentLocator.of(TextField.class).locate();

    UserInteraction.type(input, " ");
    UserInteraction.keypress(input, KeypressEvent.Key.ENTER);

    assertEquals(3, ComponentLocator.of(SimpleTaskItem.class).count());
  }

  @Test
  void shouldHideATaskWhenTheUserDeletesIt(WebforjTestbed context) {
    context.add(new CompositeView());

    var row = ComponentLocator.of(SimpleTaskItem.class).all().get(0);

    UserInteraction.click(ComponentLocator.of(Button.class).within(row).byText("Delete").locate());

    verifyThat(row).isDestroyed();
    assertEquals(2, ComponentLocator.of(SimpleTaskItem.class).count());
  }

  @Test
  void shouldStrikeThroughATaskWhenTheUserTogglesIt(WebforjTestbed context) {
    context.add(new CompositeView());

    var row = ComponentLocator.of(SimpleTaskItem.class).all().get(0);
    var toggle = ComponentLocator.of(RadioButton.class).within(row).locate();
    var text =
        ComponentLocator.of(Div.class).within(row).byText("Review the documentation").locate();

    UserInteraction.check(toggle);
    assertEquals("line-through", text.getStyle("text-decoration"));

    UserInteraction.uncheck(toggle);
    assertEquals("none", text.getStyle("text-decoration"));
  }
}
