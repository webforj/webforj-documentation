package com.webforj.samples.views.fields.maskednumberfield;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.MaskedNumberFieldSpinner;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class MaskedNumSpinnerViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new MaskedNumSpinnerView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldStepTheTipByFivePercent(WebforjTestbed context) {
    context.add(new MaskedNumSpinnerView());

    var field = ComponentLocator.of(MaskedNumberFieldSpinner.class).locate();

    field.spinUp();
    assertEquals(20d, field.getValue());

    field.spinDown();
    assertEquals(15d, field.getValue());
  }

  @Test
  void shouldStopTheTipAtTheMinimumOfZero(WebforjTestbed context) {
    context.add(new MaskedNumSpinnerView());

    var field = ComponentLocator.of(MaskedNumberFieldSpinner.class).locate();

    field.spinDown().spinDown().spinDown().spinDown();

    assertEquals(0d, field.getValue());
  }
}
