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
  private FlexLayout self = getBoundComponent();
  private TextField nameField;
  private TextField emailField;
  private Button submitButton;
  private BusyIndicator busyIndicator;

  public BusyDemoView() {
    nameField = new TextField("Name")
            .setWidth("500px");

    emailField = new TextField("Email")
            .setWidth("500px");

    submitButton = new Button("Submit", e -> showBusyIndicator())
            .setTheme(ButtonTheme.PRIMARY);

    FlexLayout form = FlexLayout.create(nameField, emailField, submitButton)
            .vertical()
            .build();

    self.setJustifyContent(FlexJustifyContent.CENTER)
            .setMargin("var(--dwc-space-l)")
            .add(form);

    busyIndicator = App.getBusyIndicator();
    showBusyIndicator();
  }

  private void showBusyIndicator() {
    busyIndicator.setText("Submitting form... Please wait.")
            .setBackdropVisible(true)
            .open()
            .getSpinner()
            .setTheme(Theme.PRIMARY);
  }
}

