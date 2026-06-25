package com.webforj.samples.views.fields.numberfield;

import com.webforj.component.Composite;
import com.webforj.component.field.NumberField;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Number Field Demo")
public class NumberFieldView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final NumberField numField = new NumberField("Quantity:");

  public NumberFieldView() {
    self.setMargin("var(--dwc-space-m)").add(numField);

    numField.setWidth("200px").setPlaceholder("Enter a number...");
  }
}
