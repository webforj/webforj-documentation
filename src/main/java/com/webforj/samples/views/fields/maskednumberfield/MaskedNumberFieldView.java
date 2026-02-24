package com.webforj.samples.views.fields.maskednumberfield;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.MaskedNumberField;
import com.webforj.component.icons.TablerIcon;
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
  private FlexLayout self = getBoundComponent();
  private MaskedNumberField billAmountField = new MaskedNumberField("Bill Amount");
  private MaskedNumberField tipPercentageField = new MaskedNumberField("Tip Percentage (%)");

  public MaskedNumberFieldView() {
    self.setDirection(FlexDirection.COLUMN)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setAlignment(FlexAlignment.CENTER)
        .setMargin("var(--dwc-space-m)");

    billAmountField
        .setMask("$######.##")
        .setValue(300d)
        .setWidth(250)
        .setPrefixComponent(TablerIcon.create("file-invoice"));
    tipPercentageField
        .setMask("###%")
        .setValue(15d)
        .setWidth(250)
        .setPrefixComponent(TablerIcon.create("circle-percentage"));

    Button calculateTipButton = new Button(
        "Calculate Tip",
        ButtonTheme.PRIMARY,
        event -> handleCalculation());
    calculateTipButton.setPrefixComponent(
        TablerIcon.create("calculator"));

    self.add(billAmountField, tipPercentageField, calculateTipButton);
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
        Toast.show("Please enter valid positive values.", Theme.DANGER);
        return;
      }

      double tipAmount = billAmount * (tipPercentage / 100);
      double totalAmount = billAmount + tipAmount;

      Toast.show("Tip: $" + String.format("%.2f", tipAmount)
          + "\nTotal: $" + String.format("%.2f", totalAmount), Theme.GRAY);
    } catch (NumberFormatException e) {
      Toast.show("Please enter valid numbers.", Theme.DANGER);
    }
  }
}
