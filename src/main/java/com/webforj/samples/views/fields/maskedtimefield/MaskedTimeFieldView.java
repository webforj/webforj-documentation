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
    MaskedTimeField meetingTimeField = new MaskedTimeField("Meeting Time");

    public MaskedTimeFieldView() {
        FlexLayout self = getBoundComponent();
        self.setDirection(FlexDirection.COLUMN)
                .setJustifyContent(FlexJustifyContent.CENTER)
                .setAlignment(FlexAlignment.CENTER)
                .setSpacing("var(--dwc-space-m)")
                .setMargin("var(--dwc-space-m)");
        meetingTimeField.setMask("%Hz:%mz");
        meetingTimeField.setValue(LocalTime.now());
        meetingTimeField.setMin(LocalTime.of(9, 0));
        meetingTimeField.setMax(LocalTime.of(17, 0));
        self.add(meetingTimeField);
    }
}
