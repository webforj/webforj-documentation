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
@FrameTitle("Temperature Input")
public class MaskedNumNegatableView extends Composite<FlexLayout> {

  MaskedNumberField temperatureField = new MaskedNumberField("Temperature (°C)")
      .setMask("###°")
      .setNegateable(true);

  public MaskedNumNegatableView() {
    FlexLayout layout = getBoundComponent();
    layout.setDirection(FlexDirection.COLUMN)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setAlignment(FlexAlignment.CENTER)
        .setSpacing("var(--dwc-space-m)")
        .setMargin("var(--dwc-space-m)");

    Button submitButton = new Button("Submit").setTheme(ButtonTheme.PRIMARY);
    submitButton.onClick(event -> handleTemperatureInput());

    layout.add(temperatureField, submitButton);
  }

  private void handleTemperatureInput() {
    try {
      Integer temperature = temperatureField.getValue() != null 
          ? temperatureField.getValue().intValue() : null;

      if (temperature == null) {
        Toast.show("Please enter a valid temperature.")
            .setPlacement(Toast.Placement.CENTER);
        return;
      }

      Toast.show("Temperature: " + temperature + "°C");
    } catch (NumberFormatException e) {
      Toast.show("Invalid input. Please enter a valid number.")
          .setPlacement(Toast.Placement.CENTER);
    }
  }
}