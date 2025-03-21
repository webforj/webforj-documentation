package com.webforj.samples.views.fields.maskeddatefield;

import java.time.LocalDate;

import com.webforj.component.Composite;
import com.webforj.component.field.MaskedDateField;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.Route;

@Route
public class MaskedDateFieldMinMaxView extends Composite<FlexLayout> {
    MaskedDateField checkInField = new MaskedDateField("Check-in Date");

    public MaskedDateFieldMinMaxView() {
        FlexLayout self = getBoundComponent();
        self.setDirection(FlexDirection.COLUMN)
                .setJustifyContent(FlexJustifyContent.CENTER)
                .setAlignment(FlexAlignment.CENTER)
                .setSpacing("var(--dwc-space-m)")
                .setMargin("var(--dwc-space-m)");
        checkInField.setMask("%Mz/%Dz/%Yz");

        LocalDate defaultDate = LocalDate.now().plusDays(1);
        checkInField.setValue(defaultDate);

        checkInField.setMin(defaultDate);
        checkInField.setMax((LocalDate.now().plusMonths(6)));
        self.add(checkInField);
    }
}
