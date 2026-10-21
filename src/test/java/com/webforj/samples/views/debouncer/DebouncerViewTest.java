package com.webforj.samples.views.debouncer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.TextArea;
import com.webforj.component.field.TextField;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class DebouncerViewTest {
  @Test
  void shouldPublishOnlyTheLatestInputWhenTheTimerRuns(WebforjTestbed context) {
    context.add(new DebouncerView());

    var input = ComponentLocator.of(TextField.class).locate();
    var output = ComponentLocator.of(TextArea.class).locate();

    UserInteraction.type(input, "First");
    UserInteraction.type(input, "Latest");

    assertEquals("Key events: 2", input.getHelperText());
    assertTrue(output.getValue().isEmpty());

    assertEquals(1, context.runIntervals());
    assertEquals("Latest", output.getValue());
    assertEquals("Key events: 0", input.getHelperText());

    assertEquals(0, context.runIntervals());
    assertTrue(output.isReadOnly());
  }
}
