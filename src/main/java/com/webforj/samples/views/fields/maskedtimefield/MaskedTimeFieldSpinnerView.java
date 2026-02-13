package com.webforj.samples.views.fields.maskedtimefield;

import java.time.LocalTime;

import com.webforj.component.Composite;
import com.webforj.component.field.MaskedTimeFieldSpinner;
import com.webforj.component.field.MaskedTimeFieldSpinner.SpinField;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Masked Time Field Spinner")
public class MaskedTimeFieldSpinnerView extends Composite<FlexLayout> {
  FlexLayout self = getBoundComponent();
  MaskedTimeFieldSpinner appointmentField = new MaskedTimeFieldSpinner("Available Time Slots");

  public MaskedTimeFieldSpinnerView() {
    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setSpacing("var(--dwc-space-m)")
        .setMargin("var(--dwc-space-m)");

    appointmentField
        .setSpinField(SpinField.MINUTE)
        .setMask("%hz:%mz %p")
        .setValue(LocalTime.of(9, 0))
        .setMin(LocalTime.of(9, 0))
        .setMax(LocalTime.of(17, 0))
        .setHelperText("<b>Min:</b> 09:00 AM, <b>Max:</b> 05:00 PM. Use the spinner to select a time.")
        .setPlaceholder("hh:mm AM/PM")
        .setAllowCustomValue(false);

    self.add(appointmentField);

    whenAttached().thenAccept(c -> appointmentField.getPicker().open());
  }
}
