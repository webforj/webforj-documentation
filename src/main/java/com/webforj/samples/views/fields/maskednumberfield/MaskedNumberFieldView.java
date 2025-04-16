package com.webforj.samples.views.fields.maskednumberfield;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.MaskedNumberField;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.toast.Toast;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Tip Calculator")
public class MaskedNumberFieldView extends Composite<FlexLayout> {

  MaskedNumberField billAmountField = new MaskedNumberField("Bill Amount")
      .setMask("$######.##");

  MaskedNumberField tipPercentageField = new MaskedNumberField("Tip Percentage (%)")
      .setMask("###%");

  public MaskedNumberFieldView() {
    FlexLayout layout = getBoundComponent();
    layout.setDirection(FlexDirection.COLUMN)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setAlignment(FlexAlignment.START)
        .setSpacing("var(--dwc-space-m)")
        .setMargin("var(--dwc-space-m)");

    FlexLayout fieldsLayout = new FlexLayout()
        .setDirection(FlexDirection.ROW)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setAlignment(FlexAlignment.START)
        .setSpacing("var(--dwc-space-m)");
   
    fieldsLayout.add(billAmountField, tipPercentageField);

    Button calculateTipButton = new Button("Calculate Tip").setTheme(ButtonTheme.PRIMARY);
    calculateTipButton.onClick(event -> handleCalculation());

    layout.add(fieldsLayout, calculateTipButton);
  }

  private void handleCalculation() {
    try {
      double billAmount = 0.0;
      if (billAmountField.getValue() != null) {
        billAmount = billAmountField.getValue();
      }

      double tipPercentage = 0.0;
      if (tipPercentageField.getValue() != null) {
        tipPercentage = tipPercentageField.getValue();
      }

      if (billAmount <= 0 || tipPercentage <= 0) {
        Toast.show("Please enter valid positive values.");
        return;
      }

      double tipAmount = billAmount * (tipPercentage / 100);
      double totalAmount = billAmount + tipAmount;

      Toast.show("Tip: $" + String.format("%.2f", tipAmount) 
        + "\nTotal: $" + String.format("%.2f", totalAmount));
    } catch (NumberFormatException e) {
      Toast.show("Please enter valid numbers.");
    }
  }
}