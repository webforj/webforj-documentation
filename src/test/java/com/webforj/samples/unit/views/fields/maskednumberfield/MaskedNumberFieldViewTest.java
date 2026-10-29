package com.webforj.samples.unit.views.fields.maskednumberfield;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.field.MaskedNumberField;
import com.webforj.component.toast.Toast;
import com.webforj.samples.views.fields.maskednumberfield.MaskedNumberFieldView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.Locale;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WebforjTest
class MaskedNumberFieldViewTest {
  private Locale previous;

  @BeforeEach
  void setTheLocale() {
    previous = Locale.getDefault();
    Locale.setDefault(Locale.US);
  }

  @AfterEach
  void resetTheLocale() {
    Locale.setDefault(previous);
  }

  @Test
  void shouldShowTheTipAndTotalOfTheEnteredBillAndPercentage(WebforjTestbed context) {
    context.add(new MaskedNumberFieldView());

    var bill =
        ComponentLocator.of(MaskedNumberField.class)
            .filter(field -> field.getLabel().equals("Bill Amount"))
            .locate();
    var tip =
        ComponentLocator.of(MaskedNumberField.class)
            .filter(field -> field.getLabel().equals("Tip Percentage (%)"))
            .locate();

    UserInteraction.type(bill, "200");
    UserInteraction.type(tip, "20");
    UserInteraction.click(ComponentLocator.of(Button.class).byText("Calculate Tip").locate());

    assertEquals("Tip: $40.00 Total: $240.00", ComponentLocator.of(Toast.class).locate().getText());
  }

  @Test
  void shouldRejectNonPositiveAmounts(WebforjTestbed context) {
    context.add(new MaskedNumberFieldView());

    var bill =
        ComponentLocator.of(MaskedNumberField.class)
            .filter(field -> field.getLabel().equals("Bill Amount"))
            .locate();

    UserInteraction.type(bill, "0");
    UserInteraction.click(ComponentLocator.of(Button.class).byText("Calculate Tip").locate());

    assertEquals(
        "Please enter valid positive values.", ComponentLocator.of(Toast.class).locate().getText());
  }
}
