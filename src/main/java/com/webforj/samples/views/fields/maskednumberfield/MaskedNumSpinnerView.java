package com.webforj.samples.views.fields.maskednumberfield;

import com.webforj.component.Composite;
import com.webforj.component.field.MaskedNumberFieldSpinner;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Spinner Quantity Demo")
public class MaskedNumSpinnerView extends Composite<FlexLayout> {

  MaskedNumberFieldSpinner quantitySpinner = new MaskedNumberFieldSpinner("Item Quantity");


  public MaskedNumSpinnerView() {
    quantitySpinner.setValue(0.0); 
    quantitySpinner.setMin(1.0);   
    quantitySpinner.setMax(50.0); 
    quantitySpinner.setStep(10.0);  
    quantitySpinner.setTooltipText("Adjust quantity using the spinner.");

    FlexLayout layout = getBoundComponent();
    layout.setDirection(FlexDirection.COLUMN)
          .setJustifyContent(FlexJustifyContent.START)
          .setAlignment(FlexAlignment.START)
          .setSpacing("var(--dwc-space-m)")
          .setMargin("var(--dwc-space-m)");

    layout.add(quantitySpinner);
  }
}