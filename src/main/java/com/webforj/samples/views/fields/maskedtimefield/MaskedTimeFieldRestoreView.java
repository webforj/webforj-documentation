package com.webforj.samples.views.fields.maskedtimefield;

import java.time.LocalTime;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.MaskedTimeField;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Masked Time Field Restore")
public class MaskedTimeFieldRestoreView extends Composite<FlexLayout> {
  FlexLayout self = getBoundComponent();
  MaskedTimeField eventField = new MaskedTimeField("Meeting Time:");

  public MaskedTimeFieldRestoreView() {
    self.setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setMargin("var(--dwc-space-m) auto");

    eventField
        .setMask("%hz:%mz %p")
        .setValue(LocalTime.of(15, 30)) // 3:30 PM
        .setRestoreValue(LocalTime.of(14, 0)) // 2:00 PM
        .setHelperText("Press <kbd>ESC</kbd> to restore the value to 2:00 PM.")
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
