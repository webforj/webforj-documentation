package com.webforj.samples.views.fields.maskedtimefield;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.MaskedTimeFieldSpinner;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

@WebforjTest
class MaskedTimeFieldSpinnerViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new MaskedTimeFieldSpinnerView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldStepTheTimeSlotByOneMinute(WebforjTestbed context) {
    context.add(new MaskedTimeFieldSpinnerView());

    var field = ComponentLocator.of(MaskedTimeFieldSpinner.class).locate();

    field.spinUp();
    assertEquals(LocalTime.of(9, 1), field.getValue());

    field.spinDown();
    assertEquals(LocalTime.of(9, 0), field.getValue());
  }
}
