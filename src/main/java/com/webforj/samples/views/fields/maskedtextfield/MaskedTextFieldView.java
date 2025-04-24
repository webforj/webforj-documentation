package com.webforj.samples.views.fields.maskedtextfield;

import com.webforj.component.Composite;
import com.webforj.component.field.MaskedTextField;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Masked Text Field")
public class MaskedTextFieldView extends Composite<FlexLayout> {

  MaskedTextField recordCode = new MaskedTextField("Record Code", "", "NE-24-0934");
  MaskedTextField couponCode = new MaskedTextField("Coupon Code", "", "ZZZZ-0000");

  public MaskedTextFieldView() {
    FlexLayout self = getBoundComponent();

    recordCode
        .setMask("AA-00-0000")
        .setValue("NE240934")
        .setHelperText("Mask: AA-00-0000 - for example: NE-24-0934")
        .setWidth(300);

    couponCode
        .setMask("ZZZZ-0000")
        .setValue("SAVE2025")
        .setHelperText("Mask: ZZZZ-0000 - for example: SAVE-2025")
        .setWidth(300);

    self.setDirection(FlexDirection.COLUMN)
        .setJustifyContent(FlexJustifyContent.START)
        .setAlignment(FlexAlignment.CENTER)
        .setSpacing("var(--dwc-space-m)")
        .setMargin("var(--dwc-space-m)");

    self.add(couponCode, recordCode);
  }
}
