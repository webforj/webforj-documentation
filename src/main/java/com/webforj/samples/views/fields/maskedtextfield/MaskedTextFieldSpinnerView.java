package com.webforj.samples.views.fields.maskedtextfield;

import java.util.List;

import com.webforj.component.Composite;
import com.webforj.component.field.MaskedTextFieldSpinner;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route("maskedtextfieldspinner")
@FrameTitle("Masked Text Field Spinner")
public class MaskedTextFieldSpinnerView extends Composite<FlexLayout> {

  private final MaskedTextFieldSpinner field = new MaskedTextFieldSpinner("Project Code:");

  public MaskedTextFieldSpinnerView() {
    FlexLayout layout = getBoundComponent();
    layout.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setMargin("var(--dwc-space-m)");

    field.setOptions(List.of(
        "PRJ001", "PRJ002", "PRJ003", "PRJ004"));
    field
        .setMask("AAA-000")
        .setValue("PRJ-002")
        .setHelperText("Select or spin through project codes");

    layout.add(field);
  }
}
