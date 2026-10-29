package com.webforj.samples.unit.views.fields.maskedtextfield;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.MaskedTextFieldSpinner;
import com.webforj.samples.views.fields.maskedtextfield.MaskedTextFieldSpinnerView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class MaskedTextFieldSpinnerViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new MaskedTextFieldSpinnerView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldMoveThroughTheProjectCodesWhenSpinning(WebforjTestbed context) {
    context.add(new MaskedTextFieldSpinnerView());

    var field = ComponentLocator.of(MaskedTextFieldSpinner.class).locate();

    field.spinUp().spinUp();
    assertEquals("PRJ003", field.getOptions().get(field.getOptionIndex()));

    field.spinDown();
    assertEquals("PRJ002", field.getOptions().get(field.getOptionIndex()));
  }
}
