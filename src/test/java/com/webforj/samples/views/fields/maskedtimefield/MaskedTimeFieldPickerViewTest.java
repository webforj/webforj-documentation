package com.webforj.samples.views.fields.maskedtimefield;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.MaskedTimeField;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class MaskedTimeFieldPickerViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new MaskedTimeFieldPickerView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldTakeTheTimeOnlyThroughThePicker(WebforjTestbed context) {
    context.add(new MaskedTimeFieldPickerView());

    var field = ComponentLocator.of(MaskedTimeField.class).locate();
    var picker = field.getPicker();

    assertFalse(field.isAllowCustomValue());
    assertTrue(picker.isIconVisible());
    assertTrue(picker.isAutoOpen());
  }
}
