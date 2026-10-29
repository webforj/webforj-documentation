package com.webforj.samples.unit.views.fields.numberfield;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.NumberField;
import com.webforj.samples.views.fields.numberfield.NumberFieldView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class NumberFieldViewTest {
  @Test
  void shouldPromptForTheQuantityWithALabelAndPlaceholder(WebforjTestbed context) {
    context.add(new NumberFieldView());

    var quantity = ComponentLocator.of(NumberField.class).locate();

    verifyThat(quantity).hasLabel("Quantity:").hasPlaceholder("Enter a number...");
  }

  @Test
  void shouldHoldTheTypedQuantityAsANumber(WebforjTestbed context) {
    context.add(new NumberFieldView());

    var quantity = ComponentLocator.of(NumberField.class).locate();

    UserInteraction.type(quantity, "1234567890");

    verifyThat(quantity).hasValue(1234567890.0);
  }
}
