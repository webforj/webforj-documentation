package com.webforj.samples.unit.views.textarea;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.act.exception.InteractionRejectedException;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.TextArea;
import com.webforj.samples.views.textarea.TextAreaStatesView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class TextAreaStatesViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new TextAreaStatesView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldLetTheUserFocusTheReadOnlyAreaButNotEditIt(WebforjTestbed context) {
    context.add(new TextAreaStatesView());

    var readOnly = ComponentLocator.of(TextArea.class).byLabel("Read-Only").locate();

    UserInteraction.focus(readOnly);
    verifyThat(readOnly).isFocused();

    assertThrows(InteractionRejectedException.class, () -> UserInteraction.type(readOnly, "New"));
    assertEquals("Value", readOnly.getValue());
  }

  @Test
  void shouldBlockFocusAndTypingOnTheDisabledArea(WebforjTestbed context) {
    context.add(new TextAreaStatesView());

    var disabled = ComponentLocator.of(TextArea.class).byLabel("Disabled").locate();

    assertThrows(InteractionRejectedException.class, () -> UserInteraction.focus(disabled));
    assertThrows(InteractionRejectedException.class, () -> UserInteraction.type(disabled, "New"));
    assertEquals("Value", disabled.getValue());
  }
}
