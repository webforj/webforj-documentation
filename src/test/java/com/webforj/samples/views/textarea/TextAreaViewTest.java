package com.webforj.samples.views.textarea;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextArea;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.toast.Toast;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class TextAreaViewTest {
  @Test
  void shouldCreateTheTextAreaFromALabelAndConfigureItThroughSetters(WebforjTestbed context) {
    context.add(new TextAreaView());

    var input = ComponentLocator.of(TextArea.class).locate();

    assertEquals("What do you think about this demo?", input.getLabel());
    assertEquals("Enter your feedback here...", input.getPlaceholder());
    assertEquals(200, input.getMaxLength());
    assertTrue(input.isLineWrap());
    assertEquals(TextArea.WrapStyle.WORD_BOUNDARIES, input.getWrapStyle());
  }

  @Test
  void shouldCountTheTypedCharactersAgainstTheLimit(WebforjTestbed context) {
    context.add(new TextAreaView());

    var input = ComponentLocator.of(TextArea.class).locate();
    var counter = ComponentLocator.of(Paragraph.class).locate();

    UserInteraction.type(input, "Hello World");

    assertEquals("Characters: 11 / 200", counter.getText());
  }

  @Test
  void shouldThankTheUserAndClearTheFeedbackOnSubmit(WebforjTestbed context) {
    context.add(new TextAreaView());

    var input = ComponentLocator.of(TextArea.class).locate();
    var counter = ComponentLocator.of(Paragraph.class).locate();

    UserInteraction.type(input, "Hello World");
    UserInteraction.click(ComponentLocator.of(Button.class).locate());

    assertEquals(
        "Thank you for your feedback!", ComponentLocator.of(Toast.class).locate().getText());
    assertTrue(input.getValue().isEmpty());
    assertEquals("Characters: 0 / 200", counter.getText());
  }

  @Test
  void shouldIgnoreBlankFeedback(WebforjTestbed context) {
    context.add(new TextAreaView());

    var input = ComponentLocator.of(TextArea.class).locate();

    UserInteraction.type(input, "   ");
    UserInteraction.click(ComponentLocator.of(Button.class).locate());

    assertTrue(ComponentLocator.of(Toast.class).all().isEmpty());
    assertEquals("   ", input.getValue());
  }
}
