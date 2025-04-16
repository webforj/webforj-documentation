package com.webforj.samples.views.fields.maskedtimefield;

import java.time.LocalTime;

import com.webforj.component.Composite;
import com.webforj.component.field.MaskedTimeFieldSpinner;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.Route;

@Route
public class MaskedTimeFieldSpinnerView extends Composite<FlexLayout> {
    MaskedTimeFieldSpinner checkInTime = new MaskedTimeFieldSpinner("Check-In Time");

    public MaskedTimeFieldSpinnerView() {
        FlexLayout self = getBoundComponent();
        self.setDirection(FlexDirection.COLUMN)
                .setJustifyContent(FlexJustifyContent.START)
                .setAlignment(FlexAlignment.START)
                .setSpacing("var(--dwc-space-m)")
                .setMargin("var(--dwc-space-m)");
        
        MaskedTimeFieldSpinner checkInTime = new MaskedTimeFieldSpinner("Check-In Time");

      
        checkInTime.setMask("%Hz:%mz");

 
        checkInTime.setMin(LocalTime.of(7, 0));
        checkInTime.setMax(LocalTime.of(10, 0)); 
        checkInTime.setValue(LocalTime.of(8, 30));

       
        checkInTime.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR); 
        self.add(checkInTime);
    }
}
