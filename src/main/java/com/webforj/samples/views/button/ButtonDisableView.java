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
  FlexLayout self = getBoundComponent();
  TextField email = new TextField();
  Button submit = new Button("Submit", ButtonTheme.PRIMARY);

  public ButtonDisableView() {
    self.setAlignment(FlexAlignment.END).setMargin("var(--dwc-space-l)").setSpacing("var(--dwc-space-xl)").setStyle(
        "flex-wrap",
        "wrap");
    self.add(email, submit);
    self.setWidth("100%");

    email.setType(Type.EMAIL)
        .setLabel("Enter an email")
        .onModify(e -> {
          if (e.getText().contains("@")) {
            submit.setEnabled(true);
          } else {
            submit.setEnabled(false);
          }
        });

    submit.setEnabled(false);
  }
}