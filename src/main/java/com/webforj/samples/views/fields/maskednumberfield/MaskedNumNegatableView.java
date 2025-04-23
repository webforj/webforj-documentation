package com.webforj.samples.views.fields.maskednumberfield;

import com.webforj.component.Composite;
import com.webforj.component.field.MaskedNumberField;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Masked Number Field with Negateable Option")
public class MaskedNumNegatableView extends Composite<FlexLayout> {
  FlexLayout self = getBoundComponent();
  MaskedNumberField field = new MaskedNumberField("Credits");
  RadioButton negateable = RadioButton.Switch("Negateable", true);

  public MaskedNumNegatableView() {
    self.setDirection(FlexDirection.COLUMN)
        .setJustifyContent(FlexJustifyContent.START)
        .setMaxWidth(300)
        .setMargin("var(--dwc-space-m) auto");

    field.setMask("-$###,###,##0.00")
        .setNegateable(true)
        .setValue(123d);

    negateable.onToggle(event -> {
      field.setNegateable(event.isToggled());
    });

    self.add(field, negateable);
  }
}
