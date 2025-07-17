package com.webforj.samples.views.fields.maskedtimefield;

import java.time.LocalTime;

import com.webforj.component.Composite;
import com.webforj.component.field.MaskedTimeField;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route("maskedtimefield")
@FrameTitle("Masked Time Field")
public class MaskedTimeFieldView extends Composite<FlexLayout> {
  FlexLayout self = getBoundComponent();
  MaskedTimeField field = new MaskedTimeField("Meeting Time");

  public MaskedTimeFieldView() {
    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setMargin("var(--dwc-space-m)");

    field
        .setMask("%h:%mz %p")
        .setValue(LocalTime.now())
        .setMaxWidth("300px")
        .setHelperText("Meeting time is formatted as %h:%mz %p.")
        .getPicker()
        .setIconVisible(false);

    self.add(field);
  }
}
