package com.webforj.samples.views.maskednumberfield;

import com.webforj.component.Composite;
import com.webforj.component.field.MaskedNumberField;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Age Input")
public class MaskedNumMinMaxView extends Composite<FlexLayout> {

  MaskedNumberField ageField = new MaskedNumberField("Enter Your Age")
            .setMask("###")
            .setMin(18.0) 
            .setMax(120.0); 

  public MaskedNumMinMaxView() {
    FlexLayout layout = getBoundComponent();
    layout.setDirection(FlexDirection.COLUMN)
          .setJustifyContent(FlexJustifyContent.CENTER)
          .setAlignment(FlexAlignment.CENTER)
          .setSpacing("var(--dwc-space-m)")
          .setMargin("var(--dwc-space-m)");

    layout.add(ageField);
  }
}