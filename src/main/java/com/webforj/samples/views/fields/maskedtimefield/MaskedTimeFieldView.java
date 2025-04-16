package com.webforj.samples.views.fields.maskedtimefield;

import java.time.LocalTime;

import com.webforj.component.Composite;
import com.webforj.component.field.MaskedTimeField;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.Route;

@Route
public class MaskedTimeFieldView extends Composite<FlexLayout> {
    MaskedTimeField deliveryFrom = new MaskedTimeField("Delivery Window From");
    MaskedTimeField deliveryTo = new MaskedTimeField("To");

    public MaskedTimeFieldView() {
        FlexLayout self = getBoundComponent();
        self.setDirection(FlexDirection.COLUMN)
                .setJustifyContent(FlexJustifyContent.CENTER)
                .setAlignment(FlexAlignment.START)
                .setSpacing("var(--dwc-space-m)")
                .setMargin("var(--dwc-space-m)");
        deliveryFrom.setMask("%Hz:%mz"); 
        deliveryFrom.setValue(LocalTime.of(9, 0));
        deliveryTo.setMask("%Hz:%mz");
        deliveryTo.setValue(LocalTime.of(18, 0));
        self.add(deliveryFrom, deliveryTo);
    }
}