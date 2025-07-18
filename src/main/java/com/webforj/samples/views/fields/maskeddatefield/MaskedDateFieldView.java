package com.webforj.samples.views.fields.maskeddatefield;

import java.time.LocalDate;

import com.webforj.component.Composite;
import com.webforj.component.field.MaskedDateField;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route("maskeddatefield")
@FrameTitle("Masked Date Field")
public class MaskedDateFieldView extends Composite<FlexLayout> {
  FlexLayout self = getBoundComponent();
  MaskedDateField field = new MaskedDateField("Meeting Date");

  public MaskedDateFieldView() {
    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setMargin("var(--dwc-space-m)");

    field
        .setMask("%Mz/%Dz/%Yz")
        .setValue(LocalDate.now())
        .setMaxWidth("300px")
        .setHelperText("Meeting Date is formatted as %Mz/%Dz/%Yz.")
        .getPicker()
        .setIconVisible(false);

    self.add(field);
  }
}
