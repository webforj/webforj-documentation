package com.webforj.samples.views.checkbox;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.Expanse;
import com.webforj.component.optioninput.CheckBox;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class CheckboxExpanseViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new CheckboxExpanseView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldShowOneCheckBoxPerExpanseFromNoneToXlarge(WebforjTestbed context) {
    context.add(new CheckboxExpanseView());

    var boxes = ComponentLocator.of(CheckBox.class).all();

    assertEquals(
        List.of(
            Expanse.NONE,
            Expanse.XSMALL,
            Expanse.SMALL,
            Expanse.MEDIUM,
            Expanse.LARGE,
            Expanse.XLARGE),
        boxes.stream().map(CheckBox::getExpanse).toList());
  }
}
