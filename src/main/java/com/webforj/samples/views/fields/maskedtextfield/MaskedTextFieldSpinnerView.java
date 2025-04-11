package com.webforj.samples.views.fields.maskedtextfield;

import java.util.List;

import com.webforj.component.Composite;
import com.webforj.component.field.MaskedTextFieldSpinner;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.Route;

@Route
public class MaskedTextFieldSpinnerView extends Composite<FlexLayout> {

  MaskedTextFieldSpinner prioritySpinner = new MaskedTextFieldSpinner("Current Priority:");

  public MaskedTextFieldSpinnerView() {
    FlexLayout self = getBoundComponent();
    self.setDirection(FlexDirection.COLUMN)
        .setJustifyContent(FlexJustifyContent.START)
        .setAlignment(FlexAlignment.START)
        .setSpacing("var(--dwc-space-m)")
        .setMargin("var(--dwc-space-m)");

    prioritySpinner.setTooltipText("Priority:");
    prioritySpinner.setOptions(List.of("Low", "Normal", "High", "Critical"));

    prioritySpinner.setValue("Normal");

    self.add(prioritySpinner);
  }
}
