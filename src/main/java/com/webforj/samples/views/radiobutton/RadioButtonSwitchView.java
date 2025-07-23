package com.webforj.samples.views.radiobutton;

import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Radio Button Switch")
public class RadioButtonSwitchView extends Composite<FlexLayout> {

  public RadioButtonSwitchView() {
    getBoundComponent().setDirection(FlexDirection.ROW).setSpacing("1em").setMargin("20px");

    RadioButton normalButton = new RadioButton("Normal RadioButton")
        .setExpanse(Expanse.XLARGE);
    RadioButton switchButton = RadioButton.Switch("Switch RadioButton")
        .setExpanse(Expanse.XLARGE);

    getBoundComponent().add(normalButton, switchButton);
  }
}
