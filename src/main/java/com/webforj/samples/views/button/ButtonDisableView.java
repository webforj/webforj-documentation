package com.webforj.samples.views.button;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.field.TextField.Type;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Button Disable")
public class ButtonDisableView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private TextField email = new TextField();

  public ButtonDisableView() {
    Button submit = new Button("Submit", ButtonTheme.PRIMARY)
            .setEnabled(false);

    email.setType(Type.EMAIL)
            .setLabel("Enter an email")
            .onModify(e -> {
              submit.setEnabled(e.getText().contains("@"));
            });

    self.setAlignment(FlexAlignment.END)
            .setMargin("var(--dwc-space-l)")
            .setSpacing("var(--dwc-space-xl)")
            .setStyle("flex-wrap", "wrap")
            .setWidth("100%")
            .add(email, submit);
  }
}