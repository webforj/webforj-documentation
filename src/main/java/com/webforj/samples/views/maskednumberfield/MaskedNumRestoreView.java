package com.webforj.samples.views.maskednumberfield;

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
@FrameTitle("Restore Value Demo")
public class MaskedNumRestoreView extends Composite<FlexLayout> {

  MaskedNumberField budgetField = new MaskedNumberField("Project Budget ($)")
      .setMask("$###,###.##")
      .setValue(5000.00)
      .setRestoreValue(5000.00);

  public MaskedNumRestoreView() {
    FlexLayout layout = getBoundComponent();
    layout.setDirection(FlexDirection.COLUMN)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setAlignment(FlexAlignment.CENTER)
        .setSpacing("var(--dwc-space-m)")
        .setMargin("var(--dwc-space-m)");

    Button restoreButton = new Button("Restore Budget").setTheme(ButtonTheme.PRIMARY);
    restoreButton.onClick(event -> handleRestore());

    Button submitButton = new Button("Submit Budget").setTheme(ButtonTheme.PRIMARY);
    submitButton.onClick(event -> handleSubmit());

    FlexLayout buttonLayout = new FlexLayout()
        .setDirection(FlexDirection.ROW)
        .setSpacing("var(--dwc-space-s)")
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setAlignment(FlexAlignment.CENTER);

    buttonLayout.add(restoreButton, submitButton);

    layout.add(budgetField, buttonLayout);
  }

  private void handleRestore() {
    budgetField.restoreValue();
    Toast.show("Budget restored to: $" + String.format("%.2f", budgetField.getValue()))
        .setPlacement(Toast.Placement.CENTER);
  }

  private void handleSubmit() {
    Double budget = budgetField.getValue();
    if (budget == null || budget <= 0) {
      Toast.show("Please enter a valid budget.").setPlacement(Toast.Placement.CENTER);
    } else {
      Toast.show("Budget submitted: $" 
        + String.format("%.2f", budget)).setPlacement(Toast.Placement.CENTER);
    }
  }
}