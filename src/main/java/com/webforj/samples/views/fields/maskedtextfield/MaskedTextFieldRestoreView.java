package com.webforj.samples.views.fields.maskedtextfield;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.MaskedTextField;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.Route;

@Route
public class MaskedTextFieldRestoreView extends Composite<FlexLayout>{
    MaskedTextField usernameField = new MaskedTextField("Username", "WebforJUser");

    public MaskedTextFieldRestoreView() {
        FlexLayout self = getBoundComponent();
        usernameField.setRestoreValue("WebforJUser");
        self.setDirection(FlexDirection.COLUMN)
                .setJustifyContent(FlexJustifyContent.CENTER)
                .setAlignment(FlexAlignment.CENTER)
                .setSpacing("var(--dwc-space-m)")
                .setMargin("var(--dwc-space-m)");

                Button restoreButton = new Button("Reset Username", event -> {
                    usernameField.restoreValue();
                });
                restoreButton.setTheme(ButtonTheme.PRIMARY);
        
        self.add(usernameField, restoreButton);
    }
}

