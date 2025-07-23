package com.webforj.samples.views.fields.passwordfield;

import com.webforj.component.Composite;
import com.webforj.component.field.PasswordField;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Password Field Demo")
public class PasswordFieldView extends Composite<FlexLayout> {
  
  PasswordField passwordField = new PasswordField();

  public PasswordFieldView() {
    getBoundComponent().setMargin("var(--dwc-space-m)");

    passwordField.setWidth("200px")
        .setLabel("Enter your Password:")
        .setPlaceholder("Password");

    getBoundComponent().add(passwordField);
  }
}