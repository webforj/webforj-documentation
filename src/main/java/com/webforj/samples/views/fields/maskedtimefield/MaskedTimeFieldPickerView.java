package com.webforj.samples.views.fields.maskedtimefield;

import java.time.LocalTime;

import com.webforj.component.Composite;
import com.webforj.component.field.MaskedTimeField;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route("maskedtimefieldpicker")
@FrameTitle("Masked Time Field Picker")
public class MaskedTimeFieldPickerView extends Composite<FlexLayout> {
  FlexLayout self = getBoundComponent();
  MaskedTimeField field = new MaskedTimeField("Meeting Time");

  public MaskedTimeFieldPickerView() {
    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setMargin("var(--dwc-space-m)");

    field
        .setMask("%hz:%mz %p")
        .setValue(LocalTime.of(9, 30)) // 9:30 AM
        .setMaxWidth("300px")
        .setHelperText("Click the icon to open the time picker.")
        .setAllowCustomValue(false)
        .getPicker()
        .setIconVisible(true)
        .setAutoOpen(true);

    self.add(field);
    whenAttached().thenAccept(c -> field.getPicker().open());
  }
}
