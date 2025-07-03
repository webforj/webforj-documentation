package com.webforj.samples.views.fields.maskednumberfield;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.MaskedNumberField;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.config.RouteConfig;

@Route(RouteConfig.MASKED_NUMBER_FIELD_RESTORE)
@FrameTitle("Masked Number Field with Restore Value")
public class MaskedNumRestoreView extends Composite<FlexLayout> {
  FlexLayout self = getBoundComponent();
  MaskedNumberField field = new MaskedNumberField("Project Budget:");

  public MaskedNumRestoreView() {
    self.setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setMargin("var(--dwc-space-m) auto");

    field
        .setMask("$###,###,##0.00")
        .setValue(1234567d)
        .setRestoreValue(1234567d)
        .setRestoreValue(1234567d)
        .setHelperText("Press <kbd>ESC</kbd> to restore the value to default.")
        .setMaxWidth("300px");

    Button restoreButton = new Button(
        "Reset value",
        ButtonTheme.PRIMARY,
        event -> field.restoreValue());

    self.add(field, restoreButton);
  }
}
