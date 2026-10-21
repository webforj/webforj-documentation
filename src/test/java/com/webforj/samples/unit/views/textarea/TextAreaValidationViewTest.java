package com.webforj.samples.unit.views.textarea;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.NumberField;
import com.webforj.component.field.TextArea;
import com.webforj.samples.views.textarea.TextAreaValidationView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@WebforjTest
class TextAreaValidationViewTest {
  @Test
  void shouldApplyTheLimitsTheUserEntersToTheTextArea(WebforjTestbed context) {
    context.add(new TextAreaValidationView());

    var input = ComponentLocator.of(TextArea.class).byLabel("Validation Playground").locate();

    UserInteraction.type(
        ComponentLocator.of(NumberField.class).byLabel("Max Length").locate(), "100");
    UserInteraction.type(ComponentLocator.of(NumberField.class).byLabel("Max Lines").locate(), "4");
    UserInteraction.type(
        ComponentLocator.of(NumberField.class).byLabel("Max Paragraph Length").locate(), "30");

    assertEquals(100, input.getMaxLength());
    assertEquals(4, input.getLineCountLimit());
    assertEquals(30, input.getParagraphLengthLimit());
  }

  @ParameterizedTest
  @ValueSource(strings = {"0", "501"})
  void shouldKeepTheMaxLengthWhenTheUserEntersAValueOutOfRange(
      String value, WebforjTestbed context) {
    context.add(new TextAreaValidationView());

    var input = ComponentLocator.of(TextArea.class).byLabel("Validation Playground").locate();

    UserInteraction.type(
        ComponentLocator.of(NumberField.class).byLabel("Max Length").locate(), value);

    assertEquals(256, input.getMaxLength());
  }

  @Test
  void shouldReportTheStatusOfTheInitialText(WebforjTestbed context) {
    context.add(new TextAreaValidationView());

    var status = ComponentLocator.of(TextArea.class).byLabel("Current Status").locate();

    assertEquals(
        """
        Current Length (including \\n): 44
        Current Line Count: 1
        Paragraph Sizes: [0=44]
        """,
        status.getValue());
  }

  @Test
  void shouldReportTheLengthLinesAndParagraphSizesOfTheTypedText(WebforjTestbed context) {
    context.add(new TextAreaValidationView());

    var input = ComponentLocator.of(TextArea.class).byLabel("Validation Playground").locate();
    var status = ComponentLocator.of(TextArea.class).byLabel("Current Status").locate();

    UserInteraction.type(input, "Hello\nWorld!");

    assertEquals(
        """
        Current Length (including \\n): 12
        Current Line Count: 2
        Paragraph Sizes: [0=5][1=6]
        """,
        status.getValue());
  }
}
