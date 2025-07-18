package com.webforj.samples.views.fields.maskeddatefield;

import java.time.LocalDate;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.MaskedDateField;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route("maskeddatefieldrestore")
@FrameTitle("Masked Date Field with Restore")
public class MaskedDateFieldRestoreView extends Composite<FlexLayout> {
  FlexLayout self = getBoundComponent();
  MaskedDateField eventField = new MaskedDateField("Meeting Date:");

  public MaskedDateFieldRestoreView() {
    self.setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setMargin("var(--dwc-space-m) auto");

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
        event -> eventField.restoreValue());

    self.add(eventField, restoreButton);
  }
}
