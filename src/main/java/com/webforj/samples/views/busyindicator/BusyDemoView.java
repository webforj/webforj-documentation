package com.webforj.samples.views.busyindicator;

import com.webforj.App;
import com.webforj.BusyIndicator;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

/**
 * Demo to show BusyIndicator basics.
 */
@Route
@FrameTitle("Busy Basics")
public class BusyDemoView extends Composite<FlexLayout> {

  TextField nameField;
  TextField emailField;
  Button submitButton;
  BusyIndicator busyIndicator;

  public BusyDemoView() {
    getBoundComponent().setJustifyContent(FlexJustifyContent.CENTER)
        .setMargin("var(--dwc-space-l)");
    
    nameField = new TextField("Name").setWidth("500px");
    
    emailField = new TextField("Email").setWidth("500px");
    
    submitButton = new Button("Submit", e -> showBusyIndicator())
        .setTheme(ButtonTheme.PRIMARY);
    
    busyIndicator = App.getBusyIndicator();

    FlexLayout form = FlexLayout.create(nameField, emailField, submitButton)
        .vertical()
        .build();
   
    getBoundComponent().add(form);

    showBusyIndicator();
  }

  private void showBusyIndicator() {
    busyIndicator.setText("Submitting form... Please wait.")
        .setBackdropVisible(true);
    busyIndicator.getSpinner().setTheme(Theme.PRIMARY);
    busyIndicator.open();
  }
}

