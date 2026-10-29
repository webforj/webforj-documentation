package com.webforj.samples.unit.views.textarea;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.TextArea;
import com.webforj.component.list.ChoiceBox;
import com.webforj.samples.views.textarea.TextAreaWrapView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class TextAreaWrapViewTest {
  @Test
  void shouldWrapTheLinesAtCharacterBoundariesInitially(WebforjTestbed context) {
    context.add(new TextAreaWrapView());

    var input = ComponentLocator.of(TextArea.class).locate();

    assertTrue(input.isLineWrap());
    assertEquals(TextArea.WrapStyle.CHARACTER_BOUNDARIES, input.getWrapStyle());
  }

  @Test
  void shouldWrapTheLinesAtTheStyleTheUserPicks(WebforjTestbed context) {
    context.add(new TextAreaWrapView());

    var input = ComponentLocator.of(TextArea.class).locate();
    var styles = ComponentLocator.of(ChoiceBox.class).locate();

    UserInteraction.selectKey(styles, TextArea.WrapStyle.WORD_BOUNDARIES);
    assertEquals(TextArea.WrapStyle.WORD_BOUNDARIES, input.getWrapStyle());

    UserInteraction.selectKey(styles, TextArea.WrapStyle.CHARACTER_BOUNDARIES);
    assertEquals(TextArea.WrapStyle.CHARACTER_BOUNDARIES, input.getWrapStyle());
  }
}
