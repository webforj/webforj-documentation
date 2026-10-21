package com.webforj.samples.views.radiobutton;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.concern.HasTextPosition;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@WebforjTest
class RadioButtonTextViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new RadioButtonTextView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @ParameterizedTest
  @CsvSource({"'Right aligned (default)', RIGHT", "Left aligned, LEFT"})
  void shouldPlaceTheTextRightByDefaultAndLeftWhenAsked(
      String text, HasTextPosition.Position position, WebforjTestbed context) {
    context.add(new RadioButtonTextView());

    var button = ComponentLocator.of(RadioButton.class).byText(text).locate();

    assertEquals(position, button.getTextPosition());
  }
}
