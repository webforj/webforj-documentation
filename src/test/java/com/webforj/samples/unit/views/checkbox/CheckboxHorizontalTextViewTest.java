package com.webforj.samples.unit.views.checkbox;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.optioninput.CheckBox;
import com.webforj.concern.HasTextPosition.Position;
import com.webforj.samples.views.checkbox.CheckboxHorizontalTextView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.Collections;
import org.junit.jupiter.api.Test;

@WebforjTest
class CheckboxHorizontalTextViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new CheckboxHorizontalTextView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldShowTheTextRightByDefaultAndLeftWhenPositionedLeft(WebforjTestbed context) {
    context.add(new CheckboxHorizontalTextView());

    var boxes = ComponentLocator.of(CheckBox.class).all();

    assertEquals(
        Collections.nCopies(5, Position.RIGHT),
        boxes.subList(0, 5).stream().map(CheckBox::getTextPosition).toList());
    assertEquals(
        Collections.nCopies(5, Position.LEFT),
        boxes.subList(5, 10).stream().map(CheckBox::getTextPosition).toList());
  }
}
