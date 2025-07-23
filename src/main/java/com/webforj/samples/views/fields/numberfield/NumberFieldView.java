package com.webforj.samples.views.fields.numberfield;

import com.webforj.component.Composite;
import com.webforj.component.field.NumberField;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Number Field Demo")
public class NumberFieldView extends Composite<FlexLayout> {
  
  NumberField numField = new NumberField("Quantity:");

  public NumberFieldView() {
    getBoundComponent().setMargin("var(--dwc-space-m)");

    numField.setWidth("200px")
            .setPlaceholder("Enter a number...");

    getBoundComponent().add(numField);
  }
}