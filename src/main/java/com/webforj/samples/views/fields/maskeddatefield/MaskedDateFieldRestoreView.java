package com.webforj.samples.views.fields.maskeddatefield;

import java.time.LocalDate;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.MaskedDateField;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.Route;

@Route
public class MaskedDateFieldRestoreView extends Composite<FlexLayout> {
  MaskedDateField eventField = new MaskedDateField("Meeting Date:");

  public MaskedDateFieldRestoreView() {
    FlexLayout self = getBoundComponent();
    eventField.setRestoreValue(LocalDate.now());
    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setSpacing("var(--dwc-space-m)")
        .setMargin("var(--dwc-space-m)");

    eventField
        .setMask("%Yl-%Mz-%Dz")
        .setValue(LocalDate.now())
        .setRestoreValue(LocalDate.now().minusDays(1))
        .setHelperText("Press <kbd>ESC</kbd> to restore the value to yesterday.")
        .setMaxWidth("300px")
        .getPicker()
        .setIconVisible(false);

    Button restoreButton = new Button(
      "Reset value",
      ButtonTheme.PRIMARY,
      event -> eventField.restoreValue()
    );
    self.add(eventField, restoreButton);
  }
}
