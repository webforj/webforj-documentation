package com.webforj.samples.views.fields.maskedtextfield;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.MaskedTextField;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.Route;

@Route
public class MaskedTextFieldRestoreView extends Composite<FlexLayout> {
  FlexLayout self = getBoundComponent();

  public MaskedTextFieldRestoreView() {
    self.setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setMargin("var(--dwc-space-m) auto");

    MaskedTextField usernameField = new MaskedTextField(
        "Postal Code", "85001 PHX", "ex: 85001 PHX");
    usernameField
        .setWidth(250)
        .setRestoreValue("85001 PHX")
        .setHelperText("Enter ZIP Code in format: 85001 PHX")
        .setPattern("[0-9]{5} [A-Z]{3}");

    Button restoreButton = new Button(
        "Restore",
        ButtonTheme.PRIMARY,
        event -> usernameField.restoreValue());

    self.add(usernameField, restoreButton);
  }
}
