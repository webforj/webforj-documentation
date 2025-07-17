package com.webforj.samples.views.fields.maskednumberfield;

import com.webforj.component.Composite;
import com.webforj.component.field.MaskedNumberFieldSpinner;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route("maskednumberfieldspinner")
@FrameTitle("Masked Number Field Spinner")
public class MaskedNumSpinnerView extends Composite<FlexLayout> {
  FlexLayout self = getBoundComponent();
  MaskedNumberFieldSpinner field = new MaskedNumberFieldSpinner("Tip Percentage (%)");

  public MaskedNumSpinnerView() {
    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setSpacing("var(--dwc-space-m)")
        .setMargin("var(--dwc-space-m)");

    field
        .setStep(5d)
        .setValue(15d)
        .setWidth(250)
        .setMin(0d)
        .setMax(100d)
        .setMask("###%")
        .setHelperText("<b>Min:</b> 0% <b>Max:</b> 100%")
        .setMaxWidth("300px");

    self.add(field);
  }
}
