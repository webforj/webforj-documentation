package com.webforj.samples.views.fields.maskeddatefield;

import java.time.LocalDate;

import com.webforj.component.Composite;
import com.webforj.component.field.MaskedDateFieldSpinner;
import com.webforj.component.field.MaskedDateFieldSpinner.SpinField;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.Route;

@Route
public class MaskedDateFieldSpinnerView extends Composite<FlexLayout> {
    MaskedDateFieldSpinner appointmentField = new MaskedDateFieldSpinner("Available Appointments");

    public MaskedDateFieldSpinnerView() {
        FlexLayout self = getBoundComponent();
        self.setDirection(FlexDirection.COLUMN)
                .setJustifyContent(FlexJustifyContent.START)
                .setAlignment(FlexAlignment.START)
                .setSpacing("var(--dwc-space-m)")
                .setMargin("var(--dwc-space-m)");
        appointmentField.setMask("%Mz/%Dz/%Yz");
        appointmentField.setValue(LocalDate.now());
        appointmentField.setMin(LocalDate.now());
        appointmentField.setMax(LocalDate.now().plusMonths(6));
        appointmentField.setSpinField(SpinField.DAY);
        self.add(appointmentField);
    }
}