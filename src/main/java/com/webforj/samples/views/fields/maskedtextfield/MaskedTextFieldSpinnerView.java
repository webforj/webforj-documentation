package com.webforj.samples.views.fields.maskedtextfield;

import java.util.List;

import com.webforj.component.Composite;
import com.webforj.component.field.MaskedTextFieldSpinner;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.Route;

@Route
public class MaskedTextFieldSpinnerView extends Composite<FlexLayout>{

    MaskedTextFieldSpinner spinner = new MaskedTextFieldSpinner();

    public MaskedTextFieldSpinnerView() {
        FlexLayout self = getBoundComponent();
        self.setDirection(FlexDirection.COLUMN)
                .setJustifyContent(FlexJustifyContent.CENTER)
                .setAlignment(FlexAlignment.CENTER)
                .setSpacing("var(--dwc-space-m)")
                .setMargin("var(--dwc-space-m)");

                spinner.setPattern("[A-Za-z]*");
                spinner.setTooltipText("Current status:");
                spinner.setOptions(List.of("Active","Busy", "Away"));

                spinner.setValue("Active");
    
        self.add(spinner);
    }
}