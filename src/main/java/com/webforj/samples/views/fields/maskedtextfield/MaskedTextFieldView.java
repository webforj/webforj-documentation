package com.webforj.samples.views.fields.maskedtextfield;

import com.webforj.component.Composite;
import com.webforj.component.field.MaskedTextField;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.Route;

@Route
public class MaskedTextFieldView extends Composite<FlexLayout> {

    MaskedTextField phoneField = new MaskedTextField("Phone Number", "", "Enter your phone number");
    MaskedTextField dobField = new MaskedTextField("Date of Birth", "", "MM/DD/YYYY");
    MaskedTextField emailField = new MaskedTextField("EMail", "", "example@gov.de");

    public MaskedTextFieldView() {
        FlexLayout self = getBoundComponent();
        phoneField.setMask("(000) 000-0000");
        dobField.setMask("00/00/0000");
        emailField.setPattern("[A-Za-z]*@[A-Za-z]*\\.[A-Za-z]*");
        self.setDirection(FlexDirection.COLUMN)
                .setJustifyContent(FlexJustifyContent.CENTER)
                .setAlignment(FlexAlignment.CENTER)
                .setSpacing("var(--dwc-space-m)")
                .setMargin("var(--dwc-space-m)");

        self.add(dobField, phoneField, emailField);
    }
}