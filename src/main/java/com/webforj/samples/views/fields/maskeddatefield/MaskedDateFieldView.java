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
public class MaskedDateFieldView extends Composite<FlexLayout> {
    MaskedDateField birthdayField = new MaskedDateField("Date of birth");
    MaskedDateField graduationField = new MaskedDateField("Date of graduation");

    public MaskedDateFieldView() {
        FlexLayout self = getBoundComponent();
        self.setDirection(FlexDirection.COLUMN)
                .setJustifyContent(FlexJustifyContent.START)
                .setAlignment(FlexAlignment.START)
                .setSpacing("var(--dwc-space-m)")
                .setMargin("var(--dwc-space-m)");
        birthdayField.setMask("%Mz/%Dz/%Yz");
        birthdayField.setMax(LocalDate.now());
        graduationField.setMask("%Y-%M-%D");
        graduationField.setMax(LocalDate.now());
        self.add(birthdayField,graduationField);
    }
}
