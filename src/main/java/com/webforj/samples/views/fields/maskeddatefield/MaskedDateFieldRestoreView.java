package com.webforj.samples.views.fields.maskeddatefield;

import java.time.LocalDate;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.field.MaskedDateField;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.Route;

@Route
public class MaskedDateFieldRestoreView extends Composite<FlexLayout> {
    MaskedDateField eventField = new MaskedDateField("New Event:");

    public MaskedDateFieldRestoreView() {
        FlexLayout self = getBoundComponent();
        eventField.setRestoreValue(LocalDate.now());
        self.setDirection(FlexDirection.COLUMN)
                .setJustifyContent(FlexJustifyContent.CENTER)
                .setAlignment(FlexAlignment.CENTER)
                .setSpacing("var(--dwc-space-m)")
                .setMargin("var(--dwc-space-m)");
        eventField.setMask("%Mz/%Dz/%Yz");
        eventField.setMax(LocalDate.now());
        Button restoreButton = new Button("Reset event date", event -> {
            eventField.restoreValue();
        });
        self.add(eventField,restoreButton);
    }
}
