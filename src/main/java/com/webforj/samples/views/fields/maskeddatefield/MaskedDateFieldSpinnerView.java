package com.webforj.samples.views.fields.maskeddatefield;

import java.time.LocalDate;

import com.webforj.component.Composite;
import com.webforj.component.field.MaskedDateFieldSpinner;
import com.webforj.component.field.MaskedDateFieldSpinner.SpinField;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Masked Date Field Spinner")
public class MaskedDateFieldSpinnerView extends Composite<FlexLayout> {
  MaskedDateFieldSpinner appointmentField = new MaskedDateFieldSpinner("Available Appointments");

  public MaskedDateFieldSpinnerView() {
    FlexLayout self = getBoundComponent();
    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setSpacing("var(--dwc-space-m)")
        .setMargin("var(--dwc-space-m)");

    appointmentField
        .setSpinField(SpinField.DAY)
        .setMask("%Dz/%Mz/%Yl")
        .setValue(LocalDate.now())
        .setMin(LocalDate.now())
        .setMax(LocalDate.now().plusMonths(6))
        .setHelperText("<b>Min:</b> today, <b>Max:</b> 6 months from now. Use the spinner to select a date.")
        .setPlaceholder("DD/MM/YYYY")
        .setAllowCustomValue(false);

    self.add(appointmentField);

    whenAttached().thenAccept(c -> appointmentField.getPicker().open());
  }
}
